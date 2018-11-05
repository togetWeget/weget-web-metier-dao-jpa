package ci.weget.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
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

import ci.weget.web.entites.Gallery;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.SousBlock;
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
	private Reponse<Gallery> getGalleryById(final Long id) {
		Gallery gallery = null;
		try {
			gallery = galleryMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Gallery>(1, Static.getErreursForException(e), null);
		}
		if (gallery == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
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
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoGallery")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile[] photoGallery,@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		List<String> pathGalleryPhoto = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "galleryPhotoDesMembres" + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}

		}
		for (MultipartFile file : photoGallery) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "galleryPhotoDesMembres" + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getGalleryPhotoDesMembres" + "/" + ga.getVersion() + "/" + ga.getId()
					+ "/" + libelle;
			pathGalleryPhoto.add(path);
			ga.setPathVideo(pathGalleryPhoto);

		}
		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		
		
		
		return jsonMapper.writeValueAsString(reponse);
	}
	// mettre en ligne des video 
	@PostMapping("/videoGallery")
	public String uploadVideo(@RequestParam(name = "image_photo") MultipartFile[] videoGallery,@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		List<String> pathVideo = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "videoDesMembres" + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdir();
			}

		}
		for (MultipartFile file : videoGallery) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();
			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "videoDesMembres" + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getvideoDesMembres" + "/" + ga.getVersion() + "/" + ga.getId()
					+ "/" + libelle;
			pathVideo.add(path);
			ga.setPathVideo(pathVideo);

		}
		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}
	private String saveUploadedFiles(List<MultipartFile> files) throws IOException {
		if (Files.notExists(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"))) {
			init();
		}

		// String randomPath = "";

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; // next pls
			}

			byte[] bytes = file.getBytes();

			String fileName = file.getOriginalFilename();
			// String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			// randomPath += generateRandomPath() + "." + suffix;

			Path path = Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/" + fileName);
			Files.write(path, bytes);
		}

		return null;
	        }
	private void init() {
		try {
			Files.createDirectory(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}
	// recuperer les images de la gallery
	@GetMapping(value = "/getPhotoGallery/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosGallery(@PathVariable String version, @PathVariable Long id,@PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/" + "galleryPhotoDesMembres" + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
	@GetMapping(value = "/getvideoDesMembres/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getVideoGallery(@PathVariable String version, @PathVariable Long id,@PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/" + "videoDesMembres" + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
}
