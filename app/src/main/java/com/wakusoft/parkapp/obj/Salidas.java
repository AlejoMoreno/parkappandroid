package com.wakusoft.parkapp.obj;

import android.service.autofill.Dataset;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Salidas {

    private String Id;
    private String Placa;
    private String Cliente;
    private Double ValorTotal;
    private String FechaDesde;
    private String FechaHasta;
    private String HorasTotal;
    private String Parkapp;


    public Salidas() {
    }

    public Salidas(String placa, String cliente, Double valorTotal, String fechaDesde, String fechaHasta, String horasTotal, String parkapp) {
        Placa = placa;
        Cliente = cliente;
        ValorTotal = valorTotal;
        FechaDesde = fechaDesde;
        FechaHasta = fechaHasta;
        HorasTotal = horasTotal;
        Parkapp = parkapp;
    }

    public Salidas save(Salidas salidas){
        return InsertDataBase(salidas);
    }

    /*--------------------------------------------------------------
    ---------------------DATABASE METODHS CRUD---------------------------
    ----------------------------------------------------------------
     */
    static Salidas InsertDataBase(final Salidas salidas){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (salidas)
        db.collection("salidas")
                .add(salidas)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("VALUE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        salidas.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error adding document", e);
                    }
                });
        return salidas;
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

    public Double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        ValorTotal = valorTotal;
    }

    public String getFechaDesde() {
        return FechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        FechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return FechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        FechaHasta = fechaHasta;
    }

    public String getHorasTotal() {
        return HorasTotal;
    }

    public void setHorasTotal(String horasTotal) {
        HorasTotal = horasTotal;
    }

    public String getParkapp() {
        return Parkapp;
    }

    public void setParkapp(String parkapp) {
        Parkapp = parkapp;
    }
}
