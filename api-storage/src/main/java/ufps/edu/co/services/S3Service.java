package ufps.edu.co.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import ufps.edu.co.processor.DocumentoProcessor;
import ufps.edu.co.records.input.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.output.DocumentoOutput;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    public byte[] descargarDocumento(DOCUMENTO_FIND input) {
        DocumentoOutput output = documentoProcessor.findById(input);
        String key = output.keyfile();
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
        return objectAsBytes.asByteArray();
    }
}