package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record OtrosvaloresOutput (
    Integer id,
    Boolean carnet,
    Boolean estampilla,
    Boolean seguro,
    List<ProgramaOutput> programaList
) implements OutputResponse {}
