package ci.weget.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.CvPersonnes;
import ci.weget.web.entites.Membres;
import ci.weget.web.entites.Personnes;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IPersonneMetier;

@SpringBootApplication
public class WegetWebMetierDaoJpaApplication implements CommandLineRunner {
    /*@Autowired
	private AccountService accountService;*/
    
    @Autowired
    private PersonnesRepository personnesRepository;
    @Autowired
    private IPersonneMetier personneMetier;
    @Autowired
    private IBlocksMetier blockMetier;
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
		
		Personnes p1=personneMetier.creer(new Membres("12345", "12345", "Yacou"));
		Personnes p2=personneMetier.creer(new Membres("1234", "1234", "Romeo"));
		
		
		Blocks b1=blockMetier.creer(new Blocks("Agriculture"));
		Blocks b2=blockMetier.creer(new Blocks("Ecurie"));
		
		personneMetier.addPersonneToBlocks(p1.getLogin(), b1.getLibelle());
		personneMetier.addPersonneToBlocks(p2.getLogin(), b1.getLibelle());
		personneMetier.addPersonneToBlocks(p2.getLogin(), b2.getLibelle());
		
		List<Personnes> personnes = personnesRepository.getPersonneParBlockLibelle(b1.getLibelle());
		
		System.out.println("personne p1 Creer:"+p1.getLogin());
		System.out.println("personne p2 Creer:"+p2.getLogin());
		System.out.println("le block b1 creer"+b1.getLibelle());
		System.out.println("le block b2 creer"+b2.getLibelle());
		
		for(int i=0;i<personnes.size();i++)
		
			System.out.println("les personne du block b1 sont attribues"+personnes.get(i));
		
		
	}
}
