package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class Menu extends AppCompatActivity {

    Button botaoCadastrarAluno = findViewById(R.id.btnCadastrarAluno);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    private void abrirCadastroAluno() {
        Intent intent = new Intent(Menu.this, CadastrarAluno.class);
        startActivity(intent);
        finish();
    }
}