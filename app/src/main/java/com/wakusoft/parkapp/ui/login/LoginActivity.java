package com.wakusoft.parkapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.menu.MenuActivity;
import com.wakusoft.parkapp.ui.usuarios.CreateUsuarioActivity;

public class LoginActivity extends AppCompatActivity {


    private View.OnClickListener goMenu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                //traer datos
                String nit = ((EditText) findViewById(R.id.TxLoginNit)).getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Create a new user with a first and last name (usuario)
                db.collection("parkapp").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Usuarios user = new Usuarios();
                        user.setCorreo(((EditText) findViewById(R.id.TxCorreo)).getText().toString());
                        user.setPassword(((EditText) findViewById(R.id.TxPassword)).getText().toString());
                        user.setNit(((EditText) findViewById(R.id.TxLoginNit)).getText().toString());

                        Log.e("ERROR", "Toma de datos del layout");
                        /*IF EXIST IN DATA BASE*/


                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Usuarios newUsuario = document.toObject(Usuarios.class);
                                if (user.getNit().equals(newUsuario.getNit()) &&
                                        user.getCorreo().equals(newUsuario.getCorreo()) &&
                                        user.getPassword().equals(newUsuario.getPassword())) {

                                    Log.e("ERROR", "Existe en la base de datos tal cual");
                                    Log.d("ERROR", document.getId() + " => " + document.getData());

                                    SharedPreferences prefs = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("ID", document.getId());
                                    editor.putString("NIT", newUsuario.getNit());
                                    editor.putString("CORREO", newUsuario.getCorreo());
                                    editor.putString("PASSWORD", newUsuario.getPassword());
                                    editor.commit();
                                    Log.e("ERROR", "Local data login_data");
                                    //ir al menu
                                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                                    startActivityForResult(intent, 0);

                                } else {
                                    Log.d("ERROR", "No se encontro el id");
                                    ((TextView) findViewById(R.id.TxResLogin)).setText("Error en Datos, Verificar");
                                }
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                    }
                });
            }
            catch (Exception exc){
                System.out.println("Error En database Reference goMenu");
            }
        }
    };

    private View.OnClickListener goCrear = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), CreateUsuarioActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        try{
            //Guardar datos en sharedPreferences SESSION
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String id = prefs.getString("ID", "");

            if(!id.equals("")){
                Intent intent = new Intent(getApplication(), MenuActivity.class);
                startActivityForResult(intent, 0);
            }
        }
        catch(Exception exc){
            System.out.println("Error");
        }

        Button btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(goMenu);

        Button btCrear = (Button) findViewById(R.id.BtCrearUsuario);
        btCrear.setOnClickListener(goCrear);
    }
}
