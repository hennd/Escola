package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Editar extends AppCompatActivity {


    Button editarBotao;
    EditText editTeste;
    Spinner spinnerEditar;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterEditar;
    ArrayList<String> spinnerEditarLista;
     DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editarBotao = (Button) findViewById(R.id.botaoEditar);
        editTeste = (EditText) findViewById(R.id.edtTeste);

        databaseReference = FirebaseDatabase.getInstance().getReference("alunos");



        retrieveData();

        editarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarAluno();
            }
        });
    }
    public void retrieveData(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {

                        final List<String> alunos = new ArrayList<String>();
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot item : dataSnapshot.getChildren()) {
                                Aluno a = item.getValue(Aluno.class);
                                alunos.add(a.getNomeAluno());

                            }
                            spinnerEditar = (Spinner) findViewById(R.id.spinnerEditar);
                            spinnerEditarLista = new ArrayList<>();
                            adapterEditar = new ArrayAdapter<String>(Editar.this,
                                    android.R.layout.simple_spinner_dropdown_item, alunos);
                            spinnerEditar.setAdapter(adapterEditar);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void alterarEdt(){

        editTeste.setText("nome");
    }
    public void editarAluno(){


        final String nome=spinnerEditar.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("alunos").orderByChild("nomeAluno").equalTo(nome).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                Aluno aluno = alunosSnapShot.getValue(Aluno.class);
                alunosSnapShot.getRef().child("nomeAluno").setValue("Rafael");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            editTeste.setText("falhou");
        }
    });

    }
}