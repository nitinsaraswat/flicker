package image.flicker.flickerimage.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import image.flicker.flickerimage.R;


public enum NetworkManager {
    nManager;

    private Context context;

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected =  activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return isConnected;
    }


    public boolean checkAndShowNetworkAlert(Context context){
        this.context = context;
        boolean isConnected = NetworkManager.nManager.isNetworkAvailable(context);
        if(!isConnected){
//            Utility.showAlertDialog(context, "", "Network is not connected. Please retry later.");
            showAlertDialog(context, "", "Internet Connection not found. Please retry.");
        }
        return isConnected;
    }

    public static void showAlertDialog(Context context, String title, String message) {
        if (context != null && context instanceof Activity) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).create();

            title = TextUtils.isEmpty(title) ? context.getResources().getString(R.string.app_name) : title;

            // Setting Dialog Title
            alertDialog.setTitle(title);

            // Setting Dialog Message
            alertDialog.setMessage(message);

            // Setting alert dialog icon
            // alertDialog.setIcon((status) ? R.drawable.success :
            // R.drawable.fail);

            // Setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }
}

