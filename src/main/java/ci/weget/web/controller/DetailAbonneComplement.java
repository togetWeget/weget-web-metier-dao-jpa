package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Chiffre;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Partenaire;
import ci.weget.web.entites.Temoignage;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IChiffreMetier;
import ci.weget.web.metier.IPanierMetier;
import ci.weget.web.metier.IPartenaireMetier;
import ci.weget.web.metier.ISousBlockMetier;
import ci.weget.web.metier.ITemoignageMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DetailAbonneComplement {
	@Autowired
	private IPartenaireMetier partenaireMetier;
	@Autowired
	private IChiffreMetier chiffreMetier;
	@Autowired
	private ITemoignageMetier temoignageMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	/////////////////////////////////////////////////////////////////////////////
	////////////// recuperer un partenaire a partir de sonidentifiant ///////////
	private Reponse<Partenaire> getPaternaireById(Long id) {
		Partenaire partenaire = null;
		try {
			partenaire = partenaireMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (partenaire == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le partenaire n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Partenaire>(0, null, partenaire);
	}

	/////////////////////////////////////////////////////////////////////////////
	////////////// recuperer un chiffre a partir de sonidentifiant ///////////
	private Reponse<Chiffre> getChiffreById(Long id) {
		Chiffre chiffre = null;
		try {
			chiffre = chiffreMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (chiffre == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le chiffre n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Chiffre>(0, null, chiffre);
	}

	/////////////////////////////////////////////////////////////////////////////
	////////////// recuperer un temoignage a partir de sonidentifiant ///////////
	private Reponse<Temoignage> getTremoignageById(Long id) {
		Temoignage temoignage = null;
		try {
			temoignage = temoignageMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (temoignage == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le chiffre n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Temoignage>(0, null, temoignage);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un partenaire dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/partenaire")
	public String creer(@RequestBody Partenaire part) throws JsonProcessingException {
		Reponse<Partenaire> reponse;

		try {

			Partenaire p1 = partenaireMetier.creer(part);
			
			
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", p1.getId()));
			reponse = new Reponse<Partenaire>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Partenaire>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	////////////////// enregistrer un chiffre dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/chiffre")
	public String creer(@RequestBody Chiffre chiffre) throws JsonProcessingException {
		Reponse<Chiffre> reponse;

		try {

			Chiffre c1 = chiffreMetier.creer(chiffre);
			
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s id du sous block à été créer avec succes", c1.getId()));
			reponse = new Reponse<Chiffre>(0, messages, c1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Chiffre>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	////////////////// enregistrer un temoignage dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/temoignage")
	public String creer(@RequestBody Temoignage temoignage) throws JsonProcessingException {
		Reponse<Temoignage> reponse;

		try {

			Temoignage t1 = temoignageMetier.creer(temoignage);
			
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Temoignage>(0, messages, t1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Temoignage>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un partenaire dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/partenaire")
	public String modfierUnPartenaire(@RequestBody Partenaire modif) throws JsonProcessingException {
		Reponse<Partenaire> reponsePersModif = null;
		Reponse<Partenaire> reponse = null;

		// on recupere la personne a modifier reponsePersModif =
		reponsePersModif = getPaternaireById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Partenaire p2 = partenaireMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", p2.getId()));
				reponse = new Reponse<Partenaire>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Partenaire>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le partenaire n'existe pas"));
			reponse = new Reponse<Partenaire>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// modifier un chiffre dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/chiffre")
	public String modfierUnChiffre(@RequestBody Chiffre modif) throws JsonProcessingException {
		Reponse<Chiffre> reponsePersModif = null;
		Reponse<Chiffre> reponse = null;

		// on recupere la personne a modifier reponsePersModif =
		reponsePersModif = getChiffreById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Chiffre c2 = chiffreMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", c2.getId()));
				reponse = new Reponse<Chiffre>(0, messages, c2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Chiffre>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le chiffre n'existe pas"));
			reponse = new Reponse<Chiffre>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// modifier un temoignage dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/temoignage")
	public String modfierUnTemoignage(@RequestBody Temoignage modif) throws JsonProcessingException {
		Reponse<Temoignage> reponsePersModif = null;
		Reponse<Temoignage> reponse = null;

		// on recupere la personne a modifier reponsePersModif =
		reponsePersModif = getTremoignageById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Temoignage t2 = temoignageMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", t2.getId()));
				reponse = new Reponse<Temoignage>(0, messages, t2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Temoignage>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le temoignage n'existe pas"));
			reponse = new Reponse<Temoignage>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/partenaire/{id}")
	public String partenaireByIdSousBlock(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Partenaire>> reponse = null;

		try {
			List<Partenaire> part = partenaireMetier.getPartenaireByIdSousBlock(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<Partenaire>>(0, messages, part);
		} catch (Exception e) {

			reponse = new Reponse<List<Partenaire>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/chiffre/{id}")
	public String chiffreByIdSousBlock(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Chiffre>> reponse = null;

		try {
			List<Chiffre> chiffre = chiffreMetier.getChifreByIdSousBlock(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<Chiffre>>(0, messages, chiffre);
		} catch (Exception e) {

			reponse = new Reponse<List<Chiffre>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/temoignage/{id}")
	public String temoignageByIdSousBlock(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Temoignage>> reponse = null;

		try {
			List<Temoignage> temoignage = temoignageMetier.getTemoignageByIdSousBlock(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<Temoignage>>(0, messages, temoignage);
		} catch (Exception e) {

			reponse = new Reponse<List<Temoignage>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

}
