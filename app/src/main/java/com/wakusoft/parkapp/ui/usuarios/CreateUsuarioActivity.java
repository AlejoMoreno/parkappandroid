package com.wakusoft.parkapp.ui.usuarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Clientes;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.app.ServiciosActivity;
import com.wakusoft.parkapp.ui.menu.MenuActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_usuario);
        getSupportActionBar().hide();

        Button btRegistrarUsuario = (Button) findViewById(R.id.BtCreacionCrear);
        btRegistrarUsuario.setOnClickListener(registarUsuario);

    }

    public View.OnClickListener registarUsuario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Usuarios usuario = new Usuarios();
            usuario.setId("");

            Log.e("Usuario","Se instancia el usuario correctamente");

            //verificar que los datos esten en formato apropiado
            int cont = 0;
            if ( (((EditText) findViewById(R.id.TxCreacionTelefono)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionNombre)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionNit)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionLVehiculos)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionLMotos)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionDireccion)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionPassword)).getText().toString().equals(""))) { cont++; }
            if ( (((EditText) findViewById(R.id.TxCreacionCorreo)).getText().toString().equals(""))) { cont++; }

            if(cont == 0){
                Log.e("Usuario","No tiene errores");
                usuario.setTelefono(    ((EditText) findViewById(R.id.TxCreacionTelefono)).getText().toString() );
                usuario.setNombre(      ((EditText) findViewById(R.id.TxCreacionNombre)).getText().toString() );
                usuario.setNit(         ((EditText) findViewById(R.id.TxCreacionNit)).getText().toString() );
                usuario.setLVehiculos(  Integer.parseInt(((EditText) findViewById(R.id.TxCreacionLVehiculos)).getText().toString()) );
                usuario.setLMotos(      Integer.parseInt(((EditText) findViewById(R.id.TxCreacionLMotos)).getText().toString()) );
                usuario.setDireccion(   ((EditText) findViewById(R.id.TxCreacionDireccion)).getText().toString() );
                usuario.setPassword(    ((EditText) findViewById(R.id.TxCreacionPassword)).getText().toString() );
                usuario.setCorreo(      ((EditText) findViewById(R.id.TxCreacionCorreo)).getText().toString() );

                List<String> tipopagos = new ArrayList<String>();
                tipopagos.add("SELECCIONE");
                tipopagos.add("EFECTIVO");
                tipopagos.add("CREDITO");
                tipopagos.add("BANCO");
                usuario.setTipoPagos(tipopagos);

                List<String> tipovehiculos = new ArrayList<String>();
                tipovehiculos.add("SELECCIONE");
                tipovehiculos.add("CARRO");
                tipovehiculos.add("MOTO");
                tipovehiculos.add("CICLA");
                usuario.setTipoVehiculo(tipovehiculos);

                //GUARDAR
                Usuarios newUsuario = usuario.Save(usuario);
                if(!newUsuario.getNit().equals("")){
                    Log.e("Usuario","guardo correctamente en firebase");

                    //Guardar datos en sharedPreferences SESSION
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ID", newUsuario.getId());
                    editor.putString("NIT", newUsuario.getNit());
                    editor.putString("CORREO", newUsuario.getCorreo());
                    editor.putString("PASSWORD", newUsuario.getPassword());
                    editor.commit();

                    String correo = prefs.getString("CORREO", "");

                    //Mostrar mensaje
                    ((TextView) findViewById(R.id.ResultadoCreacion)).setText("REGISTRO CORRECTO " + newUsuario.getNit() + " " + correo);

                    //ir al menu
                    Intent intent = new Intent(v.getContext(), MenuActivity.class);
                    startActivityForResult(intent, 0);
                }
                else{
                    Log.e("Usuario","No guardo en firebase");
                    //no guardo
                    ((TextView) findViewById(R.id.ResultadoCreacion)).setText("NO SE PUDO");
                }
            }
            else{
                Log.e("Usuario","Algun dato falta");
                ((TextView) findViewById(R.id.ResultadoCreacion)).setText("NO SE PUDO");
            }


        }
    };


}
