package ufps.edu.co.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class S3ArchivoDTO implements Serializable {

    private Integer id;
    private LocalDate fechacargue;
    private Integer idEstadodocumento;
    private Integer idTipodocumento;
    private Integer idAdministrativo;
    private Integer idPlazo;
    private Integer idAspirante;
}