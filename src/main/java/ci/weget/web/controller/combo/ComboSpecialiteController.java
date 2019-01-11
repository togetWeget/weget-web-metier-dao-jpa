package ci.weget.web.controller.combo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import ci.weget.web.entites.combo.Pays;
import ci.weget.web.entites.combo.Quartier;
import ci.weget.web.entites.combo.Specialite;
import ci.weget.web.entites.combo.Ville;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboPaysMetier;
import ci.weget.web.metier.combo.IComboQuartierMetier;
import ci.weget.web.metier.combo.IComboSpecialiteMetier;
import ci.weget.web.metier.combo.IComboVilleMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComboSpecialiteController {
	@Autowired
	private IComboSpecialiteMetier comboSpecialiteMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Specialite> getSpecialitParId(Long id) {
		Specialite specialite = null;
		try {
			specialite = comboSpecialiteMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (specialite == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Specialite>(0, null, specialite);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/specialite")
	public String creer(@RequestBody Specialite specialites) throws JsonProcessingException {
		Reponse<Specialite> reponse = null;

		try {

			Specialite sp = comboSpecialiteMetier.creer(specialites);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", sp.getLibelle()));
			reponse = new Reponse<Specialite>(0, messages, sp);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Specialite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/specialite")
	public String modfierUneSpecialite(@RequestBody Specialite modif) throws JsonProcessingException {
		Reponse<Specialite> reponsePersModif = null;
		Reponse<Specialite> reponse = null;

		try {

			reponsePersModif = getSpecialitParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				Specialite sp1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + sp1);
				Specialite sp = comboSpecialiteMetier.modifier(sp1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", sp.getLibelle()));
				reponse = new Reponse<Specialite>(0, messages, sp);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Specialite>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/specialite")
	public String findAllSpecialite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Specialite>> reponse;
		try {
			List<Specialite> p = comboSpecialiteMetier.findAll();
			reponse = new Reponse<List<Specialite>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Specialite>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/specialite/{id}")
	public String findSpecialiteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Specialite> reponse;
		try {
			reponse = getSpecialitParId(id);
			Specialite p = reponse.getBody();
			reponse = new Reponse<Specialite>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<Specialite>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/specialite/{id}")
	public String supprimerSpecialite(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = comboSpecialiteMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
