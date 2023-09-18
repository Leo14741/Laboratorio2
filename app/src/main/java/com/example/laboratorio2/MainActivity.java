package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laboratorio2.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Service typicodeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button2);

        ApplicationThreads application = (ApplicationThreads) getApplication();
        ExecutorService executorService = application.executorService;

        ContadorViewModel contadorViewModel = new ViewModelProvider(MainActivity.this).get(ContadorViewModel.class);

        contadorViewModel.getContador().observe(this, contador -> {
            //aquí o2
            binding.textView.setText(String.valueOf(contador));
        });

        binding.contador.setOnClickListener(view -> {

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

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                intent.putExtra("resultados", );
                startActivity(intent);
            }
        });

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service.class);
        binding.button2.setOnClickListener(view -> fetchWebServiceData());

    }
    public void fetchWebServiceData(){
        if(tengoInternet()){
            typicodeService.getProfile().enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    //aca estoy en el UI Thread
                    if(response.isSuccessful()){
                        Root root = response.body();
                        binding.editTextText2.setText(root.getName());
                        fetchCommentsFromWs();
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }
            });
        }
    }
    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}