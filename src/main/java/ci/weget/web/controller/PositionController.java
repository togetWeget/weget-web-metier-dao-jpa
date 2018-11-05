package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Position;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IPositionMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class PositionController {
	@Autowired
	private IPositionMetier positionMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<Position> getPositionById(Long id) {
		Position position = null;
		try {
			position = positionMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (position == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la position n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Position>(0, null, position);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/position")
	public String creer(@RequestBody Position position) throws JsonProcessingException, InvalideTogetException {
		Reponse<Position> reponse = null;

		List<Position> pos = positionMetier.findAllPositionsParIdMembre(position.getMembre().getId());
		if (pos.isEmpty()) {
			try {

				Position p1 = positionMetier.creer(position);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", p1.getId()));
				reponse = new Reponse<Position>(0, messages, p1);

			} catch (InvalideTogetException e) {

				reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
			}
		} else {
          
			Position p2 = positionMetier.modifier(position);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", p2.getId()));
			reponse = new Reponse<Position>(0, messages, p2);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/position")
	public String modfierUnBlock(@RequestBody Position modif) throws JsonProcessingException {
		Reponse<Position> reponsePersModif = null;
		Reponse<Position> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getPositionById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Position p2 = positionMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", p2.getId()));
				reponse = new Reponse<Position>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Position>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La position n'existe pas"));
			reponse = new Reponse<Position>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les blocks de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/position")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Position>> reponse;
		try {
			List<Position> pos = positionMetier.findAll();
			reponse = new Reponse<List<Position>>(0, null, pos);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
