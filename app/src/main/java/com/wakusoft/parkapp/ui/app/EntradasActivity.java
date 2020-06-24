package com.wakusoft.parkapp.ui.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Clientes;
import com.wakusoft.parkapp.obj.Entradas;
import com.wakusoft.parkapp.obj.Usuarios;
import com.wakusoft.parkapp.ui.menu.CuadroClientes;
import com.wakusoft.parkapp.ui.menu.CuadroDialogo;
import com.wakusoft.parkapp.ui.menu.CuadroServicios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import harmony.java.awt.Color;

public class EntradasActivity extends AppCompatActivity implements CuadroServicios.FinalizoCuadroServicios, CuadroClientes.FinalizoCuadroClientes {

    TextView txPlaca;
    Button btnClienteSelect;
    Button btnServicioSelect;
    Button btEntrada;
    TextView txDescripcion;
    TextView txNota;
    Switch swTarifaPlena;
    Switch swTarifaMensual;

    Context contexto;

    TextView tHora;
    int hora=0, minuto =0, segundo = 0;
    Intent i;
    Thread iniReloj = null;
    Runnable r;
    boolean isUpdate = false;
    String sec, min, hor;

    Date calendar;
    String formattedDate;

    String curTime;

    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entradas);

        txPlaca = (TextView)findViewById(R.id.TxPlaca);
        btnClienteSelect = (Button)findViewById(R.id.BtnClienteSelect);
        btnServicioSelect = (Button)findViewById(R.id.BtnServicioSelect);
        btEntrada = (Button)findViewById(R.id.BtEntrada);
        txDescripcion = (TextView)findViewById(R.id.TxDescripcion);
        txNota = (TextView)findViewById(R.id.TxNota);
        swTarifaPlena = (Switch)findViewById(R.id.SwTarifaPlena);
        swTarifaMensual = (Switch)findViewById(R.id.SwTarifaMensual);

        getSupportActionBar().hide();

        contexto = this;


        try{

            btnClienteSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");
                    new CuadroClientes(contexto, EntradasActivity.this, id);
                }
            });

            btnServicioSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");
                    new CuadroServicios(contexto, EntradasActivity.this, id);
                }
            });

            btEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                    String nit = prefs.getString("NIT", "");
                    String id = prefs.getString("ID", "");

                    String plena = (swTarifaPlena.isChecked())?"SI":"NO";
                    String mensu = (swTarifaMensual.isChecked())?"SI":"NO";

                    Entradas entrada = new Entradas(txPlaca.getText().toString(), btnClienteSelect.getText().toString(),
                            btnServicioSelect.getText().toString(), txDescripcion.getText().toString(), txNota.getText().toString(),
                            tHora.getText().toString(), plena, mensu, id);

                    entrada.save(entrada);



                    txPlaca.setText("");
                    btnClienteSelect.setText("Cliente");
                    btnServicioSelect.setText("Servicio");
                    txDescripcion.setText("");
                    txNota.setText("");
                    tHora.setText("");



                }
            });


            //reloj
            tHora = (TextView) findViewById(R.id.tHora);
            r = new RefreshClock();
            iniReloj = new Thread(r);
            iniReloj.start();

        }
        catch (Exception exc){
            Log.e("ERROR", exc.getMessage());
        }
    }

    public void generarPdf() {

        // Creamos el documento.
        Document documento = new Document();

        try {

            File f = crearFichero(NOMBRE_DOCUMENTO);

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el pie de pagina y una cabecera
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "Esta es mi cabecera"), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    "Este es mi pie de pagina"), false);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            // Añadimos un titulo con la fuente por defecto.
            documento.add(new Paragraph("Titulo 1"));

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                    Font.BOLD, Color.RED);
            documento.add(new Paragraph("Titulo personalizado", font));

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    R.mipmap.ic_launcher);
            //ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            //Image imagen = Image.getInstance(stream.toByteArray());
            //documento.add(imagen);

            // Insertamos una tabla.
            /*PdfPTable tabla = new PdfPTable(5);
            for (int i = 0; i < 15; i++) {
                tabla.addCell("Celda " + i);
            }
            documento.add(tabla);
            */
            // Agregar marca de agua
            /*font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD,
                    Color.GRAY);
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Paragraph(
                            "androfast.com", font), 297.5f, 421,
                    writer.getPageNumber() % 2 == 1 ? 45 : -45);*/

        } catch (DocumentException e) {

            Log.e("ERROR", e.getMessage());

        } catch (IOException e) {

            Log.e("ERROR", e.getMessage());

        } finally {
            // Cerramos el documento.
            documento.close();


        }
    }


    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = new File(Environment.getExternalStorageDirectory() + "/Download");
        if (!ruta.exists()) {
            return null;
        }
        return ruta;
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        try{
            //iniReloj.suspend();
            this.finish();
        }
        catch(Exception  exc){
            Log.e("ERROR", exc.getMessage());
        }
    }

    private void initClock() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{

                    if(isUpdate){
                        settingNewClock();
                    } else {
                        updateTime();
                    }
                    Date calendar = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
                    String formattedDate = df.format(calendar);

                    curTime = formattedDate;
                    tHora.setText(curTime);

                }catch (Exception e) {}
            }
        });
    }

    /**
     Esta clase hace uso de la interface Runnable la cual es la encargada de estar
     refrescando cada 1000 milisegundos es decir, un segudo, no tiene gran ciencia
     @SuppressWarnings("unused") es para decirle al compilador que obvie la advertencia
     que se genera, pero la verdad no afecta en nada el funcionamiento del mismo

     */
    class RefreshClock implements Runnable{
        // @Override
        @SuppressWarnings("unused")
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    initClock();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

    /**
     Este es el metodo inicial del reloj, a partir de el es que se muestra la hora
     cada segundo es la encargada Java.Util.Calendar
     */

    private void updateTime(){

        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        segundo = c.get(Calendar.SECOND);

        calendar = Calendar.getInstance().getTime();
        System.out.println("Current time => " + calendar);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(calendar);

        setZeroClock();

    }

    /**
     setZeroClock es para que se nos ponga el numero 0 en aquellos valores menores a
     10, pero no he podido resolver un pequeño inconveniente al momento de la llegada
     de 0:0:0 y por ende en sus derivadas, aunque no es por falta de logica, he revisado
     muy bien, pero si le encuentran arreglo me hacen el favor y me avisan de como
     solucionarlo
     */
    private void setZeroClock(){
        if(hora >=0 & hora <=9){
            hor = "0";
        }else{
            hor = "";
        }

        if(minuto >=0 & minuto <=9){
            min = ":0";
        }else{
            min = ":";
        }

        if(segundo >=0 & segundo <=9){
            sec = ":0";

        }else{
            sec = ":";
        }
    }

    /**
     Que puedo decir de este metodo mas que es el encargado de parsear la hora de una
     manera que al llegar a 24:59:59 esta retome los valores de 00:00:00 aunque en la practica
     como mencionaba en un comentario anterior esta se pone en 0:0:0, pero luego se restaura a
     00:00:01
     */
    private void settingNewClock(){
        segundo +=1;

        setZeroClock();

        if(segundo >=0 & segundo <=59){

        }else {
            segundo = 0;
            minuto +=1;
        }
        if(minuto >=0 & minuto <=59){

        }else{
            minuto = 0;
            hora +=1;
        }
        if(hora >= 0 & hora <= 24){

        }else{
            hora = 0;
        }

    }


    @Override
    public void ResultadoCuadroServicios(String peso) {
        btnServicioSelect.setText(peso);
    }

    @Override
    public void ResultadoCuadroClientes(String peso) {
        btnClienteSelect.setText(peso);
    }
}

