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

import ci.weget.web.entites.Document;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IDocumentMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DocumentController {
	@Autowired
	private IDocumentMetier documentMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Document> getDocumentByTitre(final String titre) {
		Document document = null;
		try {
			document = documentMetier.chercherDocumentParLibelle(titre);

		} catch (RuntimeException e) {
			new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		if (document == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le document n'exste pas", titre));
			return new Reponse<Document>(2, messages, null);
		}
		return new Reponse<Document>(0, null, document);

	}

	
	@PostMapping("/document")
	public String creer(@RequestBody Document doc) throws JsonProcessingException {
		Reponse<Document> reponse;

		try {

			Document d = documentMetier.creer(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Document>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/document")
	public String modifier(@RequestBody Document doc) throws JsonProcessingException {
		Reponse<Document> reponse;

		try {

			Document d = documentMetier.modifier(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Document>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoDocument")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile[] photoDocument) throws Exception {
		Reponse<Document> reponse = null;
		Reponse<Document> reponseParLibelle;
		//une boucle for pour parcourir chaque element envoye
		for (MultipartFile file : photoDocument) {
			// pour chaque file on recupere le libelle de la gallery
			String titre = file.getOriginalFilename();
			reponseParLibelle = getDocumentByTitre(titre);
			// appartir de ce libelle on recupere la gallery
			Document d = reponseParLibelle.getBody();
			System.out.println(d);
            // cette gallery recupere constitue notre lien
			String path = "http://wegetback:8080/getPhotoDocument/" + d.getVersion() + "/" + d.getId();
			System.out.println(path);
			if (reponseParLibelle.getStatus() == 0) {
				String dossier = togetImage + "/" + "Document" + "/";
				File rep = new File(dossier);

				if (!file.isEmpty()) {
					if (!rep.exists() && !rep.isDirectory()) {
						rep.mkdir();
					}
				}
				try {
					// enregistrer le chemin dans la photo
					List<String> pathDocument = new ArrayList<>();
					pathDocument.add(path);
					d.setPathDocument(pathDocument);
					System.out.println(pathDocument);
					file.transferTo(new File(dossier + d.getTitre()));
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s (photo ajouter avec succes)", d.getTitre()));
					reponse = new Reponse<Document>(0, messages, documentMetier.modifier(d));

				} catch (Exception e) {

					reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("cette personne n'existe pas"));
				reponse = new Reponse<Document>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
			}
		}
		
		
		return jsonMapper.writeValueAsString(reponse);
	}
	// recuperer les images de la gallery
	@GetMapping(value = "/getPhotoDocument/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosDocument(@PathVariable String version, @PathVariable Long id)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/" + "gallery" + "/";
		File f = new File(dossier + id);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
}
