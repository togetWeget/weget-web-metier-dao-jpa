package ci.weget.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Blocks;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BlocksController {

	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<Blocks> getBlockById(Long id) {
		Blocks block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (block == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Blocks>(0, null, block);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	private Reponse<Blocks> getBlockParLibellle(String libelle) {
		Blocks block = null;
		try {
			block = blocksMetier.rechercheParLibelle(libelle);
		} catch (RuntimeException e) {
			new Reponse<Blocks>(1, Static.getErreursForException(e), null);
		}
		if (block == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'exixte pas", libelle));
			return new Reponse<Blocks>(2, messages, null);
		}
		return new Reponse<Blocks>(0, null, block);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/blocks")
	public String creer(@RequestBody Blocks block) throws JsonProcessingException {
		Reponse<Blocks> reponse;

		try {

			Blocks b1 = blocksMetier.creer(block);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", b1.getLibelle()));
			reponse = new Reponse<Blocks>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Blocks>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	@PutMapping("/blocks")
	public String modfierUnBlock(@RequestBody Blocks modif) throws JsonProcessingException {
		Reponse<Blocks> reponsePersModif = null;
		Reponse<Blocks> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getBlockById(modif.getId());
		if (reponsePersModif.getStatut() == 0) {
			try {
				Blocks b2 = blocksMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s %s a modifier avec succes", b2.getLibelle()));
				reponse = new Reponse<Blocks>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Blocks>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La personne n'existe pas"));
			reponse = new Reponse<Blocks>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les blocks de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/blocks")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Blocks>> reponse;
		try {
			List<Blocks> mats = blocksMetier.findAll();
			reponse = new Reponse<List<Blocks>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/blocks/{id}")
	public String chercherBlockParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Blocks> reponse = null;

		reponse = getBlockById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////// recherche a partir de mot cle
	///////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////
	@GetMapping("/rechercheBlock")
	public String chercherBlocParMc(@RequestParam String mc) throws JsonProcessingException {
		Reponse<List<Blocks>> reponse = null;

		try {
			List<Blocks> blocks = blocksMetier.chercherBlockParMc(mc);
			if (!blocks.isEmpty()) {
				reponse = new Reponse<List<Blocks>>(0, null, blocks);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de block enregistrer "));
				reponse = new Reponse<List<Blocks>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/blocks/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Blocks b = null;
		if (!erreur) {
			Reponse<Blocks> responseSup = getBlockById(id);
			b = responseSup.getBody();
			if (responseSup.getStatut() != 0) {
				reponse = new Reponse<>(responseSup.getStatut(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s a ete supprime", b.getLibelle()));

				reponse = new Reponse<Boolean>(0, messages, blocksMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	////// ajouter une photo a la base a partir du libelle d'un block
	///////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////

	@PostMapping("/photo")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Blocks> reponse = null;
		Reponse<Blocks> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getBlockParLibellle(libelle);
		Blocks b = reponseParLibelle.getBody();
		System.out.println(b);

		String path = "http://localhost:8080/getPhoto/" + b.getVersion() + "/" + libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatut() == 0) {
			String dossier = togetImage + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				b.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", b.getLibelle()));
				reponse = new Reponse<Blocks>(0, messages, blocksMetier.modifier(b));

			} catch (Exception e) {

				reponse = new Reponse<Blocks>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Blocks>(reponseParLibelle.getStatut(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhoto/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable String libelle)
			throws FileNotFoundException, IOException {
		/*
		 * Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle); Blocks b =
		 * personneLibelle.getBody(); System.out.println(version); String dossier =
		 * togetImage+"/"; File f = new File(dossier+libelle); byte[] img =
		 * IOUti.toByteArray(new FileInputStream(f));
		 */
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// solution 2 solution 1 pour recuperer une photo d'un dossier pur retour un
	// objet reponse //////////////////

	@GetMapping(value = "/getPhoto1/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public String getPhotos(@PathVariable("libelle") String libelle) throws JsonProcessingException {
		/*
		 * Reponse<byte[]> reponse = null; byte[] image; Reponse<Blocks> personneLibelle
		 * = getBlockParLibellle(libelle); Blocks b = personneLibelle.getBody(); try {
		 * String dossier = togetImage+"/"; System.out.println(dossier); File f = new
		 * File(dossier+libelle); image = IOUtils.toByteArray(new FileInputStream(f));
		 * reponse = new Reponse<byte[]>(0,null, image); } catch (IOException e) {
		 * 
		 * reponse = new Reponse<byte[]>(3,Static.getErreursForException(e), null); }
		 */
		return null;
	}
}