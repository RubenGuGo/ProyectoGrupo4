package es.cic.grupo.Backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.grupo.Backend.model.Obra;
import es.cic.grupo.Backend.model.Tipo;
import es.cic.grupo.Backend.repository.ObraRepositorio;
import es.cic.grupo.Backend.repository.TipoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
public class ObraIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObraRepositorio obraRepositorio;

	@Autowired
	private TipoRepositorio tipoRepositorio;

	@Autowired
	private ObjectMapper objectMapper;

	private Tipo tipo;
	private Obra obra1;
	private Obra obra2;

	@BeforeEach
	public void setup() {
		obraRepositorio.deleteAll();
		tipoRepositorio.deleteAll();

		tipo = new Tipo();
		tipo.setNombre("Tipo de prueba");

		obra1 = new Obra(null, "Nombre de prueba 1", "Autor de prueba 1", "2023", "Localización de prueba 1", "Descripción de prueba 1");
		obra2 = new Obra(null, "Nombre de prueba 2", "Autor de prueba 2", "2023", "Localización de prueba 2", "Descripción de prueba 2");

		tipo.addObra(obra1);
		tipo.addObra(obra2);

		tipoRepositorio.save(tipo);
        obraRepositorio.save(obra1);
        obraRepositorio.save(obra2);
	}

	@Test
	public void testAddObra() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/obras")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(obra1)))
				.andExpect(status().isOk())
				.andReturn();

		Obra nuevaObra = objectMapper.readValue(result.getResponse().getContentAsString(), Obra.class);
		assertThat(nuevaObra.getId()).isNotNull();
		assertThat(nuevaObra.getNombre()).isEqualTo(obra1.getNombre());
	}

	@Test
	public void testGetAllObras() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/obras"))
				.andExpect(status().isOk())
				.andReturn();

		List<Obra> obras = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Obra>>() {});
		assertThat(obras).hasSize(2);
	}

	@Test
	public void testGetObraById() throws Exception {
		Obra savedObra = obraRepositorio.save(obra1);

		MvcResult result = mockMvc.perform(get("/api/obras/" + savedObra.getId()))
				.andExpect(status().isOk())
				.andReturn();

		Obra fetchedObra = objectMapper.readValue(result.getResponse().getContentAsString(), Obra.class);
		assertThat(fetchedObra.getId()).isEqualTo(savedObra.getId());
	}

	@Test
	public void testUpdateObra() throws Exception {
		Obra savedObra = obraRepositorio.save(obra1);
		savedObra.setNombre("Nombre actualizado");

		MvcResult result = mockMvc.perform(put("/api/obras/" + savedObra.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(savedObra)))
				.andExpect(status().isOk())
				.andReturn();

		Obra updatedObra = objectMapper.readValue(result.getResponse().getContentAsString(), Obra.class);
		assertThat(updatedObra.getNombre()).isEqualTo("Nombre actualizado");
	}

	@Test
	public void testDeleteObraById() throws Exception {
		Obra savedObra = obraRepositorio.save(obra1);

		mockMvc.perform(delete("/api/obras/" + savedObra.getId()))
				.andExpect(status().isNoContent());

		assertThat(obraRepositorio.existsById(savedObra.getId())).isFalse();
	}
}