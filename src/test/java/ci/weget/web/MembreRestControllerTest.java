package ci.weget.web;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.controller.MembreController;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.metier.IMembreMetier;

@RunWith(SpringRunner.class)
@WebMvcTest(MembreController.class)
public class MembreRestControllerTest {

	@Autowired 
	private MockMvc mvc;
	@MockBean
	private IMembreMetier membreMetierMock;
	@Autowired
	private ObjectMapper mapper = new ObjectMapper();
	@Test
	public void test() {
	//	fail("Not yet implemented");
	}
	@Test
	public void EnregistrerUnMembre() throws JsonProcessingException, Exception{
		
/*
			Personne p1 = new Membre("Mr", "koffi", "Sylvain", null, null, "ME");
			
           Personne p2 = p1;
			p2.setId(3L);
			
			// quand on donne
			given(membreMetierMock.ajouterMembres((Personne) any(Personne.class))).willReturn(p2);
           // alors
			this.mvc.perform(post("/membres").contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(p1)))
					.andExpect(status().isOk()).andExpect(jsonPath("$.statut", is(0)))
					.andExpect(jsonPath("$.body.id", is(3))).andExpect(jsonPath("$.body.nom", is("Sylvain")));*/
		
	}
	@Test
	public void trouverUnePresonne() throws Exception {
   /*     Personne p1 = new Membre("Diarra", "12345", "12345");
		p1.setId(1L);
		p1.setType("ME");
		p1.setLogin("Diarra");

		given(membreMetierMock.findByLogin("Diarra")).willReturn(p1);

		// alors
		this.mvc.perform(get("/membresLogin/{login}",p1.getLogin())).andExpect(status().isOk())
	     .andExpect(jsonPath("$.statut", is(0)))
		.andExpect(jsonPath("$.body.login", is("Diarra")));
*/
	}

}
