package posgrados.ufps.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
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
    private TipoDocumentoEntity tipoDocumento;

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

    @Column(name = "titulo_pregrado", nullable = false, length = 50)
    private String tituloPregrado;

    @Column(name = "universidad_egreso", nullable = false, length = 50)
    private String universidadEgreso;

    @Column(name = "fecha_graduacion", nullable = false)
    private LocalDate fechaGraduacion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "celular", nullable = false, length = 20)
    private String celular;

    @Column(name = "direccion", nullable = false, columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 50)
    private String correoElectronico;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "promedio_acumulado", precision = 3, scale = 2)
    private BigDecimal promedioAcumulado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oferta_academica", nullable = false)
    private OfertaAcademicaEntity ofertaAcademica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_aspirante")
    private EstadoAspiranteEntity estadoAspirante;
}
