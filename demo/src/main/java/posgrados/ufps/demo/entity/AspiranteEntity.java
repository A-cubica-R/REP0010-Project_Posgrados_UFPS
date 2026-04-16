package posgrados.ufps.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "aspirante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AspiranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private TipoDocumentoEntity idTipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "primer_nombre", nullable = false, length = 50)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false, length = 50)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genero", nullable = false)
    private GeneroEntity idGenero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_residencia")
    private ResidenciaEntity idResidencia;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "celular", nullable = false, length = 20)
    private String celular;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 50)
    private String correoElectronico;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oferta_academica", nullable = false)
    private OfertaAcademicaEntity idOfertaAcademica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_aspirante")
    private EstadoAspiranteEntity idEstadoAspirante;
}
