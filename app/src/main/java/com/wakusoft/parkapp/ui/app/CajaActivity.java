package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Entradas;
import com.wakusoft.parkapp.obj.Salidas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CajaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);

        getSupportActionBar().hide();

        SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        String nit = prefs.getString("NIT", "");
        String id = prefs.getString("ID", "");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("salidas").whereEqualTo("parkapp", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        List<String> elementos = new ArrayList<>();
                        TableLayout tableLayout = (TableLayout) findViewById(R.id.LinearTableCaja);
                        tableLayout.removeAllViews();

                        for (DocumentSnapshot document : task.getResult()) {
                            Salidas newSalida = document.toObject(Salidas.class);


                            TableRow row = new TableRow(getApplication());
                            row.setBackgroundColor(Color.BLACK);
                            row.setPadding(2, 2, 2, 2); //Border between rows

                            TableRow.LayoutParams llp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                            llp.setMargins(2, 2, 2, 2);//2px right-margin

                            LinearLayout cell = new LinearLayout(CajaActivity.this);
                            cell.setBackgroundColor(Color.WHITE);
                            cell.setLayoutParams(llp);//2px border on the right for the cell
                            TextView tv = new TextView(CajaActivity.this);
                            tv.setText(newSalida.getCliente());
                            tv.setPadding(0, 0, 4, 3);
                            cell.addView(tv);
                            row.addView(cell);

                            LinearLayout cell1 = new LinearLayout(CajaActivity.this);
                            cell1.setBackgroundColor(Color.WHITE);
                            cell1.setLayoutParams(llp);//2px border on the right for the cell
                            TextView tv1 = new TextView(CajaActivity.this);
                            tv1.setText(Double.toString(newSalida.getValorTotal()));
                            tv1.setPadding(0, 0, 4, 3);
                            cell1.addView(tv1);
                            row.addView(cell1);

                            LinearLayout cell2 = new LinearLayout(CajaActivity.this);
                            cell2.setBackgroundColor(Color.WHITE);
                            cell2.setLayoutParams(llp);//2px border on the right for the cell
                            TextView tv2 = new TextView(CajaActivity.this);
                            tv2.setText(newSalida.getFechaHasta());
                            tv2.setPadding(0, 0, 4, 3);
                            cell2.addView(tv2);
                            row.addView(cell2);

                            LinearLayout cell3 = new LinearLayout(CajaActivity.this);
                            cell3.setBackgroundColor(Color.WHITE);
                            cell3.setLayoutParams(llp);//2px border on the right for the cell
                            TextView tv3 = new TextView(CajaActivity.this);
                            tv3.setText(newSalida.getPlaca());
                            tv3.setPadding(0, 0, 4, 3);
                            cell3.addView(tv3);
                            row.addView(cell3);

                            LinearLayout cell4 = new LinearLayout(CajaActivity.this);
                            cell4.setBackgroundColor(Color.WHITE);
                            cell4.setLayoutParams(llp);//2px border on the right for the cell
                            TextView tv4 = new TextView(CajaActivity.this);
                            tv4.setText(newSalida.getHorasTotal());
                            tv4.setPadding(0, 0, 4, 3);
                            cell4.addView(tv4);
                            row.addView(cell4);

                            tableLayout.addView(row);

                        }

                    }
                }
                catch (Exception exc){
                    Log.e("ERROR", exc.getMessage());
                }
            }
        });


    }
}
