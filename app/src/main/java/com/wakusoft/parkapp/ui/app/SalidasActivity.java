package com.wakusoft.parkapp.ui.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wakusoft.parkapp.R;
import com.wakusoft.parkapp.obj.Salidas;

public class SalidasActivity extends AppCompatActivity {

    Button BtSalidaCrear;
    TextView TxSalidaPlaca;
    TextView TxSalidaCliente;
    TextView TxSalidaValorTotal;
    TextView TxSalidaDesde;
    TextView TxSalidaHasta;
    TextView TxSalidaHoraTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salidas);

        BtSalidaCrear = (Button) findViewById(R.id.BtSalidaCrear);
        TxSalidaPlaca = (TextView) findViewById(R.id.TxSalidaPlaca);
        TxSalidaCliente = (TextView) findViewById(R.id.TxSalidaCliente);
        TxSalidaValorTotal = (TextView) findViewById(R.id.TxSalidaValorTotal);
        TxSalidaDesde = (TextView) findViewById(R.id.TxSalidaDesde);
        TxSalidaHasta = (TextView) findViewById(R.id.TxSalidaHasta);
        TxSalidaHoraTotal = (TextView) findViewById(R.id.TxSalidaHoraTotal);




        BtSalidaCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Salidas salida = new Salidas();
                    salida.setCliente(TxSalidaCliente.getText().toString());
                    salida.setPlaca(TxSalidaPlaca.getText().toString());
                    salida.setValorTotal( Double.parseDouble(TxSalidaValorTotal.getText().toString()));
                    salida.setFechaDesde(TxSalidaDesde.getText().toString());
                    salida.setFechaHasta(TxSalidaHasta.getText().toString());
                    salida.setHorasTotal(TxSalidaHoraTotal.getText().toString());

                    salida.save(salida);

                }
                catch (Exception exc){
                    Log.e("ERROR", exc.getMessage());
                }
            }
        });


    }
}
