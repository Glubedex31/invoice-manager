package client;

import client.scenes.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import client.utils.ClientUtils;
import client.utils.ConfigUtils;
//import com.google.inject.Scopes;

public class MyModule implements Module {

    /**
     * Configure the binder.
     *
     * @param binder The binder
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SettingsPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(StartPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(IncomeMenuPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(NewInvoicePageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PreviewInvoicePageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InvoiceSummaryPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PaymentMenuPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(NewExpensePageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ExpenseSummaryPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PreviewExpensePageCtrl.class).in(Scopes.SINGLETON);

        binder.bind(ConfigUtils.class).in(Scopes.SINGLETON);
        binder.bind(ClientUtils.class).in(Scopes.SINGLETON);
    }
}
