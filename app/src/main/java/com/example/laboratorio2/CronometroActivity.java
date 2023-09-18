package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class CronometroActivity extends AppCompatActivity {

    private TextView tiempoTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        tiempoTextView = findViewById(R.id.tiempo_textview);

        // Configura el cronómetro con un tiempo total de 10 segundos (10000 milisegundos)
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Este método se llama cada segundo
                tiempoTextView.setText("Tiempo restante: " + millisUntilFinished / 1000 + " segundos");
            }

            public void onFinish() {
                // Este método se llama cuando el tiempo llega a cero
                tiempoTextView.setText("¡Tiempo terminado!");
            }
        };

        // Inicia el cronómetro
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el cronómetro cuando la actividad se destruye para evitar fugas de memoria
        countDownTimer.cancel();
    }
}