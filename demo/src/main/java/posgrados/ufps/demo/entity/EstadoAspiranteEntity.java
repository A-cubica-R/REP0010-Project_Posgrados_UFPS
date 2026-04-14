package posgrados.ufps.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_aspirante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoAspiranteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", nullable = false, unique = true, length = 50)
	private String nombre;
}
