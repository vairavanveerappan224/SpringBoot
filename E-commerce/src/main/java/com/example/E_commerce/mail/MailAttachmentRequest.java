package com.example.E_commerce.mail;



import lombok.Data;

@Data
public class MailAttachmentRequest {

    private String to;
    private String subject;
    private String message;
    private String filePath; // absolute path
}
