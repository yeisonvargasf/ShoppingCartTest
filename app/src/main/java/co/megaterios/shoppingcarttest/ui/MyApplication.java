package co.megaterios.shoppingcarttest.ui;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by yeison on 04/05/17.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
