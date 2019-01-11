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

import ci.weget.web.entites.combo.ComboExperience;
import ci.weget.web.entites.combo.Fonction;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboExperienceMetier;
import ci.weget.web.metier.combo.IFonctionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComoExperienceController {
	@Autowired
	private IComboExperienceMetier comboExperienceMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<ComboExperience> getComboExperienceParId(Long id) {
		ComboExperience comboExperience = null;
		try {
			comboExperience = comboExperienceMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (comboExperience == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<ComboExperience>(0, null, comboExperience);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/comboExperience")
	public String creer(@RequestBody ComboExperience comboExperience) throws JsonProcessingException {
		Reponse<ComboExperience> reponse = null;

		try {

			ComboExperience cType = comboExperienceMetier.creer(comboExperience);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<ComboExperience>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboExperience>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/comboExperience")
	public String modfierUneComboExperience(@RequestBody ComboExperience modif) throws JsonProcessingException {
		Reponse<ComboExperience> reponsePersModif = null;
		Reponse<ComboExperience> reponse = null;

		try {

			reponsePersModif = getComboExperienceParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				ComboExperience ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				ComboExperience ct = comboExperienceMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<ComboExperience>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboExperience>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/comboExperience")
	public String findAllComboExperience() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ComboExperience>> reponse;
		try {
			List<ComboExperience> p = comboExperienceMetier.findAll();
			reponse = new Reponse<List<ComboExperience>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<ComboExperience>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/comboExperience/{id}")
	public String findComboExperienceParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<ComboExperience> reponse;
		try {
			reponse = getComboExperienceParId(id);
			ComboExperience p = reponse.getBody();
			reponse = new Reponse<ComboExperience>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<ComboExperience>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/comboExperience/{id}")
	public String supprimerComboExperience(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = comboExperienceMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
