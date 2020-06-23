package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Clientes;
import com.wakusoft.parkapp.obj.Servicios;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.menu.CuadroDialogo;

import java.util.ArrayList;
import java.util.List;

public class ServiciosActivity extends AppCompatActivity implements CuadroDialogo.FinalizoCuadroDialogo {

    private Spinner mSpinner;
    Context contexto;
    TextView TxServiciosVehiculo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        getSupportActionBar().hide();

        contexto = this;
        LinearLayout boton = (LinearLayout) findViewById(R.id.ventana);
        TxServiciosVehiculo1 = (TextView) findViewById(R.id.TxServiciosVehiculo);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                String nit = prefs.getString("NIT", "");
                String id = prefs.getString("ID", "");
                new CuadroDialogo(contexto, ServiciosActivity.this, id);
            }
        });

        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String nit = prefs.getString("NIT", "");
        String id = prefs.getString("ID", "");

        try{

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("servicios").whereEqualTo("parkapp", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        TableLayout tableLayout = (TableLayout) findViewById(R.id.LinearTableServicios);
                        tableLayout.removeAllViews();
                        for( DocumentSnapshot document : task.getResult() ){
                            Servicios newServicio = document.toObject(Servicios.class);
                            Log.e("ERROR", "SON IGUALES nombre" + newServicio.toString());

                            TableRow row = new TableRow(getApplication());

                            TextView text_1 = new TextView(getApplication());
                            TextView text_2 = new TextView(getApplication());
                            TextView text_3 = new TextView(getApplication());
                            TextView text_4 = new TextView(getApplication());
                            TextView text_5 = new TextView(getApplication());
                            text_1.setText(newServicio.getNombre());
                            text_2.setText( Double.toString(newServicio.getValor_fraccion()) );
                            text_3.setText( Double.toString(newServicio.getValor_hora()) );
                            text_4.setText( Double.toString(newServicio.getValor_medio()) );
                            text_5.setText( Double.toString(newServicio.getValor_mensualidad()) );

                            row.addView(text_1);
                            row.addView(text_2);
                            row.addView(text_3);
                            row.addView(text_4);
                            row.addView(text_5);

                            tableLayout.addView(row);
                        }
                    }
                }
            });

        }
        catch (Exception exc){
            Log.e("ERROR", exc.getMessage());
        }



        Button btn_crear = (Button) findViewById(R.id.BtServicioCrear);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //variables del lauout
                TextView TxServiciosVehiculo = (TextView) findViewById(R.id.TxServiciosVehiculo);
                TextView TxServiciosNombreTarifa = (TextView) findViewById(R.id.TxServiciosNombreTarifa);
                TextView TxServiciosValorFraccion = (TextView) findViewById(R.id.TxServiciosValorFraccion);
                TextView TxServiciosValorHora = (TextView) findViewById(R.id.TxServiciosValorHora);
                TextView TxServiciosValorMedioDia = (TextView) findViewById(R.id.TxServiciosValorMedioDia);
                TextView TxServiciosValorMensualidad = (TextView) findViewById(R.id.TxServiciosValorMensualidad);

                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                String nit = prefs.getString("NIT", "");
                String id = prefs.getString("ID", "");

                Servicios servicios = new Servicios(
                        TxServiciosVehiculo.getText().toString(),
                        TxServiciosNombreTarifa.getText().toString(),
                        Double.parseDouble(TxServiciosValorFraccion.getText().toString()),
                        Double.parseDouble(TxServiciosValorHora.getText().toString()),
                        Double.parseDouble(TxServiciosValorMedioDia.getText().toString()),
                        Double.parseDouble(TxServiciosValorMensualidad.getText().toString()),
                        id);

                servicios.save(servicios);

                TxServiciosVehiculo.setText("");
                TxServiciosNombreTarifa.setText("");
                TxServiciosValorFraccion.setText("");
                TxServiciosValorHora.setText("");
                TxServiciosValorMedioDia.setText("");
                TxServiciosValorMensualidad.setText("");

            }
        });



    }

    @Override
    public void ResultadoCuadroDialogo(String peso) {
        TxServiciosVehiculo1.setText(peso);
    }
}
