package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    List<String> alunosTurma = new ArrayList<>();
    List<String> professoresTurma = new ArrayList<>();

    DatabaseReference reference;
    String nomeTurma;
    EditText edtNomeTurma;
    Button btnCriarTurma;


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

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo("turma da Roberta t3ete").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);


                    edtNomeTurma.setText("entrou");

                    alunosTurma = turma.getAlunosTurma();

                    edtNomeTurma.setText("passou");

                    alunosTurma.add(turma.getAlunosTurma().toString());

                    Toast.makeText(AdicionarTurma.this, "Dados Atualizados", Toast.LENGTH_LONG).show();
                    recreate();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nomeTurma= edtNomeTurma.getText().toString();
        alunosTurma.add("aluno teste254");
        professoresTurma.add("professora teste");
        reference = FirebaseDatabase.getInstance().getReference("Turmas");

        String key = reference.child("Turmas").push().getKey();

        turma.setKeyUserTurma(key);
        turma.setNomeTurma(nomeTurma);
        turma.setProfessores(professoresTurma);
        turma.setAlunosTurma(alunosTurma);
        reference.child(key).setValue(turma);

    }

}