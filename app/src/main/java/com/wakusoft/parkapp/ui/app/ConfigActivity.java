package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.core.FirestoreClient;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigActivity extends AppCompatActivity {

    List<String> dataTipoPagos = new ArrayList<String>();
    List<String> dataTipoVehiculo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        getSupportActionBar().hide();

        //traer datos
        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String nit = prefs.getString("NIT", "");
        String id = prefs.getString("ID", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (usuario)
        db.collection("parkapp").whereEqualTo("nit", nit).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try{
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Usuarios dataUsuario = document.toObject(Usuarios.class);

                            Log.d("EXISTE", document.getId() + " => " + document.getData());

                            ((TextView) findViewById(R.id.TxCreacionNit)).setText(dataUsuario.getNit());
                            ((TextView) findViewById(R.id.TxCreacionNombre)).setText(dataUsuario.getNombre());
                            ((TextView) findViewById(R.id.TxCreacionDireccion)).setText(dataUsuario.getDireccion());
                            ((TextView) findViewById(R.id.TxCreacionTelefono)).setText(dataUsuario.getTelefono());
                            ((TextView) findViewById(R.id.TxCreacionLVehiculos)).setText(String.valueOf(dataUsuario.getLVehiculos()));
                            ((TextView) findViewById(R.id.TxCreacionLMotos)).setText(String.valueOf(dataUsuario.getLMotos()));

                            TableLayout tableLayout = (TableLayout) findViewById(R.id.tipoPagoListalayout1);
                            tableLayout.removeAllViews();
                            ///PENDIENTE CREAR LINEAR LAYOUT para tipo pa

                            //TableLayout table = new TableLayout(getApplication()); // Java. You suck.
                            //TableLayout.LayoutParams lp = new TableLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                            //table.setLayoutParams(lp); // This line has no effect! WHYYYY?!
                            tableLayout.setStretchAllColumns(true);  //10347623
                            for (int i=0; i < dataUsuario.getTipoPagos().size(); i++) {
                                TableRow row = new TableRow(getApplication());
                                String id = "100"+Integer.toString(i);

                                TextView text_tipo_pago1 = new TextView(getApplication());
                                text_tipo_pago1.setText( dataUsuario.getTipoPagos().get(i) );
                                text_tipo_pago1.setId(Integer.parseInt(id));

                                Button btn = new Button(getApplication());
                                btn.setText("X");
                                //btn.setBackground("#FFF44336");
                                //String id = "EliminarTipoPago"+Integer.toString(r);

                                row.addView(text_tipo_pago1);
                                row.addView(btn);

                                tableLayout.addView(row);
                            }

                            //linearLayout.addView(table);

                            TableLayout tableLayout2 = (TableLayout) findViewById(R.id.tipoVehiculoListalayout1);
                            tableLayout2.removeAllViews();
                            ///PENDIENTE CREAR LINEAR LAYOUT para tipo pa

                            //TableLayout table = new TableLayout(getApplication()); // Java. You suck.
                            //TableLayout.LayoutParams lp = new TableLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                            //table.setLayoutParams(lp); // This line has no effect! WHYYYY?!
                            tableLayout2.setStretchAllColumns(true);  //10347623
                            for (int i=0; i < dataUsuario.getTipoVehiculo().size(); i++) {
                                TableRow row = new TableRow(getApplication());
                                String id = "200"+Integer.toString(i);

                                TextView text_tipo_vehiculo1 = new TextView(getApplication());
                                text_tipo_vehiculo1.setText( dataUsuario.getTipoVehiculo().get(i) );
                                text_tipo_vehiculo1.setId(Integer.parseInt(id));

                                Button btn = new Button(getApplication());
                                btn.setText("X");
                                //btn.setBackground("#FFF44336");
                                //String id = "EliminarTipoPago"+Integer.toString(r);

                                row.addView(text_tipo_vehiculo1);
                                row.addView(btn);

                                tableLayout2.addView(row);
                            }
                        }
                    } else {
                        Log.w("ERROR", "Error getting documents.", task.getException());
                    }
                }
                catch(Exception exc){
                    Log.e("ERROR", "Error al guardar datos en firebase"+ exc.getMessage());
                }

            }
        });

        Button btsaveTipoPago = (Button) findViewById(R.id.BtTipoPagos);
        btsaveTipoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");

                    Log.e("APP", "ID: "+ id);

                    dataTipoPagos.add( ((EditText) findViewById(R.id.TxTipoPago)).getText().toString() );

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("parkapp").document(id);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                                String nit = prefs.getString("NIT", "");
                                String id = prefs.getString("ID", "");

                                TableLayout tabla = (TableLayout) findViewById(R.id.tipoPagoListalayout1);
                                Log.e("APP", "Cantidad de datos: "+ tabla.getChildCount());

                                for (int i = 0; i < tabla.getChildCount(); i++) {
                                    String id1 = "100"+Integer.toString(i);
                                    TextView text = (TextView) findViewById(Integer.parseInt(id1));
                                    dataTipoPagos.add(text.getText().toString());
                                }
                                Log.e("APP", "dataTipoPagos: "+ dataTipoPagos.toString());

                                DocumentSnapshot document = task.getResult();
                                Usuarios user = document.toObject(Usuarios.class);
                                user.setTipoPagos(dataTipoPagos);
                                Log.e("APP", "USUARIO UPDATE: "+ user.toString());

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("parkapp").document(id).set(user);

                            }
                        }
                    });



                    ((EditText) findViewById(R.id.TxTipoPago)).setText("");

                }
                catch (Exception exc){
                    Log.e("ERROR", "Error al guardar datos en firebase"+ exc.getMessage());
                }
            }
        });

        Button btsaveTipovehiculo = (Button) findViewById(R.id.BtTipoVehiculo);
        btsaveTipovehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");

                    Log.e("APP", "ID: "+ id);

                    dataTipoVehiculo.add( ((EditText) findViewById(R.id.TxTipoVehiculo)).getText().toString() );

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("parkapp").document(id);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                                String nit = prefs.getString("NIT", "");
                                String id = prefs.getString("ID", "");

                                TableLayout tabla = (TableLayout) findViewById(R.id.tipoVehiculoListalayout1);
                                Log.e("APP", "Cantidad de datos: "+ tabla.getChildCount());

                                for (int i = 0; i < tabla.getChildCount(); i++) {
                                    String id1 = "200"+Integer.toString(i);
                                    TextView text = (TextView) findViewById(Integer.parseInt(id1));
                                    dataTipoVehiculo.add(text.getText().toString());
                                    Log.e("APP", "Datos: "+ text.getText().toString());
                                }
                                Log.e("APP", "dataTipoVehiculos: "+ dataTipoVehiculo.toString());

                                DocumentSnapshot document = task.getResult();
                                Usuarios user = document.toObject(Usuarios.class);
                                user.setTipoVehiculo(dataTipoVehiculo);
                                Log.e("APP", "USUARIO UPDATE: "+ user.toString());

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("parkapp").document(id).set(user);

                            }
                        }
                    });

                    ((EditText) findViewById(R.id.TxTipoVehiculo)).setText("");

                }
                catch (Exception exc){
                    Log.e("ERROR", "Error al guardar datos en firebase"+ exc.getMessage());
                }
            }
        });


    }
}
