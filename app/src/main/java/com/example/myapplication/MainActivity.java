package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void login(View view){

        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
        finish();
    }
    public void registrar(View view){

        Intent intent = new Intent(this, CadastrarAluno.class);
        startActivity(intent);
        finish();
    }



}