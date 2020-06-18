package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfessora extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edtNomeProfessoraAtualiza;
    private EditText edtTelefoneProfessoraAtualiza;
    private Spinner spinner_cargo_professora_atualiza;
    private Button btnAtualizarProfessora;
    private Button btnVoltarProfessora;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_professora);

        btnVoltarProfessora= (Button)findViewById(R.id.btnVoltarTelaProfessores);
        edtNomeProfessoraAtualiza= (EditText)findViewById(R.id.edtNomeProfessoraAtualiza);
        edtTelefoneProfessoraAtualiza= (EditText)findViewById(R.id.edtTelefoneProfessoraAtualiza);
        spinner_cargo_professora_atualiza=(Spinner)findViewById(R.id.spinner_cargo_professora_atualiza);

        Spinner cargoProfessoraAtualiza = findViewById(R.id.spinner_cargo_professora_atualiza);
        ArrayAdapter<CharSequence> cargoProfessorAtualizaAdapter = ArrayAdapter.createFromResource(this,R.array.cargo,android.R.layout.simple_spinner_item);
        cargoProfessorAtualizaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargoProfessoraAtualiza.setAdapter(cargoProfessorAtualizaAdapter);
        cargoProfessoraAtualiza.setOnItemSelectedListener(this);

        btnAtualizarProfessora= (Button)findViewById(R.id.btnAtualizaProfessora);

        edtNomeProfessoraAtualiza.setText(TelaProfessorDiretoria.nomeProfessoraEditar);
        edtTelefoneProfessoraAtualiza.setText(TelaProfessorDiretoria.telefoneProfessoraEditar);
        spinner_cargo_professora_atualiza.setSelection(TelaProfessorDiretoria.spinnerCargoProfessoraPosition);



        btnAtualizarProfessora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDadosProfessora();
            }
        });


        btnVoltarProfessora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaProfessores();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void voltarTelaProfessores(){
        Intent intent = new Intent(UpdateProfessora.this, TelaProfessorDiretoria.class);
        startActivity(intent);
        finish();
    }
    public void atualizarDadosProfessora(){

        String key = TelaProfessorDiretoria.keyUserProfessoraEditar;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("professores").orderByChild("keyUser").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot professorasSnapShot : dataSnapshot.getChildren()) {
                    Professor professor = professorasSnapShot.getValue(Professor.class);
                    professorasSnapShot.getRef().child("nomeProfessora").setValue(edtNomeProfessoraAtualiza.getText().toString());
                    professorasSnapShot.getRef().child("telefoneProfessora").setValue(edtTelefoneProfessoraAtualiza.getText().toString());
                    professorasSnapShot.getRef().child("cargoSpinnerProfessora").setValue(spinner_cargo_professora_atualiza.getSelectedItemPosition());
                    professorasSnapShot.getRef().child("cargoProfessora").setValue(spinner_cargo_professora_atualiza.getSelectedItem().toString());
                    Toast.makeText(UpdateProfessora.this, "Dados Atualizados", Toast.LENGTH_LONG).show();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}