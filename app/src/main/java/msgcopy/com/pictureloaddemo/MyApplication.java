package msgcopy.com.pictureloaddemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by liang on 2017/5/19.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        myApplication = this;
    }
    public static MyApplication getInstance(){
        return myApplication;
    }
}
