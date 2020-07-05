package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CadastrarTurma extends AppCompatActivity {
    private Turma turma;
    Button btnVoltarMenu;
    DatabaseReference reference;
    String nomeTurma;
    EditText edtNomeTurma;
    Button btnCriarTurma;
    List<String> listaProfessoresNova=new ArrayList<>();
    List<String> listaAlunosNova = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_turma);
        btnVoltarMenu=(Button)findViewById(R.id.btnSairCriarTurma);
        edtNomeTurma= (EditText)findViewById(R.id.edtNomeTurma);
        btnCriarTurma=(Button)findViewById(R.id.btnCriarTurma);

        btnVoltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarDeCadastrar();

            }
        });

        btnCriarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTurma();


            }
        });

    }
    public void voltarDeCadastrar(){

        Intent intent = new Intent(CadastrarTurma.this, MenuTurmas.class);
        startActivity(intent);
        finish();
    }
    public void salvarTurma(){

        turma = new Turma();
        String nturma;
        nturma = edtNomeTurma.getText().toString();




        if (TextUtils.isEmpty(nturma)) {
            Toast.makeText(getApplicationContext(), "Insira um Nome!", Toast.LENGTH_LONG).show();
            return;
        }

        nomeTurma= edtNomeTurma.getText().toString();
        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        String key = reference.child("Turmas").push().getKey();
        listaAlunosNova.add(" 0");
        listaProfessoresNova.add(" 0");
        turma.setAlunosTurma(listaAlunosNova);
        turma.setProfessores(listaProfessoresNova);
        turma.setKeyUserTurma(key);
        turma.setNomeTurma(nomeTurma);
        reference.child(key).setValue(turma);
        listaProfessoresNova.clear();
        listaAlunosNova.clear();
        Toast.makeText(this, "Turma criada com sucesso!", Toast.LENGTH_SHORT).show();
        recreate();
        edtNomeTurma.setText("");

    }
}