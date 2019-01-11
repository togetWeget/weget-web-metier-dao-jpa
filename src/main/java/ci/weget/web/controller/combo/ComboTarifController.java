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

import ci.weget.web.entites.combo.ComboTarif;
import ci.weget.web.entites.combo.ContratPeriode;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboTarifMetier;
import ci.weget.web.metier.combo.IContratPeriodeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ComboTarifController {
	@Autowired
	private IComboTarifMetier comboTarifMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<ComboTarif> getComboTarifParId(Long id) {
		ComboTarif comboTarif = null;
		try {
			comboTarif = comboTarifMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (comboTarif == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<ComboTarif>(0, null, comboTarif);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/comboTarif")
	public String creer(@RequestBody ComboTarif comboTarif) throws JsonProcessingException {
		Reponse<ComboTarif> reponse = null;

		try {

			ComboTarif ct = comboTarifMetier.creer(comboTarif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ct.getId()));
			reponse = new Reponse<ComboTarif>(0, messages, ct);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboTarif>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/comboTarif")
	public String modfierUneComboTarif(@RequestBody ComboTarif modif) throws JsonProcessingException {
		Reponse<ComboTarif> reponsePersModif = null;
		Reponse<ComboTarif> reponse = null;

		try {

			reponsePersModif = getComboTarifParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				ComboTarif ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				ComboTarif ct = comboTarifMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<ComboTarif>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ComboTarif>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/comboTarif")
	public String findAllComboTarif() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ComboTarif>> reponse;
		try {
			List<ComboTarif> p = comboTarifMetier.findAll();
			reponse = new Reponse<List<ComboTarif>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<ComboTarif>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer combotarif par id
	@GetMapping("/comboTarif/{id}")
	public String findComboTarifParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<ComboTarif> reponse;
		try {
			reponse = getComboTarifParId(id);
			ComboTarif p = reponse.getBody();
			reponse = new Reponse<ComboTarif>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<ComboTarif>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/comboTarif/{id}")
	public String supprimerComboTarif(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = comboTarifMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
