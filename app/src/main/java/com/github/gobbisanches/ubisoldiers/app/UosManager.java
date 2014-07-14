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
        } else {
            Log.i("UBISOLDIERS", "The UOS object is valid");
        }

        if(uos.getFactory() == null) {
            throw new RuntimeException("Invalid UOS Factory");
        } else {
            Log.i("UBISOLDIERS", "The UOS factory is valid");
        }

        SmartSpaceGateway gateway = (SmartSpaceGateway) uos.getGateway();
        if(gateway == null) {
            throw new RuntimeException("Invalid UOS Gateway");
        } else {
            Log.i("UBISOLDIERS", "The UOS gateway is valid");
        }


        DriverManager driverManager = gateway.getDriverManager();

        UbisoldiersDriver driver = new UbisoldiersDriver();
        try {
            driverManager.deployDriver(driver.getDriver(), driver, "");
        } catch(Exception e) {
            Log.e("UBISOLDIERS", "Exception when trying to load driver: " + e.getMessage());
        }

        List<DriverData> driverDataList = gateway.listDrivers("com.github.gobbisanches.ubisoldiers");
        Log.d("UBISOLDIERS", "UOS Driver list has " + driverDataList.size() + " elements ");
        DriverData driverData = driverDataList.get(0);
        UpDriver upDriver = driverData.getDriver();
        String driverName = upDriver.getName();

        if (driverName == "com.github.gobbisanches.ubisoldiers") {
            Log.i("UBISOLDIERS", "Driver ok");
        } else {
            Log.e("UBISOLDIERS", "Driver is broken");
        }

        try {
        uos.getGateway().callService(
                null,
                "performBattle",
                "com.github.gobbisanches.ubisoldiers",
                "",
                null,
                new TreeMap<String, Object>());
        } catch(Exception e) {
            throw new RuntimeException("Could not invoke a call through UOS: " + e.getMessage());
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

    long performSearch(int playerGeneralId) {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        ServiceResponse response;

        params.put("CONTEXT", context);
        params.put("PLAYER_GENERAL_ID", playerGeneralId);

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

        return ((Long) response.getResponseData("RANDOM_SEED")).longValue();
    }
}
