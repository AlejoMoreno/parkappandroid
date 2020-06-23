package com.wakusoft.parkapp.obj;

import android.service.autofill.Dataset;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Clientes {

    /*--------------------------------------------------------------
    ---------------------DECLARACION DE VARIABLES-------------------
    ----------------------------------------------------------------
     */
    private String Id;
    private String Cedula;
    private String Titular;
    private boolean Amparado;
    private String Telefono;
    private String Token;
    private String Parkapp;

    public Clientes() {
    }


    public Clientes(String cedula, String titular, boolean amparado, String telefono, String token, String parkapp) {
        Cedula = cedula;
        Titular = titular;
        Amparado = amparado;
        Telefono = telefono;
        Token = token;
        Parkapp = parkapp;
    }

    public Clientes save(Clientes clientes){
        return InsertDataBase(clientes);
    }

    /*--------------------------------------------------------------
    ---------------------DATABASE METODHS CRUD---------------------------
    ----------------------------------------------------------------
     */
    static Clientes InsertDataBase(final Clientes clientes){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (clientes)
        db.collection("clientes")
                .add(clientes)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("VALUE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        clientes.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error adding document", e);
                    }
                });
        return clientes;
    }
    static boolean UpdateDataBase(Clientes clientes){
        return true;
    }
    static boolean DeleteDataBase(Clientes clientes){
        return true;
    }
    static Dataset SelectDataBase(String query){
        Dataset ds = null;
        return ds;
    }

    /*--------------------------------------------------------------
    ---------------------GETTERS AND SETTERS------------------------
    ----------------------------------------------------------------
     */

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getTitular() {
        return Titular;
    }

    public void setTitular(String titular) {
        Titular = titular;
    }

    public boolean isAmparado() {
        return Amparado;
    }

    public void setAmparado(boolean amparado) {
        Amparado = amparado;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getParkapp() {
        return Parkapp;
    }

    public void setParkapp(String parkapp) {
        Parkapp = parkapp;
    }
}
