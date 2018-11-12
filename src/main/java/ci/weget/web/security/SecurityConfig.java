package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	    CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 @Autowired
	 private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**","/roleParPersonne/**","/ajouterUR/**","/fichierCv/**","/sousBlocks/**","/SousBlocksParIdDetailBlock/**","/document/**",
				"/personnesparId/**","/rechercheBlock/**","/panierParPersonne/**","/blocks/**","/photoCouvertureMembre/**","/gallery/**","/nombreVue/**","/sousblockDocument/**",
				"/getPhotoBlock/**","/membresLogin/**","/typePersonnes/**","/detailBlock/**","/getPhotoMembre/**","/photoGallery/**","/detailBlockParIdBlock/**","/getsoublockDocument/**",
				"/envoiemessages/**","/categoryBlocks/**","/position/**","/flashInfo/**","/flashInfoSousBlock/**","/formations/**","/formationSousBlock/**",
				"/tarifsBlocksId/**","/tarifs/**","/ajouterDb/**","/Personneblocks/**","/profilAbonneLogin/**","/messageries/**","/cursus/**","/messages/**",
				"/profil/**","/tousLesAbonnesParBlock/**","/tousLesBlockParAbonne/**","/misAjourProfil/**","/message/**","/experience/**","/documentCompetence/**",
				"/getDocCompetence/**","/SousBlocksParIdBlock/**","/sousBlockphotoCouverture/**","/getPhotoCouvertureSousBlock/**","/sousBlockLogo/**","/getPhotoLogoSousBlock/**",
				"/rechercheParComptence/**","/partenaire/**","/chiffre/**","/temoignage/**","/panier/**","/blocks/**","/photoBlock/**","/photoDocumentCompetence/**",
				"/abonnes/**","/admin/**","/abonneParblocks/**","/membres/**","/photoMembre/**","/getPhotoCouvertureMembre/**","/videoGallerySousBlock/**","/photoVideoMembre/**",
				"/getSoublockVideo/**","/getMembresVideo/**","/galleryParIdSousBlock/**","/galleryParIdMembre/**",
				"/documentParIdSousBlock/**","/photoGallerySouBlock/**","/photoGalleryMembre/**","/getSoublockGallery/**","/getMembresGallery/**").permitAll();
		  http.authorizeRequests().antMatchers("/membres/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/blocks/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoBlock/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/documentCompetence/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.GET, "/documentCompetence/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/documentCompetence/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/sousblockDocument/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/sousblockDocument/**").hasAuthority("MEMBRE");

	    }
}
