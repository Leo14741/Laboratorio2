package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.laboratorio2.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;

public class PaginaActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ApplicationThreads application = (ApplicationThreads) getApplication();
        ExecutorService executorService = application.executorService;

        ContadorViewModel contadorViewModel = new ViewModelProvider(PaginaActivity.this).get(ContadorViewModel.class);

        contadorViewModel.getContador().observe(this, contador -> {
            //aquí o2
            binding.textView.setText(String.valueOf(contador));
        });

        binding..setOnClickListener(view -> {

            //es un hilo en background
            executorService.execute(() -> {
                for (int i = 1; i <= 10; i++) {

                    //
                    contadorViewModel.getContador().postValue(i); // o1
                    Log.d("msg-test", "i: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        });
    }
}