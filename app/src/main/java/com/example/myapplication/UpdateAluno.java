package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAluno extends AppCompatActivity {


    private EditText edtNomeAlunoAtualiza;
    private EditText edtNomeMaeAlunoAtualiza;
    private EditText edtNomePaiAlunoAtualiza;
    private EditText edtTelefoneContatoAlunoAtualiza;
    private Button btnAtualizar;
    private Button btnVoltar;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_aluno);
        edtNomeAlunoAtualiza= (EditText)findViewById(R.id.edtNomeAlunoAtualiza);
        edtNomeMaeAlunoAtualiza= (EditText)findViewById(R.id.edtNomeMaeAlunoAtualiza);
        edtNomePaiAlunoAtualiza= (EditText)findViewById(R.id.edtNomePaiAlunoAtualiza);
        edtTelefoneContatoAlunoAtualiza= (EditText)findViewById(R.id.edtTelefoneContatoAlunoAtualiza);
        btnAtualizar= (Button)findViewById(R.id.btnAtualizar);
        btnVoltar= (Button)findViewById(R.id.btnVoltar);
        edtNomeAlunoAtualiza.setText(TelaAlunoDiretoria.nomeUserAlunoEditar);
        edtNomeMaeAlunoAtualiza.setText(TelaAlunoDiretoria.nomeMaeUserAlunoEditar);
        edtNomePaiAlunoAtualiza.setText(TelaAlunoDiretoria.nomePaiUserAlunoEditar);
        edtTelefoneContatoAlunoAtualiza.setText(TelaAlunoDiretoria.telefoneUserAlunoEditar);

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDadosAluno();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaAlunos();
            }
        });
    }
    public void atualizarDadosAluno(){

        String key = TelaAlunoDiretoria.keyUserAlunoEditar;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("alunos").orderByChild("keyUser").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    Aluno aluno = alunosSnapShot.getValue(Aluno.class);
                    alunosSnapShot.getRef().child("nomeAluno").setValue(edtNomeAlunoAtualiza.getText().toString());
                    alunosSnapShot.getRef().child("nomeMaeAluno").setValue(edtNomeMaeAlunoAtualiza.getText().toString());
                    alunosSnapShot.getRef().child("nomePaiAluno").setValue(edtNomePaiAlunoAtualiza.getText().toString());
                    alunosSnapShot.getRef().child("telefoneAluno").setValue(edtTelefoneContatoAlunoAtualiza.getText().toString());
                    Toast.makeText(UpdateAluno.this, "Dados Atualizados", Toast.LENGTH_LONG).show();
                    recreate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void voltarTelaAlunos(){
        Intent intent = new Intent(UpdateAluno.this, TelaAlunoDiretoria.class);
        startActivity(intent);
        finish();
    }

}