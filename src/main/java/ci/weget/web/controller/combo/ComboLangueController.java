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

import ci.weget.web.entites.combo.Langue;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.ILangueMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComboLangueController {
	@Autowired
	private ILangueMetier langueMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Langue> getLangueParId(Long id) {
		Langue langue = null;
		try {
			langue = langueMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (langue == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Langue>(0, null, langue);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/langue")
	public String creer(@RequestBody Langue langue) throws JsonProcessingException {
		Reponse<Langue> reponse = null;

		try {

			Langue l = langueMetier.creer(langue);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", l.getId()));
			reponse = new Reponse<Langue>(0, messages, l);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Langue>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/langue")
	public String modfierUneLangue(@RequestBody Langue modif) throws JsonProcessingException {
		Reponse<Langue> reponsePersModif = null;
		Reponse<Langue> reponse = null;

		try {

			reponsePersModif = getLangueParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				Langue l1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + l1);
				Langue l = langueMetier.modifier(l1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", l.getId()));
				reponse = new Reponse<Langue>(0, messages, l);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Langue>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/langue")
	public String findAllSpecialite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Langue>> reponse;
		try {
			List<Langue> p = langueMetier.findAll();
			reponse = new Reponse<List<Langue>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Langue>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/langue/{id}")
	public String findSpecialiteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Langue> reponse;
		try {
			reponse = getLangueParId(id);
			Langue l = reponse.getBody();
			reponse = new Reponse<Langue>(0, null, l);
		} catch (Exception e) {
			reponse = new Reponse<Langue>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/langue/{id}")
	public String supprimerSpecialite(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = langueMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
