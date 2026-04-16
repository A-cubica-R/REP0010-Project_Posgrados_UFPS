package posgrados.ufps.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_documento_requerido")
public class TipoDocumentoRequeridoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String nombre;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String description;

    @Column(length = 30, nullable = false)
    @NotBlank
    private String formato;

    @Column(name = "tamano_maximo_mb",nullable = false)
    @NotNull
    private Integer tamanoMaximoMB;

    public String getFormato() {
        return formato;
    }
}
