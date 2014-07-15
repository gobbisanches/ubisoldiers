package com.github.gobbisanches.ubisoldiers.app;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.SmartSpaceGateway;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.driverManager.DriverManager;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceResponse;

import java.io.IOException;
import java.io.InputStream;

import java.util.*;

/**
 * Created by Sanches on 13/07/2014.
 */

// UosManager runs in the main task, but its inner tasks run on another threads
public class UosManager {
    private static UosManager instance = null;
    private Context context;
    private UOS uos;

    class StartUosTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                uos = new UOS();
                uos.init(getResourceBundleFromFile());
                Log.i("UBISOLDIERS", "UOS was successfully initialized");
            } catch(Exception e) {
                Log.e("UBISOLDIERS", "Could not initiliaze UOS, because: " + e.getMessage());
            }

            return null;
        }

        private ResourceBundle getResourceBundleFromFile() {
//            InputStream is;
//            final ResourceBundle bundle;
//
//            try {
//                is = context.getAssets().open("ubiquitous.properties");
//                bundle = new PropertyResourceBundle(is);
//            } catch (IOException e) {
//                throw new RuntimeException("Could not open UOS bundle file: " + e.getMessage());
//            }
//
//            return bundle;

            return new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][]{
//                            {"ubiquitos.application.deploylist", UbisoldierUosApplication.class.getName()},
                            {"ubiquitos.connectionManager", "org.unbiquitous.uos.core.network.loopback.connectionManager.LoopbackConnectionManager"},
//                            {"ubiquitos.driver.deploylist", UbisoldiersDriver.class.getName()},
                            {"ubiquitos.driver.path", "/"},
                    };
                }
            };
        }
    }

    public class SearchParameters {
        private long randomSeed;
        private double modifier;

        public SearchParameters(long randomSeed, double modifier) {
            this.randomSeed = randomSeed;
            this.modifier = modifier;
        }

        public long getRandomSeed() {
            return randomSeed;
        }

        public double getModifier() {
            return modifier;
        }
    }

    UosManager(Context context) {
        this.context = context;
    }

    void startUos() {
        new StartUosTask().execute();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(uos == null) {
            throw new RuntimeException("Invalid UOS object");
        }

        if(uos.getFactory() == null) {
            throw new RuntimeException("Invalid UOS Factory");
        }

        SmartSpaceGateway gateway = (SmartSpaceGateway) uos.getGateway();
        if(gateway == null) {
            throw new RuntimeException("Invalid UOS Gateway");
        }

        DriverManager driverManager = gateway.getDriverManager();
        UbisoldiersDriver driver = new UbisoldiersDriver();
        try {
            driverManager.deployDriver(driver.getDriver(), driver, "");
        } catch(Exception e) {
            throw new RuntimeException("Exception when trying to load driver: " + e.getMessage());
        }

        List<DriverData> driverDataList = gateway.listDrivers("com.github.gobbisanches.ubisoldiers");
        DriverData driverData = driverDataList.get(0);
        UpDriver upDriver = driverData.getDriver();
        String driverName = upDriver.getName();

        if (driverName == "com.github.gobbisanches.ubisoldiers") {
            Log.i("UBISOLDIERS", "Ubisoldiers uOS driver successfully loaded");
        } else {
            throw new RuntimeException("Unable to initialize the Ubisoldiers uOS driver");
        }
    }

    void stopUos() {

    }

    Location getLocation() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        ServiceResponse response;

        params.put("CONTEXT", context);

        try {
            response = uos.getGateway().callService(
                    null,
                    "getLocation",
                    "com.github.gobbisanches.ubisoldiers",
                    "",
                    null,
                    params);
        } catch(Exception e) {
            throw new RuntimeException("Could not invoke a call through UOS: " + e.getMessage());
        }

        return (Location) response.getResponseData("LOCATION");
    }

    long performBattle(int playerGeneralId, int opponentGeneralId) {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        ServiceResponse response;

        params.put("CONTEXT", context);
        params.put("PLAYER_GENERAL_ID", playerGeneralId);
        params.put("OPPONENT_GENERAL_ID", opponentGeneralId);

        try {
            response = uos.getGateway().callService(
                    null,
                    "performBattle",
                    "com.github.gobbisanches.ubisoldiers",
                    "",
                    null,
                    params);
        } catch(Exception e) {
            throw new RuntimeException("Could not invoke a call through UOS: " + e.getMessage());
        }



        return ((Long) response.getResponseData("RANDOM_SEED")).longValue();
    }

    SearchParameters performSearch(int playerGeneralId, Location location, long wifiStrength) {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        ServiceResponse response;

        params.put("CONTEXT", context);
        params.put("PLAYER_GENERAL_ID", new Integer(playerGeneralId));
        params.put("LOCATION", location);
        params.put("SIGNAL_STRENGTH", new Long(wifiStrength));

        try {
            response = uos.getGateway().callService(
                    null,
                    "performSearch",
                    "com.github.gobbisanches.ubisoldiers",
                    "",
                    null,
                    params);
        } catch(Exception e) {
            throw new RuntimeException("Could not invoke a call through UOS: " + e.getMessage());
        }

        long randomSeed = ((Long) response.getResponseData("RANDOM_SEED")).longValue();
        double modifier = ((Double) response.getResponseData("MODIFIER")).doubleValue();

        return new SearchParameters(randomSeed, modifier);
    }

    long getWifiSignalStrength() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        ServiceResponse response;

        params.put("CONTEXT", context);

        try {
            response = uos.getGateway().callService(
                    null,
                    "getWifiSignalStrength",
                    "com.github.gobbisanches.ubisoldiers",
                    "",
                    null,
                    params);
        } catch(Exception e) {
            throw new RuntimeException("Could not invoke a call through UOS: " + e.getMessage());
        }

        long strength = ((Long) response.getResponseData("SIGNAL_STRENGTH")).longValue();

        Log.d("UBISOLDIERS", "[WIFI] level on Manager = " + strength);

        return strength;
    }

    public static UosManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("UosManager was not initialized");
        }

        return instance;
    }

    public static void initInstance(Context context) {
        instance = new UosManager(context);
        instance.startUos();
    }
}
