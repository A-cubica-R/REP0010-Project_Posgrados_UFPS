package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record OtrosvaloresOutput (
    Integer id,
    boolean carnet,
    boolean estampilla,
    boolean seguro
) implements OutputResponse {}
