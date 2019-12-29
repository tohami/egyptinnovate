package com.reactlibrary;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import static android.content.Context.BATTERY_SERVICE;

public class BatteryModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public BatteryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Battery";
    }

    @ReactMethod
    public void batteryLevel(Callback callback) {
        int percentage = 0 ;
        if (Build.VERSION.SDK_INT >= 21) {

                BatteryManager bm = (BatteryManager) reactContext.getSystemService(BATTERY_SERVICE);
                percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        } else {

                IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = reactContext.registerReceiver(null, iFilter);

                int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
                int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

                double batteryPct = level / (double) scale;

                percentage = (int) (batteryPct * 100);
        }
        callback.invoke(percentage + "%");
    }


   @ReactMethod
   public void showToast(String message, Callback callback) {
       Toast.makeText(reactContext, message,
       Toast.LENGTH_LONG).show();
   }
}
