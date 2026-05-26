package ufps.edu.co.persistence.projections;

import java.math.*;

public interface CriterioCalificacionView {
    Integer getId();
    BigDecimal getPesoSnapshot();
    String getNombreCriterio();
    BigDecimal getPuntajeObtenido();
}
