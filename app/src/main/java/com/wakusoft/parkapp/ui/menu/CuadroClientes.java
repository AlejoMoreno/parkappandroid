package com.wakusoft.parkapp.ui.menu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Clientes;
import com.wakusoft.parkapp.obj.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class CuadroClientes {

    public interface FinalizoCuadroClientes{
        void ResultadoCuadroClientes(String peso);
    }

    private CuadroClientes.FinalizoCuadroClientes interfaz;



    public CuadroClientes(final Context contexto, CuadroClientes.FinalizoCuadroClientes actividad, String id){

        interfaz = actividad;

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.cuadro_clientes);

        TextView textView30 = (TextView) dialog.findViewById(R.id.textView30);
        textView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clientes").whereEqualTo("parkapp", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        List<String> elementos = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Clientes newCliente = document.toObject(Clientes.class);
                            Log.e("ERROR", "SON IGUALES nombre" + newCliente.toString());
                            elementos.add(newCliente.getCedula());
                        }

                        ArrayAdapter adp =  new ArrayAdapter(contexto, R.layout.simple_spinner_dropdown_item, elementos);

                        final ListView listView = (ListView) dialog.findViewById(R.id.ListaCliente);
                        listView.setAdapter(adp);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String elemento = (String) listView.getAdapter().getItem(position);
                                interfaz.ResultadoCuadroClientes(elemento);
                                dialog.dismiss();
                            }
                        });
                    }
                }
                catch (Exception exc){
                    Log.e("ERROR", exc.getMessage());
                }
            }
        });

        dialog.show();
    }
}
