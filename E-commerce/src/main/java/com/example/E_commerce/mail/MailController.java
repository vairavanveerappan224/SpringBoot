package com.example.E_commerce.mail;



import com.example.E_commerce.mail.MailRequest;
import com.example.E_commerce.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public String sendMail(@RequestBody MailRequest request) {
        mailService.sendWelcomeMail(
                request.getTo(),
                request.getName()
        );
        return "Mail sent successfully";
    }
    @PostMapping("/send-attachment")
    public String sendMailWithAttachment(
            @RequestBody MailAttachmentRequest request
    ) {
        mailService.sendMailWithAttachment(
                request.getTo(),
                request.getSubject(),
                request.getMessage(),
                request.getFilePath()
        );
        return "Mail with attachment sent successfully";
    }
}

