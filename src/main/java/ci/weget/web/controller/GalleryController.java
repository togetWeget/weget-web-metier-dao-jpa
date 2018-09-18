package ci.weget.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Gallery;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IGalleryMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class GalleryController {

	@Autowired
	private IGalleryMetier galleryMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Gallery> getGalleryByLibelle(final String libelle) {
		Gallery gallery = null;
		try {
			gallery = galleryMetier.chercherGalleryParLibelle(libelle);

		} catch (RuntimeException e) {
			new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		if (gallery == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", libelle));
			return new Reponse<Gallery>(2, messages, null);
		}
		return new Reponse<Gallery>(0, null, gallery);

	}

	/*
	 * private Reponse<List<PhotoGallery>> getPhotoGallery(final Long id) {
	 * List<PhotoGallery> photo = null; try { photo =
	 * galleryMetier.pathPhotoParGalleryId(id);
	 * 
	 * } catch (RuntimeException e) { new Reponse<Gallery>(1,
	 * Static.getErreursForException(e), null); } if (photo == null) { List<String>
	 * messages = new ArrayList<String>();
	 * messages.add(String.format("La personne n'exste pas")); return new
	 * Reponse<List<PhotoGallery>>(2, messages, null); } return new
	 * Reponse<List<PhotoGallery>>(0, null, photo);
	 * 
	 * }
	 */
	@PostMapping("/gallery")
	public String creer(@RequestBody Gallery gallery) throws JsonProcessingException {
		Reponse<Gallery> reponse;

		try {

			Gallery ga = galleryMetier.creer(gallery);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ga.getId()));
			reponse = new Reponse<Gallery>(0, messages, ga);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/gallery")
	public String modifier(@RequestBody Gallery gallery) throws JsonProcessingException {
		Reponse<Gallery> reponse;

		try {

			Gallery ga = galleryMetier.modifier(gallery);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", ga.getId()));
			reponse = new Reponse<Gallery>(0, messages, ga);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer la phorto d'un membre dans la
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoGallery")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile[] myfiles) throws Exception {
		for (MultipartFile file : myfiles) {

			if (file.isEmpty()) {
				System.out.println("fichier vide");
			} else {

				String fileName = file.getOriginalFilename();

				// String path1 = request.getSession().getServletContext().getRealPath("file")+
				// File.separator;

				// String path2 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+
				// fileName;
				// request.getSession().setAttribute("document",path2);
				String path = null;

				System.out.println("path");

				File localFile = new File(path);
				file.transferTo(localFile);
			}
		}
		return togetImage;
	}
}
