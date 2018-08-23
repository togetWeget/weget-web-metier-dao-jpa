package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IDetailBlocksMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.PostAjoutDetailBlock;
import ci.weget.web.modeles.PostModifAbonne;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DetailBlockContrller {
	@Autowired
	private IDetailBlocksMetier detailBlocksMetier;
	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private IAdminMetier adminMetier;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuprer le block a partir de son identifiant
	private Reponse<Block> getBlock(long id) {
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

	// recuperer la personne a partir de son identifiant
	private Reponse<Personne> getPersonneById(final Long id) {
		Personne personne = null;
		try {
			personne = adminMetier.findById(id);
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

	private Reponse<DetailBlock> getdetailBlock(long id) {
		// on récupère le block
		DetailBlock detailBlock = null;
		try {
			detailBlock = detailBlocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<DetailBlock>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (detailBlock == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<DetailBlock>(2, messages, null);
		}
		// ok
		return new Reponse<DetailBlock>(0, null, detailBlock);
	}

	//////////////// creer un abonne
	// creer un abonne revient a dire quún membre a paye le bloc donc son statut
	// est abonne
	@PostMapping("/abonnes")
	public String creerAbonne(@RequestBody PostAjoutDetailBlock post) throws JsonProcessingException {
		Reponse<Boolean> reponse;
		long idBlock = post.getIdBlock();
		long idPersonne = post.getIdPersonne();

		// on récupère le block reponse block
		Reponse<Block> reponseBlock = getBlock(idBlock);
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();
		// on récupère la personne
		Reponse<Personne> reponsePersonne = getMembreById(idPersonne);

		Personne personne = (Personne) reponsePersonne.getBody();
		try {
			boolean boo = detailBlocksMetier.creerAbonne(personne.getLogin(), block.getLibelle());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("detail block à été créer avec succes"));
			reponse = new Reponse<Boolean>(0, messages, boo);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// faire la mise a jour du profil d'un abonne
	@PutMapping("/misAjourProfil")
	public String modifier(@RequestBody Personne modif) throws JsonProcessingException {
		Reponse<Personne> reponse;

		// on récupère la personne
		Reponse<Personne> reponsePersonne = getMembreById(modif.getId());

		Personne personne = (Personne) reponsePersonne.getBody();
		// on verifie si la personne existe dans detail block ou si il est abonne
		List<DetailBlock> db = detailBlocksMetier.detailBlocksPersonneParId(personne.getId());
		if (db.isEmpty()) {
			throw new RuntimeException("cette personne n'est pas abonne");
		} else {
			try {
				Personne p1 = membreMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s personne modifier avec succes", p1.getNomComplet()));
				reponse = new Reponse<Personne>(0, messages, p1);

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/profilAbonneId/{id}")
	public String profilAbonneId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<DetailBlock> reponse = null;

		reponse = getdetailBlock(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/profilAbonneLogin/{login}")
	public String profilAbonneLogin(@PathVariable String login) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<DetailBlock>> reponse = null;

		try {
			List<DetailBlock> db = detailBlocksMetier.detailBlocksPersonneParLogin(login);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<DetailBlock>>(0, messages, db);
		} catch (Exception e) {

			reponse = new Reponse<List<DetailBlock>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir tous les abonnes
	@GetMapping("/abonnes")
	public String findAllAbonne() throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;
		try {
			List<DetailBlock> pers = detailBlocksMetier.tousLesDetailBlock();
			reponse = new Reponse<List<DetailBlock>>(0, null, pers);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir la liste des memmbres
	@GetMapping("/tousLesAbonnesParBlock/{id}")
	public String findAllTypePersonne(@PathVariable("id") Long id) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;

		try {
			List<DetailBlock> personneTous = detailBlocksMetier.personneALLBlock(id);
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<DetailBlock>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<DetailBlock>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// obtenir des blocks d'un abonne
	@GetMapping("/tousLesBlockParAbonne/{id}")
	public String findAllBlockParPersonne(@PathVariable("id") Long id) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;

		try {
			List<DetailBlock> blocks = detailBlocksMetier.detailBlocksPersonneParId(id);

			List<String> messages = new ArrayList<>();
			messages.add("les blocks de la personne");
			reponse = new Reponse<List<DetailBlock>>(1, messages, blocks);

		} catch (Exception e) {

			reponse = new Reponse<List<DetailBlock>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les Abonnes par competence
	// tous les membres qui ont leur statut a abonne et ont paye le block
	@GetMapping("/abonnes/{competence}")
	public String chercherPersonneParCompetence(@PathVariable String competence) throws JsonProcessingException {
		/*
		 * Reponse<List<Personne>> reponse; try { List<Personne> db =
		 * detailBlocksMetier.ajoutdetailBlocks(competence); reponse = new
		 * Reponse<List<Personne>>(0, null, db); } catch (Exception e) { reponse = new
		 * Reponse<>(1, Static.getErreursForException(e), null); }
		 */
		return null;

	}

}
