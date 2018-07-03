package ci.weget.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ImessagerieMetier;

@RestController
public class Messageries {
	private ImessagerieMetier messagerieMetier;

	@PostMapping("/messageries")
	public Messagerie creer(Messagerie entity) throws InvalideTogetException {
		return messagerieMetier.creer(entity);
	}

	@PutMapping("messageries")
	public Messagerie modifier(Messagerie entity) throws InvalideTogetException {
		return messagerieMetier.modifier(entity);
	}
   @GetMapping("Messageries")
	public List<Messagerie> findAll() {
		return messagerieMetier.findAll();
	}

	public Messagerie findById(Long id) {
		return messagerieMetier.findById(id);
	}

	public boolean supprimer(Long id) {
		return messagerieMetier.supprimer(id);
	}

	public boolean supprimer(List<Messagerie> entites) {
		return messagerieMetier.supprimer(entites);
	}

	public boolean existe(Long id) {
		return messagerieMetier.existe(id);
	}

}
