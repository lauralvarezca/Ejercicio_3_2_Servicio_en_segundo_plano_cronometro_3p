package com.example.ejercicio_3_2_servicio_en_segundo_plano_cronometro_3p;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Message;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class Cronometro extends Service {
    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 10;
    private static ActivityService UPDATE_LISTENER;
    private double cronometro = 0;
    private Handler handler;

    public static void setUpdateListener(ActivityService mainService) {
        UPDATE_LISTENER = mainService;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(){
        super.onCreate();
        iniciarCronometro();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UPDATE_LISTENER.actualizarCronometro(cronometro);
            }
        };
    }

    @Override
    public  void onDestroy(){
        pararCronometro();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void iniciarCronometro() {
        temporizador.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               cronometro += 0.01;
               handler.sendEmptyMessage(0);
           }
        },0,INTERVALO_ACTUALIZACION);
    }
    
    private void pararCronometro() {
        if(temporizador !=null)
            temporizador.cancel();
    }
    
}
