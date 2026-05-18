package ufps.edu.co.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private Resend resend;

    @Value("${mail.from}")
    private String fromEmail;

    private static final String RECIPIENT = "jljb1704@gmail.com";

    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(RECIPIENT)
                .subject(subject)
                .html(htmlBody)
                .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Error enviando email: " + e.getMessage(), e);
        }
    }

    public void sendPdfToDirector(String directorNombre, String cohorteNombre, byte[] pdfBytes) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
            String safeNombre = cohorteNombre.replaceAll("[^a-zA-Z0-9]", "_");
            String fileName = "admitidos_" + safeNombre + "_" + timestamp + ".pdf";

            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .content(Base64.getEncoder().encodeToString(pdfBytes))
                    .build();

            String html = "<p>Estimado/a <strong>" + directorNombre + "</strong>,</p>"
                    + "<p>Adjunto encontrará la lista oficial de aspirantes admitidos "
                    + "en la cohorte <strong>" + cohorteNombre + "</strong>.</p>";

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(RECIPIENT)
                    .subject("Aspirantes admitidos - " + cohorteNombre)
                    .html(html)
                    .attachments(List.of(attachment))
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Error enviando correo con PDF al director: " + e.getMessage(), e);
        }
    }
}
