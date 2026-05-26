package maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ufps.edu.co.maps.specific.DocumentosrequisitoprogramaMap;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.DOCUMENTOSREQUISITOPROGRAMA_CREATE;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.DOCUMENTOSREQUISITOPROGRAMA_DELETE;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.DOCUMENTOSREQUISITOPROGRAMA_FIND;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.DOCUMENTOSREQUISITOPROGRAMA_PATCH;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.DOCUMENTOSREQUISITOPROGRAMA_UPDATE;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramaDTO;

public class DocumentorequisitoprogramaMap_test {

	@Test
	void toDtoMapsCreateFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DOCUMENTOSREQUISITOPROGRAMA_CREATE input = new DOCUMENTOSREQUISITOPROGRAMA_CREATE(
				"Documento A",
				1);

		DocumentosrequisitoprogramaDTO dto = mapper.toDto(input);

		assertNotNull(dto);
		assertNull(dto.getId());
		assertEquals("Documento A", dto.getNombre());
		assertEquals(Integer.valueOf(512), dto.getTamanomaximo());
		assertEquals("https://example.com/formato-a.pdf", dto.getUrlformato());
	}

	@Test
	void toDtoMapsUpdateFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DOCUMENTOSREQUISITOPROGRAMA_UPDATE input = new DOCUMENTOSREQUISITOPROGRAMA_UPDATE(
				10,
				"Documento B",
				1024,
				"https://example.com/formato-b.pdf");

		DocumentosrequisitoprogramaDTO dto = mapper.toDto(input);

		assertNotNull(dto);
		assertEquals(Integer.valueOf(10), dto.getId());
		assertEquals("Documento B", dto.getNombre());
		assertEquals(Integer.valueOf(1024), dto.getTamanomaximo());
		assertEquals("https://example.com/formato-b.pdf", dto.getUrlformato());
	}

	@Test
	void toDtoMapsDeleteFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DOCUMENTOSREQUISITOPROGRAMA_DELETE input = new DOCUMENTOSREQUISITOPROGRAMA_DELETE(15);

		DocumentosrequisitoprogramaDTO dto = mapper.toDto(input);

		assertNotNull(dto);
		assertEquals(Integer.valueOf(15), dto.getId());
		assertNull(dto.getNombre());
		assertNull(dto.getTamanomaximo());
		assertNull(dto.getUrlformato());
	}

	@Test
	void toDtoMapsFindFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DOCUMENTOSREQUISITOPROGRAMA_FIND input = new DOCUMENTOSREQUISITOPROGRAMA_FIND(20);

		DocumentosrequisitoprogramaDTO dto = mapper.toDto(input);

		assertNotNull(dto);
		assertEquals(Integer.valueOf(20), dto.getId());
		assertNull(dto.getNombre());
		assertNull(dto.getTamanomaximo());
		assertNull(dto.getUrlformato());
	}

	@Test
	void toDtoMapsPatchFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DOCUMENTOSREQUISITOPROGRAMA_PATCH input = new DOCUMENTOSREQUISITOPROGRAMA_PATCH(
				25,
				"Documento C",
				2048,
				"https://example.com/formato-c.pdf");

		DocumentosrequisitoprogramaDTO dto = mapper.toDto(input);

		assertNotNull(dto);
		assertEquals(Integer.valueOf(25), dto.getId());
		assertEquals("Documento C", dto.getNombre());
		assertEquals(Integer.valueOf(2048), dto.getTamanomaximo());
		assertEquals("https://example.com/formato-c.pdf", dto.getUrlformato());
	}

	@Test
	void toOutputMapsDtoFields() {
		DocumentosrequisitoprogramaMap mapper = new DocumentosrequisitoprogramaMap();
		DocumentosrequisitoprogramaDTO dto = DocumentosrequisitoprogramaDTO.builder()
				.id(30)
				.nombre("Documento D")
				.tamanomaximo(4096)
				.urlformato("https://example.com/formato-d.pdf")
				.build();

		var output = mapper.toOutput(dto);

		assertNotNull(output);
		assertEquals(Integer.valueOf(30), output.id());
		assertEquals("Documento D", output.nombre());
		assertEquals(Integer.valueOf(4096), output.tamanomaximo());
		assertEquals("https://example.com/formato-d.pdf", output.urlformato());
	}
}
