package com.wakusoft.parkapp.obj;

import android.service.autofill.Dataset;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Entradas {
    /*--------------------------------------------------------------
        ---------------------DECLARACION DE VARIABLES-------------------
        ----------------------------------------------------------------
         */
    private String Id;
    private String Placa;
    private String Cliente;
    private String Servicio;
    private String Descripcion;
    private String Nota;
    private String Hora;
    private String TarifaPlena;
    private String TarifaMensual;
    private String Parkapp;

    public Entradas(String placa, String cliente, String servicio, String descripcion, String nota, String hora, String tarifaPlena, String tarifaMensual, String parkapp) {
        Placa = placa;
        Cliente = cliente;
        Servicio = servicio;
        Descripcion = descripcion;
        Nota = nota;
        Hora = hora;
        TarifaPlena = tarifaPlena;
        TarifaMensual = tarifaMensual;
        Parkapp = parkapp;
    }

    public Entradas() {
    }

    public Entradas save(Entradas entradas){
        return InsertDataBase(entradas);
    }

    /*--------------------------------------------------------------
    ---------------------DATABASE METODHS CRUD---------------------------
    ----------------------------------------------------------------
     */
    static Entradas InsertDataBase(final Entradas entradas){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (entradas)
        db.collection("entradas")
                .add(entradas)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("VALUE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        entradas.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error adding document", e);
                    }
                });
        return entradas;
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


    @Override
    public String toString() {
        return "Entradas{" +
                "Id='" + Id + '\'' +
                ", Placa='" + Placa + '\'' +
                ", Cliente='" + Cliente + '\'' +
                ", Servicio='" + Servicio + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Nota='" + Nota + '\'' +
                ", Hora='" + Hora + '\'' +
                ", TarifaPlena='" + TarifaPlena + '\'' +
                ", TarifaMensual='" + TarifaMensual + '\'' +
                ", Parkapp='" + Parkapp + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String nota) {
        Nota = nota;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getTarifaPlena() {
        return TarifaPlena;
    }

    public void setTarifaPlena(String tarifaPlena) {
        TarifaPlena = tarifaPlena;
    }

    public String getTarifaMensual() {
        return TarifaMensual;
    }

    public void setTarifaMensual(String tarifaMensual) {
        TarifaMensual = tarifaMensual;
    }

    public String getParkapp() {
        return Parkapp;
    }

    public void setParkapp(String parkapp) {
        Parkapp = parkapp;
    }
}
