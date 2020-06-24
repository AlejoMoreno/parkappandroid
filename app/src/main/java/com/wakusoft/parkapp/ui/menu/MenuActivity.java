package com.wakusoft.parkapp.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.ui.app.CajaActivity;
import com.wakusoft.parkapp.ui.app.ClientesActivity;
import com.wakusoft.parkapp.ui.app.ConfigActivity;
import com.wakusoft.parkapp.ui.app.EntradasActivity;
import com.wakusoft.parkapp.ui.app.PerfilActivity;
import com.wakusoft.parkapp.ui.app.SalidasActivity;
import com.wakusoft.parkapp.ui.app.ServiciosActivity;
import com.wakusoft.parkapp.ui.login.LoginActivity;

public class MenuActivity extends AppCompatActivity {

    private View.OnClickListener goConfiguracion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), ConfigActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goCaja = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), CajaActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goClientes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), ClientesActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goEntradas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), EntradasActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goPerfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), PerfilActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goServicios = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), ServiciosActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener goSalidas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");
            String correo = prefs.getString("CORREO", "");
            String nit = prefs.getString("NIT", "");
            Intent intent = new Intent(v.getContext(), SalidasActivity.class);
            intent.putExtra("CORREO", correo);
            intent.putExtra("NIT", nit);
            intent.putExtra("ID", id);
            startActivityForResult(intent, 0);
        }
    };

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        //anuncion
        MobileAds.initialize(this,
                "ca-app-pub-4639820515028360~1880865872");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //-------------TOMAR DATOS DE USUARIO SESION -----------------
        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String id = prefs.getString("ID", "");
        String correo = prefs.getString("CORREO", "");
        String nit = prefs.getString("NIT", "");
        ((TextView)  findViewById(R.id.usuarioText)).setText(correo);
        ((TextView)  findViewById(R.id.nitText)).setText(nit);


        Button btConfiguracion = (Button) findViewById(R.id.BtConfiguracion);
        btConfiguracion.setOnClickListener(goConfiguracion);

        Button btCaja = (Button) findViewById(R.id.BtCaja);
        btCaja.setOnClickListener(goCaja);

        Button btClientes = (Button) findViewById(R.id.BtClientes);
        btClientes.setOnClickListener(goClientes);

        Button btEntradas = (Button) findViewById(R.id.BtEntradas);
        btEntradas.setOnClickListener(goEntradas);

        Button btPerfil = (Button) findViewById(R.id.BtPerfil);
        btPerfil.setOnClickListener(goPerfil);

        Button btServicios = (Button) findViewById(R.id.BtServicios);
        btServicios.setOnClickListener(goServicios);

        Button btCenso = (Button) findViewById(R.id.BtCenso);
        btCenso.setOnClickListener(goSalidas);


        Button btSalir = (Button) findViewById(R.id.BtSalir);
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar datos en sharedPreferences SESSION
                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ID", "");
                editor.putString("NIT", "");
                editor.putString("CORREO", "");
                editor.putString("PASSWORD", "");
                editor.commit();

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}
