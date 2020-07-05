package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuDiretoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diretoria);

    }

    public void abrirTelaAlunos(View view) {
        Intent intent = new Intent(MenuDiretoria.this, TelaAlunoDiretoria.class);
        startActivity(intent);
        finish();
    } public void abrirTelaProfessores(View view) {
        Intent intent = new Intent(MenuDiretoria.this, TelaProfessorDiretoria.class);
        startActivity(intent);
        finish();
    }
    public void abrirTelaTurmas(View view) {
        Intent intent = new Intent(MenuDiretoria.this, MenuTurmas.class);
        startActivity(intent);
        finish();
    }
    public void abrirTelaCardapio(View view){

        Intent intent = new Intent(MenuDiretoria.this, InserirCardapio.class);
        startActivity(intent);
        finish();

    }

    public void sair(View view){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MenuDiretoria.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Saindo", Toast.LENGTH_LONG).show();
    }
}