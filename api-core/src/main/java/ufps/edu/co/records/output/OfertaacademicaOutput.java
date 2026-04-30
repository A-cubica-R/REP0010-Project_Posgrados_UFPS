package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record OfertaacademicaOutput(Integer id, String encuentros, Integer idPrograma, Integer idModalidad, Integer idJornada) implements OutputResponse {}
