package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Fragment fragment =new PreferencesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingslayout,fragment)
                .commit();
    }
}