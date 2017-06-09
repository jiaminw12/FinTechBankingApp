package finapp.publicstatic.com.fintechbankapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintChecker {

    private Boolean result;

    public FingerprintChecker(Activity activity){
        result = false;
        checkVersion(activity);
    }

    private Boolean getResult(){
        return result;
    }

    private void checkVersion(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(KEYGUARD_SERVICE);
            FingerprintManager fingerprintManager = (FingerprintManager) activity.getSystemService(FINGERPRINT_SERVICE);
            try {
                if (!fingerprintManager.isHardwareDetected()) {
                    // Device doesn't support fingerprint authentication

                    // show password ....
                    // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                    result = false;
                }

                //Check whether the user has granted your app the USE_FINGERPRINT permission//
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    // If your app doesn't have this permission, then display the following text//
                    //textView.setText("Please enable the fingerprint " +
                    //        "permission");
                    result = false;
                }


                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    // User hasn't enrolled any fingerprints to authenticate with
                    result = false;
                }

                //Check that the lockscreen is secured//
                if (!keyguardManager.isKeyguardSecure()) {
                    // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                    //textView.setText("Please enable lockscreen security in your device's Settings");
                    result = false;
                } else {
                    result = true;
                }

            } catch (SecurityException e){

            }
        }
    }

}
