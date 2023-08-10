package com.example.ejercicio_3_2_servicio_en_segundo_plano_cronometro_3p;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityService extends Activity {

    private TextView textoCronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoCronometro = (TextView) findViewById(R.id.textoCronometro);

        Button starButton = (Button) findViewById(R.id.btnIniciar);
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarCronometro();
            }
        });

        Button stopButton = (Button) findViewById(R.id.btnFinalizar);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararCronometro();
            }
        });

        Cronometro.setUpdateListener(this);

    }

    protected void onDestroy(){
        pararCronometro();
        super.onDestroy();
    }

    private void iniciarCronometro() {
        Intent service = new Intent(this, Cronometro.class);
        startService(service);
    }

    private void pararCronometro() {
        Intent service = new Intent(this, Cronometro.class);
        stopService(service);
    }

    public void actualizarCronometro(double tiempo) {
        textoCronometro.setText(String.format("%.2f",tiempo)+"s");
    }
}
