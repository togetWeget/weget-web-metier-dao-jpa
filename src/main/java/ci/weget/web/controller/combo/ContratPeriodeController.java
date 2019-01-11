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

import ci.weget.web.entites.combo.ContratPeriode;
import ci.weget.web.entites.combo.Specialite;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.combo.IComboSpecialiteMetier;
import ci.weget.web.metier.combo.IContratPeriodeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ContratPeriodeController {
	@Autowired
	private IContratPeriodeMetier contratPeriodeMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<ContratPeriode> getContratPeriodeParId(Long id) {
		ContratPeriode contratPeriode = null;
		try {
			contratPeriode = contratPeriodeMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (contratPeriode == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la specialite n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<ContratPeriode>(0, null, contratPeriode);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une specialite dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/contratPeriode")
	public String creer(@RequestBody ContratPeriode contratPeriode) throws JsonProcessingException {
		Reponse<ContratPeriode> reponse = null;

		try {

			ContratPeriode ct = contratPeriodeMetier.creer(contratPeriode);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ct.getId()));
			reponse = new Reponse<ContratPeriode>(0, messages, ct);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ContratPeriode>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/contratPeriode")
	public String modfierUneSpecialite(@RequestBody ContratPeriode modif) throws JsonProcessingException {
		Reponse<ContratPeriode> reponsePersModif = null;
		Reponse<ContratPeriode> reponse = null;

		try {

			reponsePersModif = getContratPeriodeParId(modif.getId());
			if (reponsePersModif.getBody() == null) {
				throw new RuntimeException("pas de specialite renvoye");
			} else {

				ContratPeriode ct1 = reponsePersModif.getBody();
				System.out.println("les elements a modifier" + ct1);
				ContratPeriode ct = contratPeriodeMetier.modifier(ct1);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été modifie avec succes", ct.getId()));
				reponse = new Reponse<ContratPeriode>(0, messages, ct);

			}

		} catch (InvalideTogetException e) {

			reponse = new Reponse<ContratPeriode>(1, Static.getErreursForException(e), null);
		}

		// on recupere la personne a modifier

		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer toutes les specialite
	@GetMapping("/contratPeriode")
	public String findAllSpecialite() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ContratPeriode>> reponse;
		try {
			List<ContratPeriode> p = contratPeriodeMetier.findAll();
			reponse = new Reponse<List<ContratPeriode>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<ContratPeriode>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer touts les pays
	@GetMapping("/contratPeriode/{id}")
	public String findSpecialiteParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<ContratPeriode> reponse;
		try {
			reponse = getContratPeriodeParId(id);
			ContratPeriode p = reponse.getBody();
			reponse = new Reponse<ContratPeriode>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<ContratPeriode>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer touts les pays
	@DeleteMapping("/contratPeriode/{id}")
	public String supprimerSpecialite(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Boolean> reponse;
		try {
			boolean b = contratPeriodeMetier.supprimer(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", b));
			reponse = new Reponse<Boolean>(0, messages, b);
		} catch (Exception e) {
			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
