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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaProfessorDiretoria extends AppCompatActivity {

    Button btnEditarProfessora;
    Button btnExcluirProfessora;
    Spinner spinnerEditarProfessoras;
    TextView txtProfessoras;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapterEditarProfessora;
    ArrayList<String> spinnerEditarListaProfessoras;
    DatabaseReference reference;

    public static String nomeProfessoraEditar;
    public static String telefoneProfessoraEditar;
    public static String cargoProfessoraEditar;
    public static String keyUserProfessoraEditar;
    public static Integer spinnerCargoProfessoraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_professor_diretoria);

        btnEditarProfessora = (Button) findViewById(R.id.btnEditarProfessora);
        btnExcluirProfessora = (Button)findViewById(R.id.btnExcluirProfessora);

        databaseReference = FirebaseDatabase.getInstance().getReference("professores");

        listaProfessorasSpinner();


        btnExcluirProfessora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirProfesora();
            }
        });

        btnEditarProfessora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarProfessora();
            }
        });

    }
    public void listaProfessorasSpinner(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {

            final List<String> professoras = new ArrayList<String>();
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Professor a = item.getValue(Professor.class);
                    professoras.add(a.getNomeProfessora());

                }

                spinnerEditarProfessoras = (Spinner) findViewById(R.id.spinnerProfessoras);
                spinnerEditarListaProfessoras = new ArrayList<>();
                adapterEditarProfessora = new ArrayAdapter<String>(TelaProfessorDiretoria.this,
                        android.R.layout.simple_spinner_dropdown_item, professoras);
                spinnerEditarProfessoras.setAdapter(adapterEditarProfessora);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void editarProfessora() {


        final String nome = spinnerEditarProfessoras.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("professores").orderByChild("nomeProfessora").equalTo(nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot professorasSnapShot : dataSnapshot.getChildren()) {
                    Professor professor = professorasSnapShot.getValue(Professor.class);

                    nomeProfessoraEditar = professor.getNomeProfessora();
                    telefoneProfessoraEditar = professor.getTelefoneProfessora();
                    cargoProfessoraEditar = professor.getCargoProfessora();
                    keyUserProfessoraEditar = professor.getKeyUser();
                    spinnerCargoProfessoraPosition= professor.getCargoSpinnerProfessora();


                    Intent intent = new Intent(TelaProfessorDiretoria.this, UpdateProfessora.class);
                    startActivity(intent);
                    finish();

                    // DatabaseReference agoravai = reference.child("alunos").child(aluno.getKeyUser()).child(aluno.getNomeAluno()).child("Agenda").push();
                    // editTeste.setText(aluno.getNomeMaeAluno());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void excluirProfesora(){


        final String nome=spinnerEditarProfessoras.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("professores").orderByChild("nomeProfessora").equalTo(nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot professorasSnapShot : dataSnapshot.getChildren()) {
                    Professor professor = professorasSnapShot.getValue(Professor.class);


                    databaseReference.child(professor.getKeyUser()).removeValue();


                    recreate();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void voltarTelaDiretoria(View view) {
        Intent intent = new Intent(TelaProfessorDiretoria.this, MenuDiretoria.class);
        startActivity(intent);
        finish();
    }
    public void abrirTelaProfessoras(View view) {
        Intent intent = new Intent(TelaProfessorDiretoria.this, CadastrarProfessor.class);
        startActivity(intent);
        finish();
    }

}