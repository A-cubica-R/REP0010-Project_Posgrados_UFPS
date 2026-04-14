package posgrados.ufps.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import posgrados.ufps.demo.dto.DocumentoDTO;
import posgrados.ufps.demo.entity.DocumentoEntity;

public interface DocumentoService {

    DocumentoEntity cargarDocumento(DocumentoDTO dto, MultipartFile file) throws IOException;

    List<DocumentoEntity> listarDocumentos(Integer idAspirante);

    byte[] descargarDocumento(Integer idDocumento);

    DocumentoEntity buscarPorId(Integer idDocumento);
}
