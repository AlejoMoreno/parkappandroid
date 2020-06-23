package com.wakusoft.parkapp.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wakusoft.parkapp.obj.Usuarios;

public class UserController {

    public Usuarios usuarios;

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
/*--------------------------------------------------------------
    ---------------------DATABASE METODHS CRUD---------------------------
    ----------------------------------------------------------------
     */

    static boolean UpdateDataBase(Usuarios usuario){
        return true;
    }
    static boolean DeleteDataBase(Usuarios usuario){
        return true;
    }

    public Usuarios InsertDataBase(final Usuarios usuario){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name (usuario)
        db.collection("parkapp")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("VALUE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        usuario.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ERROR", "Error adding document", e);
                    }
                });
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userNode = database.getReference("parkapp"+usuario.getNit());
        userNode.setValue(usuario);*/

        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("parkapp")
                .add(usuario);*/

        return usuario;
    }

    public Usuarios Get(String id) throws InterruptedException{

        interfaz.usuarioRecibido(id, new Usuarios());
        return this.getUsuarios();
    }

    public interface UsuariosCallback{
        void usuarioRecibido(final String id, final Usuarios usuarios);
    }

    public void recuperaUsuario(final String id, final Usuarios usuarios, final UsuariosCallback callback) throws InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("parkapp").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Usuarios usuarioNuevo = (document.toObject(Usuarios.class));
                    if (callback != null) {
                        callback.usuarioRecibido("",usuarioNuevo);
                    }

                }
            }
        });
    }

    private UsuariosCallback interfaz = new UsuariosCallback() {
        @Override
        public void usuarioRecibido(String id, Usuarios usuarios) {
            final Usuarios user = new Usuarios();
            String ids = "";
            try {
                recuperaUsuario(id, usuarios, new UsuariosCallback() {
                    @Override
                    public void usuarioRecibido(String id, Usuarios usuarionuevo) {
                        user.setUsuarios(usuarionuevo);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}

