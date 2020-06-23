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
import com.wakusoft.parkapp.controller.ServiciosController;

import java.util.List;

public class Servicios {

    /*--------------------------------------------------------------
    ---------------------DECLARACION DE VARIABLES-------------------
    ----------------------------------------------------------------
     */
    private String Id;
    private String Tipo_vehiculo;
    private String Nombre;
    private double Valor_fraccion;
    private double Valor_hora;
    private double Valor_medio;
    private double Valor_mensualidad;
    private String Parkapp;

    public Servicios() {
    }

    public Servicios(String tipo_vehiculo, String nombre, double valor_fraccion, double valor_hora, double valor_medio, double valor_mensualidad, String parkapp) {
        Tipo_vehiculo = tipo_vehiculo;
        Nombre = nombre;
        Valor_fraccion = valor_fraccion;
        Valor_hora = valor_hora;
        Valor_medio = valor_medio;
        Valor_mensualidad = valor_mensualidad;
        Parkapp = parkapp;
    }

    public Servicios save(Servicios servicios){
        ServiciosController servicioController = new ServiciosController();
        return servicioController.InsertDataBase(servicios);
    }

    public List<Servicios> get(String id_usuario){
        ServiciosController servicioController = new ServiciosController();
        return servicioController.GetDataBase(id_usuario);
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

    public String getTipo_vehiculo() {
        return Tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        Tipo_vehiculo = tipo_vehiculo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getValor_fraccion() {
        return Valor_fraccion;
    }

    public void setValor_fraccion(double valor_fraccion) {
        Valor_fraccion = valor_fraccion;
    }

    public double getValor_hora() {
        return Valor_hora;
    }

    public void setValor_hora(double valor_hora) {
        Valor_hora = valor_hora;
    }

    public double getValor_medio() {
        return Valor_medio;
    }

    public void setValor_medio(double valor_medio) {
        Valor_medio = valor_medio;
    }

    public double getValor_mensualidad() {
        return Valor_mensualidad;
    }

    public void setValor_mensualidad(double valor_mensualidad) {
        Valor_mensualidad = valor_mensualidad;
    }

    public String getParkapp() {
        return Parkapp;
    }

    public void setParkapp(String parkapp) {
        Parkapp = parkapp;
    }

    @Override
    public String toString() {
        return "Servicios{" +
                "Id='" + Id + '\'' +
                ", Tipo_vehiculo='" + Tipo_vehiculo + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Valor_fraccion=" + Valor_fraccion +
                ", Valor_hora=" + Valor_hora +
                ", Valor_medio=" + Valor_medio +
                ", Valor_mensualidad=" + Valor_mensualidad +
                ", Parkapp='" + Parkapp + '\'' +
                '}';
    }
}
