package posgrados.ufps.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "documento")
public class DocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Implementar así mientras se crea el Aspirante
    @Column(name = "aspirante_id", nullable = false)
    private Integer aspirante;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false, updatable = false)
    private TipoDocumentoEntity tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoDocumentoEntity estado;

    @Column(nullable = false)
    @NotEmpty
    private String url;

    @Column(nullable = false)
    @NotEmpty
    private String keyFile;

    @Column(nullable = false)
    @NotEmpty
    private String formato;

    @Column(nullable = true)
    private String retroalimentacion;

    public DocumentoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAspirante() {
        return aspirante;
    }

    public void setAspirante(Integer aspirante) {
        this.aspirante = aspirante;
    }

    public TipoDocumentoEntity getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoEntity tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public EstadoDocumentoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoDocumentoEntity estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    

}
