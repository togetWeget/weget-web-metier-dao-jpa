package ci.weget.web;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.dao.EspaceRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Administrateurs;
import ci.weget.web.entites.Espaces;
import ci.weget.web.entites.Membres;
import ci.weget.web.entites.Personnes;
import ci.weget.web.metier.IPersonneMetier;
import ci.weget.web.security.AppRoles;

@SpringBootApplication
public class WegetWebMetierDaoJpaApplication implements CommandLineRunner {
    /*@Autowired
	private AccountService accountService;*/
    @Autowired
    private EspaceRepository espaceRepository;
    @Autowired
    private PersonnesRepository personnesRepository;
    @Autowired
    private IPersonneMetier personneMetier;
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WegetWebMetierDaoJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Personnes admin=personneMetier.creer(new Membres("Mr", "admin", "123456789", "123456789"));
		Personnes user=personneMetier.creer(new Membres("Mr","user", "12345678","12345678"));
		AppRoles role1=personneMetier.saveRole(new AppRoles("ADMIN"));
		AppRoles role2=personneMetier.saveRole(new AppRoles("USER"));
		personneMetier.addRoleToUser(admin.getLogin(), role1.getNom());
		personneMetier.addRoleToUser(user.getLogin(), role2.getNom());
		personneMetier.addRoleToUser(admin.getLogin(), role2.getNom());
		List<AppRoles> roles = personnesRepository.getRoles(admin.getId());
		
		System.out.println("Utilisateur Creer:"+admin.getLogin());
		System.out.println("les role admin creer"+role1.getNom());
		for(int i=0;i<roles.size();i++)
		System.out.println("les role de admin attribues"+roles.get(i));
		
		Stream.of("E1","E2","E3").forEach(e->{
			espaceRepository.save(new Espaces(1900d,e));
		});
		espaceRepository.findAll().forEach(e->{
			System.out.println(e.getDescription());
		});
		Stream.of("P1","P2","P3").forEach(P->{
			personnesRepository.save(new Membres(P, "password"));
		});
		personnesRepository.findAll().forEach(P->{
			System.out.println(P.getLogin());
		});
		Stream.of("D1","D2","D3").forEach(D->{
			personnesRepository.save(new Administrateurs(D, "password"));
		});
		personnesRepository.findAll().forEach(D->{
			System.out.println(D.getLogin());
		});*/
	}
}
