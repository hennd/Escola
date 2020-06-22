package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdicionarTurma extends AppCompatActivity {

    private Turma turma;

    DatabaseReference reference;
    String nomeTurma;
    EditText edtNomeTurma;
    Button btnCriarTurma;
    List<String> listaProfessoresNova=new ArrayList<>();
    List<String> listaAlunosNova = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_turma);

        edtNomeTurma= (EditText)findViewById(R.id.edtNomeTurma);
        btnCriarTurma=(Button)findViewById(R.id.btnCriarTurma);



        btnCriarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTurma();


            }
        });


    }

    public void salvarTurma(){

        turma = new Turma();

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

            }
    public void excluirTurma(){

        turma = new Turma();

        nomeTurma= edtNomeTurma.getText().toString();
        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        String key = reference.child("Turmas").push().getKey();

        turma.setKeyUserTurma(key);
        turma.setNomeTurma(nomeTurma);
        reference.child(key).setValue(turma);

        Toast.makeText(this, "Turma criada com sucesso!", Toast.LENGTH_SHORT).show();

    }






}