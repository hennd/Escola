package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GerenciarTurmas extends AppCompatActivity {

    TextView txtTitulo;
    List<String> spinnerTurmasLista;
    List<String> spinnerAlunosAdicionar;
    Spinner turmaSelecionada;
    SpinnerAdapter adapterTurmas;
    SpinnerAdapter adapterAlunosAdicionarTurma;

    DatabaseReference reference;
    List<String> listTurmas;
    List<String> alunosTurmaExcluir;
    Spinner listaAlunosAdicionarTurma;
    ListView listaAlunosTurma;
    ArrayAdapter<String> arrayAdapterTurma;
    ArrayAdapter<String> arrayAdapterAlunosExcluir;
    List<String> alunosTurmaAdicionar;
    List<String> listadeAlunosExcluir;
    Turma turmaAlunoAdicionado = new Turma();
    Turma turmaAlunoRemovido = new Turma();
    Button btnAdicionarAlunoTurma;
    Spinner spinnerExcluirAlunoTurma;
    Button btnExcluirAluno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_turmas);
        txtTitulo = (TextView) findViewById(R.id.txtVisualizarTurmas);
        turmaSelecionada = findViewById(R.id.spinner_gerenciar_turmas);
        listaAlunosTurma = (ListView) findViewById(R.id.lvAlunosTurmaSelecionada);
        listaAlunosAdicionarTurma = (Spinner) findViewById(R.id.spinner_todos_alunos_gerenciar_turma);
        btnAdicionarAlunoTurma = (Button) findViewById(R.id.btnAdcionarAlunoTurma);
        spinnerExcluirAlunoTurma = (Spinner) findViewById(R.id.spinner_alunos_para_excluir_turma);
        btnExcluirAluno = (Button) findViewById(R.id.btnExcluirAlunoTurma);

        listarTurmas();
        listarAlunosAdicionar();


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
                        }

                        arrayAdapterTurma = new ArrayAdapter<String>(GerenciarTurmas.this,
                                android.R.layout.simple_list_item_1, listTurmas);
                        listaAlunosTurma.setAdapter(arrayAdapterTurma);

                        arrayAdapterAlunosExcluir = new ArrayAdapter<String>(GerenciarTurmas.this,
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

    public void adicionarAlunoTurma() {
        reference = FirebaseDatabase.getInstance().getReference("Turmas");
        reference.orderByChild("nomeTurma").equalTo(turmaSelecionada.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot turmasSnapShot : dataSnapshot.getChildren()) {
                    Turma turma = turmasSnapShot.getValue(Turma.class);

                    alunosTurmaAdicionar = turma.getAlunosTurma();

                    turmaAlunoAdicionado.setKeyUserTurma(turma.getKeyUserTurma());
                    turmaAlunoAdicionado.setNomeTurma(turma.getNomeTurma());
                    turmaAlunoAdicionado.setProfessores(turma.getProfessores());

                }


                alunosTurmaAdicionar.add(listaAlunosAdicionarTurma.getSelectedItem().toString());
                reference = FirebaseDatabase.getInstance().getReference("Turmas");

                turmaAlunoAdicionado.setAlunosTurma(alunosTurmaAdicionar);

                reference.child(turmaAlunoAdicionado.getKeyUserTurma()).setValue(turmaAlunoAdicionado);
                Toast.makeText(GerenciarTurmas.this, "Aluno Adicionado", Toast.LENGTH_LONG).show();
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
                Toast.makeText(GerenciarTurmas.this, "Aluno Removido", Toast.LENGTH_LONG).show();
                recreate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
}