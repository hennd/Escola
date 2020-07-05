package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcluirTurma extends AppCompatActivity {
    private Turma turma;
    Button btnVoltarMenu;
    ValueEventListener listener;
    DatabaseReference reference;
    String nomeTurma;
    EditText edtNomeTurma;
    Button btnCriarTurma;
    List<String> listaProfessoresNova=new ArrayList<>();
    List<String> listaAlunosNova = new ArrayList<>();
    Spinner spinnerExcluirTurma;
    Button excluirTurma;
    Button irGerenciarTurmas;


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Turmas");
    ArrayAdapter<String> adapterExcluir;
    ArrayList<String> spinnerExcluirLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_turma);

        listarTurmasSpinner();
        btnVoltarMenu=(Button)findViewById(R.id.btnVoltarMenuTurmas);
        edtNomeTurma= (EditText)findViewById(R.id.edtNomeTurma);
        btnCriarTurma=(Button)findViewById(R.id.btnCriarTurma);
        spinnerExcluirTurma=(Spinner)findViewById(R.id.spinner_excluir_turmas);
        excluirTurma=(Button)findViewById(R.id.btnExcluirTurma);



        excluirTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirTurma();


            }
        });

    }


    public void excluirTurma(){

        final String nome=spinnerExcluirTurma.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Turmas").orderByChild("nomeTurma").equalTo(nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = alunosSnapShot.getValue(Turma.class);


                    databaseReference.child(turma.getKeyUserTurma()).removeValue();


               //     recreate();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void listarTurmasSpinner(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {

            final List<String> turmas = new ArrayList<String>();
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Turma t = item.getValue(Turma.class);
                    turmas.add(t.getNomeTurma());

                }

                spinnerExcluirTurma = (Spinner) findViewById(R.id.spinner_excluir_turmas);
                spinnerExcluirLista = new ArrayList<>();
                adapterExcluir = new ArrayAdapter<String>(ExcluirTurma.this,
                        android.R.layout.simple_spinner_dropdown_item, turmas);
                spinnerExcluirTurma.setAdapter(adapterExcluir);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void voltarExcluirTurmas(View view) {
        Intent intent = new Intent(ExcluirTurma.this, MenuTurmas.class);
        startActivity(intent);
        finish();
    }
}