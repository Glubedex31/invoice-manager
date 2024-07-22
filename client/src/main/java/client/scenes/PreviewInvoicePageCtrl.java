package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Invoice;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class PreviewInvoicePageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private Invoice invoice;
    private final ServerUtils serverUtils;

    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveButtonReceipt;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private ScrollPane scrollPane;


    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public PreviewInvoicePageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
                                  ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.clientUtils = clientUtils;
        this.serverUtils = serverUtils;
    }

    /**
     * Initialize the controller.
     * @param location The location
     * @param resources The resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        updateLanguage();
        if (invoice != null) {
            try {
                File file = new File("src/main/resources/invoices/invoice_" + invoice.getId() + ".pdf");
                PDDocument document = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(document);

                VBox box = new VBox(5);
                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage bufferedImage = renderer.renderImageWithDPI(
                        page, 150, ImageType.RGB);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(scrollPane.getWidth() - 20);
                    imageView.setPreserveRatio(true);
                    box.getChildren().add(imageView);
                }

                scrollPane.setContent(box);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        backButton.setText(map.get("settings_back"));
        saveButton.setText(map.get("settings_save"));
        saveButtonReceipt.setText(map.get("preview_save_receipt"));
        deleteButton.setText(map.get("preview_delete"));
        editButton.setText(map.get("preview_edit"));
    }

    /**
     * Gets the invoice.
     * @return invoice The invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Sets the invoice.
     * @param invoice The invoice
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * Handles the save button.
     */
    public void handleSave() {
        mainCtrl.showIncomeMenuPage();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showStartPage();
    }

    /**
     * Handles the delete button.
     */
    public void handleDelete() {
        //TODO: Implement delete invoice
    }

    /**
     * Handles the edit button.
     */
    public void handleEdit() {
        //TODO: Implement edit invoice
    }

    /**
     * Handles the save receipt button.
     */
    public void handleSaveReceipt() {
        //TODO: Implement save receipt
    }
}
