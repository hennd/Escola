package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class GerenciarTurmasProfessores extends AppCompatActivity {
    TextView txtTitulo;
    List<String> spinnerTurmasLista;

    List<String> spinnerProfessoresAdicionar;
    Spinner turmaSelecionada;
    SpinnerAdapter adapterTurmas;

    SpinnerAdapter adapterProfessoresAdicionarTurma;
    DatabaseReference reference;
    List<String> listTurmas;
    List<String> listProfessores;
    List<String> professoresExcluir;
    List<String> alunosTurmaExcluir;
    ListView listaAlunosTurma;
    ListView listaProfessoresTurma;
    ArrayAdapter<String> arrayAdapterTurma;
    ArrayAdapter<String> professoresAdapaterTurma;
    ArrayAdapter<String> arrayAdapterAlunosExcluir;
    ArrayAdapter<String> arrayAdapterProfessorExcluir;
    List<String> professoreasAdicionar;
    List<String> listaProfessoresExcluir;
    Turma turmaProfessorAdicionado = new Turma();
    Turma turmaProfessorRemovido = new Turma();
    Spinner spinnerExcluirAlunoTurma;
    Spinner spinnerExcluirProfessorTurma;
    Spinner spinnerAdicionarProfessorTurma;
    String keyTurmaSelecionado;
    public static String professorexcluirturmaantiga;
    public static String turmaProfessorantiga;
    Button btnAdicionarProfessorTurma;
    Button btnExcluirProfessorTurma;
    Button btnVoltar;
    public String keyParaExcluirProfessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turmas_professores);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);

        listarTurmas();
        listarProfessores();

        btnVoltar = (Button) findViewById(R.id.btnVoltar4);

        turmaSelecionada = findViewById(R.id.spinner_gerenciar_turmas3);

        listaProfessoresTurma = (ListView) findViewById(R.id.lvProfessoresTurmaSelecionada3);
        spinnerExcluirProfessorTurma = (Spinner) findViewById(R.id.spinner_professores_para_excluir_turma3);
        spinnerAdicionarProfessorTurma = (Spinner) findViewById(R.id.spinner_todos_professores_gerenciar_turma3);
        btnAdicionarProfessorTurma = (Button) findViewById(R.id.btnAdcionarProfessorTurma3);
        btnExcluirProfessorTurma = (Button) findViewById(R.id.btnExcluirProfessorTurma3);


        listaProfessoresTurma.addFooterView(footer);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GerenciarTurmasProfessores.this, MenuTurmas.class);
                startActivity(intent);
                finish();
            }
        });
        btnAdicionarProfessorTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarProfessoraTurma();
            }
        });

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

                            listProfessores = turma.getProfessores();
                            professoresExcluir = turma.getProfessores();
                        }


                        professoresAdapaterTurma = new ArrayAdapter<String>(GerenciarTurmasProfessores.this,
                                android.R.layout.simple_list_item_1, listProfessores);
                        listaProfessoresTurma.setAdapter(professoresAdapaterTurma);



                        arrayAdapterProfessorExcluir = new ArrayAdapter<String>(GerenciarTurmasProfessores.this,
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

                turmaSelecionada = (Spinner) findViewById(R.id.spinner_gerenciar_turmas3);
                spinnerTurmasLista = new ArrayList<>();
                adapterTurmas = new ArrayAdapter<String>(GerenciarTurmasProfessores.this,
                        android.R.layout.simple_spinner_dropdown_item, turmas);
                turmaSelecionada.setAdapter(adapterTurmas);

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
                professores.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Professor p = item.getValue(Professor.class);
                    professores.add(p.getNomeProfessora());

                }

                spinnerProfessoresAdicionar = new ArrayList<>();
                adapterProfessoresAdicionarTurma = new ArrayAdapter<String>(GerenciarTurmasProfessores.this,
                        android.R.layout.simple_spinner_dropdown_item, professores);
                spinnerAdicionarProfessorTurma.setAdapter(adapterProfessoresAdicionarTurma);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void adicionarProfessoraTurma() {

        professorexcluirturmaantiga = spinnerAdicionarProfessorTurma.getSelectedItem().toString();

        final DatabaseReference referenciaProfessora = FirebaseDatabase.getInstance().getReference("professores");
        referenciaProfessora.orderByChild("nomeProfessora").equalTo(spinnerAdicionarProfessorTurma.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot profSnap2 : dataSnapshot.getChildren()) {
                    Professor professor4 = profSnap2.getValue(Professor.class);

                    turmaProfessorantiga = professor4.getKeyTurma();

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
                Toast.makeText(GerenciarTurmasProfessores.this, "Professora Adicionada", Toast.LENGTH_LONG).show();


                String nomeProfessoraAddKey = spinnerAdicionarProfessorTurma.getSelectedItem().toString();

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

                excluirProfessoresTurmaAntiga(turmaProfessorantiga, professorexcluirturmaantiga);

               // recreate();

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

                Toast.makeText(GerenciarTurmasProfessores.this, "Professor Removido", Toast.LENGTH_LONG).show();

                final DatabaseReference referenciaProfessor = FirebaseDatabase.getInstance().getReference("professores");
                referenciaProfessor.orderByChild("nomeProfessora").equalTo(spinnerExcluirProfessorTurma.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot profSnappr : dataSnapshot.getChildren()) {
                            Professor alunoexcluir = profSnappr.getValue(Professor.class);

                            profSnappr.getRef().child("keyTurma").setValue("semTurma");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

               // recreate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
    public void excluirProfessoresTurmaAntiga(final String turmaKeyProfessor, final String professor) {

        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("keyUserTurma").equalTo(turmaKeyProfessor).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);

                    listaProfessoresExcluir = turma.getProfessores();

                    listaProfessoresExcluir.remove(professor);

                    keyParaExcluirProfessor = (turma.getKeyUserTurma());

                    turmaProfessorRemovido.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaProfessorRemovido.setNomeTurma(turma.getNomeTurma());
                    turmaProfessorRemovido.setAlunosTurma(turma.getAlunosTurma());

                    reference = FirebaseDatabase.getInstance().getReference("Turmas");

                    turmaProfessorRemovido.setProfessores(listaProfessoresExcluir);

                    reference.child(keyParaExcluirProfessor).setValue(turmaProfessorRemovido);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
}