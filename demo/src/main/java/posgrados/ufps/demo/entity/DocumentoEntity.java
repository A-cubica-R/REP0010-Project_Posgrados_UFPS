package posgrados.ufps.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "documento")
public class DocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Implementar así mientras se crea el Aspirante
    @Column(name = "id_aspirante", nullable = false)
    @NotNull
    private Integer idAspirante;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento_requerido", nullable = false, updatable = false)
    @NotNull
    private TipoDocumentoRequeridoEntity idTipoDocumentoRequerido;

    @ManyToOne
    @JoinColumn(name = "id_estado_documento", nullable = false)
    @NotNull
    private EstadoDocumentoEntity idEstadoDocumento;

    // Implementar administrativo
    private Integer idAdministrativoReviso;

    @Column(name = "fecha_maxima_cargue")
    @NotNull
    private Date fechaMaximaCargue;

    @Column(name = "fecha_cargue")
    @NotNull
    private Date fechaCargue;

    @Column(nullable = false, length = 255)
    @NotEmpty
    private String url;

    @Column(nullable = false, length = 255)
    @NotEmpty
    private String keyFile;

    public DocumentoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(Integer idAspirante) {
        this.idAspirante = idAspirante;
    }

    public TipoDocumentoRequeridoEntity getIdTipoDocumentoRequerido() {
        return idTipoDocumentoRequerido;
    }

    public void setIdTipoDocumentoRequerido(TipoDocumentoRequeridoEntity idTipoDocumentoRequerido) {
        this.idTipoDocumentoRequerido = idTipoDocumentoRequerido;
    }

    public EstadoDocumentoEntity getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    public void setIdEstadoDocumento(EstadoDocumentoEntity idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    public Integer getIdAdministrativoReviso() {
        return idAdministrativoReviso;
    }

    public void setIdAdministrativoReviso(Integer idAdministrativoReviso) {
        this.idAdministrativoReviso = idAdministrativoReviso;
    }

    public Date getFechaMaximaCargue() {
        return fechaMaximaCargue;
    }

    public void setFechaMaximaCargue(Date fechaMaximaCargue) {
        this.fechaMaximaCargue = fechaMaximaCargue;
    }

    public Date getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
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

    

}
