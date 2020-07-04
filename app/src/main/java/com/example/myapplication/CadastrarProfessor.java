package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CadastrarProfessor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<String> professoreasAdicionarCriar;
    String keyTurmaSelecionadoCriar;
    Turma turmaProfessorAdicionadoCriar = new Turma();
    private EditText edtNomeProfessora, edtTelefoneProfessora, edtEmailProfessora, edtSenhaProfessora;
    private Spinner spinner_cargo_professora;
    private Button btnCadastrarProfessor;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Professor professor;
    private DatabaseReference reference;
    Spinner todasasturmasprof;
    List<String> listaTurmasprof;
    SpinnerAdapter adapterlistarTurmasprof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_professor);

        Spinner cargo = findViewById(R.id.spinner_cargo_professora);
        ArrayAdapter<CharSequence> cargoProfessorAdapter = ArrayAdapter.createFromResource(this,R.array.cargo,android.R.layout.simple_spinner_item);
        cargoProfessorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargo.setAdapter(cargoProfessorAdapter);
        cargo.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        professor = new Professor();

        listarTurmasAddProf();
        initializeUI();

        btnCadastrarProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void initializeUI() {

        edtNomeProfessora = findViewById(R.id.edtNomeProfessora);
        edtTelefoneProfessora = findViewById(R.id.edtTelefoneProfessora);
        spinner_cargo_professora= findViewById(R.id.spinner_cargo_professora);
        edtEmailProfessora = findViewById(R.id.edtEmailProfessora);
        edtSenhaProfessora = findViewById(R.id.edtSenhaProfessora);

        btnCadastrarProfessor = findViewById(R.id.btnCadastrarProfessora);

    }
    private void registerNewUser() {

        String nomeProfessora,telefoneProfessora, cargoProfessora,  emailProfessora, senhaProfessora;
        Integer cargoSpinnerProfessora;
        nomeProfessora = edtNomeProfessora.getText().toString();
        telefoneProfessora = edtTelefoneProfessora.getText().toString();
        cargoProfessora= spinner_cargo_professora.getSelectedItem().toString();
        cargoSpinnerProfessora = spinner_cargo_professora.getSelectedItemPosition();
        emailProfessora = edtEmailProfessora.getText().toString();
        senhaProfessora = edtSenhaProfessora.getText().toString();


        if (TextUtils.isEmpty(nomeProfessora)) {
            Toast.makeText(getApplicationContext(), "Informe o Nome", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(telefoneProfessora)) {
            Toast.makeText(getApplicationContext(), "Informe o Telefone", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(cargoProfessora)) {
            Toast.makeText(getApplicationContext(), "Selecione o cargo", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(emailProfessora)) {
            Toast.makeText(getApplicationContext(), "Insira o email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(senhaProfessora)) {
            Toast.makeText(getApplicationContext(), "Insira a senha", Toast.LENGTH_LONG).show();
            return;
        }

        professor.setCargoSpinnerProfessora(cargoSpinnerProfessora);
        professor.setNomeProfessora(edtNomeProfessora.getText().toString());
        professor.setTelefoneProfessora(edtTelefoneProfessora.getText().toString());
        professor.setCargoProfessora(spinner_cargo_professora.getSelectedItem().toString());
        professor.setEmailProfessora(edtEmailProfessora.getText().toString());
        professor.setSenhaProfessora(edtSenhaProfessora.getText().toString());

        criarConta(professor);



    }
    private void criarConta(final Professor professor) {
        mAuth.createUserWithEmailAndPassword(professor.getEmailProfessora(), professor.getSenhaProfessora())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registrado com sucesso", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            CadastrarProfessorDatabase(professor);
                            CadastrarProfDiretoTurma(professor.getNomeProfessora());
                            Intent intent = new Intent(CadastrarProfessor.this, Dash.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao Registrar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void CadastrarProfessorDatabase(Professor professor){


        myRef = database.getReference("professores");
        String key = myRef.child("professores").push().getKey();

        professor.setKeyUser(key);
        myRef.child(key).setValue(professor);


    }
    public void voltarTelaAlunos(View view) {
        Intent intent = new Intent(CadastrarProfessor.this, TelaProfessorDiretoria.class);
        startActivity(intent);
        finish();
    }
    public void listarTurmasAddProf() {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> turmas = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                turmas.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Turma t = item.getValue(Turma.class);

                    turmas.add(t.getNomeTurma());

                }

                todasasturmasprof = (Spinner) findViewById(R.id.spinner_todas_turmas_addprof);
                listaTurmasprof = new ArrayList<>();
                adapterlistarTurmasprof = new ArrayAdapter<String>(CadastrarProfessor.this,
                        android.R.layout.simple_spinner_dropdown_item, turmas);
                todasasturmasprof.setAdapter(adapterlistarTurmasprof);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CadastrarProfDiretoTurma(final String nomeProfCriado){

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(todasasturmasprof.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);


                    professoreasAdicionarCriar = turma.getProfessores();

                    keyTurmaSelecionadoCriar = turma.getKeyUserTurma();
                    turmaProfessorAdicionadoCriar.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaProfessorAdicionadoCriar.setNomeTurma(turma.getNomeTurma());
                    turmaProfessorAdicionadoCriar.setAlunosTurma(turma.getAlunosTurma());

                }


                professoreasAdicionarCriar.add(nomeProfCriado);

                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaProfessorAdicionadoCriar.setProfessores(professoreasAdicionarCriar);

                reference.child(turmaProfessorAdicionadoCriar.getKeyUserTurma()).setValue(turmaProfessorAdicionadoCriar);


                final DatabaseReference referenciaProfessora = FirebaseDatabase.getInstance().getReference("professores");
                referenciaProfessora.orderByChild("nomeProfessora").equalTo(nomeProfCriado).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Professor professoracomTurma = new Professor();
                        for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                            Professor professor = profSnap.getValue(Professor.class);

                            professoracomTurma.setCargoProfessora(professor.getCargoProfessora());
                            professoracomTurma.setNomeProfessora(professor.getNomeProfessora());
                            professoracomTurma.setCargoSpinnerProfessora(professor.getCargoSpinnerProfessora());
                            professoracomTurma.setSenhaProfessora(professor.getSenhaProfessora());
                            professoracomTurma.setTelefoneProfessora(professor.getTelefoneProfessora());
                            professoracomTurma.setKeyUser(professor.getKeyUser());
                            professoracomTurma.setEmailProfessora(professor.getEmailProfessora());
                            professoracomTurma.setKeyTurma(keyTurmaSelecionadoCriar);

                            referenciaProfessora.child(professor.getKeyUser()).setValue(professoracomTurma);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}