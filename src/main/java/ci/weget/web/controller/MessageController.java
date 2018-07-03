package ci.weget.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ImessagerieMetier;

@RestController
public class MessageController {
	@Autowired
	private ImessagerieMetier messagerie;

	@PostMapping("/Messageries")
	public Messagerie creer(@RequestBody Messagerie entity) throws InvalideTogetException {
		return messagerie.creer(entity);
	}

	public Messagerie modifier(Messagerie entity) throws InvalideTogetException {
		return messagerie.modifier(entity);
	}

	@GetMapping("/messageries")
	public List<Messagerie> findAll() {
		return messagerie.findAll();
	}

	public Messagerie findById(Long id) {
		return messagerie.findById(id);
	}

	public boolean supprimer(Long id) {
		return messagerie.supprimer(id);
	}

	public boolean supprimer(List<Messagerie> entites) {
		return messagerie.supprimer(entites);
	}

	public boolean existe(Long id) {
		return messagerie.existe(id);
	}

}
