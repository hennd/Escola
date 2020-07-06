package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TelaInicialProfessor extends AppCompatActivity {

    Boolean passou=false;
    Button btnsairprofessor;
    Button dprotina;
    String emailProfessorLogado;
    String nomeProfessorLogado;
    SpinnerAdapter adapterListaAlunosProf;
    Spinner spinneralunosTurmaProfessorLogado;
    String keyTurmaProfessorLogado;
    EditText edtdataselecionada;

    List<String> alunosturmaproflogado = new ArrayList<>();
    static public String idalunoSelecionadoRotina;
    static public String nomealunoselecionado;
    static public String dataselecionada;
    final Calendar c = Calendar.getInstance();
    final int day = c.get(Calendar.DAY_OF_MONTH);
    final int month = c.get(Calendar.MONTH);
    final int year = c.get(Calendar.YEAR);
    private DatePickerDialog datapicker;
    private Date DataAtual = new Date();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button btnadicionarrotina;
    private Button btnvisualizarrotina;
    DatabaseReference referenceAlunos = FirebaseDatabase.getInstance().getReference("alunos");
    DatabaseReference referenceProfessor = FirebaseDatabase.getInstance().getReference("professores");
    DatabaseReference referenceTurmas = FirebaseDatabase.getInstance().getReference("Turmas");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_professor);
        spinneralunosTurmaProfessorLogado =(Spinner)findViewById(R.id.spinner_alunos_prof_logado);
        btnsairprofessor=(Button)findViewById(R.id.btnSairProfessor);
        btnadicionarrotina=(Button)findViewById(R.id.btnAdicionarRotina);
        dprotina=(Button)findViewById(R.id.dpRotina);
        edtdataselecionada=(EditText)findViewById(R.id.edtData);
        edtdataselecionada.setEnabled(false);
        btnvisualizarrotina=(Button)findViewById(R.id.btnVisualizarRotina);

        DataAtual.getTime();
        SimpleDateFormat sddia= new SimpleDateFormat("dd");
        SimpleDateFormat smes= new SimpleDateFormat("MM");
        SimpleDateFormat sano= new SimpleDateFormat("yyyy");
        String diaagora = sddia.format(DataAtual);
        final String anoagora = sano.format(DataAtual);
        String mesagora = smes.format(DataAtual);
        final Integer diaagoraint = Integer.parseInt(diaagora);
        final Integer anoagoraint = Integer.parseInt(anoagora);
        final Integer mesagoraint = Integer.parseInt(mesagora);
        listaAlunosTurmaProfessorLogado();


        btnadicionarrotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataselecionada=edtdataselecionada.getText().toString();
                nomealunoselecionado=spinneralunosTurmaProfessorLogado.getSelectedItem().toString();
                adicionarRotina();
            }
        });
        btnvisualizarrotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataselecionada=edtdataselecionada.getText().toString();
                nomealunoselecionado=spinneralunosTurmaProfessorLogado.getSelectedItem().toString();
                visualizarRotina();

            }
        });
        dprotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datapicker=new DatePickerDialog(TelaInicialProfessor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        edtdataselecionada.setText(mDay + "/" +(mMonth+1) + "/" + mYear);
                        dataselecionada=mDay + "/" +(mMonth+1) + "/" + mYear;
                        edtdataselecionada.requestFocus();
                    }
                },day,month,year);
                datapicker.updateDate(anoagoraint,mesagoraint-1,diaagoraint);
                datapicker.show();
            }
        });



        btnsairprofessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sairProfessor();
            }
        });
    }
    public void sairProfessor(){

        FirebaseAuth.getInstance().signOut();
        finish();

        Toast.makeText(this, "Saindo", Toast.LENGTH_LONG).show();
    }
    public void visualizarRotina(){

        final String oquefoi=TelaInicialProfessor.nomealunoselecionado+TelaInicialProfessor.dataselecionada;
        final DatabaseReference referenciaAlunoAgenda= FirebaseDatabase.getInstance().getReference("agendas");
        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(oquefoi).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);

                    passou = true;


                }
                if(passou==false) {
                    Toast.makeText(TelaInicialProfessor.this, "Não há rotinas com estas informações selecionadas", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(TelaInicialProfessor.this, MostrarRotina.class);
                    startActivity(intent);
                    finish();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }
    public void adicionarRotina(){

        referenceAlunos.orderByChild("nomeAluno").equalTo(spinneralunosTurmaProfessorLogado.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunoSnap : dataSnapshot.getChildren()) {
                    Aluno aluno = alunoSnap.getValue(Aluno.class);


                    idalunoSelecionadoRotina = aluno.getKeyUser();
                    nomealunoselecionado = aluno.getNomeAluno();

                    referenceTurmas.orderByChild("keyUserTurma").equalTo(keyTurmaProfessorLogado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                                Turma turma = profSnap.getValue(Turma.class);
                                alunosturmaproflogado = turma.getAlunosTurma();
                                adapterListaAlunosProf = new ArrayAdapter<String>(TelaInicialProfessor.this,
                                        android.R.layout.simple_spinner_dropdown_item, alunosturmaproflogado);
                                spinneralunosTurmaProfessorLogado.setAdapter(adapterListaAlunosProf);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent intent = new Intent(TelaInicialProfessor.this, Agenda.class);
        startActivity(intent);
        finish();


    }

    public void listaAlunosTurmaProfessorLogado() {

        emailProfessorLogado = mAuth.getCurrentUser().getEmail();

        referenceProfessor.orderByChild("emailProfessora").equalTo(emailProfessorLogado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                    Professor professor = profSnap.getValue(Professor.class);


                    nomeProfessorLogado = professor.getNomeProfessora();
                    keyTurmaProfessorLogado = professor.getKeyTurma();

                    referenceTurmas.orderByChild("keyUserTurma").equalTo(keyTurmaProfessorLogado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                                Turma turma = profSnap.getValue(Turma.class);
                                alunosturmaproflogado = turma.getAlunosTurma();
                                adapterListaAlunosProf = new ArrayAdapter<String>(TelaInicialProfessor.this,
                                        android.R.layout.simple_spinner_dropdown_item, alunosturmaproflogado);
                                spinneralunosTurmaProfessorLogado.setAdapter(adapterListaAlunosProf);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}