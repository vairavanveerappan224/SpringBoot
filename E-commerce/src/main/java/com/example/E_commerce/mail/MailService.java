package com.example.E_commerce.mail;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeMail(String to, String name) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Welcome to E-Commerce App");

            String html = """
                <html>
                <body>
                    <h2>Hello %s 👋</h2>
                    <p>Your account has been created successfully.</p>
                </body>
                </html>
                """.formatted(name);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Mail sending failed");
        }
    }
    public void sendMailWithAttachment(
            String to,
            String subject,
            String message,
            String filePath
    ) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // true = multipart (required for attachments)
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, false);

            FileSystemResource file =
                    new FileSystemResource(new File(filePath));

            helper.addAttachment(file.getFilename(), file);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send mail with attachment");
        }
    }
}
