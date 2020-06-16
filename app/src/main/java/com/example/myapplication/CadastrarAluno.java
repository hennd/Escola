package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarAluno extends AppCompatActivity {

    private EditText nomeAluno, nomeMaeAluno, nomePaiAluno, telefoneAluno, emailTV, passwordTV;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        aluno = new Aluno();
        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        String nomealuno,nomemae, nomepai,telefonealuno, email, password;
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
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            CadastrarAlunoDatabase(aluno);

                            Intent intent = new Intent(CadastrarAluno.this, Dash.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void voltarTelaAlunos(View view) {
        Intent intent = new Intent(CadastrarAluno.this, TelaAlunoDiretoria.class);
        startActivity(intent);
        finish();
    }

}