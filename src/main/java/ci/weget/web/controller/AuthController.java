package ci.weget.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ci.weget.web.entites.Personne;
import ci.weget.web.security.JWTAutenticationFilter;


@RestController
public class AuthController {
	 

	@PostMapping(value = {"/auth"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestBody Personne authenticationRequest) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

	    if(authentication != null && authentication.isAuthenticated()) {
	        JWTAutenticationFilter tokens = new JWTAutenticationFilter(authenticationManager);
	        return ResponseEntity.ok().body(tokens);
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
	}
}
