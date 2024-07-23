package client.scenes;

import com.google.inject.Inject;
import commons.Invoice;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import client.utils.ClientUtils;
import client.utils.ConfigUtils;

public class MainCtrl {
    private Stage primaryStage;
    private StartPageCtrl startPageCtrl;
    private Scene startPage;
    private SettingsPageCtrl settingsPageCtrl;
    private Scene settingsPage;
    private IncomeMenuPageCtrl incomeMenuPageCtrl;
    private Scene incomeMenuPage;
    private NewInvoicePageCtrl newInvoicePageCtrl;
    private Scene newInvoicePage;
    private PreviewInvoicePageCtrl previewInvoicePageCtrl;
    private Scene previewInvoicePage;
    private InvoiceSummaryPageCtrl invoiceSummaryPageCtrl;
    private Scene invoiceSummaryPage;
    @Inject
    private ClientUtils clientUtils;
    @Inject
    private ConfigUtils configUtils;

    /**
     * Constructor for the main controller.
     * @param primaryStage The primary stage
     * @param startPage The start page
     * @param settingsPage The settings page
     * @param incomeMenuPage The income menu page
     * @param newInvoicePage The new invoice page
     * @param previewInvoicePage The preview invoice page
     */
    public void initialize(Stage primaryStage,
                           Pair<StartPageCtrl, Parent> startPage,
                           Pair<SettingsPageCtrl, Parent> settingsPage,
                           Pair<IncomeMenuPageCtrl, Parent> incomeMenuPage,
                           Pair<NewInvoicePageCtrl, Parent> newInvoicePage,
                           Pair<PreviewInvoicePageCtrl, Parent> previewInvoicePage,
                           Pair<InvoiceSummaryPageCtrl, Parent> invoiceSummaryPage) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.startPage = new Scene(startPage.getValue());

        this.settingsPageCtrl = settingsPage.getKey();
        this.settingsPage = new Scene(settingsPage.getValue());

        this.incomeMenuPageCtrl = incomeMenuPage.getKey();
        this.incomeMenuPage = new Scene(incomeMenuPage.getValue());

        this.newInvoicePageCtrl = newInvoicePage.getKey();
        this.newInvoicePage = new Scene(newInvoicePage.getValue());

        this.previewInvoicePageCtrl = previewInvoicePage.getKey();
        this.previewInvoicePage = new Scene(previewInvoicePage.getValue());

        this.invoiceSummaryPageCtrl = invoiceSummaryPage.getKey();
        this.invoiceSummaryPage = new Scene(invoiceSummaryPage.getValue());

        clientUtils.setLanguage(configUtils.getLanguage());

        showStartPage();
        primaryStage.show();
    }

    /**
     * Set the primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Show the start page.
     */
    public void showStartPage() {
        primaryStage.setTitle("Start Page");
        primaryStage.setScene(startPage);
        startPageCtrl.refresh();
    }

    /**
     * Show the settings page.
     */
    public void showSettingsPage() {
        primaryStage.setTitle("Settings Page");
        primaryStage.setScene(settingsPage);
        settingsPageCtrl.refresh();
    }

    /**
     * Show the income menu page.
     */
    public void showIncomeMenuPage() {
        primaryStage.setTitle("Income Menu Page");
        primaryStage.setScene(incomeMenuPage);
        incomeMenuPageCtrl.refresh();
    }

    /**
     * Show the new invoice page.
     */
    public void showNewInvoicePage() {
        primaryStage.setTitle("New Invoice Page");
        primaryStage.setScene(newInvoicePage);
        newInvoicePageCtrl.refresh(false, null);
    }

    /**
     * Show the edit invoice page.
     * @param invoice The invoice to edit
     */
    public void showEditInvoicePage(Invoice invoice) {
        primaryStage.setTitle("Edit Invoice Page");
        primaryStage.setScene(newInvoicePage);
        newInvoicePageCtrl.refresh(true, invoice);
    }

    /**
     * Show the preview invoice page.
     * @param invoice The invoice to preview
     */
    public void showPreviewInvoicePage(Invoice invoice) {
        primaryStage.setTitle("Preview Invoice Page");
        primaryStage.setScene(previewInvoicePage);
        previewInvoicePageCtrl.setInvoice(invoice);
        previewInvoicePageCtrl.refresh();
    }

    /**
     * Show the invoice summary page.
     */
    public void showInvoiceSummaryPage() {
        primaryStage.setTitle("Invoice Summary Page");
        primaryStage.setScene(invoiceSummaryPage);
        invoiceSummaryPageCtrl.refresh();
    }
}
