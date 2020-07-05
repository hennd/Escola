package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TelaDiretoriaGerenciarRotinas extends AppCompatActivity {
    Boolean passou=false;
    Boolean passouExcluir=false;
    Button btnsairgerenciarrotina;
    Button dprotinagerenciar;
    SpinnerAdapter adapterTodosAlunos;
    Spinner spinnerTodosAlunos;
    EditText edtdataselecionadagerenciar;
    Button btnsairGerenciarRotinas;

    List<String> todosalunos = new ArrayList<>();
    static public String alunoselecionado;
    static public String idagendabuscar;
    static public String dataselecionadagerenciar;

    final Calendar c = Calendar.getInstance();
    final int day = c.get(Calendar.DAY_OF_MONTH);
    final int month = c.get(Calendar.MONTH);
    final int year = c.get(Calendar.YEAR);
    private DatePickerDialog datapicker;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button btneditarrotina;
    private Button btnexcluirrotina;
   DatabaseReference referenciaAlunoAgenda = FirebaseDatabase.getInstance().getReference("agendas");
    DatabaseReference referenceAlunos = FirebaseDatabase.getInstance().getReference("alunos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_diretoria_gerenciar_rotinas);
        spinnerTodosAlunos =(Spinner)findViewById(R.id.spinner_alunos_Gerenciar);
        btnsairgerenciarrotina=(Button)findViewById(R.id.btnSairGerenciarRotinas);
        btneditarrotina=(Button)findViewById(R.id.btnEditarRotina);
        dprotinagerenciar=(Button)findViewById(R.id.dpRotinaGerenciar);
        edtdataselecionadagerenciar=(EditText)findViewById(R.id.edtDataGerenciar);
        edtdataselecionadagerenciar.setEnabled(false);
        btnexcluirrotina=(Button)findViewById(R.id.btnExcluirRotina);
        btnsairgerenciarrotina=(Button)findViewById(R.id.btnSairGerenciarRotinas);
        btnsairgerenciarrotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaInicialAluno.pesquisaAgenda = " ";
                Intent intent = new Intent(TelaDiretoriaGerenciarRotinas.this, MenuDiretoria.class);
                startActivity(intent);
                finish();
            }
        });
        listarTodosAlunos();
        btnexcluirrotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alunoselecionado=spinnerTodosAlunos.getSelectedItem().toString();
                idagendabuscar=alunoselecionado+dataselecionadagerenciar;
                excluirRotina();
            }
        });
        btneditarrotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alunoselecionado=spinnerTodosAlunos.getSelectedItem().toString();
                idagendabuscar=alunoselecionado+dataselecionadagerenciar;
                irEditarRotina();
            }
        });
        dprotinagerenciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datapicker=new DatePickerDialog(TelaDiretoriaGerenciarRotinas.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        edtdataselecionadagerenciar.setText(mDay + "/" +(mMonth+1) + "/" + mYear);
                        dataselecionadagerenciar=mDay + "/" +(mMonth+1) + "/" + mYear;
                        edtdataselecionadagerenciar.requestFocus();
                    }
                },day,month,year);
                datapicker.updateDate(2020,1-1,1);
                datapicker.show();

            }
        });
    }

    public void listarTodosAlunos() {

        referenceAlunos = FirebaseDatabase.getInstance().getReference("alunos");
        referenceAlunos.addValueEventListener(new ValueEventListener() {

            final List<String> alunos = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alunos.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Aluno a = item.getValue(Aluno.class);
                    alunos.add(a.getNomeAluno());

                }


                todosalunos = new ArrayList<>();
                adapterTodosAlunos = new ArrayAdapter<String>(TelaDiretoriaGerenciarRotinas.this,
                        android.R.layout.simple_spinner_dropdown_item, alunos);
                spinnerTodosAlunos.setAdapter(adapterTodosAlunos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void irEditarRotina(){

        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(idagendabuscar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);

                    passou=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(passou==false) {
            Toast.makeText(this, "Não há rotinas com estas informações selecionadas", Toast.LENGTH_LONG).show();

        }else {
            Intent intent = new Intent(TelaDiretoriaGerenciarRotinas.this, GerenciarRotinas.class);
            startActivity(intent);
            finish();
        }
    }
    public void excluirRotina(){

        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(idagendabuscar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);

                    referenciaAlunoAgenda.child(dados.getKeyAgenda()).removeValue();
                    passouExcluir=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(passouExcluir==false) {
            Toast.makeText(this, "Não há rotinas com estas informações selecionadas", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Rotina do aluno: "+alunoselecionado+", do dia "+dataselecionadagerenciar+" excluida", Toast.LENGTH_LONG).show();
        }
    }
    }
