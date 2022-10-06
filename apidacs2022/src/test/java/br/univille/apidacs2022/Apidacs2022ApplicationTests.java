package br.univille.apidacs2022;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.univille.apidacs2022.api.PacienteControllerAPI;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTests {

	@Autowired
	private PacienteControllerAPI pacienteControllerAPI;
	@Autowired
	private MockMvc mockMvc;
	private String jwtToken;

	@Test
	void preparacaoAmbienteTest() {
		assertThat(pacienteControllerAPI).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	private void geraToken() throws Exception {

		MvcResult resultAuth = mockMvc.perform(post("/api/v1/auth/signin")
				.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		jwtToken = resultAuth.getResponse().getContentAsString();
	}

	@Test
	void pacienteControllerAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/pacientes")
				.content("{\"nome\":\"Zezinho\",\"sexo\":\"Masculino\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/pacientes/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Zezinho")))
				.andExpect(jsonPath("$.sexo", is("Masculino")));
	}

	@Test
	void medicoControllerAPIPOSTGETTest() throws Exception {
		MvcResult result;
		String resultStr;
		JSONObject objJson;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/medicos")
				.content("{\"nome\":\"Djonatan\",\"crm\":\"999\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medicos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Djonatan")))
				.andExpect(jsonPath("$.crm", is("999")));
	}

	@Test
	void procedimentoAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/procedimentos")
				.content("{\"descricao\":\"Cirurgia\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is("Cirurgia")));

	}

	@Test
	void cidadeAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/cidades")
		.content("{\"nome\":\"Araquari\"}")
		.header("Authorization", "Bearer " + jwtToken)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/cidades/" + objJson.getString("id"))
		.header("Authorization", "Bearer " + jwtToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Araquari")));
		
	}

	@Test
	void procedimentoRealizadoAPIPOSTGETTest() throws Exception {
		String resultStr;
		MvcResult result;

		if (jwtToken == null) {
			geraToken();
		}

		String idProcedimento = criaProcedimento("Teste");
		JSONObject procedimento = new JSONObject();
		procedimento.put("id", idProcedimento);
		procedimento.put("descricao", "Teste");
		
		JSONObject objJson = new JSONObject(); 
		objJson.put("descricao", "Teste");
		objJson.put("valor", 1234);
		objJson.put("procedimento", procedimento);

		result = mockMvc.perform(post("/api/v1/procedimentos/realizados")
				.content(objJson.toString()).header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		JSONObject objJsonResponse = new JSONObject(result.getResponse().getContentAsString());

		mockMvc.perform(get("/api/v1/procedimentos/realizados/" + objJsonResponse.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is("Teste")))
				.andExpect(jsonPath("$.valor", is(1234.0)));

	}

	private String criaProcedimento(String descricao) throws Exception {
		String idProcedimento, resultStr;
		JSONObject objJson;
		MvcResult result;

		result = mockMvc.perform(post("/api/v1/procedimentos")
				.content("{\"descricao\":\"" + descricao + "\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);
		idProcedimento = objJson.getString("id");

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is(descricao)));

		return idProcedimento;
	}
}
