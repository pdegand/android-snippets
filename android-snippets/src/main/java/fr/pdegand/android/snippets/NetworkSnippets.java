package fr.pdegand.android.snippets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Special network snippets
 */
public class NetworkSnippets {

    /**
     * Check if the network is available and connected <br />
     * <em>Be sure to add android.permission.ACCESS_NETWORK_STATE to your manifest before using this</em>
     *
     * @param context any app/ui context
     * @return true if the network is active and connected, false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Check if the Wifi is enabled or not <br />
     * <em>Be sure to add android.permission.ACCESS_NETWORK_STATE and android.permission.ACCESS_WIFI_STATE to your manifest before using this</em>
     *
     * @param context any app/ui context
     * @return true if the wifi is enabled, false otherwise
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

}
