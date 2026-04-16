package posgrados.ufps.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import posgrados.ufps.demo.dto.DocumentoDTO;
import posgrados.ufps.demo.entity.DocumentoEntity;
import posgrados.ufps.demo.entity.EstadoDocumentoEntity;
import posgrados.ufps.demo.entity.TipoDocumentoRequeridoEntity;
import posgrados.ufps.demo.repository.DocumentoRepository;
import posgrados.ufps.demo.repository.EstadoDocumentoRepository;
import posgrados.ufps.demo.repository.TipoDocumentoRepository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class DocumentoServiceImpl implements DocumentoRequeridoService {

        @Autowired
        private S3Client s3Client;

        @Value("${aws.bucket.name}")
        private String bucketName;

        @Autowired
        private DocumentoRepository documentoRepository;

        @Autowired
        private TipoDocumentoRepository tipoDocumentoRepository;

        @Autowired
        private EstadoDocumentoRepository estadoDocumentoRepository;

        @Override
        public DocumentoEntity cargarDocumento(DocumentoDTO dto, MultipartFile file) throws IOException {

                DocumentoEntity documento = new DocumentoEntity();

                TipoDocumentoRequeridoEntity tipoDocumento = tipoDocumentoRepository.findById(dto.getTipoDocumento())
                                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

                // Falta el aspirante !!!!

                EstadoDocumentoEntity estadoDocumento = estadoDocumentoRepository.findById(dto.getEstado())
                                .orElseThrow(() -> new RuntimeException("Estado de documento no encontrado"));

                String key = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                documento.setAspirante(dto.getAspirante());
                documento.setTipoDocumentoRequerido(tipoDocumento);
                documento.setEstado(estadoDocumento);
                documento.setUrl("/download/" + key);
                documento.setKeyFile(key);

                documentoRepository.save(documento);

                s3Client.putObject(PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build(),
                                RequestBody.fromBytes(file.getBytes()));
                return documento;
        }

        @Override
        public List<DocumentoEntity> listarDocumentos(Integer IdAspirante) {
                return documentoRepository.findByAspirante(IdAspirante);
        }

        @Override
        public byte[] descargarDocumento(Integer idDocumento) {
                DocumentoEntity documento = documentoRepository.findById(idDocumento)
                                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
                String key = documento.getKeyFile();
                ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(
                                GetObjectRequest.builder()
                                                .bucket(bucketName)
                                                .key(key)
                                                .build());
                return objectAsBytes.asByteArray();
        }

        @Override
        public DocumentoEntity buscarPorId(Integer idDocumento) {
                return documentoRepository.findById(idDocumento)
                                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        }
}
