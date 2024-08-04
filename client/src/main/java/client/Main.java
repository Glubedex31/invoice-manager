package client;

import client.scenes.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import client.utils.ClientUtils;
import client.utils.ConfigUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @Inject
    private ConfigUtils configUtils;
    @Inject
    private ClientUtils clientUtils;

    /**
     * Main method.
     *
     * @param args Arguments
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Get the injector.
     * @return The injector
     */
    public static Injector injector() {
        return INJECTOR;
    }

    /**
     * Start the application.
     * @param primaryStage The primary stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.INJECTOR.injectMembers(this);
        if (configUtils == null) {
            throw new RuntimeException("Dependency injection failed for configReader.");
        }
        configUtils.initialize();
        var startPage = FXML.load(StartPageCtrl.class, "scenes", "StartPage.fxml");
        var settingsPage = FXML.load(SettingsPageCtrl.class, "scenes", "SettingsPage.fxml");
        var incomeMenuPage = FXML.load(IncomeMenuPageCtrl.class, "scenes", "IncomeMenuPage.fxml");
        var newInvoicePage = FXML.load(NewInvoicePageCtrl.class, "scenes", "NewInvoicePage.fxml");
        var previewInvoicePage = FXML.load(PreviewInvoicePageCtrl.class,
            "scenes", "PreviewInvoicePage.fxml");
        var invoiceSummaryPage = FXML.load(InvoiceSummaryPageCtrl.class,
            "scenes", "InvoiceSummaryPage.fxml");
        var paymentMenuPage = FXML.load(PaymentMenuPageCtrl.class,
            "scenes", "PaymentMenuPage.fxml");
        var newExpensePage = FXML.load(NewExpensePageCtrl.class,
            "scenes", "NewExpensePage.fxml");
        var expenseSummaryPage = FXML.load(ExpenseSummaryPageCtrl.class,
            "scenes", "ExpenseSummaryPage.fxml");
        var previewExpensePage = FXML.load(PreviewExpensePageCtrl.class,
            "scenes", "PreviewExpensePage.fxml");
        var newReceiptPage = FXML.load(NewReceiptPageCtrl.class,
            "scenes", "NewReceiptPage.fxml");
        var previewReceiptPage = FXML.load(PreviewReceiptPageCtrl.class,
            "scenes", "PreviewReceiptPage.fxml");
        var receiptSummaryPage = FXML.load(ReceiptSummaryPageCtrl.class,
            "scenes", "ReceiptSummaryPage.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage,
            startPage,
            settingsPage,
            incomeMenuPage,
            newInvoicePage,
            previewInvoicePage,
            invoiceSummaryPage,
            paymentMenuPage,
            newExpensePage,
            expenseSummaryPage,
            previewExpensePage,
            newReceiptPage,
            previewReceiptPage,
            receiptSummaryPage);
        primaryStage.setOnCloseRequest(e -> {
            configUtils.writeLanguage(clientUtils.getLanguage());
        });
    }
}