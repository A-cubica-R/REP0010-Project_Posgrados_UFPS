package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record OtrosvaloresOutput (
    Integer id,
    boolean carnet,
    boolean estampilla,
    boolean seguro
) implements OutputResponse {}
