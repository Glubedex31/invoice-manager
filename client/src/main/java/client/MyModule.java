package client;

import com.google.inject.Binder;
import com.google.inject.Module;
//import com.google.inject.Scopes;

public class MyModule implements Module {

    /**
     * Configure the binder.
     *
     * @param binder The binder
     */
    @Override
    public void configure(Binder binder) {
//        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
//        binder.bind(AddExpenseCtrl.class).in(Scopes.SINGLETON);
//
//        binder.bind(ConfigReader.class).in(Scopes.SINGLETON);
//        binder.bind(ClientUtils.class).in(Scopes.SINGLETON);
//        binder.bind(UIUtils.class).in(Scopes.SINGLETON);
//        binder.bind(WebSocketServerUtils.class).asEagerSingleton();
    }
}
