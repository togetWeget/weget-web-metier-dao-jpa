package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.PostAjoutTarifBlock;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TarifController {
	
	@Autowired
	private ITarifMetier tarifMetier;
	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	private Reponse<Block> getBlockById(Long id) {
		Block block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (block == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Block>(0, null, block);
	}

	private Reponse<Tarif> getTarifById(Long id) {
		Tarif tarif = null;
		try {
			tarif = tarifMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (tarif == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le tarif n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Tarif>(0, null, tarif);
	}

	
	@PostMapping("/tarifs")
	public String creer(@RequestBody PostAjoutTarifBlock post) throws JsonProcessingException {
		Reponse<Tarif> reponseTarif;
		Reponse<Block> reponseBlock;
		long idBlock = post.getIdBlock();
		long idTarif = post.getIdTarif();

		// on récupère le block reponse block
		 reponseBlock = getBlockById(idBlock);
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();
		// on récupère la personne
		reponseTarif = getTarifById(idTarif);

		Tarif tarif = (Tarif) reponseTarif.getBody();
		try {

			Tarif t = tarifMetier.ajouterBlock(tarif, block);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t.getPrix()));
			reponseTarif = new Reponse<Tarif>(0, messages, t);

		} catch (Exception e) {

			reponseTarif = new Reponse<Tarif>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponseTarif);
	}
	@GetMapping("/tarifsBlocksId/{id}")
	public String obtenirTariParIdBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Tarif>> reponse;
		try {
			List<Tarif> tarifs = tarifMetier.getTarifParBlockId(id);
			reponse = new Reponse<List<Tarif>>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
