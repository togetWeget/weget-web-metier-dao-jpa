package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IDetailBlocksMetier;
import ci.weget.web.modeles.PostAjoutDetailBlock;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DetailBlockContrller {
	@Autowired
	private IDetailBlocksMetier detailBlocksMetier;
	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private IAdminMetier adminMetier;
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

	// ajouter un block a un abonne block
	@RequestMapping(value = "/ajouterDb")
	public String ajouterDb(@RequestBody PostAjoutDetailBlock post) throws JsonProcessingException {
		// on récupère les valeurs postées
		Reponse<DetailBlock> reponse = null;
		long idBlock = post.getIdBlock();
		long idPersonne = post.getIdPersonne();

		// on récupère le block
		Reponse<Block> reponseBlock = getBlock(idBlock);
		if (reponseBlock.getStatut() != 0) {
			return reponseBlock.getBody().getLibelle();
		}
		Block block = (Block) reponseBlock.getBody();
		// on récupère la personne

		Reponse<Personne> reponsePersonne = getPersonneById(idPersonne);
		if (reponsePersonne.getStatut() != 0) {
			// reponse.incrStatusBy(2);
			return reponsePersonne.getBody().getNomComplet();
		}
		Personne personne = (Personne) reponsePersonne.getBody();
		
	   //on verifie si la personne a un statut abonne
		if (personne.getTypestatut().getLibelle().equals("abonne")) {
			// on ajoute la personne au block
			DetailBlock db = null;
			try {
				db = detailBlocksMetier.ajoutdetailBlocks(personne, block);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s %s  à été créer avec succes", db.getBlock().getLibelle(),
						db.getPersonne().getNomComplet()));
				reponse = new Reponse<DetailBlock>(0, messages, db);
			} catch (Exception e1) {

				reponse = new Reponse<DetailBlock>(1, Static.getErreursForException(e1), null);

			}
		}
		// on rend la réponse
		return jsonMapper.writeValueAsString(reponse);

	}
}
