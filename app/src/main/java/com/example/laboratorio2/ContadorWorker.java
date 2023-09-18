package com.example.laboratorio2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ContadorWorker extends Worker {


    public ContadorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        for (int i = 104; i <= 226; i++) {

            Log.d("msg-test", "i: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return Result.failure();
                //throw new RuntimeException(e);
            }
        }

        return Result.success();
    }
}
