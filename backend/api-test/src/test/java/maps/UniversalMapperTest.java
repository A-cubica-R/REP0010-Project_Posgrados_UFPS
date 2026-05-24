package maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ufps.edu.co.maps.specific.CriterioevaluacionMap;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIOEVALUACION_CREATE;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIOEVALUACION_DELETE;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIOEVALUACION_FIND;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIOEVALUACION_UPDATE;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;

public class UniversalMapperTest {

    @Test
    void toDtoMapsCreateFields() {
        CriterioevaluacionMap mapper = new CriterioevaluacionMap();
        CRITERIOEVALUACION_CREATE input = CRITERIOEVALUACION_CREATE.builder()
                .nombre("Crit A")
                .activo(true)
                .descripcion("Desc")
                .peso(new BigDecimal("1.25"))
                .idPrograma(7)
                .build();

        CriterioevaluacionDTO dto = mapper.toDto(input);

        assertNotNull(dto);
        assertEquals("Crit A", dto.getNombre());
        assertEquals(Boolean.TRUE, dto.getActivo());
        assertEquals("Desc", dto.getDescripcion());
        assertEquals(new BigDecimal("1.25"), dto.getPeso());
        assertEquals(7, dto.getIdprograma());
    }

    @Test
    void toDtoMapsUpdateWithNullOmissions() {
        CriterioevaluacionMap mapper = new CriterioevaluacionMap();
        CRITERIOEVALUACION_UPDATE input = CRITERIOEVALUACION_UPDATE.builder()
                .id(9)
                .nombre("Crit B")
                .activo(null)
                .descripcion(null)
                .peso(null)
                .idPrograma(null)
                .build();

        CriterioevaluacionDTO dto = mapper.toDto(input);

        assertNotNull(dto);
        assertEquals(9, dto.getId());
        assertEquals("Crit B", dto.getNombre());
        assertNull(dto.getActivo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getPeso());
        assertNull(dto.getIdprograma());
    }

    @Test
    void toDtoMapsDeleteFields() {
        CriterioevaluacionMap mapper = new CriterioevaluacionMap();
        CRITERIOEVALUACION_DELETE input = CRITERIOEVALUACION_DELETE.builder()
                .id(3)
                .build();

        CriterioevaluacionDTO dto = mapper.toDto(input);

        assertNotNull(dto);
        assertEquals(3, dto.getId());
        assertNull(dto.getNombre());
        assertNull(dto.getActivo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getPeso());
        assertNull(dto.getIdprograma());
    }

    @Test
    void toDtoMapsFindFields() {
        CriterioevaluacionMap mapper = new CriterioevaluacionMap();
        CRITERIOEVALUACION_FIND input = CRITERIOEVALUACION_FIND.builder()
                .id(4)
                .build();

        CriterioevaluacionDTO dto = mapper.toDto(input);

        assertNotNull(dto);
        assertEquals(4, dto.getId());
        assertNull(dto.getNombre());
        assertNull(dto.getActivo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getPeso());
        assertNull(dto.getIdprograma());
    }
}
