package ci.weget.web.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAppRoleMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.JwtTokenProvider;
import ci.weget.web.security.UserRoles;
import ci.weget.web.utilitaires.ApiResponse;
import ci.weget.web.utilitaires.JwtAuthenticationResponse;
import ci.weget.web.utilitaires.LoginRequest;
import ci.weget.web.utilitaires.SignUpRequest;
import ci.weget.web.utilitaires.Static;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private IAppRoleMetier roleMetier;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

	@PostMapping("/sign")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		    String jwt = tokenProvider.generateToken(authentication);
	        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

	}

	@PostMapping("/membre")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
			throws InvalideTogetException {
		if (membreMetier.findByLogin(signUpRequest.getLogin()) != null) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		// Creation de compte utilisateur

		Personne membre = new Membre(signUpRequest.getLogin(), signUpRequest.getPassword(), signUpRequest.getRepassword());


		AppRoles roleMembre = roleMetier.findRoleByNom("membre");

		membreMetier.addRoleToUser(membre.getLogin(), roleMembre.getNom());
		Personne result = membreMetier.creer(membre);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
				.buildAndExpand(result.getLogin()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}