package ufps.edu.co.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private Resend resend;

    @Value("${mail.from}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(fromEmail)
                .to("jljb1704@gmail.com")
                .subject(subject)
                .html(htmlBody)
                .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Error enviando email: " + e.getMessage(), e);
        }
    }
}
