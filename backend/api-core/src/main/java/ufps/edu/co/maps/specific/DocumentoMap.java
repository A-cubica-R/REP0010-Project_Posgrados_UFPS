package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.DocumentoInput.*;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.rest.dto.DocumentoDTO;

@Component
public class DocumentoMap extends
        GlobalMapper<DOCUMENTO_CREATE, DOCUMENTO_UPDATE, DOCUMENTO_DELETE, DOCUMENTO_PATCH, DOCUMENTO_FIND, DocumentoOutput, DocumentoDTO> {

    @Autowired private AdministrativoMap administrativoMap;
    @Autowired private AspiranteMap aspiranteMap;
    @Autowired private EstadodocumentoMap estadodocumentoMap;
    @Autowired private PlazoMap plazoMap;
    @Autowired private TipodocumentoMap tipodocumentoMap;

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
                .idTipodocumento(input.idTipodocumento())
                .keyfile(input.keyfile())
                .observaciones(input.observaciones())
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
                .idTipodocumento(input.idTipodocumento())
                .keyfile(input.keyfile())
                .observaciones(input.observaciones())
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
        // TODO funcion patch no implementada aún
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
        if (dto == null) return null;
        return DocumentoOutput.builder()
                .id(dto.getId())
                .enlaceurl(dto.getEnlaceurl())
                .fechacargue(dto.getFechacargue())
                .idAdministrativo(dto.getIdAdministrativo())
                .idAspirante(dto.getIdAspirante())
                .idEstadodocumento(dto.getIdEstadodocumento())
                .idPlazo(dto.getIdPlazo())
                .idTipodocumento(dto.getIdTipodocumento())
                .keyfile(dto.getKeyfile())
                .observaciones(dto.getObservaciones())
                .administrativo(mapOrNull(dto.getAdministrativo(), administrativoMap::toOutput))
                .aspirante(mapOrNull(dto.getAspirante(), aspiranteMap::toOutput))
                .estadodocumento(mapOrNull(dto.getEstadodocumento(), estadodocumentoMap::toOutput))
                .plazo(mapOrNull(dto.getPlazo(), plazoMap::toOutput))
                .tipodocumento(mapOrNull(dto.getTipodocumento(), tipodocumentoMap::toOutput))
                .build();
    }

    public List<DocumentoOutput> toOutputList(List<DocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
