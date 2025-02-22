/*
 * Copyright (C) 2020 The Pixel Experience Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.internal.util.custom;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import java.util.Arrays;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PixelPropsUtils {

    private static final String TAG = PixelPropsUtils.class.getSimpleName();
    private static final boolean PRODUCT_SUPPORT_HIGH_FPS =
            SystemProperties.getBoolean("ro.device.support_high_fps", false);
    private static final boolean PRODUCT_SUPPORT_CONTENT_REFRESH =
            SystemProperties.getBoolean("ro.surface_flinger.use_content_detection_for_refresh_rate", false);
    private static final boolean DEBUG = false;

    private static final Map<String, Object> propsToChange;
    private static final Map<String, Object> propsToChangePixel5;
    private static final Map<String, Object> propsToChangeOGPixelXL;
    private static final Map<String, Object> propsToChangePUBG;
    private static final Map<String, Object> propsToChangeCOD;

    private static final String[] packagesToChange = {
            "com.google.android.gms"
    };

    private static final String[] packagesToChangeOGPixelXL = {
            "com.google.android.apps.photos"
    };

    private static final String[] packagesToChangePixel5 = {
            "com.google.android.apps.wallpaper"
    };

    private static final String[] packagesToChangeCOD = {
        "com.activision.callofduty.shooter"
    };

    private static final String[] packagesToChangePUBG = {
        "com.tencent.ig",
        "com.pubg.krmobile",
        "com.vng.pubgmobile",
        "com.rekoo.pubgm",
        "com.pubg.imobile",
        "com.pubg.newstate",
        "com.gameloft.android.ANMP.GloftA9HM" // Asphalt 9
    };

    static {
        propsToChange = new HashMap<>();
        propsToChange.put("FINGERPRINT", "google/walleye/walleye:8.1.0/OPM1.171019.011/4448085:user/release-keys");
        propsToChangePixel5 = new HashMap<>();
        propsToChangePixel5.put("BRAND", "google");
        propsToChangePixel5.put("MANUFACTURER", "Google");
        propsToChangePixel5.put("DEVICE", "redfin");
        propsToChangePixel5.put("PRODUCT", "redfin");
        propsToChangePixel5.put("MODEL", "Pixel 5");
        propsToChangePixel5.put("FINGERPRINT", "google/redfin/redfin:11/RQ3A.210705.001/7380771:user/release-keys");
        propsToChangeOGPixelXL = new HashMap<>();
        propsToChangeOGPixelXL.put("BRAND", "google");
        propsToChangeOGPixelXL.put("MANUFACTURER", "Google");
        propsToChangeOGPixelXL.put("DEVICE", "marlin");
        propsToChangeOGPixelXL.put("PRODUCT", "marlin");
        propsToChangeOGPixelXL.put("MODEL", "Pixel XL");
        propsToChangeOGPixelXL.put("FINGERPRINT", "google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys");
        propsToChangePUBG = new HashMap<>();
        propsToChangePUBG.put("MODEL", "GM1917");
        propsToChangeCOD = new HashMap<>();
        propsToChangeCOD.put("MODEL", "SO-52A");
    }

    public static void setProps(String packageName) {
        if (packageName == null){
            return;
        }
        if (Arrays.asList(packagesToChange).contains(packageName)){
            if (DEBUG){
                Log.d(TAG, "Defining props for: " + packageName);
            }
            for (Map.Entry<String, Object> prop : propsToChange.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                setPropValue(key, value);
            }
        }
        if (Arrays.asList(packagesToChangePixel5).contains(packageName)){
            if (DEBUG){
                Log.d(TAG, "Defining props for: " + packageName);
            }
            for (Map.Entry<String, Object> prop : propsToChangePixel5.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                setPropValue(key, value);
            }
        }
        if (Arrays.asList(packagesToChangeOGPixelXL).contains(packageName)){
            if (DEBUG){
                Log.d(TAG, "Defining props for: " + packageName);
            }
            for (Map.Entry<String, Object> prop : propsToChangeOGPixelXL.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                setPropValue(key, value);
            }
        }
        if (PRODUCT_SUPPORT_HIGH_FPS || PRODUCT_SUPPORT_CONTENT_REFRESH) {
            if (Arrays.asList(packagesToChangePUBG).contains(packageName)){
                if (DEBUG){
                    Log.d(TAG, "Defining props for: " + packageName);
                }
                for (Map.Entry<String, Object> prop : propsToChangePUBG.entrySet()) {
                    String key = prop.getKey();
                    Object value = prop.getValue();
                    setPropValue(key, value);
                }
            }
            if (Arrays.asList(packagesToChangeCOD).contains(packageName)){
                if (DEBUG){
                    Log.d(TAG, "Defining props for: " + packageName);
                }
                for (Map.Entry<String, Object> prop : propsToChangeCOD.entrySet()) {
                    String key = prop.getKey();
                    Object value = prop.getValue();
                    setPropValue(key, value);
                }
            }
        }
    }

    private static void setPropValue(String key, Object value){
        try {
            if (DEBUG){
                Log.d(TAG, "Defining prop " + key + " to " + value.toString());
            }
            Field field = Build.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "Failed to set prop " + key, e);
        }
    }
}
