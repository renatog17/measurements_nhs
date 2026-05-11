package com.nhst.medicoes.comunication;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(EmailData emailData) {

        System.out.println("""
                
                ================= EMAIL =================
                
                FROM:
                %s <%s@zendaavip.com.br>
                
                TO:
                %s <%s>
                
                SUBJECT:
                %s
                
                TEXT:
                %s
                
                HTML:
                %s
                
                =========================================
                """.formatted(
                emailData.getFromName(),
                emailData.getFromEmail(),

                emailData.getToNome(),
                emailData.getToEmail(),

                emailData.getAssunto(),

                emailData.getTextoSimples(),

                emailData.getHtmlConteudo()
        ));
    }
}