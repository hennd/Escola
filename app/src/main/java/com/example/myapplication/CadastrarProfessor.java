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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarProfessor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edtNomeProfessora, edtTelefoneProfessora, edtEmailProfessora, edtSenhaProfessora;
    private Spinner spinner_cargo_professora;
    private Button btnCadastrarProfessor;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Professor professor;
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

        String nomeProfessora,telefoneProfessora, cargoProfessora, emailProfessora, senhaProfessora;
        nomeProfessora = edtNomeProfessora.getText().toString();
        telefoneProfessora = edtTelefoneProfessora.getText().toString();
        cargoProfessora = spinner_cargo_professora.getSelectedItem().toString();
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
}