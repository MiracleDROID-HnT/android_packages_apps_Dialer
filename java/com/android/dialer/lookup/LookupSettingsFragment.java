/*
 * Copyright (C) 2014 The CyanogenMod Project
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
 * limitations under the License
 */

package com.android.dialer.lookup;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.android.dialer.R;

import android.provider.Settings;

import java.util.Arrays;

public class LookupSettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_ENABLE_FORWARD_LOOKUP = "enable_forward_lookup";
    private static final String KEY_ENABLE_PEOPLE_LOOKUP = "enable_people_lookup";
    private static final String KEY_ENABLE_REVERSE_LOOKUP = "enable_reverse_lookup";
    private static final String KEY_FORWARD_LOOKUP_PROVIDER = "forward_lookup_provider";
    private static final String KEY_PEOPLE_LOOKUP_PROVIDER = "people_lookup_provider";
    private static final String KEY_REVERSE_LOOKUP_PROVIDER = "reverse_lookup_provider";

    private SwitchPreference mEnableForwardLookup;
    private SwitchPreference mEnablePeopleLookup;
    private SwitchPreference mEnableReverseLookup;
    private ListPreference mForwardLookupProvider;
    private ListPreference mPeopleLookupProvider;
    private ListPreference mReverseLookupProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.lookup_settings);

        mEnableForwardLookup = (SwitchPreference) findPreference(KEY_ENABLE_FORWARD_LOOKUP);
        mEnablePeopleLookup = (SwitchPreference) findPreference(KEY_ENABLE_PEOPLE_LOOKUP);
        mEnableReverseLookup = (SwitchPreference) findPreference(KEY_ENABLE_REVERSE_LOOKUP);

        mEnableForwardLookup.setOnPreferenceChangeListener(this);
        mEnablePeopleLookup.setOnPreferenceChangeListener(this);
        mEnableReverseLookup.setOnPreferenceChangeListener(this);

        mForwardLookupProvider = (ListPreference) findPreference(KEY_FORWARD_LOOKUP_PROVIDER);
        mPeopleLookupProvider = (ListPreference) findPreference(KEY_PEOPLE_LOOKUP_PROVIDER);
        mReverseLookupProvider = (ListPreference) findPreference(KEY_REVERSE_LOOKUP_PROVIDER);

        mForwardLookupProvider.setOnPreferenceChangeListener(this);
        mPeopleLookupProvider.setOnPreferenceChangeListener(this);
        mReverseLookupProvider.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        restoreLookupProviderSwitches();
        restoreLookupProviders();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver cr = getActivity().getContentResolver();

        if (preference == mEnableForwardLookup) {
            Settings.System.putInt(cr, Settings.System.ENABLE_FORWARD_LOOKUP,
                    ((Boolean) newValue) ? 1 : 0);
        } else if (preference == mEnablePeopleLookup) {
            Settings.System.putInt(cr, Settings.System.ENABLE_PEOPLE_LOOKUP,
                    ((Boolean) newValue) ? 1 : 0);
        } else if (preference == mEnableReverseLookup) {
            Settings.System.putInt(cr, Settings.System.ENABLE_REVERSE_LOOKUP,
                    ((Boolean) newValue) ? 1 : 0);
        } else if (preference == mForwardLookupProvider) {
            Settings.System.putString(cr, Settings.System.FORWARD_LOOKUP_PROVIDER,
                    (String) newValue);
        } else if (preference == mPeopleLookupProvider) {
            Settings.System.putString(cr, Settings.System.PEOPLE_LOOKUP_PROVIDER,
                    (String) newValue);
        } else if (preference == mReverseLookupProvider) {
            Settings.System.putString(cr, Settings.System.REVERSE_LOOKUP_PROVIDER,
                    (String) newValue);
        }

        return true;
    }

    private void restoreLookupProviderSwitches() {
        final ContentResolver cr = getActivity().getContentResolver();
        mEnableForwardLookup.setChecked(Settings.System.getInt(cr,
                Settings.System.ENABLE_FORWARD_LOOKUP, 1) != 0);
        mEnablePeopleLookup.setChecked(Settings.System.getInt(cr,
                Settings.System.ENABLE_PEOPLE_LOOKUP, 1) != 0);
        mEnableReverseLookup.setChecked(Settings.System.getInt(cr,
                Settings.System.ENABLE_REVERSE_LOOKUP, 1) != 0);
    }

    private void restoreLookupProviders() {
        restoreLookupProvider(mForwardLookupProvider,
                Settings.System.FORWARD_LOOKUP_PROVIDER);
        restoreLookupProvider(mPeopleLookupProvider,
                Settings.System.PEOPLE_LOOKUP_PROVIDER);
        restoreLookupProvider(mReverseLookupProvider,
                Settings.System.REVERSE_LOOKUP_PROVIDER);
    }

    private void restoreLookupProvider(ListPreference pref, String key) {
        if (pref.getEntries().length < 1) {
            pref.setEnabled(false);
            return;
        }

        final ContentResolver cr = getActivity().getContentResolver();
        String provider = Settings.System.getString(cr, key);
        if (provider == null) {
            pref.setValueIndex(0);
            Settings.System.putString(cr, key, pref.getValue());
        } else {
            pref.setValue(provider);
        }
    }
}
