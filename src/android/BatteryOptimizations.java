package org.apache.cordova.batteryoptimizations;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import android.content.DialogInterface;
import android.util.Log;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

/**
 * Plugin class which does the actual handling
 */
public class BatteryOptimizations extends CordovaPlugin {

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    
    if (action.equals("check")) {
        Context context = cordova.getActivity().getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callbackContext.success(""+((PowerManager)context.getSystemService(Context.POWER_SERVICE)).isIgnoringBatteryOptimizations(context.getPackageName()));
            return true;
        }
        return false;
     }

    if (action.equals("run")) {
        final Context context = cordova.getActivity();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String packageName = context.getPackageName();
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){return  false;}
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("For remove battery optimization from application go to battery settings");
            alertDialog.setNegativeButton("Cancel", null);
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent newIntent = new Intent();
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.setAction(Intent.ACTION_POWER_USAGE_SUMMARY);
                    context.startActivity(newIntent);
                }
            });
            alertDialog.show();
        }
    } else {
        return false;
    }

    // Only alert and confirm are async.
    callbackContext.success();

    return true;
  }

}
