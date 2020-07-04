package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
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

public class CadastrarAluno extends AppCompatActivity {
    List<String> alunosTurmaAdicionar;
    String keyTurmaAlunoSelecionado;
    Turma turmaAlunoAdicionado = new Turma();
    String teste;
    private EditText nomeAluno, nomeMaeAluno, nomePaiAluno, telefoneAluno, emailTV, passwordTV;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Aluno aluno;
    private DatabaseReference reference;
    Spinner todasasturmas;
    List<String> listaTurmas;
    SpinnerAdapter adapterlistarTurmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        aluno = new Aluno();
        listarTurmasAdd();
        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();

            }
        });
    }

    private void registerNewUser() {


        String nomealuno, nomemae, nomepai, telefonealuno, email, password;
        nomealuno = nomeAluno.getText().toString();
        nomemae = nomeMaeAluno.getText().toString();
        nomepai = nomePaiAluno.getText().toString();
        telefonealuno = telefoneAluno.getText().toString();
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();




        if (TextUtils.isEmpty(nomealuno)) {
            Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(nomemae)) {

            Toast.makeText(getApplicationContext(), "Please enter maename!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(nomepai)) {
            Toast.makeText(getApplicationContext(), "Please enter painame!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(telefonealuno)) {
            Toast.makeText(getApplicationContext(), "Please enter telname!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }




        aluno.setNomeAluno(nomeAluno.getText().toString());
        aluno.setNomeMaeAluno(nomeMaeAluno.getText().toString());
        aluno.setNomePaiAluno(nomePaiAluno.getText().toString());
        aluno.setTelefoneAluno(telefoneAluno.getText().toString());
        aluno.setEmailAluno(emailTV.getText().toString());
        aluno.setPassword(passwordTV.getText().toString());

        criarConta(aluno);



    }

    private void initializeUI() {

        nomeAluno = findViewById(R.id.nomeAluno);
        nomePaiAluno = findViewById(R.id.nomePaiAluno);
        nomeMaeAluno = findViewById(R.id.nomeMaeAluno);
        telefoneAluno= findViewById(R.id.telefoneContatoAluno);
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);

        regBtn = findViewById(R.id.register);

    }

    private void CadastrarAlunoDatabase(Aluno aluno){


        myRef = database.getReference("alunos");
        String key = myRef.child("alunos").push().getKey();

        aluno.setKeyUser(key);
        myRef.child(key).setValue(aluno);


    }
    private void criarConta(final Aluno aluno) {
        mAuth.createUserWithEmailAndPassword(aluno.getEmailAluno(), aluno.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuário Cadastrado", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            CadastrarAlunoDatabase(aluno);
                            CadastrarAlunoAddTurma(aluno.getNomeAluno());

                            Intent intent = new Intent(CadastrarAluno.this, TelaAlunoDiretoria.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Dados de email ou senha inválidos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void voltarTelaAlunos(View view) {
        Intent intent = new Intent(CadastrarAluno.this, TelaAlunoDiretoria.class);
        startActivity(intent);
        finish();
    }

    public void listarTurmasAdd() {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> turmas = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                turmas.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Turma t = item.getValue(Turma.class);

                    turmas.add(t.getNomeTurma());

                }

                todasasturmas = (Spinner) findViewById(R.id.spinner_todas_turmas_addalunos);
                listaTurmas = new ArrayList<>();
                adapterlistarTurmas = new ArrayAdapter<String>(CadastrarAluno.this,
                        android.R.layout.simple_spinner_dropdown_item, turmas);
                todasasturmas.setAdapter(adapterlistarTurmas);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void CadastrarAlunoAddTurma(final String nomeAlunoAdd){

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(todasasturmas.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);


                    alunosTurmaAdicionar = turma.getAlunosTurma();
                    keyTurmaAlunoSelecionado = turma.getKeyUserTurma();
                    turmaAlunoAdicionado.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaAlunoAdicionado.setNomeTurma(turma.getNomeTurma());
                    turmaAlunoAdicionado.setProfessores(turma.getProfessores());

                }


                alunosTurmaAdicionar.add(nomeAlunoAdd);
                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaAlunoAdicionado.setAlunosTurma(alunosTurmaAdicionar);

                reference.child(turmaAlunoAdicionado.getKeyUserTurma()).setValue(turmaAlunoAdicionado);


                final DatabaseReference referenciaAlunosaddd = FirebaseDatabase.getInstance().getReference("alunos");
                referenciaAlunosaddd.orderByChild("nomeAluno").equalTo(nomeAlunoAdd).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Aluno alunocomturma = new Aluno();
                        for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                            Aluno aluno = profSnap.getValue(Aluno.class);


                            alunocomturma.setKeyUser(aluno.getKeyUser());
                            alunocomturma.setNomeAluno(aluno.getNomeAluno());
                            alunocomturma.setEmailAluno(aluno.getEmailAluno());
                            alunocomturma.setPassword(aluno.getPassword());
                            alunocomturma.setNomeMaeAluno(aluno.getNomeMaeAluno());
                            alunocomturma.setNomePaiAluno(aluno.getNomePaiAluno());
                            alunocomturma.setTelefoneAluno(aluno.getTelefoneAluno());
                            alunocomturma.setKeyTurma(keyTurmaAlunoSelecionado);

                            referenciaAlunosaddd.child(aluno.getKeyUser()).setValue(alunocomturma);
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