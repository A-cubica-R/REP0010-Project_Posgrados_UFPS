package posgrados.ufps.demo.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "documento", nullable = false, unique = true, length = 30)
    private String documento;
}
