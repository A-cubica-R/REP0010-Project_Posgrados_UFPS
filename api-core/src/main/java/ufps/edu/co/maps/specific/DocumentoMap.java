package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.DocumentoInput.*;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.records.output.entity.EstadodocumentoOutput;
import ufps.edu.co.records.output.entity.PlazoOutput;
import ufps.edu.co.records.output.entity.TipodocumentoOutput;
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
        DocumentoDTO dto = new DocumentoDTO();
        dto.setFechacargue(input.fechacargue());
        dto.setIdEstadodocumento(input.idEstadodocumento());
        dto.setIdTipodocumento(input.idTipodocumento());
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setIdPlazo(input.idPlazo());
        dto.setIdAspirante(input.idAspirante());
        return dto;
    }

    @Override
    protected DocumentoDTO toDtoUpdate(DOCUMENTO_UPDATE input) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(input.id());
        dto.setFechacargue(input.fechacargue());
        dto.setIdEstadodocumento(input.idEstadodocumento());
        dto.setIdTipodocumento(input.idTipodocumento());
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setIdPlazo(input.idPlazo());
        dto.setIdAspirante(input.idAspirante());
        return dto;
    }

    @Override
    protected DocumentoDTO toDtoDelete(DOCUMENTO_DELETE input) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected DocumentoDTO toDtoPatch(DOCUMENTO_PATCH input) {
        DocumentoDTO.DocumentoDTOBuilder builder = DocumentoDTO.builder()
                .id(input.id());

        if (input.fechacargue() != null) {
            builder.fechacargue(input.fechacargue());
        }
        if (input.idEstadodocumento() != null) {
            builder.idEstadodocumento(input.idEstadodocumento());
        }
        if (input.idTipodocumento() != null) {
            builder.idTipodocumento(input.idTipodocumento());
        }
        if (input.idAdministrativo() != null) {
            builder.idAdministrativo(input.idAdministrativo());
        }
        if (input.idPlazo() != null) {
            builder.idPlazo(input.idPlazo());
        }
        if (input.idAspirante() != null) {
            builder.idAspirante(input.idAspirante());
        }

        return builder.build();
    }

    @Override
    protected DocumentoDTO toDtoFind(DOCUMENTO_FIND input) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public DocumentoOutput toOutput(DocumentoDTO dto) {
        if (dto == null) {
            return null;
        }

        PlazoOutput plazo = null;
        if (dto.getPlazo() != null) {
            plazo = PlazoOutput.builder()
                    .id(dto.getPlazo().getId())
                    .fechainicio(dto.getPlazo().getFechainicio())
                    .fechafin(dto.getPlazo().getFechafin())
                    .build();
        }

        TipodocumentoOutput tipodocumento = null;
        if (dto.getTipodocumento() != null) {
            tipodocumento = TipodocumentoOutput.builder()
                    .id(dto.getTipodocumento().getId())
                    .nombre(dto.getTipodocumento().getNombre())
                    .descripcion(dto.getTipodocumento().getDescripcion())
                    .extension(dto.getTipodocumento().getExtension())
                    .tamanomaximo(dto.getTipodocumento().getTamanomaximo())
                    .build();
        }

        EstadodocumentoOutput estadodocumento = null;
        if (dto.getEstadodocumento() != null) {
            estadodocumento = EstadodocumentoOutput.builder()
                    .id(dto.getEstadodocumento().getId())
                    .estado(dto.getEstadodocumento().getEstado())
                    .build();
        }

        AdministrativoOutput administrativo = null;
        if (dto.getAdministrativo() != null) {
            administrativo = AdministrativoOutput.builder()
                    .id(dto.getAdministrativo().getId())
                    .build();
        }

        AspiranteOutput aspirante = null;
        if (dto.getAspirante() != null) {
            aspirante = AspiranteOutput.builder()
                    .id(dto.getAspirante().getId())
                    .build();
        }

        return new DocumentoOutput(dto.getId(), dto.getFechacargue(), plazo, tipodocumento, estadodocumento, administrativo, aspirante);
    }

    public List<DocumentoOutput> toOutputList(List<DocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
