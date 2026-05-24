package ufps.edu.co.persistence.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documentosrequisitoprogramacohorte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentosrequisitoprogramacohorteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_docrequisito")
    private Integer idDocrequisito;

    @Column(name = "id_cohorte")
    private Integer idCohorte;

    @ManyToOne
    @JoinColumn(name = "id_docrequisito", referencedColumnName = "id", insertable = false, updatable = false)
    private DocumentosrequisitoprogramaEntity documentosrequisitoprograma;

    @ManyToOne
    @JoinColumn(name = "id_cohorte", referencedColumnName = "id", insertable = false, updatable = false)
    private CohorteEntity cohorte;
}
