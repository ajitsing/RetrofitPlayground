package com.singhajit.retrofitplayground.github.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static java.util.Arrays.asList;

public class NetworkStateIdentifier {
  private NetworkStateIdentifier() {
    //Nothing
  }

  public static boolean isConnectedToInternet(Context context) {
    try {
      if (context != null) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
          return checkIsConnectedToInternetForAPI20AndBelow(connectivityManager);
        } else {
          return checkIsConnectedToInternetForAPI21AndAbove(connectivityManager, context);
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean checkIsConnectedToInternetForAPI20AndBelow(ConnectivityManager connectivityManager) {
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @SuppressLint("NewApi")
  private static boolean checkIsConnectedToInternetForAPI21AndAbove(final ConnectivityManager connectivityManager, Context context) {
    Network[] allNetworks = connectivityManager.getAllNetworks();
    for (Network network : allNetworks) {
      try {
        if (network != null && isNetworkConnected(connectivityManager, network, context)) {
          return true;
        }
      } catch (Exception e) {
      }
    }

    return false;
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @SuppressLint("NewApi")
  private static boolean isNetworkConnected(ConnectivityManager connectivityManager, Network network, Context context) {
    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
    return networkInfo!=null && isWifiOrMobile(networkInfo) && networkInfo.isConnected();
  }

  private static boolean isWifiOrMobile(NetworkInfo networkInfo) {
    List<Integer> networks = asList(TYPE_MOBILE, TYPE_WIFI);
    return networks.contains(networkInfo.getType());
  }

}
