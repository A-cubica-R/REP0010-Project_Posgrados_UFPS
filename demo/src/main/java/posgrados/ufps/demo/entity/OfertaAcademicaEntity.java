package posgrados.ufps.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
	name = "oferta_academica",
	uniqueConstraints = @UniqueConstraint(columnNames = { "codigo_programa", "nombre_cohorte" })
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaAcademicaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "codigo_programa", nullable = false)
	private Integer codigoPrograma;

	@Column(name = "nombre_cohorte", nullable = false, length = 20)
	private String nombreCohorte;

	@Column(name = "cupos", nullable = false)
	private Integer cupos;
}
