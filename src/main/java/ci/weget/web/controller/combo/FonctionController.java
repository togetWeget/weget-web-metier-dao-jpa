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

import ci.weget.web.entites.combo.Fonction;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IFonctionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class FonctionController {
	@Autowired
	private IFonctionMetier fonctionMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Fonction> getFonctionParId(Long id) {
		Fonction fonction = null;
		try {
			fonction = fonctionMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (fonction == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Fonction>(0, null, fonction);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/fonction")
	public String creer(@RequestBody Fonction fonction) throws JsonProcessingException {
		Reponse<Fonction> reponse = null;

		try {

			Fonction cType = fonctionMetier.creer(fonction);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<Fonction>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Fonction>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/fonction")
	public String modfierUneFonction(@RequestBody Fonction modif) throws JsonProcessingException {
		Reponse<Fonction> reponsePersModif = null;
		Reponse<Fonction> reponse = null;

		try {

			reponsePersModif = getFonctionParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				Fonction ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				Fonction ct = fonctionMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<Fonction>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Fonction>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/fonction")
	public String findAllFonction() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Fonction>> reponse;
		try {
			List<Fonction> p = fonctionMetier.findAll();
			reponse = new Reponse<List<Fonction>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Fonction>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/fonction/{id}")
	public String findFonctionParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Fonction> reponse;
		try {
			reponse = getFonctionParId(id);
			Fonction p = reponse.getBody();
			reponse = new Reponse<Fonction>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Fonction>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/fonction/{id}")
	public String supprimerDomaine(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = fonctionMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
