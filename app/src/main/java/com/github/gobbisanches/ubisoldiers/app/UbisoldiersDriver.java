package com.github.gobbisanches.ubisoldiers.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.github.gobbisanches.ubisoldiers.mechanics.PolicyManager;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UOSMessageContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceCall;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceResponse;

import java.security.Policy;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sanches on 13/07/2014.
 */
public class UbisoldiersDriver implements UosDriver {
    Random seedGenerator = new Random(0);

    @Override
    public UpDriver getDriver() {
        UpDriver driver = new UpDriver("com.github.gobbisanches.ubisoldiers");
        driver.addService("performBattle");
        driver.addService("performSearch");

        return driver;
    }

    public void performBattle(ServiceCall call, ServiceResponse response, UOSMessageContext msgContext) {
        Log.i("UBISOLDIERS", "UOS Successfuly invoked performBattle");

        response.addParameter("RANDOM_SEED", seedGenerator.nextLong());
    }

    public void performSearch(ServiceCall call, ServiceResponse response, UOSMessageContext msgContext) {
        Log.i("UBISOLDIERS", "UOS Successfuly invoked performSearch");

        Context context = (Context) call.getParameter("CONTEXT");
        int playerGeneralId = ((Integer) call.getParameter("PLAYER_GENERAL_ID")).intValue();
        Location location = (Location) call.getParameter("LOCATION");
        long wifiStrength = ((Long) call.getParameter("SIGNAL_STRENGTH")).longValue();

        Log.d("UBISOLDIERS", "[SEARCH] level = " + wifiStrength);

        double modifier = PolicyManager.getRules().calculateSearchModifier(location, wifiStrength);

        long randomSeed = seedGenerator.nextLong();
        Log.d("UBISOLDIERS", "[SEARCH] Random Seed = " + randomSeed);
        response.addParameter("RANDOM_SEED", new Long(randomSeed));
        response.addParameter("MODIFIER", new Double(modifier));
    }

    public void getLocation(ServiceCall call, ServiceResponse response, UOSMessageContext msgContext) {
        Log.i("UBISOLDIERS", "UOS Successfuly invoked getLocation");

        Context context = (Context) call.getParameter("CONTEXT");
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if(providers.isEmpty()) {
            throw new RuntimeException("No location provider is available");
        }

        Location location = locationManager.getLastKnownLocation(providers.get(0));
        for(int i=1; i < providers.size(); ++i) {
            Location newLocation = locationManager.getLastKnownLocation(providers.get(1));
            if(newLocation.getTime() > location.getTime()) {
                location = newLocation;
            }
        }

        response.addParameter("LOCATION", location);
    }

    public void getWifiSignalStrength(ServiceCall call, ServiceResponse response, UOSMessageContext msgContext) {
        Log.i("UBISOLDIERS", "UOS Successfuly invoked getWifiSignalStrength");

        Context context = (Context) call.getParameter("CONTEXT");
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        long level = wifiManager.calculateSignalLevel(info.getRssi(), 100);

        Log.d("UBISOLDIERS", "[WIFI] RSSI = " + info.getRssi());
        Log.d("UBISOLDIERS", "[WIFI] level = " + level);

        response.addParameter("SIGNAL_STRENGTH", new Long(level));
    }

    @Override
    public List<UpDriver> getParent() {
        return null;
    }

    @Override
    public void init(Gateway gateway, String instanceId) {

    }

    @Override
    public void destroy() {

    }
}
