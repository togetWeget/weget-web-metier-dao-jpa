package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		/*
		 * auth.inMemoryAuthentication()
		 * .withUser("admin").password("{noop}1234").roles("ADMIN","USER") .and()
		 * .withUser("user").password("{noop}1234").roles("USER");
		 */
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// http.formLogin();

		http.authorizeRequests().antMatchers("/login/**","/roleParPersonne/**","/ajouterUR/**","/fichierCv/**","/sousBlocks/**","/SousBlocksParBlock/**",
				"/personnesparId/**","/rechercheBlock/**","/panierParPersonne/**","/blocks/**","/photoCouvertureMembre/**","/gallery/**","/nombreVue/**",
				"/photoBlock/**","/getPhotoBlock/**","/membresLogin/**","/typePersonnes/**","/detailBlock/**","/getPhotoMembre/**","/photoGallery/**",
				"/tarifsBlocksId/**","/tarifs/**","/ajouterDb/**","/Personneblocks/**","/profilAbonneLogin/**","/messageries/**","/cursus/**","/messages/**",
				"/profil/**","/tousLesAbonnesParBlock/**","/panier/**","/tousLesBlockParAbonne/**","/misAjourProfil/**","/message/**","/experience/**",
				"/abonnes/**","/admin/**","/abonneParblocks/**","/membres/**","/photoMembre/**","/getPhotoCouvertureMembre/**").permitAll();
		  http.authorizeRequests().antMatchers("/membres/**").permitAll();
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/blocks/**").hasAuthority("ADMIN");

		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/blocks/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/blocks/**").hasAuthority("MEMBRE");

		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/membres/**").hasAuthority("ADMIN");
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/membres/**").hasAuthority("ADMIN");

		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/membres/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/membres/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.PUT, "/messageries/**").hasAuthority("ADMIN");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/messageries/**").hasAuthority("MEMBRE");
		  http.authorizeRequests().antMatchers(HttpMethod.POST, "/panier/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/misAjourProfil/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/membresLogin/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/membresLogin/**").hasAuthority("ADMIN");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/gallery/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/gallery/**").hasAuthority("ADMIN");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoGallery/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoGallery/**").hasAuthority("ADMIN");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoCouvertureMembre/**").hasAuthority("MEMBRE");
          http.authorizeRequests().antMatchers(HttpMethod.POST, "/photoCouvertureMembre/**").hasAuthority("ADMIN");

		// toutes les requetes necessite une aurhentication
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JWTAutenticationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
