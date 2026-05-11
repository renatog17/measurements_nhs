package com.nhst.medicoes.comunication.templates;

import com.nhst.medicoes.comunication.EmailData;
import com.nhst.medicoes.comunication.EmailService;
import com.nhst.medicoes.domain.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceAvailableEmail implements EmailTemplate {
    private final EmailService emailService;

    public InvoiceAvailableEmail(
            EmailService emailService
    ) {
        this.emailService = emailService;
    }

    @Override
    public void enviarEmail(Object object) {

        Invoice invoice = (Invoice) object;

        String customerName =
                invoice.getInstallation()
                        .getClient()
                        .getName();

        String customerEmail =
                invoice.getInstallation()
                        .getClient()
                        .getEmail();

        String textoSimples =
                "Olá " + customerName + ",\n\n"
                        + "Sua fatura já está disponível para pagamento.\n\n"
                        + "Valor total: R$ "
                        + invoice.getTotalAmountDue() + "\n"
                        + "Vencimento: "
                        + invoice.getBankSlips().getFirst().getDueDate() + "\n\n"
                        + "Linha digitável:\n"
                        + invoice.getBankSlips().getFirst().getDigitableLine()
                        + "\n\n"
                        + "Atenciosamente,\n"
                        + "Equipe NHST";

        String htmlConteudo =
                "<html><body>"
                        + "<h2>Fatura disponível</h2>"

                        + "<p>Olá <strong>"
                        + customerName
                        + "</strong>,</p>"

                        + "<p>Sua fatura já está disponível para pagamento.</p>"

                        + "<p><strong>Valor:</strong> R$ "
                        + invoice.getTotalAmountDue()
                        + "</p>"

                        + "<p><strong>Vencimento:</strong> "
                        + invoice.getBankSlips().getFirst().getDueDate()
                        + "</p>"

                        + "<p><strong>Linha digitável:</strong></p>"

                        + "<div style='padding:10px;"
                        + "background:#f5f5f5;"
                        + "border-radius:8px;"
                        + "font-size:18px;'>"

                        + invoice.getBankSlips()
                        .getFirst()
                        .getDigitableLine()

                        + "</div>"

                        + "<br>"

                        + "<p>"
                        + "Realize o pagamento até a data de vencimento "
                        + "para evitar juros e multas."
                        + "</p>"

                        + "<br>"

                        + "<p>Atenciosamente,<br>"
                        + "Equipe NHST</p>"

                        + "</body></html>";

        EmailData emailData = new EmailData(
                "nhst",
                "Equipe NHST",
                customerEmail,
                customerName,
                "Sua fatura está disponível",
                textoSimples,
                htmlConteudo
        );

        emailService.sendEmail(emailData);
    }
}