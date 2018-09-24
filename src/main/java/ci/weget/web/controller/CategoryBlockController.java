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

import ci.weget.web.entites.CategoryBlock;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ICategoryBlockMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class CategoryBlockController {
	@Autowired
	private ICategoryBlockMetier categoryBlockMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer une categoryBlock a partir de son identifiant
	private Reponse<CategoryBlock> getCategoryBlockById(Long id) {
		CategoryBlock catBlock = null;
		try {
			catBlock = categoryBlockMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (catBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<CategoryBlock>(0, null, catBlock);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	private Reponse<CategoryBlock> getCategoryBlockParLibellle(String libelle) {
		CategoryBlock catBlock = null;
		try {
			catBlock = categoryBlockMetier.rechercheParLibelle(libelle);
		} catch (RuntimeException e) {
			new Reponse<CategoryBlock>(1, Static.getErreursForException(e), null);
		}
		if (catBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'exixte pas", libelle));
			return new Reponse<CategoryBlock>(2, messages, null);
		}
		return new Reponse<CategoryBlock>(0, null, catBlock);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/categoryBlocks")
	public String creer(@RequestBody CategoryBlock catBlock) throws JsonProcessingException {
		Reponse<CategoryBlock> reponse;

		try {

			CategoryBlock b1 = categoryBlockMetier.creer(catBlock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", b1.getLibelle()));
			reponse = new Reponse<CategoryBlock>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<CategoryBlock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	
	@PutMapping("/categoryBlocks")
	public String modfierUnBlock(@RequestBody CategoryBlock modif) throws JsonProcessingException {
		Reponse<CategoryBlock> reponsePersModif = null;
		Reponse<CategoryBlock> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getCategoryBlockById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				CategoryBlock b2 = categoryBlockMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getLibelle()));
				reponse = new Reponse<CategoryBlock>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<CategoryBlock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<CategoryBlock>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/photoCategoryBlock")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<CategoryBlock> reponse = null;
		Reponse<CategoryBlock> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getCategoryBlockParLibellle(libelle);
		CategoryBlock b = reponseParLibelle.getBody();
		System.out.println(b);

		String path = "http://wegetback:8080/getPhotoCategoryBlock/"+ b.getVersion()+"/" + b.getId();
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
				b.setPhathphoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + b.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", b.getLibelle()));
				reponse = new Reponse<CategoryBlock>(0, messages, categoryBlockMetier.modifier(b));

			} catch (Exception e) {

				reponse = new Reponse<CategoryBlock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<CategoryBlock>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoCategoryBlock/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
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
}
