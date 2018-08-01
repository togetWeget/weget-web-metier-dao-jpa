package ci.weget.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.controller.MembreController;
import ci.weget.web.metier.IMembreMetier;

@RunWith(SpringRunner.class)
@WebMvcTest(MembreController.class)
public class MembreRestControllerTest {

	@Autowired 
	private MockMvc mvc;
	@MockBean
	private IMembreMetier membreMetier;
	private ObjectMapper mapper = new ObjectMapper();
	@Test
	public void test() {
	//	fail("Not yet implemented");
	}
	@Test
	public void EnregistrerUnMembre(){
		/*

			Personnes p1 = new Membres("12345", "12345", "Sylvain");

			Personnes p2 = p1;
			p2.setId(3L);
			// quand on donne
			given(membreMetier.ajouterMembres(any(Membres.class)).willReturn(p2);

			// alors
			this.mvc.perform(post("/membres").contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(p1)))
					.andExpect(status().isOk()).andExpect(jsonPath("$.statut", is(0)))
					.andExpect(jsonPath("$.body.id", is(3))).andExpect((jsonPath("$.body.nom", is("Diarra")));

		*/
	}

}
