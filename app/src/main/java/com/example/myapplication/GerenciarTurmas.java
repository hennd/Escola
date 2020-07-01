package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class GerenciarTurmas extends AppCompatActivity {

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
    List<String>professoresExcluir;
    List<String> alunosTurmaExcluir;
    Spinner listaAlunosAdicionarTurma;
    ListView listaAlunosTurma;
    ListView listaProfessoresTurma;
    ArrayAdapter<String> arrayAdapterTurma;
    ArrayAdapter<String> professoresAdapaterTurma;
    ArrayAdapter<String> arrayAdapterAlunosExcluir;
    ArrayAdapter<String> arrayAdapterProfessorExcluir;
    List<String> alunosTurmaAdicionar;
    List<String>professoreasAdicionar;
    List<String> listadeAlunosExcluir;
    List<String>listaProfessoresExcluir;
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

    Button btnAdicionarProfessorTurma;
    Button btnExcluirProfessorTurma;
    Button btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turmas);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);


        btnVoltar= (Button)findViewById(R.id.btnVoltar);
        txtTitulo = (TextView) findViewById(R.id.txtVisualizarTurmas);
        turmaSelecionada = findViewById(R.id.spinner_gerenciar_turmas);
        listaAlunosTurma = (ListView) findViewById(R.id.lvAlunosTurmaSelecionada);
        listaAlunosAdicionarTurma = (Spinner) findViewById(R.id.spinner_todos_alunos_gerenciar_turma);
        btnAdicionarAlunoTurma = (Button) findViewById(R.id.btnAdcionarAlunoTurma);
        spinnerExcluirAlunoTurma = (Spinner) findViewById(R.id.spinner_alunos_para_excluir_turma);
        btnExcluirAluno = (Button) findViewById(R.id.btnExcluirAlunoTurma);
        listaProfessoresTurma= (ListView)findViewById(R.id.lvProfessoresTurmaSelecionada);
        spinnerExcluirProfessorTurma=(Spinner)findViewById(R.id.spinner_professores_para_excluir_turma);
        spinnerAdicionarProfessorTurma=(Spinner)findViewById(R.id.spinner_todos_professores_gerenciar_turma);
        btnAdicionarProfessorTurma=(Button)findViewById(R.id.btnAdcionarProfessorTurma);
        btnExcluirProfessorTurma=(Button)findViewById(R.id.btnExcluirProfessora);

        listarTurmas();
        listarAlunosAdicionar();
        listarProfessores();

        listaAlunosTurma.addHeaderView(header);
        listaProfessoresTurma.addFooterView(footer);
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

                            listProfessores = turma.getProfessores();
                            professoresExcluir=turma.getProfessores();
                        }

                        arrayAdapterTurma = new ArrayAdapter<String>(GerenciarTurmas.this,
                                android.R.layout.simple_list_item_1, listTurmas);
                        listaAlunosTurma.setAdapter(arrayAdapterTurma);

                        professoresAdapaterTurma = new ArrayAdapter<String>(GerenciarTurmas.this,
                                android.R.layout.simple_list_item_1, listProfessores);
                        listaProfessoresTurma.setAdapter(professoresAdapaterTurma);

                        arrayAdapterAlunosExcluir = new ArrayAdapter<String>(GerenciarTurmas.this,
                                android.R.layout.simple_list_item_1, alunosTurmaExcluir);
                        spinnerExcluirAlunoTurma.setAdapter(arrayAdapterAlunosExcluir);


                        arrayAdapterProfessorExcluir = new ArrayAdapter<String>(GerenciarTurmas.this,
                                android.R.layout.simple_list_item_1, professoresExcluir);
                        spinnerExcluirProfessorTurma.setAdapter(arrayAdapterProfessorExcluir);


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
                Intent intent = new Intent(GerenciarTurmas.this, MenuDiretoria.class);
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
        btnAdicionarProfessorTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarProfessoraTurma();
            }
        });

    }


    public void listarTurmas() {
        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> turmas = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Turma t = item.getValue(Turma.class);
                    turmas.add(t.getNomeTurma());

                }

                turmaSelecionada = (Spinner) findViewById(R.id.spinner_gerenciar_turmas);
                spinnerTurmasLista = new ArrayList<>();
                adapterTurmas = new ArrayAdapter<String>(GerenciarTurmas.this,
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
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Aluno a = item.getValue(Aluno.class);
                    alunos.add(a.getNomeAluno());

                }


                spinnerAlunosAdicionar = new ArrayList<>();
                adapterAlunosAdicionarTurma = new ArrayAdapter<String>(GerenciarTurmas.this,
                        android.R.layout.simple_spinner_dropdown_item, alunos);
                listaAlunosAdicionarTurma.setAdapter(adapterAlunosAdicionarTurma);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void listarProfessores() {

        reference = FirebaseDatabase.getInstance().getReference("professores");
        reference.addValueEventListener(new ValueEventListener() {

            final List<String> professores = new ArrayList<String>();

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Professor p = item.getValue(Professor.class);
                    professores.add(p.getNomeProfessora());

                }

                spinnerProfessoresAdicionar = new ArrayList<>();
                adapterProfessoresAdicionarTurma = new ArrayAdapter<String>(GerenciarTurmas.this,
                        android.R.layout.simple_spinner_dropdown_item, professores);
                spinnerAdicionarProfessorTurma.setAdapter(adapterProfessoresAdicionarTurma);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void adicionarAlunoTurma() {
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
                Toast.makeText(GerenciarTurmas.this, "Aluno Adicionado", Toast.LENGTH_LONG).show();

                String nomeAlunoAddKey= listaAlunosAdicionarTurma.getSelectedItem().toString();

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

                recreate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void adicionarProfessoraTurma() {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);


                    professoreasAdicionar = turma.getProfessores();

                    keyTurmaSelecionado = turma.getKeyUserTurma();
                    turmaProfessorAdicionado.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaProfessorAdicionado.setNomeTurma(turma.getNomeTurma());
                    turmaProfessorAdicionado.setAlunosTurma(turma.getAlunosTurma());

                }


                professoreasAdicionar.add(spinnerAdicionarProfessorTurma.getSelectedItem().toString());

                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaProfessorAdicionado.setProfessores(professoreasAdicionar);

                reference.child(turmaProfessorAdicionado.getKeyUserTurma()).setValue(turmaProfessorAdicionado);
                Toast.makeText(GerenciarTurmas.this, "Professora Adicionada", Toast.LENGTH_LONG).show();


                String nomeProfessoraAddKey= spinnerAdicionarProfessorTurma.getSelectedItem().toString();

                final DatabaseReference referenciaProfessora = FirebaseDatabase.getInstance().getReference("professores");
                referenciaProfessora.orderByChild("nomeProfessora").equalTo(nomeProfessoraAddKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Professor professoracomTurma = new Professor();
                        for (DataSnapshot profSnap : dataSnapshot.getChildren()) {
                            Professor professor = profSnap.getValue(Professor.class);

                            professoracomTurma.setCargoProfessora(professor.getCargoProfessora());
                            professoracomTurma.setNomeProfessora(professor.getNomeProfessora());
                            professoracomTurma.setCargoSpinnerProfessora(professor.getCargoSpinnerProfessora());
                            professoracomTurma.setSenhaProfessora(professor.getSenhaProfessora());
                            professoracomTurma.setTelefoneProfessora(professor.getTelefoneProfessora());
                            professoracomTurma.setKeyUser(professor.getKeyUser());
                            professoracomTurma.setEmailProfessora(professor.getEmailProfessora());
                            professoracomTurma.setKeyTurma(keyTurmaSelecionado);

                            referenciaProfessora.child(professor.getKeyUser()).setValue(professoracomTurma);

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
                Toast.makeText(GerenciarTurmas.this, "Aluno Removido", Toast.LENGTH_LONG).show();
                recreate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
    public void excluirProfessoresTurma(View View) {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);

                    listaProfessoresExcluir = turma.getProfessores();

                    listaProfessoresExcluir.remove(spinnerExcluirProfessorTurma.getSelectedItem().toString());


                    turmaProfessorRemovido.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaProfessorRemovido.setNomeTurma(turma.getNomeTurma());
                    turmaProfessorRemovido.setAlunosTurma(turma.getAlunosTurma());

                }

                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaProfessorRemovido.setProfessores(listaProfessoresExcluir);

                reference.child(turmaProfessorRemovido.getKeyUserTurma()).setValue(turmaProfessorRemovido);
                Toast.makeText(GerenciarTurmas.this, "Professor Removido", Toast.LENGTH_LONG).show();
                recreate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
}