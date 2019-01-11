package ci.weget.web.controller.combo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.combo.Diplome;
import ci.weget.web.entites.combo.Pays;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboDiplomeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComboDiplomeController {

	@Autowired
	private IComboDiplomeMetier comboDiplomeMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<Diplome> getDiplomeParId(Long id) {
		Diplome diplome = null;
		try {
			diplome = comboDiplomeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (diplome == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le pays n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Diplome>(0, null, diplome);
	}

	@PostMapping("/diplome")
	public String creer(@RequestBody Diplome diplome) throws JsonProcessingException {
		Reponse<Diplome> reponse = null;

		try {

			Diplome p = comboDiplomeMetier.creer(diplome);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", p.getId()));
			reponse = new Reponse<Diplome>(0, messages, p);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Diplome>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/diplome")
	public String modfierUnPays(@RequestBody Diplome modif) throws JsonProcessingException {
		Reponse<Diplome> reponsePersModif = null;
		Reponse<Diplome> reponse = null;

		try {

			reponsePersModif = getDiplomeParId(modif.getId());
			if (reponsePersModif == null) {
				throw new RuntimeException("pas de pays renvoye");
			}
			Diplome p1 = reponsePersModif.getBody();
			System.out.println("les elements a modifier" + p1);
			Diplome p = comboDiplomeMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
			reponse = new Reponse<Diplome>(0, messages, p);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Diplome>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer le pays par id
	@GetMapping("/diplome/{id}")
	public String findDiplomeParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Diplome> reponse;
		try {
			reponse = getDiplomeParId(id);
			Diplome p = reponse.getBody();
			reponse = new Reponse<Diplome>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Diplome>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les diplome
	@GetMapping("/diplome")
	public String findAllPays() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Diplome>> reponse;
		try {
			List<Diplome> p = comboDiplomeMetier.findAll();
			reponse = new Reponse<List<Diplome>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Diplome>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// supprimer touts les diplomes par id
		@DeleteMapping("/diplome/{id}")
		public String supprimer(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
			Reponse<Boolean> reponse;
			try {
				boolean p = comboDiplomeMetier.supprimer(id);
				reponse = new Reponse<Boolean>(0, null, p);
			} catch (Exception e) {
				reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
}
