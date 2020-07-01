package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {


    private EditText emailTV, passwordTV;
    private Button loginBtn;
    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    public static Boolean ealuno;
    public static Boolean eprofessor;
    public static Boolean eadm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        initializeUI();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {


        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }


        loginTelaInicial(emailTV.getText().toString(), passwordTV.getText().toString());

    }

    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
    }

    private void loginTelaInicial(final String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();



                            reference = FirebaseDatabase.getInstance().getReference();
                            String email = mAuth.getCurrentUser().getEmail();




                            reference.child("alunos").orderByChild("emailAluno").equalTo(email).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                                        ealuno=true;
                                      abrirAluno();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "Nao rolou o search", Toast.LENGTH_LONG).show();
                                }
                            });
                            reference.child("administradores").orderByChild("emailAdministrador").equalTo(email).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {

                                        abrirAdministrador();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "Nao rolou o search", Toast.LENGTH_LONG).show();
                                }
                            });

                            reference.child("professores").orderByChild("emailProfessora").equalTo(email).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {

                                        abrirProfessor();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "Nao rolou o search", Toast.LENGTH_LONG).show();
                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void abrirAdministrador() {
        Intent intent = new Intent(Login.this, MenuDiretoria.class);
        startActivity(intent);
        finish();
    }
    private void abrirAluno() {
        Intent intent = new Intent(Login.this, TelaInicialAluno.class);
        startActivity(intent);
        finish();
    }
    private void abrirProfessor() {
        Intent intent = new Intent(Login.this, TelaInicialProfessor.class);
        startActivity(intent);

    }
    private void abrirTelaInicial() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        firebaseAuth = firebaseAuth.getInstance();


        if (currentUser != null) {


        } else {
            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

        }
    }
}