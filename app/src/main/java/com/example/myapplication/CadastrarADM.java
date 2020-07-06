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

public class CadastrarADM extends AppCompatActivity {


    private EditText edtnomeAdministrador, edtemailadministrador, edtsenhaadministrador;
    private Button btncadastraradm, btnvoltartelaadm;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Administrador administrador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_a_d_m);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        administrador = new Administrador();

        initializeUI();

        btncadastraradm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNovoAdm();
            }
        });
        btnvoltartelaadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sairtelaADM();
            }
        });


    }

    private void initializeUI() {

        edtnomeAdministrador = findViewById(R.id.edtNomeAdm);
        edtemailadministrador = findViewById(R.id.edtEmailAdm);
        edtsenhaadministrador = findViewById(R.id.edtSenhaAdm);

        btncadastraradm = findViewById(R.id.btnCadastrarAdministrador);
        btnvoltartelaadm = findViewById(R.id.btnVoltarTelaAdministrador);
    }

    private void CadastrarAdministradorDatabase(Administrador administrador) {


        myRef = database.getReference("administradores");
        String key = myRef.child("administradores").push().getKey();

        administrador.setKyeUserAdministrador(key);
        myRef.child(key).setValue(administrador);


    }

    private void criarConta(final Administrador Administrador) {
        mAuth.createUserWithEmailAndPassword(administrador.getEmailAdministrador(), administrador.getSenhaAdministrador())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            CadastrarAdministradorDatabase(administrador);


                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void registerNovoAdm() {

        String nomeadm, emailadm, senhaadm;
        nomeadm = edtnomeAdministrador.getText().toString();
        emailadm = edtemailadministrador.getText().toString();
        senhaadm = edtsenhaadministrador.getText().toString();


        if (TextUtils.isEmpty(nomeadm)) {
            Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(emailadm)) {
            Toast.makeText(getApplicationContext(), "Please enter maename!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(senhaadm)) {
            Toast.makeText(getApplicationContext(), "Please enter painame!", Toast.LENGTH_LONG).show();
            return;
        }


            administrador.setNomeAdministrador(edtnomeAdministrador.getText().toString());
            administrador.setEmailAdministrador(edtemailadministrador.getText().toString());
            administrador.setSenhaAdministrador(edtsenhaadministrador.getText().toString());

            criarConta(administrador);


    }
    public void sairtelaADM(){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(CadastrarADM.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Saindo", Toast.LENGTH_LONG).show();
    }
}