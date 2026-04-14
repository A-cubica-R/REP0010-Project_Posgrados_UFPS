package posgrados.ufps.demo.dto;

public class DocumentoDTO {

    private Integer tipoDocumento;
    private Integer aspirante;
    private Integer estado;
    
    public DocumentoDTO() {
    }

    public DocumentoDTO(Integer tipoDocumento, Integer aspirante, Integer estado) {
        this.tipoDocumento = tipoDocumento;
        this.aspirante = aspirante;
        this.estado = estado;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getAspirante() {
        return aspirante;
    }

    public void setAspirante(Integer aspirante) {
        this.aspirante = aspirante;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    
}
