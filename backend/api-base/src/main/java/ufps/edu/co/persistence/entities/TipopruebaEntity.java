package ufps.edu.co.persistence.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "tipoprueba")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipopruebaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "descripcion", length = 65535)
    private String descripcion;

    @OneToMany(mappedBy = "tipoprueba")
    private List<PruebaEntity> pruebaList;
}
