package ci.weget.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import ci.weget.web.entites.Document;
import ci.weget.web.entites.Gallery;
import ci.weget.web.entites.Membre;
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
	@GetMapping("/galleryParIdSousBlock/{id}")
	public String findGalleryIdSousBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Gallery>> reponse;
		try {
			List<Gallery> ga = galleryMetier.findGalleryParIdSouBlock(id);
			reponse = new Reponse<List<Gallery>>(0, null, ga);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas de docs  trouve");
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Gallery>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/galleryParIdMembre/{id}")
	public String findGalleryIdMembre(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Gallery>> reponse;
		try {
			List<Gallery> ga = galleryMetier.findGalleryParIdMembre(id);
			reponse = new Reponse<List<Gallery>>(0, null, ga);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas de docs  trouve");
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Gallery>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoGallerySouBlock")
	public String uploadGalleryPhoto(@RequestParam(name = "image_photo[]") MultipartFile[] files, @RequestParam Long id)
			throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);
        
		Gallery gallery = reponseParLibelle.getBody();
		SousBlock sb = gallery.getSousBlock();
		Long idSb=sb.getId();
		List<String> pathGalleryPhoto = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "sousBlockGallery" + "/" + idSb+ "/"+gallery.getLibelle()+ "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();
		
			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String fileName = file.getOriginalFilename();
	       
	        if (file.isEmpty()) {
				 throw new Exception("impossible de charger un fichier vide "+file.getOriginalFilename() ); 
			}
	        
	        System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "sousBlockGallery" + "/" + idSb + "/"+gallery.getLibelle()+ "/"+fileName;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getSoublockGallery" +"/" +gallery.getVersion() +"/" + idSb + "/"+gallery.getLibelle()+ "/"+fileName;
					
			pathGalleryPhoto.add(path);
			gallery.setPathPhoto(pathGalleryPhoto);
	        
		}
		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(gallery));

		return jsonMapper.writeValueAsString(reponse);
	}
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoGalleryMembre")
	public String creerGalleriePhotosMembre(@RequestParam(name = "image_photo") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		Membre me = ga.getMembre();
		Long idMe = me.getId();
		List<String> pathGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "membresGallery" + "/" + idMe + "/" + ga.getLibelle() + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "membresGallery" + "/" + idMe + "/" + ga.getLibelle() + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getMembresGallery" + "/" + ga.getVersion() + "/" + idMe + "/"
					+ ga.getLibelle() + "/" + libelle;

			pathGallery.add(path);
			ga.setPathPhoto(pathGallery);

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
	@GetMapping(value = "/getSoublockGallery/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosGallerySousBlock(@PathVariable String version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle) throws FileNotFoundException, IOException {

		System.out.println(version);
		String dossier = togetImage + "/" + "sousBlockGallery" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getMembresGallery/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosGalleryMembres(@PathVariable String version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle) throws FileNotFoundException, IOException {

		System.out.println(version);
		String dossier = togetImage + "/" + "membresGallery" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// mettre en ligne des video des sous block
	@PostMapping("/videoGallerySousBlock")
	public String uploadVideoSousBlock(@RequestParam(name = "image_photo") MultipartFile[] files, @RequestParam Long id)
			throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		SousBlock sb = ga.getSousBlock();
		Long idSb = sb.getId();
		List<String> pathGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "sousBlockVideo" + "/" + idSb + "/" + ga.getLibelle() + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "sousBlockVideo" + "/" + idSb + "/" + ga.getLibelle() + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getsoublockVideo" + "/" + ga.getVersion() + "/" + idSb + "/"
					+ ga.getLibelle() + "/" + libelle;

			pathGallery.add(path);
			ga.setPathPhoto(pathGallery);

		}
		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/photoVideoMembre")
	public String creerVideoPhotosMembre(@RequestParam(name = "image_photo") MultipartFile[] files,
			@RequestParam Long id) throws Exception {
		Reponse<Gallery> reponse = null;
		Reponse<Gallery> reponseParLibelle;
		reponseParLibelle = getGalleryById(id);

		Gallery ga = reponseParLibelle.getBody();
		Membre me = ga.getMembre();
		Long idMe = me.getId();
		List<String> pathGallery = new ArrayList<>();
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "membresVideo" + "/" + idMe + "/" + ga.getLibelle() + "/";
			File rep = new File(dossier);
			if (!rep.exists() && !rep.isDirectory()) {
				rep.mkdirs();

			}

		}
		for (MultipartFile file : files) {

			System.out.println("Le nom du file dans le for" + file);
			String libelle = file.getOriginalFilename();

			if (file.isEmpty()) {
				throw new Exception("impossible de charger un fichier vide " + file.getOriginalFilename());
			}

			System.out.println("*******************************************");
			System.out.println("les types de fichier" + file.getContentType());
			System.out.println("********************************************");
			String dossier = togetImage + "/" + "membresVideo" + "/" + idMe + "/" + ga.getLibelle() + "/" + libelle;
			file.transferTo(new File(dossier));
			String path = "http://wegetback:8080/getMembresVideo" + "/" + ga.getVersion() + "/" + idMe + "/"
					+ ga.getLibelle() + "/" + libelle;

			pathGallery.add(path);
			ga.setPathPhoto(pathGallery);

		}
		reponse = new Reponse<Gallery>(0, null, galleryMetier.modifier(ga));

		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getSoublockVideo/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosVideoSoublocks(@PathVariable String version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle) throws FileNotFoundException, IOException {

		System.out.println(version);
		String dossier = togetImage + "/" + "sousBlockVideo" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getMembresVideo/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosVideoMembres(@PathVariable String version, @PathVariable Long id, @PathVariable String titre,
			@PathVariable String libelle) throws FileNotFoundException, IOException {

		System.out.println(version);
		String dossier = togetImage + "/" + "membresVideo" + "/" + id + "/" + titre + "/" + libelle;
		File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

}
