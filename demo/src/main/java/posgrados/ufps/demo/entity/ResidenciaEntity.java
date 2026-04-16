package posgrados.ufps.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "residencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Residencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", nullable = false)
    private PaisEntity pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departamento", nullable = false)
    private DepartamentoEntity departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private MunicipioEntity municipio;
    
}
