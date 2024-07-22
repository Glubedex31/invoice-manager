package server;

import com.itextpdf.html2pdf.HtmlConverter;
import commons.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileOutputStream;

@Service
public class PdfGenerationService {

    @Autowired
    private TemplateEngine templateEngine;

    public void generateInvoicePdf(Invoice invoice) throws Exception {
        Context context = new Context();
        context.setVariable("invoice", invoice);

        String htmlContent = templateEngine.process("invoiceTemplate.html", context);

        HtmlConverter.convertToPdf(htmlContent,
            new FileOutputStream("client/src/main/resources/invoices/invoice_"
                + invoice.getId() + ".pdf"));

        System.out.println("PDF Generated");
    }
}
