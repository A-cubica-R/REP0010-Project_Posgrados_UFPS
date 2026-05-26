package ufps.edu.co.rest.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosrequisitoconcejocohorteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idDocrequisito;
    private Integer idCohorte;
}
