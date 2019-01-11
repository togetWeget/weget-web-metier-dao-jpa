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

import ci.weget.web.entites.combo.Disponibilite;
import ci.weget.web.entites.combo.Fonction;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IDisponibiliteMetier;
import ci.weget.web.metier.combo.IFonctionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DisponibiliteController {
	@Autowired
	private IDisponibiliteMetier disponibiliteMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Disponibilite> getDisponibiliteParId(Long id) {
		Disponibilite disponibilite = null;
		try {
			disponibilite = disponibiliteMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (disponibilite == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Disponibilite>(0, null, disponibilite);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/disponibilite")
	public String creer(@RequestBody Disponibilite disponibilite) throws JsonProcessingException {
		Reponse<Disponibilite> reponse = null;

		try {

			Disponibilite cType = disponibiliteMetier.creer(disponibilite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cType.getId()));
			reponse = new Reponse<Disponibilite>(0, messages, cType);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Disponibilite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/disponibilite")
	public String modfierUneDisponibilite(@RequestBody Disponibilite modif) throws JsonProcessingException {
		Reponse<Disponibilite> reponsePersModif = null;
		Reponse<Disponibilite> reponse = null;

		try {

			reponsePersModif = getDisponibiliteParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				Disponibilite ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				Disponibilite ct = disponibiliteMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<Disponibilite>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Disponibilite>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/disponibilite")
	public String findAllDisponibilite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Disponibilite>> reponse;
		try {
			List<Disponibilite> p = disponibiliteMetier.findAll();
			reponse = new Reponse<List<Disponibilite>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Disponibilite>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/disponibilite/{id}")
	public String findDisponibiliteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Disponibilite> reponse;
		try {
			reponse = getDisponibiliteParId(id);
			Disponibilite p = reponse.getBody();
			reponse = new Reponse<Disponibilite>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Disponibilite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/disponibilite/{id}")
	public String supprimerDisponibilite(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = disponibiliteMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
