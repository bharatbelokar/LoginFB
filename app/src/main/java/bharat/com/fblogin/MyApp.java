package bharat.com.fblogin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shrikant on 24-01-2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the SDK before executing any other operations,
    }

}

