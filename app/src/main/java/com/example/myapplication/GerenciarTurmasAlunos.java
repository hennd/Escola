package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GerenciarTurmasAlunos extends AppCompatActivity {
    TextView txtTitulo;
    List<String> spinnerTurmasLista;
    List<String> spinnerAlunosAdicionar;
    List<String> spinnerProfessoresAdicionar;
    Spinner turmaSelecionada;
    SpinnerAdapter adapterTurmas;
    SpinnerAdapter adapterAlunosAdicionarTurma;
    SpinnerAdapter adapterProfessoresAdicionarTurma;
    DatabaseReference reference;
    List<String> listTurmas;
    List<String> listProfessores;
    List<String> professoresExcluir;
    List<String> alunosTurmaExcluir;
    Spinner listaAlunosAdicionarTurma;
    ListView listaAlunosTurma;
    ListView listaProfessoresTurma;
    ArrayAdapter<String> arrayAdapterTurma;
    ArrayAdapter<String> professoresAdapaterTurma;
    ArrayAdapter<String> arrayAdapterAlunosExcluir;
    ArrayAdapter<String> arrayAdapterProfessorExcluir;
    List<String> alunosTurmaAdicionar;
    List<String> professoreasAdicionar;
    List<String> listadeAlunosExcluir;
    List<String> listaProfessoresExcluir;
    Turma turmaAlunoAdicionado = new Turma();
    Turma turmaAlunoRemovido = new Turma();
    Turma turmaProfessorAdicionado = new Turma();
    Turma turmaProfessorRemovido = new Turma();
    Button btnAdicionarAlunoTurma;
    Spinner spinnerExcluirAlunoTurma;
    Spinner spinnerExcluirProfessorTurma;
    Spinner spinnerAdicionarProfessorTurma;
    Button btnExcluirAluno;
    String keyTurmaSelecionado;
    String keyTurmaAlunoSelecionado;
    public static String alunoexcluirturmaantiga;
    public static String turmadoAlunoantiga;
    public static String professorexcluirturmaantiga;
    public static String turmaProfessorantiga;
    Button btnAdicionarProfessorTurma;
    Button btnExcluirProfessorTurma;
    Button btnVoltar;
    public String keyParaExcluir;
    public String keyParaExcluirProfessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turmas_alunos);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);
        listarTurmas();
        listarAlunosAdicionar();

        btnVoltar = (Button) findViewById(R.id.btnVoltar2);

        turmaSelecionada = findViewById(R.id.spinner_gerenciar_turmas2);
        listaAlunosTurma = (ListView) findViewById(R.id.lvAlunosTurmaSelecionada2);
        listaAlunosAdicionarTurma = (Spinner) findViewById(R.id.spinner_todos_alunos_gerenciar_turma2);
        btnAdicionarAlunoTurma = (Button) findViewById(R.id.btnAdcionarAlunoTurma2);
        spinnerExcluirAlunoTurma = (Spinner) findViewById(R.id.spinner_alunos_para_excluir_turma2);
        btnExcluirAluno = (Button) findViewById(R.id.btnExcluirAlunoTurma2);

        listaAlunosTurma.addHeaderView(header);

        turmaSelecionada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reference = FirebaseDatabase.getInstance().getReference().child("Turmas");
                reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                            Turma turma = turmasSnapShot.getValue(Turma.class);

                            listTurmas = turma.getAlunosTurma();
                            alunosTurmaExcluir = turma.getAlunosTurma();


                            professoresExcluir = turma.getProfessores();
                        }

                        arrayAdapterTurma = new ArrayAdapter<String>(GerenciarTurmasAlunos.this,
                                android.R.layout.simple_list_item_1, listTurmas);
                        listaAlunosTurma.setAdapter(arrayAdapterTurma);

                        arrayAdapterAlunosExcluir = new ArrayAdapter<String>(GerenciarTurmasAlunos.this,
                                android.R.layout.simple_list_item_1, alunosTurmaExcluir);
                        spinnerExcluirAlunoTurma.setAdapter(arrayAdapterAlunosExcluir);





                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        txtTitulo.setText("não ha alunos");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAdicionarAlunoTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarAlunoTurma();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GerenciarTurmasAlunos.this, MenuTurmas.class);
                startActivity(intent);
                finish();
            }
        });
        btnExcluirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirAlunosDaTurma();
            }
        });
    }
    public void listarTurmas() {
        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> turmas = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                turmas.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Turma t = item.getValue(Turma.class);

                    turmas.add(t.getNomeTurma());

                }

                turmaSelecionada = (Spinner) findViewById(R.id.spinner_gerenciar_turmas2);
                spinnerTurmasLista = new ArrayList<>();
                adapterTurmas = new ArrayAdapter<String>(GerenciarTurmasAlunos.this,
                        android.R.layout.simple_spinner_dropdown_item, turmas);
                turmaSelecionada.setAdapter(adapterTurmas);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void listarAlunosAdicionar() {

        reference = FirebaseDatabase.getInstance().getReference("alunos");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> alunos = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alunos.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Aluno a = item.getValue(Aluno.class);
                    alunos.add(a.getNomeAluno());

                }


                spinnerAlunosAdicionar = new ArrayList<>();
                adapterAlunosAdicionarTurma = new ArrayAdapter<String>(GerenciarTurmasAlunos.this,
                        android.R.layout.simple_spinner_dropdown_item, alunos);
                listaAlunosAdicionarTurma.setAdapter(adapterAlunosAdicionarTurma);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void adicionarAlunoTurma() {

        alunoexcluirturmaantiga = listaAlunosAdicionarTurma.getSelectedItem().toString();

        final DatabaseReference referenciaProfessora = FirebaseDatabase.getInstance().getReference("alunos");
        referenciaProfessora.orderByChild("nomeAluno").equalTo(listaAlunosAdicionarTurma.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                    Aluno aluno4 = profSnap.getValue(Aluno.class);

                    turmadoAlunoantiga = aluno4.getKeyTurma();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
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


                alunosTurmaAdicionar.add(listaAlunosAdicionarTurma.getSelectedItem().toString());
                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaAlunoAdicionado.setAlunosTurma(alunosTurmaAdicionar);

                reference.child(turmaAlunoAdicionado.getKeyUserTurma()).setValue(turmaAlunoAdicionado);
                Toast.makeText(GerenciarTurmasAlunos.this, "Aluno Adicionado", Toast.LENGTH_LONG).show();

                final String nomeAlunoAddKey = listaAlunosAdicionarTurma.getSelectedItem().toString();

                final DatabaseReference referenciaProfessora = FirebaseDatabase.getInstance().getReference("alunos");
                referenciaProfessora.orderByChild("nomeAluno").equalTo(nomeAlunoAddKey).addListenerForSingleValueEvent(new ValueEventListener() {
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

                            referenciaProfessora.child(aluno.getKeyUser()).setValue(alunocomturma);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                excluirAlunosDaTurmaAntiga(turmadoAlunoantiga, alunoexcluirturmaantiga);
                recreate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void excluirAlunosDaTurma() {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);


                    listadeAlunosExcluir = turma.getAlunosTurma();

                    listadeAlunosExcluir.remove(spinnerExcluirAlunoTurma.getSelectedItem().toString());


                    turmaAlunoRemovido.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaAlunoRemovido.setNomeTurma(turma.getNomeTurma());
                    turmaAlunoRemovido.setProfessores(turma.getProfessores());

                }

                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaAlunoRemovido.setAlunosTurma(listadeAlunosExcluir);

                reference.child(turmaAlunoRemovido.getKeyUserTurma()).setValue(turmaAlunoRemovido);
                Toast.makeText(GerenciarTurmasAlunos.this, "Aluno Removido", Toast.LENGTH_LONG).show();

                final DatabaseReference referenciaAluno = FirebaseDatabase.getInstance().getReference("alunos");
                referenciaAluno.orderByChild("nomeAluno").equalTo(spinnerExcluirAlunoTurma.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                            Aluno alunoexcluir = profSnap.getValue(Aluno.class);

                            profSnap.getRef().child("keyTurma").setValue("semTurma");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                recreate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
    public void excluirAlunosDaTurmaAntiga(String turmaKey, final String aluno) {


        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("keyUserTurma").equalTo(turmaKey).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);

                    listadeAlunosExcluir = turma.getAlunosTurma();

                    listadeAlunosExcluir.remove(aluno);

                    keyParaExcluir = (turma.getKeyUserTurma());
                    turmaAlunoRemovido.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaAlunoRemovido.setNomeTurma(turma.getNomeTurma());
                    turmaAlunoRemovido.setProfessores(turma.getProfessores());

                    reference = FirebaseDatabase.getInstance().getReference("Turmas");

                    turmaAlunoRemovido.setAlunosTurma(listadeAlunosExcluir);

                    reference.child(keyParaExcluir).setValue(turmaAlunoRemovido);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
}