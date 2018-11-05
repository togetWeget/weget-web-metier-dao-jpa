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

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IDetailBlocksMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.PostAjoutDetailBlock;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DetailBlockContrller {
	@Autowired
	private IDetailBlocksMetier detailBlocksMetier;
	@Autowired
	private IBlocksMetier blocksMetier;
	@Autowired
	private IAdminMetier adminMetier;
	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	// recuprer le block a partir de son identifiant
	private Reponse<Block> getBlock(long id) {
		// on récupère le block
		Block block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Block>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Block>(2, messages, null);
		}
		// ok
		return new Reponse<Block>(0, null, block);
	}

	// recuperer la personne a partir de son identifiant
	private Reponse<Personne> getPersonneById(final Long id) {
		Personne personne = null;
		try {
			personne = adminMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	private Reponse<Personne> getMembreById(final Long id) {
		Personne personne = null;
		try {
			personne = membreMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	private Reponse<DetailBlock> getdetailBlock(long id) {
		// on récupère le block
		DetailBlock detailBlock = null;
		try {
			detailBlock = detailBlocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<DetailBlock>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (detailBlock == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<DetailBlock>(2, messages, null);
		}
		// ok
		return new Reponse<DetailBlock>(0, null, detailBlock);
	}

	private Reponse<Personne> getMembreByLogin(final String login) {
		Personne personne = null;
		try {
			personne = membreMetier.findByLogin(login);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	//////////////// creer un abonne
	// creer un abonne revient a dire quún membre a paye le bloc donc son statut
	// est abonne
	@PostMapping("/abonnes")
	public String creerAbonne(@RequestBody PostAjoutDetailBlock post) throws JsonProcessingException {
		Reponse<Boolean> reponse;
		long idBlock = post.getIdBlock();
		long idPersonne = post.getIdPersonne();

		// on récupère le block reponse block
		Reponse<Block> reponseBlock = getBlock(idBlock);
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();
		// on récupère la personne
		Reponse<Personne> reponsePersonne = getMembreById(idPersonne);

		Personne personne = (Personne) reponsePersonne.getBody();
		try {
			boolean boo = detailBlocksMetier.creerAbonne(personne.getLogin(), block.getLibelle());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("detail block à été créer avec succes"));
			reponse = new Reponse<Boolean>(0, messages, boo);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// faire la mise a jour du profil d'un abonne
	@PutMapping("/misAjourProfil")
	public String modifier(@RequestBody DetailBlock modif) throws JsonProcessingException {
		Reponse<DetailBlock> reponsePersModif = null;
		Reponse<DetailBlock> reponse = null;

		try {
			DetailBlock db = detailBlocksMetier.modifier(modif);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s a modifier avec succes", db.getId()));
			reponse = new Reponse<DetailBlock>(0, messages, db);
		} catch (InvalideTogetException e) {

			reponse = new Reponse<DetailBlock>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/profilAbonneId/{id}")
	public String profilAbonneId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<DetailBlock> reponse = null;

		reponse = getdetailBlock(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/profilAbonneLogin/{login}")
	public String profilAbonneLogin(@PathVariable String login) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<DetailBlock>> reponse = null;

		try {
			List<DetailBlock> db = detailBlocksMetier.detailBlocksPersonneParLogin(login);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<DetailBlock>>(0, messages, db);
		} catch (Exception e) {

			reponse = new Reponse<List<DetailBlock>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir tous les abonnes
	@GetMapping("/abonnes")
	public String findAllAbonne() throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;
		try {
			List<DetailBlock> pers = detailBlocksMetier.tousLesDetailBlock();
			reponse = new Reponse<List<DetailBlock>>(0, null, pers);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir tous les details blocks par leur identifiant
	@GetMapping("/detailBlock/{id}")
	public String finddetailBlockParId(@PathVariable Long id) throws JsonProcessingException {
		Reponse<DetailBlock> reponse;
		try {
			DetailBlock db = detailBlocksMetier.findById(id);
			reponse = new Reponse<DetailBlock>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir la liste des membres
	@GetMapping("/tousLesAbonnesParBlock/{id}")
	public String findAllTypePersonne(@PathVariable("id") Long id) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;

		try {
			List<DetailBlock> personneTous = detailBlocksMetier.personneALLBlock(id);
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<DetailBlock>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<DetailBlock>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// obtenir des blocks d'un abonne
	@GetMapping("/tousLesBlockParAbonne/{id}")
	public String findAllBlockParPersonne(@PathVariable("id") Long id) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;

		try {
			List<DetailBlock> blocks = detailBlocksMetier.detailBlocksPersonneParId(id);

			List<String> messages = new ArrayList<>();
			messages.add("les blocks de la personne");
			reponse = new Reponse<List<DetailBlock>>(1, messages, blocks);

		} catch (Exception e) {

			reponse = new Reponse<List<DetailBlock>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
//////////rechercher un membre par competence
	@GetMapping("/rechercheParComptence")
	public String chercherBlocParMc(@RequestParam String specialite) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse = null;

		try {
			List<DetailBlock> db = detailBlocksMetier.chercherPersonneParSpecialite(specialite);
			if (!db.isEmpty()) {
				reponse = new Reponse<List<DetailBlock>>(0, null, db);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de block enregistrer "));
				reponse = new Reponse<List<DetailBlock>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir des blocks d'un abonne
	@GetMapping("/abonneSpecial")
	public String findAllAbonneSpecial(@RequestBody DetailBlock db) throws JsonProcessingException {
		Reponse<List<DetailBlock>> reponse;

		try {
			List<DetailBlock> db1 = detailBlocksMetier.abonneSpecial((DetailBlock) db);

			List<String> messages = new ArrayList<>();
			messages.add("les blocks de la personne");
			reponse = new Reponse<List<DetailBlock>>(1, messages, db1);

		} catch (Exception e) {

			reponse = new Reponse<List<DetailBlock>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les Abonnes par competence
	// tous les membres qui ont leur statut a abonne et ont paye le block
	@GetMapping("/abonnes/{competence}")
	public String chercherPersonneParCompetence(@PathVariable String competence) throws JsonProcessingException {
		/*
		 * Reponse<List<Personne>> reponse; try { List<Personne> db =
		 * detailBlocksMetier.ajoutdetailBlocks(competence); reponse = new
		 * Reponse<List<Personne>>(0, null, db); } catch (Exception e) { reponse = new
		 * Reponse<>(1, Static.getErreursForException(e), null); }
		 */
		return null;

	}

	// mettre a jour le nombre de vue d'un membre
	@PutMapping("/nombreVue")
	public String modifierVue(@RequestBody PostAjoutDetailBlock modifVue) throws JsonProcessingException {
		Reponse<DetailBlock> reponse;
		long idBlock = modifVue.getIdBlock();
		long idPersonne = modifVue.getIdPersonne();

		// on récupère le block reponse block
		Reponse<Block> reponseBlock = getBlock(idBlock);
		// on recupere le block
		Block block = (Block) reponseBlock.getBody();
		// on récupère la personne
		Reponse<Personne> reponsePersonne = getMembreById(idPersonne);

		Personne personne = (Personne) reponsePersonne.getBody();
		try {
			DetailBlock db = detailBlocksMetier.modifierVue(personne.getId(), block.getId());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("detail block à été modifier avec succes"));
			reponse = new Reponse<DetailBlock>(0, messages, db);

		} catch (Exception e) {

			reponse = new Reponse<DetailBlock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// obtenir tous les details blocks par leur identifiant
		@GetMapping("/detailBlockParIdBlock/{id}")
		public String finddetailBlockParIdBlock(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<DetailBlock>> reponse;
			try {
				List<DetailBlock> db = detailBlocksMetier.findDtailBlocksParIdBlock(id);
				reponse = new Reponse<List<DetailBlock>>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}


	@PostMapping("/photoAbonnes")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLogin;
		// recuperer le libelle à partir du nom de la photo
		String login = file.getOriginalFilename();
		reponseParLogin = getMembreByLogin(login);
		Personne p = reponseParLogin.getBody();
		System.out.println(p);

		String path = "http://localhost:8080/getPhotoAbonnes/" + p.getVersion() + "/" + login;
		System.out.println(path);
		if (reponseParLogin.getStatus() == 0) {
			String dossier = togetImage + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				p.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + login));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getLogin()));
				reponse = new Reponse<Personne>(0, messages, membreMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personne>(reponseParLogin.getStatus(), reponseParLogin.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoAbonnes/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
