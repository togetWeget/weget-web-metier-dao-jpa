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
import ci.weget.web.entites.combo.NiveauEtude;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IFonctionMetier;
import ci.weget.web.metier.combo.INiveauEtudeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class NiveauEtudeController {
	@Autowired
	private INiveauEtudeMetier niveauEtudeMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<NiveauEtude> getNiveauEtudeParId(Long id) {
		NiveauEtude niveauEtude = null;
		try {
			niveauEtude = niveauEtudeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (niveauEtude == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<NiveauEtude>(0, null, niveauEtude);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/niveauEtude")
	public String creer(@RequestBody NiveauEtude niveauEtude) throws JsonProcessingException {
		Reponse<NiveauEtude> reponse = null;

		try {

			NiveauEtude cType = niveauEtudeMetier.creer(niveauEtude);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<NiveauEtude>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<NiveauEtude>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/niveauEtude")
	public String modfierUnNiveauEtude(@RequestBody NiveauEtude modif) throws JsonProcessingException {
		Reponse<NiveauEtude> reponsePersModif = null;
		Reponse<NiveauEtude> reponse = null;

		try {

			reponsePersModif = getNiveauEtudeParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				NiveauEtude ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				NiveauEtude ct = niveauEtudeMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<NiveauEtude>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<NiveauEtude>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/niveauEtude")
	public String findAllNiveauEtude() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<NiveauEtude>> reponse;
		try {
			List<NiveauEtude> p = niveauEtudeMetier.findAll();
			reponse = new Reponse<List<NiveauEtude>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<NiveauEtude>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/niveauEtude/{id}")
	public String findNiveauEtudeParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<NiveauEtude> reponse;
		try {
			reponse = getNiveauEtudeParId(id);
			NiveauEtude p = reponse.getBody();
			reponse = new Reponse<NiveauEtude>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<NiveauEtude>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/niveauEtude/{id}")
	public String supprimerDomaine(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = niveauEtudeMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
