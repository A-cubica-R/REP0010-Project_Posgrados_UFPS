package ufps.edu.co.records.output.cases;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;

@Builder
public record Listdocumentosprogramaconsejo(
    List<DocumentosrequisitoprogramaOutput> documentosConsejo,
    List<DocumentosrequisitoprogramaOutput> documentosPrograma
) {
}
