package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuTurmas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_turmas);
    }
    public void irTelaExcluir(View view) {
        Intent intent = new Intent(MenuTurmas.this, ExcluirTurma.class);
        startActivity(intent);
        finish();
    }
    public void irTelaAdicionarTurma(View view) {
        Intent intent = new Intent(MenuTurmas.this, CadastrarTurma.class);
        startActivity(intent);
        finish();
    }
    public void irTelaGerenciarAlunos(View view) {
        Intent intent = new Intent(MenuTurmas.this, GerenciarTurmasAlunos.class);
        startActivity(intent);
        finish();
    }
    public void irTelaGerenciarProfessores(View view) {
        Intent intent = new Intent(MenuTurmas.this, GerenciarTurmasProfessores.class);
        startActivity(intent);
        finish();
    }
    public void voltarTelaDiretoria(View view) {
        Intent intent = new Intent(MenuTurmas.this, MenuDiretoria.class);
        startActivity(intent);
        finish();
    }
}