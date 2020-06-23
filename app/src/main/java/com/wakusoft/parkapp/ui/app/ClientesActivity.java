package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Clientes;
import com.wakusoft.parkapp.obj.Servicios;
import com.wakusoft.parkapp.obj.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    private TextView TxClienteCedula;
    private TextView TxClienteTitular;
    private TextView TxClienteAmparado;
    private TextView TxClienteTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        getSupportActionBar().hide();

        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String nit = prefs.getString("NIT", "");
        String id = prefs.getString("ID", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clientes").whereEqualTo("parkapp", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try{
                    if(task.isSuccessful()){
                        TableLayout tableLayout = (TableLayout) findViewById(R.id.LinearTableClientes);
                        tableLayout.removeAllViews();
                        for( DocumentSnapshot document : task.getResult() ){
                            Clientes newCliente = document.toObject(Clientes.class);
                            Log.e("ERROR", "SON IGUALES nombre" + newCliente.toString());

                            TableRow row = new TableRow(getApplication());

                            TextView text_1 = new TextView(getApplication());
                            TextView text_2 = new TextView(getApplication());
                            TextView text_3 = new TextView(getApplication());
                            TextView text_4 = new TextView(getApplication());
                            TextView text_5 = new TextView(getApplication());
                            text_1.setText(newCliente.getCedula());
                            text_2.setText( newCliente.getTitular() );
                            text_3.setText( newCliente.getTelefono() );

                            row.addView(text_1);
                            row.addView(text_2);
                            row.addView(text_3);

                            tableLayout.addView(row);
                        }
                    }
                }
                catch (Exception exc){
                    Log.e("ERROR", exc.getMessage());
                }

            }
        });




        Button btn_crear = (Button) findViewById(R.id.BtClientesCrear);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    TxClienteCedula = (TextView) findViewById(R.id.TxClienteCedula);
                    TxClienteTitular = (TextView) findViewById(R.id.TxClienteTitular);
                    TxClienteAmparado = (TextView) findViewById(R.id.TxClienteAmparado);
                    TxClienteTelefono = (TextView) findViewById(R.id.TxClienteTelefono);
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");

                    Clientes clientes = new Clientes(
                            TxClienteCedula.getText().toString(),
                            TxClienteTitular.getText().toString(),
                            true,
                            TxClienteTelefono.getText().toString(),
                            "TOKEN1234",
                            id);

                    clientes.save(clientes);

                    TxClienteCedula.setText("");
                    TxClienteTitular.setText("");
                    TxClienteAmparado.setText("");
                    TxClienteTelefono.setText("");

                }
                catch (Exception exc){
                    Log.e("ERROR", "Error al guardar datos en firebase"+ exc.getMessage());
                }
            }
        });


    }
}
