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
import ci.weget.web.entites.combo.Domaine;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IContratTypeMetier;
import ci.weget.web.metier.combo.IDomaineMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DomaineController {
	@Autowired
	private IDomaineMetier domaineMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Domaine> getDomaineParId(Long id) {
		Domaine domaine = null;
		try {
			domaine = domaineMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (domaine == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Domaine>(0, null, domaine);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/domaine")
	public String creer(@RequestBody Domaine domaine) throws JsonProcessingException {
		Reponse<Domaine> reponse = null;

		try {

			Domaine cType = domaineMetier.creer(domaine);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<Domaine>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Domaine>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/domaine")
	public String modfierUneDomaine(@RequestBody Domaine modif) throws JsonProcessingException {
		Reponse<Domaine> reponsePersModif = null;
		Reponse<Domaine> reponse = null;

		try {

			reponsePersModif = getDomaineParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				Domaine ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				Domaine ct = domaineMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<Domaine>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Domaine>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/domaine")
	public String findAllDomaine() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Domaine>> reponse;
		try {
			List<Domaine> p = domaineMetier.findAll();
			reponse = new Reponse<List<Domaine>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Domaine>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/domaine/{id}")
	public String findDomaineParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Domaine> reponse;
		try {
			reponse = getDomaineParId(id);
			Domaine p = reponse.getBody();
			reponse = new Reponse<Domaine>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Domaine>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/domaine/{id}")
	public String supprimerDomaine(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = domaineMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
