/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.battery;

import android.content.Context;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import java.io.BufferedReader;
import java.io.FileReader;

public class ChargeFullPreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin {

    private static final String KEY_CHARGE_FULL = "charge_full";
    private int CHARGE_FULL_DIVIDER = 1000;

    public ChargeFullPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(getChargeFull());
    }

    private static String readOneLine(String fname) {
        BufferedReader br;
        String line = null;
        try {
            br = new BufferedReader(new FileReader(fname), 512);
            try {
                line = br.readLine();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            return null;
        }
        return line;
    }

    private String getChargeFull() {
        String value = mContext.getResources().getString(R.string.config_battChargeFullPath);
        value = null;
        try {
            return String.format("%s", Integer.parseInt(value) / CHARGE_FULL_DIVIDER) + "mAh";
        } catch (Exception e) {
            return mContext.getResources().getString(R.string.abi_unavailable);
        }
    }

    @Override
    public String getPreferenceKey() {
        return KEY_CHARGE_FULL;
    }
}
