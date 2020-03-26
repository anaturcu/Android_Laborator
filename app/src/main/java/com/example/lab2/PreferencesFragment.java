package com.example.lab2;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.lab2.R;

public class PreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
