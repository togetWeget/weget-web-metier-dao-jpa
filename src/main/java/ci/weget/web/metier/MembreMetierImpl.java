package ci.weget.web.metier;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.VerificationTokenRepository;
import ci.weget.web.entites.Adresse;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.VerificationToken;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.CreerAbonne;

@Service
@Transactional
public class MembreMetierImpl implements IMembreMetier {

	public static final String TOKEN_INVALID = "INVALID";
	public static final String TOKEN_EXPIRED = "EXPIRED";
	public static final String TOKEN_VALID = "VALID";
	static final String USER_NOT_FOUND_ERROR = "user-not-found";
	static final String INTERNAL_ERROR = "internal-error";
	static final String ID_TOKEN_REVOKED_ERROR = "id-token-revoked";
	static final String SESSION_COOKIE_REVOKED_ERROR = "session-cookie-revoked";

	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CreerAbonne creerAbonne;

	// verifier si un utilisateur existe sur firebase
	private UserRecord verifierUser(Personne personne) {
		UserRecord user = null;
		try {
			user = FirebaseAuth.getInstance().getUserByEmail(personne.getLogin());
		} catch (FirebaseAuthException e) {

			e.getMessage();
		}
		return user;

	}

	/////// creer un utilisateur sur firebase///////////////////////////////////////

	
	public UserRecord createUser(Personne personne) throws FirebaseException, InterruptedException, ExecutionException {

		UserRecord userRecord = null;
		//userRecord = (ApiFuture<UserRecord>) FirebaseAuth.getInstance().getUserByEmail(personne.getLogin());
		

		Optional<Personne> p = personnesRepository.findByLogin(personne.getLogin());
		//System.out.println("Successfully   userRecord: " + userRecord);
		System.out.println("Successfully   p: " + p);

		if (!p.isPresent()) {
			CreateRequest request = new CreateRequest().setEmail(personne.getLogin())
					.setPassword(personne.getPassword());

			userRecord = FirebaseAuth.getInstance().createUser(request);
			System.out.println("Successfully created new user: " + userRecord.getUid());

			System.out.println("Successfully created new user: " + userRecord.getUid());
		}

		return userRecord;

	}

	@Override
	public UserRecord updateUser(Personne personne) throws Exception {

		UpdateRequest request = new UpdateRequest(personne.getLogin()).setEmail(personne.getLogin())
				// .setEmailVerified(personne.getAdresse().getEmail())
				.setPassword(personne.getPassword());
		UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);

		return userRecord;
	}

	@Override
	public Personne findById(final Long id) {

		return personnesRepository.getPersonneByid(id);
	}

	@Override
	public Personne creer(Personne p) throws InvalideTogetException {

		if (!p.getPassword().equals(p.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		}

		Optional<Personne> pers = null;

		pers = personnesRepository.findByLogin(p.getLogin());
		if (pers.isPresent()) {
			throw new InvalideTogetException("ce login est deja utilise");
		}
		Adresse adresse = new Adresse();
		adresse.setLatitude(0);
		adresse.setLongitude(0);
		p.setAdresse(adresse);
		p.setPassword(passwordEncoder.encode(p.getPassword()));
		p.setRepassword(passwordEncoder.encode(p.getRepassword()));
		return personnesRepository.save(p);
	}

	@Override
	@Transactional
	public Personne modifier(Personne modif) throws InvalideTogetException {

		if (modif != null) {
			Optional<Personne> pers = personnesRepository.findByLogin(modif.getLogin());
			if (pers.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else {
			throw new InvalideTogetException("modif est un objet null");
		}
		Optional<Personne> pers1 = personnesRepository.findByLogin(modif.getLogin());
		String hshPW = pers1.get().getPassword();
		 String hshRPW = pers1.get().getRepassword();
		modif.setPassword(hshPW);
		modif.setRepassword(hshRPW);
	    return personnesRepository.save(modif);
	}

	@Override
	public Personne modifierPassword(String login, String password, String repassword) throws InvalideTogetException {

		Optional<Personne> pers1 = personnesRepository.findByLogin(login);
		String hshPW = pers1.get().getPassword();
		pers1.get().setPassword(passwordEncoder.encode(password));
		pers1.get().setRepassword(passwordEncoder.encode(repassword));
		return personnesRepository.save(pers1.get());
	}

	@Override
	public Personne findByLogin(String login) {

		return personnesRepository.findByMembreLogin(login);
	}

	@Override
	public List<Personne> findAll() {

		return personnesRepository.findAllMembres();
	}

	@Override
	public boolean supprimer(Long id) {
    personnesRepository.deleteById(id);
		
		return true;
	}

	@Override
	public boolean supprimer(List<Personne> entites) {

		personnesRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {

		return personnesRepository.existsById(id);
	}

	@Override
	public Membre getMembre(String verificationToken) {
		Membre membre = tokenRepository.findByToken(verificationToken).getMembre();
		return membre;
	}

	@Override
	public void createVerificationToken(Membre membre, String token) {
		VerificationToken myToken = new VerificationToken(token, membre);
		tokenRepository.save(myToken);

	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);

		LocalDateTime dateActuelle = LocalDateTime.now();

		if (dateActuelle.isAfter(verificationToken.getExpiryDate())) {
			return TOKEN_EXPIRED;
		} else {

			Membre user = verificationToken.getMembre();

			user.setEnabled(true);

			personnesRepository.save(user);

		}
		return TOKEN_VALID;
	}

	private boolean emailExist(String login) {
		Optional<Personne> user = personnesRepository.findByLogin(login);
		if (user.get().getLogin() != null) {
			return true;
		}
		return false;
	}

	@Override
	public VerificationToken generateNewVerificationToken(String existingVerificationToken) {

		VerificationToken vToken = null;
		vToken = tokenRepository.findByToken(existingVerificationToken);
		tokenRepository.deleteById(vToken.getId());
		return vToken;
	}

	@Override
	public VerificationToken getVericationTokenParMembre(Long id) {

		return tokenRepository.rechercherByMembre(id);
	}

	@Override
	public List<Personne> personneALL(String type) {
		List<Personne> pers = personnesRepository.findAll();

		List<Personne> personne = pers.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());

		return personne;
	}

	@Override
	public VerificationToken modifDateExpire(Long id) {
		final VerificationToken verificationToken = tokenRepository.findByTokenById(id);
		verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
		return tokenRepository.save(verificationToken);
	}

	@Override
	public boolean creerAbonne(Personne personne) throws InvalideTogetException {
		try {
			creerAbonne.creerUnAbonne(personne);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}