package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Entradas;
import com.wakusoft.parkapp.obj.Salidas;
import com.wakusoft.parkapp.obj.Servicios;
import com.wakusoft.parkapp.ui.menu.CuadroClientes;
import com.wakusoft.parkapp.ui.menu.CuadroEntradas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class SalidasActivity extends AppCompatActivity implements CuadroEntradas.FinalizoCuadroEntradas {

    Button BtSalidaCrear;
    TextView TxSalidaPlaca;
    TextView TxSalidaCliente;
    TextView TxSalidaValorTotal;
    TextView TxSalidaDesde;
    TextView TxSalidaHasta;
    TextView TxSalidaHoraTotal;

    Context contexto;

    double valorTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salidas);

        BtSalidaCrear = (Button) findViewById(R.id.BtSalidaCrear);
        TxSalidaPlaca = (TextView) findViewById(R.id.TxSalidaPlaca);
        TxSalidaCliente = (TextView) findViewById(R.id.TxSalidaCliente);
        TxSalidaValorTotal = (TextView) findViewById(R.id.TxSalidaValorTotal);
        TxSalidaDesde = (TextView) findViewById(R.id.TxSalidaDesde);
        TxSalidaHasta = (TextView) findViewById(R.id.TxSalidaHasta);
        TxSalidaHoraTotal = (TextView) findViewById(R.id.TxSalidaHoraTotal);

        TextView textView17 = (TextView) findViewById(R.id.textView17);

        contexto = this;


        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                String nit = prefs.getString("NIT", "");
                String id = prefs.getString("ID", "");
                new CuadroEntradas(contexto, SalidasActivity.this, id);
            }
        });


        BtSalidaCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");

                    Salidas salida = new Salidas();
                    salida.setCliente(TxSalidaCliente.getText().toString());
                    salida.setPlaca(TxSalidaPlaca.getText().toString());
                    salida.setValorTotal( Double.parseDouble(TxSalidaValorTotal.getText().toString()));
                    salida.setFechaDesde(TxSalidaDesde.getText().toString());
                    salida.setFechaHasta(TxSalidaHasta.getText().toString());
                    salida.setHorasTotal(TxSalidaHoraTotal.getText().toString());
                    salida.setParkapp(id);

                    salida.save(salida);

                    TxSalidaCliente.setText("");
                    TxSalidaPlaca.setText("");
                    TxSalidaValorTotal.setText("");
                    TxSalidaDesde.setText("");
                    TxSalidaHasta.setText("");
                    TxSalidaHoraTotal.setText("");

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("entradas").whereEqualTo("placa",TxSalidaPlaca.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("entradas").document(document.getId()).update("nota", "pagado");
                                }
                            }
                        }
                    });

                }
                catch (Exception exc){
                    Log.e("ERROR", exc.getMessage());
                }
            }
        });


    }

    @Override
    public void ResultadoCuadroEntradas(String peso) {

        //TxSalidaCliente.setText(peso);

        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String nit = prefs.getString("NIT", "");
        final String id = prefs.getString("ID", "");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("entradas").whereEqualTo("parkapp", id).whereEqualTo("placa", peso).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document : task.getResult()) {
                        Entradas newEntradas = document.toObject(Entradas.class);
                        if(!newEntradas.getNota().equals("pagado")){
                            Log.e("ERROR", "SON IGUALES nombre" + newEntradas.toString());

                            Date calendar = Calendar.getInstance().getTime();

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
                            String formattedDate = df.format(calendar);

                            String newDate = newEntradas.getHora();
                            String stringMinutos = "";

                            try {
                                Date date = new Date(df.parse(newDate).getTime());
                                Date datefin = new Date(df.parse(formattedDate).getTime());

                                long diff = datefin.getTime() - date.getTime();

                                final long segundos = diff / 1000;
                                final long minutos = segundos / 60;
                                final long horas = minutos / 60;
                                final long dias = horas / 24;

                                SimpleDateFormat dh = new SimpleDateFormat("HH:mm:ss");
                                dh.format(diff);
                                dh.getTimeZone().getID();
                                dh.getTimeZone().getOffset(diff);
                                dh.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                                String restante = dh.format(diff);

                                stringMinutos = restante;


                                db.collection("servicios").whereEqualTo("parkapp", id).whereEqualTo("nombre",newEntradas.getServicio()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            double valor = 0;
                                            for (DocumentSnapshot document : task.getResult()) {
                                                Servicios newServicio = document.toObject(Servicios.class);
                                                Log.e("ERROR", "SON IGUALES nombre" + newServicio.toString());

                                                if( (minutos <= 15) ){
                                                    valor = 1 * newServicio.getValor_fraccion();
                                                }
                                                if( (minutos <= 30) & (minutos > 15) ){
                                                    valor = 2 *  newServicio.getValor_fraccion();
                                                }
                                                if( (minutos <= 45) & (minutos > 30) ){
                                                    valor = 3 * newServicio.getValor_fraccion();
                                                }
                                                if( (minutos < 60) & (minutos > 45) ){
                                                    valor = 4 * newServicio.getValor_fraccion();
                                                }
                                                //horas
                                                if( (horas >= 1) & (horas < 12) & (horas != 0)){
                                                    valor = valor + (horas * newServicio.getValor_hora());
                                                }
                                                else {
                                                    //horas
                                                    if( (horas >= 12) & (horas < 24)){
                                                        valor = valor + ((horas -12) * newServicio.getValor_hora());
                                                    }
                                                    //medio dia
                                                    if( (horas <= 24) & (horas > 12) ){
                                                        valor = valor + ( 1 * newServicio.getValor_medio());
                                                    }
                                                }

                                            }
                                            TxSalidaValorTotal.setText(Double.toString(valor));
                                        }
                                    }
                                });

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            TxSalidaCliente.setText(newEntradas.getCliente());
                            TxSalidaPlaca.setText(newEntradas.getPlaca());

                            TxSalidaDesde.setText(newDate);
                            TxSalidaHasta.setText(formattedDate);
                            TxSalidaHoraTotal.setText(stringMinutos);
                        }
                    }
                }
            }
        });


    }
}
