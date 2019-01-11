package ci.weget.web.metier;

import java.time.LocalDateTime;
import java.util.List;

import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.VerificationToken;
import ci.weget.web.exception.InvalideTogetException;

public interface IMembreMetier extends Imetier<Personne, Long> {

	public Personne findByLogin(String login);

	List<Personne> personneALL(String type);

	public UserRecord createUser(Personne personne) throws Exception;

	public UserRecord updateUser(Personne personne) throws Exception;

	Membre getMembre(String verificationToken);

	void createVerificationToken(Membre user, String token);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String token);
	VerificationToken modifDateExpire(Long id);
	boolean creerAbonne(Personne personne) throws InvalideTogetException;

	

	VerificationToken getVericationTokenParMembre(Long id);

	 Personne modifierPassword(String login, String password,String repassword) throws InvalideTogetException;

}
