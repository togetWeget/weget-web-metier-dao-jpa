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

import ci.weget.web.entites.combo.EmploieType;
import ci.weget.web.entites.combo.Fonction;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IEmploieTypeMetier;
import ci.weget.web.metier.combo.IFonctionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class EmploieTypeController {
	@Autowired
	private IEmploieTypeMetier emploieTypeMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<EmploieType> getEmploieTypeParId(Long id) {
		EmploieType emploieType = null;
		try {
			emploieType = emploieTypeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (emploieType == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<EmploieType>(0, null, emploieType);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/emploieType")
	public String creer(@RequestBody EmploieType emploieType) throws JsonProcessingException {
		Reponse<EmploieType> reponse = null;

		try {

			EmploieType cType = emploieTypeMetier.creer(emploieType);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<EmploieType>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<EmploieType>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/emploieType")
	public String modfierUnEmploieType(@RequestBody EmploieType modif) throws JsonProcessingException {
		Reponse<EmploieType> reponsePersModif = null;
		Reponse<EmploieType> reponse = null;

		try {

			reponsePersModif = getEmploieTypeParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				EmploieType ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				EmploieType ct = emploieTypeMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<EmploieType>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<EmploieType>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/emploieType")
	public String findAllEmploieType() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<EmploieType>> reponse;
		try {
			List<EmploieType> p = emploieTypeMetier.findAll();
			reponse = new Reponse<List<EmploieType>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<EmploieType>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/emploieType/{id}")
	public String findEmploieTypeParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<EmploieType> reponse;
		try {
			reponse = getEmploieTypeParId(id);
			EmploieType p = reponse.getBody();
			reponse = new Reponse<EmploieType>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<EmploieType>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/emploieType/{id}")
	public String supprimerEmploieType(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = emploieTypeMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
