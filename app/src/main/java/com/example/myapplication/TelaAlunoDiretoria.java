package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaAlunoDiretoria extends AppCompatActivity {

    Button btnEditarAluno;
    Button btnExcluirAluno;
    Spinner spinnerEditar;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterEditar;
    ArrayList<String> spinnerEditarLista;
    DatabaseReference reference;
    public static String nomeUserAlunoEditar;
    public static String nomeMaeUserAlunoEditar;
    public static String nomePaiUserAlunoEditar;
    public static String telefoneUserAlunoEditar;
    public static String emailUserAlunoEditar;
    public static String keyUserAlunoEditar;



    TextView txtAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aluno_diretoria);
        btnEditarAluno = (Button) findViewById(R.id.btnEditarAluno);
        btnExcluirAluno = (Button)findViewById(R.id.btnExcluirAluno);
        txtAlunos =  (TextView) findViewById(R.id.txtAlunos);
        databaseReference = FirebaseDatabase.getInstance().getReference("alunos");

        listaAlunosSpinner();

        btnExcluirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirAluno();
            }
        });

        btnEditarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarAluno();
            }
        });
    }
    public void abrirTelaAlunos(View view) {
        Intent intent = new Intent(TelaAlunoDiretoria.this, CadastrarAluno.class);
        startActivity(intent);
        finish();
    }
    public void voltarTelaDiretoria(View view) {
        Intent intent = new Intent(TelaAlunoDiretoria.this, MenuDiretoria.class);
        startActivity(intent);
        finish();
    }
    public void listaAlunosSpinner(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {

            final List<String> alunos = new ArrayList<String>();
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Aluno a = item.getValue(Aluno.class);
                    alunos.add(a.getNomeAluno());

                }

                spinnerEditar = (Spinner) findViewById(R.id.spinnerAlunos);
                spinnerEditarLista = new ArrayList<>();
                adapterEditar = new ArrayAdapter<String>(TelaAlunoDiretoria.this,
                        android.R.layout.simple_spinner_dropdown_item, alunos);
                spinnerEditar.setAdapter(adapterEditar);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void editarAluno(){


        final String nome=spinnerEditar.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("alunos").orderByChild("nomeAluno").equalTo(nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    Aluno aluno = alunosSnapShot.getValue(Aluno.class);
                    nomeUserAlunoEditar = aluno.getNomeAluno();
                    nomeMaeUserAlunoEditar=aluno.getNomeMaeAluno();
                    nomePaiUserAlunoEditar= aluno.getNomePaiAluno();
                    telefoneUserAlunoEditar= aluno.getTelefoneAluno();
                    emailUserAlunoEditar= aluno.getEmailAluno();
                    keyUserAlunoEditar=aluno.getKeyUser();

                    Intent intent = new Intent(TelaAlunoDiretoria.this, UpdateAluno.class);
                    startActivity(intent);
                    finish();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void excluirAluno(){


        final String nome=spinnerEditar.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("alunos").orderByChild("nomeAluno").equalTo(nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    Aluno aluno = alunosSnapShot.getValue(Aluno.class);


                    databaseReference.child(aluno.getKeyUser()).removeValue();


                    recreate();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}