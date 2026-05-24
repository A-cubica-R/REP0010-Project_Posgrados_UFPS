package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.DocumentoInput.*;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.rest.dto.DocumentoDTO;

@Component
public class DocumentoMap extends
        GlobalMapper<DOCUMENTO_CREATE, DOCUMENTO_UPDATE, DOCUMENTO_DELETE, DOCUMENTO_PATCH, DOCUMENTO_FIND, DocumentoOutput, DocumentoDTO> {

    public DocumentoMap() {
        super(DOCUMENTO_CREATE.class, DOCUMENTO_UPDATE.class, DOCUMENTO_DELETE.class, DOCUMENTO_PATCH.class,
                DOCUMENTO_FIND.class);
    }

    @Override
    protected DocumentoDTO toDtoCreate(DOCUMENTO_CREATE input) {
        return DocumentoDTO.builder()
                .enlaceurl(input.enlaceurl())
                .fechacargue(input.fechacargue())
                .idAdministrativo(input.idAdministrativo())
                .idAspirante(input.idAspirante())
                .idEstadodocumento(input.idEstadodocumento())
                .idPlazo(input.idPlazo())
                .keyfile(input.keyfile())
                .observaciones(input.observaciones())
                .idDocumentosrequisitoconsejocohorte(input.idDocumentosrequisitoconsejocohorte())
                .idDocumentosrequisitoprogramacohorte(input.idDocumentosrequisitoprogramacohorte())
                .build();
    }

    @Override
    protected DocumentoDTO toDtoUpdate(DOCUMENTO_UPDATE input) {
        return DocumentoDTO.builder()
                .id(input.id())
                .enlaceurl(input.enlaceurl())
                .fechacargue(input.fechacargue())
                .idAdministrativo(input.idAdministrativo())
                .idAspirante(input.idAspirante())
                .idEstadodocumento(input.idEstadodocumento())
                .idPlazo(input.idPlazo())
                .keyfile(input.keyfile())
                .observaciones(input.observaciones())
                .idDocumentosrequisitoconsejocohorte(input.idDocumentosrequisitoconsejocohorte())
                .idDocumentosrequisitoprogramacohorte(input.idDocumentosrequisitoprogramacohorte())
                .build();
    }

    @Override
    protected DocumentoDTO toDtoDelete(DOCUMENTO_DELETE input) {
        return DocumentoDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected DocumentoDTO toDtoPatch(DOCUMENTO_PATCH input) {
        throw new UnsupportedOperationException("Función PATCH no implementada para Documento");
    }

    @Override
    protected DocumentoDTO toDtoFind(DOCUMENTO_FIND input) {
        return DocumentoDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public DocumentoOutput toOutput(DocumentoDTO dto) {
        if (dto == null)
            return null;

        AdministrativoMap administrativoMap = new AdministrativoMap();
        AspiranteMap aspiranteMap = new AspiranteMap();
        EstadodocumentoMap estadodocumentoMap = new EstadodocumentoMap();
        PlazoMap plazoMap = new PlazoMap();

        return DocumentoOutput.builder()
                .id(dto.getId())
                .enlaceurl(dto.getEnlaceurl())
                .fechacargue(dto.getFechacargue())
                .idAdministrativo(dto.getIdAdministrativo())
                .idAspirante(dto.getIdAspirante())
                .idEstadodocumento(dto.getIdEstadodocumento())
                .idPlazo(dto.getIdPlazo())
                .keyfile(dto.getKeyfile())
                .observaciones(dto.getObservaciones())
                .idDocumentosrequisitoconsejocohorte(dto.getIdDocumentosrequisitoconsejocohorte())
                .idDocumentosrequisitoprogramacohorte(dto.getIdDocumentosrequisitoprogramacohorte())
                .administrativo(
                        dto.getAdministrativo() != null ? administrativoMap.toOutput(dto.getAdministrativo()) : null)
                .aspirante(dto.getAspirante() != null ? aspiranteMap.toOutput(dto.getAspirante()) : null)
                .estadodocumento(
                        dto.getEstadodocumento() != null ? estadodocumentoMap.toOutput(dto.getEstadodocumento()) : null)
                .plazo(dto.getPlazo() != null ? plazoMap.toOutput(dto.getPlazo()) : null)
                .build();
                // TODO: de momento no existe forma de determinar a donde se suben los documentos. Faltan las FK faltantes
    }

    public List<DocumentoOutput> toOutputList(List<DocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
