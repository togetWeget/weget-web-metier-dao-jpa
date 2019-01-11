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

import ci.weget.web.entites.combo.ContratType;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IContratTypeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ContratTypeController {
	@Autowired
	private IContratTypeMetier contratTypeMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<ContratType> getContratTypeParId(Long id) {
		ContratType contratType = null;
		try {
			contratType = contratTypeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (contratType == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<ContratType>(0, null, contratType);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/contratType")
	public String creer(@RequestBody ContratType contratType) throws JsonProcessingException {
		Reponse<ContratType> reponse = null;

		try {

			ContratType cType = contratTypeMetier.creer(contratType);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<ContratType>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ContratType>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/contratType")
	public String modfierUneSpecialite(@RequestBody ContratType modif) throws JsonProcessingException {
		Reponse<ContratType> reponsePersModif = null;
		Reponse<ContratType> reponse = null;

		try {

			reponsePersModif = getContratTypeParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				ContratType ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				ContratType ct = contratTypeMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<ContratType>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ContratType>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/contratType")
	public String findAllSpecialite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ContratType>> reponse;
		try {
			List<ContratType> p = contratTypeMetier.findAll();
			reponse = new Reponse<List<ContratType>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<ContratType>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/contratType/{id}")
	public String findSpecialiteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<ContratType> reponse;
		try {
			reponse = getContratTypeParId(id);
			ContratType p = reponse.getBody();
			reponse = new Reponse<ContratType>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<ContratType>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/contratType/{id}")
	public String supprimerSpecialite(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = contratTypeMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
