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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.app.ServiciosActivity;

import java.util.List;

public class CuadroDialogo {

    public interface FinalizoCuadroDialogo{
        void ResultadoCuadroDialogo(String peso);
    }

    private FinalizoCuadroDialogo interfaz;

    public CuadroDialogo(final Context contexto, FinalizoCuadroDialogo actividad, String id){

        interfaz = actividad;

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.cuadro_dialogo);

        TextView textView30 = (TextView) dialog.findViewById(R.id.textView30);
        textView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("parkapp").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Usuarios usuarioNuevo = (document.toObject(Usuarios.class));

                    Log.e("APP", "USUARIO GET___: "+ usuarioNuevo.toString());

                    List<String> elementos = usuarioNuevo.getTipoVehiculo();

                    ArrayAdapter adp =  new ArrayAdapter(contexto, R.layout.simple_spinner_dropdown_item, elementos);

                    final ListView listView = (ListView) dialog.findViewById(R.id.lvItems);
                    listView.setAdapter(adp);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String elemento = (String) listView.getAdapter().getItem(position);
                            interfaz.ResultadoCuadroDialogo(elemento);
                            dialog.dismiss();
                        }
                    });

                }
            }
        });

        dialog.show();
    }
}
