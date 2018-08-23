package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.IPanierMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.AjoutPanier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class PanierController {
	@Autowired
	private IPanierMetier panierMetier;
	@Autowired
	private ITarifMetier tarifMetier;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	private Reponse<Personne> getMembreById(final Long id) {
		Personne personne = null;
		try {
			personne = membreMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	private Reponse<Personne> getMembreByLogin(final String login) {
		Personne personne = null;
		try {
			personne = membreMetier.findByLogin(login);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	// recuprer le block a partir de son identifiant
	private Reponse<Block> getBlock(final Long id) {
		// on récupère le block
		Block block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Block>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Block>(2, messages, null);
		}
		// ok
		return new Reponse<Block>(0, null, block);
	}

	// recuprer le Tarif a partir de son identifiant
	private Reponse<Tarif> getTarif(final Long id) {
		// on récupère le tarif
		Tarif tarif = null;
		try {
			tarif = tarifMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Tarif>(1, Static.getErreursForException(e1), null);
		}
		// tarif existant ?
		if (tarif == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Tarif>(2, messages, null);
		}
		// ok
		return new Reponse<Tarif>(0, null, tarif);
	}

	@PostMapping("/panier")
	public String creer(@RequestBody AjoutPanier post) throws JsonProcessingException {

		Reponse<Boolean> reponse ;

		
		long idTarif = post.getIdTarif();
		long idMembre = post.getIdMembre();
		// on récupère le tarif reponse tarif
		Reponse<Tarif> reponseTarif = getTarif(idTarif);
		// on recupere le tarif
		Tarif tarif = (Tarif) reponseTarif.getBody();
		// on récupère le block reponse block
		Reponse<Block> reponseBlock = getBlock(tarif.getBlock().getId());
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();

		// on récupère la personne reponse personne
		Reponse<Personne> reponsePersonne = getMembreById(idMembre);
		// on recupere le block
		Personne personne = (Personne) reponsePersonne.getBody();
		Double quantite= post.getQuantite();
		Double total = tarif.getPrix()*post.getQuantite();

		try {
			boolean boo = panierMetier.ajoutLignePanier(tarif, block, personne,quantite,total);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", true));
			reponse = new Reponse<Boolean>(0, messages, true);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
