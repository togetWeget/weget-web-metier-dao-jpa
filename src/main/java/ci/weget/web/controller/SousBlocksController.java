package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
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

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.SousBlock;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.ISousBlockMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class SousBlocksController {

	@Autowired
	private ISousBlockMetier sousBlocksMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<SousBlock> getSousBlockById(Long id) {
		SousBlock sousBlock = null;
		try {
			sousBlock = sousBlocksMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (sousBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<SousBlock>(0, null, sousBlock);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	/*private Reponse<SousBlock> getSousBlockParLibellle(String libelle) {
		SousBlock sousBlock = null;
		try {
			sousBlock = sousBlocksMetier.rechercheSousBlockParLibelle(libelle);
		} catch (RuntimeException e) {
			new Reponse<Block>(1, Static.getErreursForException(e), null);
		}
		if (sousBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'exixte pas", libelle));
			return new Reponse<SousBlock>(2, messages, null);
		}
		return new Reponse<SousBlock>(0, null, sousBlock);
	}
*/
	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/sousBlocks")
	public String creer(@RequestBody SousBlock sousBlock) throws JsonProcessingException {
		Reponse<SousBlock> reponse;

		try {

			SousBlock sb1 = sousBlocksMetier.creer(sousBlock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", sb1.getId()));
			reponse = new Reponse<SousBlock>(0, messages, sb1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<SousBlock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	@PutMapping("/sousBlocks")
	public String modfierUnBlock(@RequestBody SousBlock modif) throws JsonProcessingException {
		Reponse<SousBlock> reponsePersModif = null;
		Reponse<SousBlock> reponse = null;

		// on recupere la personne a modifier
		
		
			try {
				SousBlock sb2 = sousBlocksMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", sb2.getId()));
				reponse = new Reponse<SousBlock>(0, messages, sb2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<SousBlock>(1, Static.getErreursForException(e), null);
			}

		

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les blocks de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/SousBlocks")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<SousBlock>> reponse;
		try {
			List<SousBlock> mats = sousBlocksMetier.findAll();
			reponse = new Reponse<List<SousBlock>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	//////////// personnes d'un block par id
	@GetMapping("/SousBlocksParBlock/{id}")
	public String getPersonneBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<SousBlock>> reponse;
		try {
			List<SousBlock> sb = sousBlocksMetier.findSousBlockParIdBlock(id);
			reponse = new Reponse<List<SousBlock>>(0, null, sb);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/sousBlocks/{id}")
	public String chercherBlockParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<SousBlock> reponse = null;

		reponse = getSousBlockById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////// recherche a partir de mot cle
	///////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////
	@GetMapping("/rechercheSousBlock")
	public String chercherSousBlocParMc(@RequestParam String mc) throws JsonProcessingException {
		Reponse<List<SousBlock>> reponse = null;

		try {
			List<SousBlock> blocks = sousBlocksMetier.chercherSousBlockParMc(mc);
			if (!blocks.isEmpty()) {
				reponse = new Reponse<List<SousBlock>>(0, null, blocks);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de block enregistrer "));
				reponse = new Reponse<List<SousBlock>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/sousBlocks/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		SousBlock b = null;
		if (!erreur) {
			Reponse<SousBlock> responseSup = getSousBlockById(id);
			b = responseSup.getBody();
			if (responseSup.getStatus() != 0) {
				reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s a ete supprime", b.getId()));

				reponse = new Reponse<Boolean>(0, messages, sousBlocksMetier.supprimer(id));

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

	/*@PostMapping("/sousBlockphoto")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<SousBlock> reponse = null;
		Reponse<SousBlock> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getSousBlockParLibellle(libelle);
		SousBlock sb = reponseParLibelle.getBody();
		System.out.println(sb);

		String path = "http://localhost:8080/getPhotoSousBlock/"+ sb.getVersion()+"/" + sb.getId();
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				sb.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + sb.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", sb.getId()));
				reponse = new Reponse<SousBlock>(0, messages, sousBlocksMetier.modifier(sb));

			} catch (Exception e) {

				reponse = new Reponse<SousBlock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<SousBlock>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}*/

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoSousBlock/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable Long id)
			throws FileNotFoundException, IOException {
		
		 // Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle); 
		  //Blocks b = personneLibelle.getBody(); 
		  System.out.println(version); 
		  String dossier = togetImage+"/"; 
		  File f = new File(dossier+id); 
		  byte[] img = IOUtils.toByteArray(new FileInputStream(f));
		 
		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// photo de couverture du sous block

	/*@PostMapping("/sousBlockphotoCouverture")
	public String creerPhotoCouverture(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<SousBlock> reponse = null;
		Reponse<SousBlock> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getSousBlockParLibellle(libelle);
		SousBlock sb = reponseParLibelle.getBody();
		System.out.println(sb);

		String path = "http://localhost:8080/getPhotoCouvertureSousBlock/"+ sb.getVersion()+"/" + sb.getId();
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				sb.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + sb.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", sb.getId()));
				reponse = new Reponse<SousBlock>(0, messages, sousBlocksMetier.modifier(sb));

			} catch (Exception e) {

				reponse = new Reponse<SousBlock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<SousBlock>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
*/
	//////// recuperer une photo de couverture avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoCouvertureSousBlock/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosCouverture(@PathVariable String version, @PathVariable Long id)
			throws FileNotFoundException, IOException {
		
		 // Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle); 
		  //Blocks b = personneLibelle.getBody(); 
		  System.out.println(version); 
		  String dossier = togetImage+"/"; 
		  File f = new File(dossier+id); 
		  byte[] img = IOUtils.toByteArray(new FileInputStream(f));
		 
		return img;
	}
}
