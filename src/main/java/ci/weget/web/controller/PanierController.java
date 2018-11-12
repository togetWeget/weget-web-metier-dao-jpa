package ci.weget.web.controller;

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

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Tarif;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.IPanierMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.AjoutPanier;
import ci.weget.web.modeles.ModifPanier;
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

	private Reponse<Panier> getPanierById(final Long id) {
		Panier panier = null;
		try {
			panier = panierMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Panier>(1, Static.getErreursForException(e), null);
		}
		if (panier == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le panier n'exste pas", id));
			return new Reponse<Panier>(2, messages, null);
		}
		return new Reponse<Panier>(0, null, panier);

	}

	private Reponse<Personne> getMembreById(final long id) {
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
	private Reponse<Block> getBlock(final long id) {
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
	private Reponse<Tarif> getTarif(final long id) {
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
			messages.add(String.format("Le tarif n'exste pas", id));
			return new Reponse<Tarif>(2, messages, null);
		}
		// ok
		return new Reponse<Tarif>(0, null, tarif);
	}

	@PostMapping("/panier")
	public String creer(@RequestBody AjoutPanier post) throws JsonProcessingException {

		Reponse<Boolean> reponse;

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
		Double quantite = post.getQuantite();
		Double total = tarif.getPrix() * post.getQuantite();

		try {
			boolean boo = panierMetier.ajoutLignePanier(tarif, block, personne, quantite, total);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("element ajouter au panier avec succes", true));
			reponse = new Reponse<Boolean>(0, messages, true);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// modif d'un panier dans la base de donnee
	@PutMapping("/panier")
	public String modfierUnPanier(@RequestBody ModifPanier modif) throws JsonProcessingException {
		Reponse<Boolean> reponse;
		Reponse<Panier> reponsePanier;

		long idTarif = modif.getIdTarif();
		long idMembre = modif.getIdMembre();
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
		Double quantite = modif.getQuantite();
		Double total = tarif.getPrix() * modif.getQuantite();

		try {
			reponsePanier=	getPanierById(modif.getId());
			Panier pa=reponsePanier.getBody();
			Long id=pa.getId();
			Long version = pa.getVersion();
			pa.getVersion();
			boolean boo = panierMetier.modifLignePanier(id,version,tarif, block, personne, quantite, total);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("element modifier avec succes", true));
			reponse = new Reponse<Boolean>(0, messages, true);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer toutes les paniers de la base de donnee
	@GetMapping("/panier")
	public String findAllPanier() throws JsonProcessingException {
		Reponse<List<Panier>> reponse;
		try {
			List<Panier> paniers = panierMetier.findAll();
			reponse = new Reponse<List<Panier>>(0, null, paniers);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// renvoie les  paniers d'une personne par son identifiant
		@GetMapping("/panierParPersonne/{idPersonne}")
		public String panierPanierParPrersonneId(@PathVariable("idPersonne") long idPersonne) throws JsonProcessingException {
			Reponse<List<Panier>> reponse;
			try {
				List<Panier> paniers = panierMetier.LesPanierDeLaPersonne(idPersonne);
				reponse = new Reponse<List<Panier>>(0, null, paniers);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
	// renvoie un panier par son identifiant
	@GetMapping("/panier/{id}")
	public String chercherPanierParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Panier> reponse = null;
		reponse = getPanierById(id);
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/panier")
	public String supprimerAll(@RequestBody Panier panier) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;
		List<Panier> paniers = null;

		try {
			boolean boo = panierMetier.supprimer(paniers);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("pas de matiere enregistrer "));
			reponse = new Reponse<Boolean>(2, messages, true);

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/panier/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, panierMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
}
