package posgrados.ufps.demo.dto;

public class DocumentoDTO {

    private Integer tipoDocumento;
    private String formato;
    private Integer aspirante;
    private Integer estado;
    
    public DocumentoDTO() {
    }

    public DocumentoDTO(Integer tipoDocumento, String formato, Integer aspirante, Integer estado) {
        this.tipoDocumento = tipoDocumento;
        this.formato = formato;
        this.aspirante = aspirante;
        this.estado = estado;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
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
