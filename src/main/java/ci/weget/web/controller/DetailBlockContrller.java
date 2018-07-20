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

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Personnes;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IDetailBlocksMetier;
import ci.weget.web.metier.IPersonneMetier;
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
	private IPersonneMetier personneMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	// recuprer le block a partir de son identifiant
	private Reponse<Blocks> getBlock(long id) {
		// on récupère le block
		Blocks block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1),null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse(2, messages,null);
		}
		// ok
		return new Reponse(0,null, block);
	}
	// recuperer la personne a partir de son identifiant
	private Reponse<Personnes> getPersonneById(final Long id) {
		Personnes personne = null;
		try {
			personne = personneMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personnes>(2, messages, null);
		}
		return new Reponse<Personnes>(0, null, personne);

	}
	// ajouter un detail block 
	@RequestMapping(value = "/ajouterDb")
	public String ajouterDb(@RequestBody PostAjoutDetailBlock post) throws JsonProcessingException {
		// on récupère les valeurs postées
		Reponse<DetailBlocks> reponse = null;
		long idBlock = post.getIdBlock();
		long idPersonne = post.getIdPersonne();
		
		// on récupère le block
		Reponse<Blocks> reponseBlock = getBlock(idBlock);
		if (reponseBlock.getStatut() != 0) {
			return reponseBlock.getBody().getLibelle();
		}
		Blocks block  = (Blocks) reponseBlock.getBody();
		// on récupère la personne
		
		Reponse<Personnes> reponsePersonne = getPersonneById(idPersonne);
		if (reponsePersonne.getStatut()!= 0) {
			//reponse.incrStatusBy(2);
			return reponsePersonne.getBody().getNomComplet();
		}
		Personnes personne = (Personnes) reponsePersonne.getBody();
		// on ajoute le Rv
		DetailBlocks db = null;
		try {
			db = detailBlocksMetier.ajoutdetailBlocks(block, personne);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s  à été créer avec succes", db.getBlocks().getLibelle(), db.getPersonne().getNomComplet()));
			reponse = new Reponse<DetailBlocks>(0, messages, db);
		} catch (Exception e1) {
			
        reponse = new Reponse<DetailBlocks>(1, Static.getErreursForException(e1), null);

		}
		// on rend la réponse
		
		return jsonMapper.writeValueAsString(reponse);
	
	}
}
