package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*@Autowired
	private UserDetailsService userDetailsService;*/
	@Autowired
    CustomUserDetailsService customUserDetailsService;
	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 */
	 @Autowired
	 private JwtAuthenticationEntryPoint unauthorizedHandler;
	 @Bean
	    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter();
	    }

	    @Override
	    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder
	                .userDetailsService(customUserDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }

	    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
*/
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
        .cors()
            .and()
        .csrf()
            .disable()
        .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .authorizeRequests().antMatchers("/ajouterUR/**","/fichierCv/**","/detailAbonnement/**","/detailAbonnementParIdAbonnement/**","/document/**",
				"/personnesparId/**","/rechercheBlock/**","/panierParPersonne/**","/blocks/**","/photoCouvertureMembre/**","/gallery/**","/nombreVue/**","/detailAbonnementDocument/**",
				"/getPhotoBlock/**","/membresLogin/**","/typePersonnes/**","/abonnes/**","/getPhotoMembre/**","/photoGallery/**","/abonneParIdBlock/**","/getDetailAbonnementDocument/**",
				"/envoiemessages/**","/categoryBlocks/**","/position/**","/flashInfo/**","/flashInfoDetailAbonnement/**","/formations/**","/formationDetailAbonnement/**",
				"/tarifsBlocksId/**","/tarifs/**","/ajouterDb/**","/Personneblocks/**","/profilAbonneLogin/**","/messageries/**","/cursus/**","/messages/**",
				"/profil/**","/tousLesAbonnesParBlock/**","/tousLesBlockParAbonne/**","/misAjourProfil/**","/message/**","/experience/**","/documentCompetence/**","/infoEntete/",
				"/getDocCompetence/**","/detailAbonnementParIdBlock/**","/detailAbonnementPhotoCouverture/**","/getPhotoCouvertureDetailAbonnement/**","/getPhotoLogoDetailAbonnement/**",
				"/rechercheParComptence/**","/rechercheParVille/**","/partenaire/**","/chiffre/**","/temoignage/**","/panier/**","/blocks/**","/photoBlock/**","/photoDocumentCompetence/**",
				"/admin/**","/abonneParblocks/**","/membres/**","/photoMembre/**","/getPhotoCouvertureMembre/**","/videoMembre/**","/findParCompetence/**",
				"/getMembresVideo/**","/galleryParIdMembre/**","/rechercheParComptenceOuVille/**","/paiement/**","/commande/**",
				"/formationPhoto/**","/getPhotoFormation/**","/formationFormulaire/**","/getFormulaireFormation/**","/formationCatalogue/**","/getCatalogueFormation/**",
				"/documentParIdDetailAbonnement/**","/photoGalleryMembre/**","/getMembresGallery/**",
				"/regitrationConfirm/**","/paiement/**").permitAll();
		  http.authorizeRequests().antMatchers("/membres/**","/abonnesSpeciaux","/verifierLoginToken/**").permitAll();
		  http.authorizeRequests().antMatchers("/login/**","/register/**","/resendRegistrationToken/**","/passwordOublier/**","/newPassword/**","/adminMembres/**").permitAll();
		  http.authorizeRequests().antMatchers("/getDetailAbonnementVideo/**","/galleryParIdDetailAbonnement/**","/photoGalleryDetailAbonnement/**",
				  "/getDetailAbonnementGallery/**","/videoGalleryDetailAbonnement/**",
				  "/detailAbonnementLogo/**","/https://api.cinetpay.com/v1/**")
          .permitAll();
		  http.authorizeRequests().antMatchers( "/quartier/**","/quartierParVille/**","/abonneSpecial/**")
          .permitAll();
		  http.authorizeRequests().antMatchers( "/specialite/**","/niveauEtude/**","/disponibilite/**",
				  "/langue/**","/contratPeriode/**","/emploieType/**","/fonction/**","/comboExperience/**",
				  "/contratType/**","/domaine/**","/comboTarif/**","/fonction/**","/api/client/**")
          .permitAll();
		  http.authorizeRequests().antMatchers("/partenaireLogo/**","/getLogoPartenaire/**","/temoignagePhoto/**","/getPhotoTemoignage/**").permitAll();
		  http.authorizeRequests().antMatchers("/pays/**","/ville/**","/villeParPays/**").permitAll();
		  http.authorizeRequests().antMatchers("/photoCouvertureMembre/**","/paiement/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/blocks/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoBlock/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/documentCompetence/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.GET, "/documentCompetence/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/documentCompetence/**").hasAuthority("MEMBRE");

		  http.authorizeRequests().antMatchers( "/blocks/**","/membreStatut/**","/misAjourProfil/**","/abonneGratuit/**").permitAll();
		  http.authorizeRequests().antMatchers( "/diplome/**").permitAll();
		  http.authorizeRequests().antMatchers( "/detailAbonnementParEtablissement/**","/typeEtablissement/**").permitAll();
		  http.authorizeRequests().antMatchers("/roleParPersonne/**").hasAuthority("ADMIN");


		  http.authorizeRequests().antMatchers("/membres/**","/membre/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/membres/**").hasAuthority("ADMIN");

		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/membres/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/membres/**").permitAll();;
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/messageries/**").permitAll();;
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/messageries/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/panier/**").permitAll();
          http.authorizeRequests().antMatchers("/misAjourProfil/**","/CvPersonne/**","/cvPersonneParIdAbonnement/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/membresLogin/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/membresLogin/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/gallery/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/gallery/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoGallery/**").permitAll();
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoGallery/**").permitAll();

		// toutes les requetes necessite une aurhentication
		http.authorizeRequests().anyRequest().authenticated();
		//http.addFilter(new JWTAutenticationFilter(authenticationManager()));
		//http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
