package com.wakusoft.parkapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.Dataset;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Servicios;
import com.wakusoft.parkapp.obj.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class ServiciosController {

    public Servicios servicios;
    public List<Servicios> listaServicios =  new ArrayList<>();

    /*--------------------------------------------------------------
   ---------------------DATABASE METODHS CRUD---------------------------
   ----------------------------------------------------------------
    */
    public Servicios InsertDataBase(final Servicios servicios){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (servicios)
        db.collection("servicios")
                .add(servicios)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("VALUE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        servicios.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error adding document", e);
                    }
                });
        return servicios;
    }

    public List<Servicios> GetDataBase(final String id_usuario) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> task = db.collection("servicios").whereEqualTo("parkapp", id_usuario).get();
        List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

        for (DocumentSnapshot document : listDocument) {

            Servicios newServicio = document.toObject(Servicios.class);
            if(newServicio.getParkapp().equals(id_usuario)){
                Log.e("ERROR", "SON IGUALES nombre" + newServicio.toString());
                listaServicios.add(newServicio);
            }
            else{
                Log.e("ERROR", "NO SON IGUALES");
            }
        }



        return this.listaServicios;
    }

    static boolean UpdateDataBase(Servicios servicios){
        return true;
    }
    static boolean DeleteDataBase(Servicios servicios){
        return true;
    }


}
