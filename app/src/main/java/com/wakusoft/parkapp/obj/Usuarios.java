package com.wakusoft.parkapp.obj;


/*--------------------------------------------------------------
---------------------CLASE USUARIOS trabaja con bd--------------
----------------------------------------------------------------
AUTOR:      ALEJANDRO MORENO
EMPRESA:    WAKUSOFT
FECHA:      26-05-2020
----------------------------------------------------------------
 */


import android.service.autofill.Dataset;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wakusoft.parkapp.controller.UserController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Usuarios {


    /*--------------------------------------------------------------
    ---------------------DECLARACION DE VARIABLES-------------------
    ----------------------------------------------------------------
     */
    private String     Id;
    private String  Correo;
    private String  Password;
    private String  Nit;
    private String  Nombre;
    private String  Direccion;
    private String  Telefono;
    private int     LVehiculos;
    private int     LMotos;

    private List<String> TipoPagos;
    private List<String> TipoVehiculo;


    /*--------------------------------------------------------------
        ---------------------CONSTRUCTORES------------------------------
        ----------------------------------------------------------------
         */
    public Usuarios() {
    }


    /*--------------------------------------------------------------
    ---------------------METODOS------------------------------------
    ----------------------------------------------------------------
     */

    public Usuarios Save(Usuarios usuario){
        UserController userController = new UserController();
        return userController.InsertDataBase(usuario);
    }

    public Usuarios Get(String id) throws InterruptedException {
        UserController userController = new UserController();
        return userController.Get(id);
    }



    /*--------------------------------------------------------------
    ---------------------GETTERS AND SETTERS------------------------
    ----------------------------------------------------------------
     */

    public void setUsuarios(Usuarios usuario){
        this.setNit(usuario.getNit());
        this.setCorreo(usuario.getCorreo());
        this.setPassword(usuario.getPassword());
        this.setDireccion(usuario.getDireccion());
        this.setLMotos(usuario.getLMotos());
        this.setLVehiculos(usuario.getLVehiculos());
        this.setNombre(usuario.getNombre());
        this.setTelefono(usuario.getTelefono());
    }

    public List<String> getTipoPagos() {
        return TipoPagos;
    }
    public void setTipoPagos(List<String> tipoPagos) {
        TipoPagos = tipoPagos;
    }
    public List<String> getTipoVehiculo() {
        return TipoVehiculo;
    }
    public void setTipoVehiculo(List<String> tipoVehiculo) {
        TipoVehiculo = tipoVehiculo;
    }


    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getCorreo() {
        return Correo;
    }
    public void setCorreo(String correo) {
        Correo = correo;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getNit() {
        return Nit;
    }
    public void setNit(String nit) {
        Nit = nit;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public int getLVehiculos() {
        return LVehiculos;
    }
    public void setLVehiculos(int LVehiculos) {
        this.LVehiculos = LVehiculos;
    }
    public int getLMotos() {
        return LMotos;
    }
    public void setLMotos(int LMotos) {
        this.LMotos = LMotos;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "Id='" + Id + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Password='" + Password + '\'' +
                ", Nit='" + Nit + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", LVehiculos=" + LVehiculos +
                ", LMotos=" + LMotos +
                ", TipoPagos=" + TipoPagos +
                ", TipoVehiculo=" + TipoVehiculo +
                '}';
    }
}
