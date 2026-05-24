package ufps.edu.co.persistence.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "documentosrequisitoconsejo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentosrequisitoconsejoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "tamanomaximo")
    private Integer tamanomaximo;

    @OneToMany(mappedBy = "documentosrequisitoconsejo")
    private List<DocumentosrequisitoconcejocohorteEntity> documentosrequisitoconcejocohorteList;
}
