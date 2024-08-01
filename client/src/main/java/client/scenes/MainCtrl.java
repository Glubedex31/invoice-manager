package client.scenes;

import com.google.inject.Inject;
import commons.Invoice;
import commons.Payment;
import commons.Receipt;
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
    private PaymentMenuPageCtrl paymentMenuPageCtrl;
    private Scene paymentMenuPage;
    private NewExpensePageCtrl newExpensePageCtrl;
    private Scene newExpensePage;
    private ExpenseSummaryPageCtrl expenseSummaryPageCtrl;
    private Scene expenseSummaryPage;
    private PreviewExpensePageCtrl previewExpensePageCtrl;
    private Scene previewExpensePage;
    private NewReceiptPageCtrl newReceiptPageCtrl;
    private Scene newReceiptPage;
    private PreviewReceiptPageCtrl previewReceiptPageCtrl;
    private Scene previewReceiptPage;
    private ReceiptSummaryPageCtrl receiptSummaryPageCtrl;
    private Scene receiptSummaryPage;
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
     * @param invoiceSummaryPage The invoice summary page
     * @param paymentMenuPage The payment menu page
     * @param newExpensePage The new expense page
     * @param expenseSummaryPage The expense summary page
     * @param previewExpensePage The preview expense page
     * @param newReceiptPage The new receipt page
     * @param previewReceiptPage The preview receipt page
     * @param receiptSummaryPage The receipt summary page
     */
    public void initialize(Stage primaryStage,
                           Pair<StartPageCtrl, Parent> startPage,
                           Pair<SettingsPageCtrl, Parent> settingsPage,
                           Pair<IncomeMenuPageCtrl, Parent> incomeMenuPage,
                           Pair<NewInvoicePageCtrl, Parent> newInvoicePage,
                           Pair<PreviewInvoicePageCtrl, Parent> previewInvoicePage,
                           Pair<InvoiceSummaryPageCtrl, Parent> invoiceSummaryPage,
                           Pair<PaymentMenuPageCtrl, Parent> paymentMenuPage,
                           Pair<NewExpensePageCtrl, Parent> newExpensePage,
                           Pair<ExpenseSummaryPageCtrl, Parent> expenseSummaryPage,
                           Pair<PreviewExpensePageCtrl, Parent> previewExpensePage,
                           Pair<NewReceiptPageCtrl, Parent> newReceiptPage,
                           Pair<PreviewReceiptPageCtrl, Parent> previewReceiptPage,
                           Pair<ReceiptSummaryPageCtrl, Parent> receiptSummaryPage) {
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

        this.paymentMenuPageCtrl = paymentMenuPage.getKey();
        this.paymentMenuPage = new Scene(paymentMenuPage.getValue());

        this.newExpensePageCtrl = newExpensePage.getKey();
        this.newExpensePage = new Scene(newExpensePage.getValue());

        this.expenseSummaryPageCtrl = expenseSummaryPage.getKey();
        this.expenseSummaryPage = new Scene(expenseSummaryPage.getValue());

        this.previewExpensePageCtrl = previewExpensePage.getKey();
        this.previewExpensePage = new Scene(previewExpensePage.getValue());

        this.newReceiptPageCtrl = newReceiptPage.getKey();
        this.newReceiptPage = new Scene(newReceiptPage.getValue());

        this.previewReceiptPageCtrl = previewReceiptPage.getKey();
        this.previewReceiptPage = new Scene(previewReceiptPage.getValue());

        this.receiptSummaryPageCtrl = receiptSummaryPage.getKey();
        this.receiptSummaryPage = new Scene(receiptSummaryPage.getValue());

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
        invoiceSummaryPageCtrl.refresh(false);
    }

    /**
     * Show the invoice summary page on receipt menu.
     */
    public void showChooseInvoicePage() {
        primaryStage.setTitle("Choose Invoice Page");
        primaryStage.setScene(invoiceSummaryPage);
        invoiceSummaryPageCtrl.refresh(true);
    }

    /**
     * Show the payment menu page.
     */
    public void showPaymentMenuPage() {
        primaryStage.setTitle("Payment Menu Page");
        primaryStage.setScene(paymentMenuPage);
        paymentMenuPageCtrl.refresh();
    }

    /**
     * Show the new expense page.
     */
    public void showNewExpensePage() {
        primaryStage.setTitle("New Expense Page");
        primaryStage.setScene(newExpensePage);
        newExpensePageCtrl.refresh(false, null);
    }

    /**
     * Show the edit expense page.
     * @param payment The expense to edit
     */
    public void showEditExpensePage(Payment payment) {
        primaryStage.setTitle("Edit Expense Page");
        primaryStage.setScene(newExpensePage);
        newExpensePageCtrl.refresh(true, payment);
    }

    /**
     * Show the preview expense page.
     * @param payment The expense to preview
     */
    public void showPreviewExpensePage(Payment payment) {
        primaryStage.setTitle("Preview Expense Page");
        primaryStage.setScene(previewExpensePage);
        previewExpensePageCtrl.setPayment(payment);
        previewExpensePageCtrl.refresh();
    }

    /**
     * Show the expense summary page.
     */
    public void showExpenseSummaryPage() {
        primaryStage.setTitle("Expense Summary Page");
        primaryStage.setScene(expenseSummaryPage);
        expenseSummaryPageCtrl.refresh();
    }

    /**
     * Show the new receipt page.
     * @param invoice The invoice
     */
    public void showNewReceiptPage(Invoice invoice) {
        primaryStage.setTitle("New Receipt Page");
        primaryStage.setScene(newReceiptPage);
        newReceiptPageCtrl.refresh(false, null, invoice);
    }

    /**
     * Show the edit receipt page.
     * @param receipt The receipt to edit
     */
    public void showEditReceiptPage(Receipt receipt) {
        primaryStage.setTitle("Edit Receipt Page");
        primaryStage.setScene(newReceiptPage);
        newReceiptPageCtrl.refresh(true, receipt, receipt.getInvoice());
    }

    /**
     * Show the preview receipt page.
     * @param receipt The receipt to preview
     */
    public void showPreviewReceiptPage(Receipt receipt) {
        primaryStage.setTitle("Preview Receipt Page");
        primaryStage.setScene(previewReceiptPage);
        previewReceiptPageCtrl.setReceipt(receipt);
        previewReceiptPageCtrl.refresh();
    }

    /**
     * Show the receipt summary page.
     */
    public void showReceiptSummaryPage() {
        primaryStage.setTitle("Receipt Summary Page");
        primaryStage.setScene(receiptSummaryPage);
        receiptSummaryPageCtrl.refresh();
    }
}
