package com.wakusoft.parkapp.ui.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wakusoft.parkapp.R;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportActionBar().hide();
    }
}
