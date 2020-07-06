package com.example.myapplication;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.myapplication.R.color.disabled_color;

public class MostrarRotinaAluno extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtLanche1va;
    EditText edtAlmocova;
    EditText edtLanche2va;
    EditText edtJantarva;
    Spinner spinnerLanche1va;
    Spinner spinnerAlmocova;
    Spinner spinnerLanche2va;
    Spinner spinnerJantarva;
    Spinner spinnerSonova;
    Spinner spinnerSonoTempova;
    Spinner spinnerEvacuacaova;
    RadioGroup rgFebreva;
    RadioButton rbFebreSimva;
    RadioButton rbFebreNaova;
    EditText edtHoraFebreva;
    Spinner spinnerFebreTemperaturava;
    EditText medicacaoFebreva;
    EditText atividadeDiaManhava;
    EditText atividadeDiaTardeva;
    EditText atividadeEspecializadava;
    EditText observacoesva;
    EditText edtDataRotinava;
    Button btnSairRotinaAluno;

    DatabaseReference referenciaAlunoAgenda= FirebaseDatabase.getInstance().getReference("agendas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_rotina_aluno);

        btnSairRotinaAluno=(Button)findViewById(R.id.btnSairRotinaAl);
        btnSairRotinaAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaInicialAluno.pesquisaAgenda = " ";
                Intent intent = new Intent(MostrarRotinaAluno.this, TelaInicialAluno.class);
                startActivity(intent);
                finish();
            }
        });
        edtDataRotinava=(EditText)findViewById(R.id.edtDataRotinava);
        edtDataRotinava.setEnabled(false);
        edtLanche1va = (EditText)findViewById(R.id.edtLanche1va);
        edtLanche1va.setEnabled(false);
        edtAlmocova= (EditText)findViewById(R.id.edtAlmocova);
        edtAlmocova.setEnabled(false);
        edtLanche2va = (EditText)findViewById(R.id.edtLanche2va);
        edtLanche2va.setEnabled(false);
        edtJantarva = (EditText)findViewById(R.id.edtJantarva);
        edtJantarva.setEnabled(false);

        spinnerLanche1va=(Spinner)findViewById(R.id.spinner_lanche1va);
        spinnerLanche1va.setEnabled(false);
        spinnerAlmocova=(Spinner)findViewById(R.id.spinner_almocova);
        spinnerAlmocova.setEnabled(false);
        spinnerLanche2va=(Spinner)findViewById(R.id.spinner_lanche2va);
        spinnerLanche2va.setEnabled(false);
        spinnerJantarva=(Spinner)findViewById(R.id.spinner_jantarva);
        spinnerJantarva.setEnabled(false);

        spinnerSonova=(Spinner)findViewById(R.id.spinner_sono_turnosva);
        spinnerSonova.setEnabled(false);
        spinnerSonoTempova=(Spinner)findViewById(R.id.spinner_tempo_sonova);
        spinnerSonoTempova.setEnabled(false);

        spinnerEvacuacaova=(Spinner)findViewById(R.id.spinner_evacuacaova);
        spinnerEvacuacaova.setEnabled(false);
        edtHoraFebreva=(EditText)findViewById(R.id.edtHoraFebreva);

        edtHoraFebreva.setEnabled(false);
        rgFebreva=(RadioGroup)findViewById(R.id.rgFebreva);
        rgFebreva.setEnabled(false);
        rbFebreSimva=(RadioButton)findViewById(R.id.rbFebreSimva);
        rbFebreSimva.setEnabled(false);
        rbFebreNaova=(RadioButton)findViewById(R.id.rbFebreNaova);
        rbFebreNaova.setEnabled(false);
        spinnerFebreTemperaturava=(Spinner)findViewById(R.id.spinner_febre_tempva);
        medicacaoFebreva=(EditText)findViewById(R.id.edtMedicacaova);
        medicacaoFebreva.setEnabled(false);
        atividadeDiaManhava=(EditText)findViewById(R.id.edtAtividadedoDiaManhava);
        atividadeDiaManhava.setEnabled(false);
        atividadeDiaTardeva=(EditText)findViewById(R.id.edtAtividadedoDiaTardeva);
        atividadeDiaTardeva.setEnabled(false);
        atividadeEspecializadava=(EditText)findViewById(R.id.edtAtividadeEspecializadava);
        atividadeEspecializadava.setEnabled(false);
        observacoesva=(EditText)findViewById(R.id.edtObservacoesva);
        observacoesva.setEnabled(false);

        final Spinner lancheva = findViewById(R.id.spinner_lanche1va);
        ArrayAdapter<CharSequence> lancheAdapterva = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lancheAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lancheva.setAdapter(lancheAdapterva);
        lancheva.setOnItemSelectedListener(this);
        lancheva.setEnabled(false);

        final Spinner almoçova = findViewById(R.id.spinner_almocova);
        ArrayAdapter<CharSequence> almocoAdapterva = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        almocoAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        almoçova.setAdapter(almocoAdapterva);
        almoçova.setOnItemSelectedListener(this);
        almoçova.setEnabled(false);


        final Spinner lanche2va = findViewById(R.id.spinner_lanche2va);
        ArrayAdapter<CharSequence> lanche2Adapterva = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lanche2Adapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanche2va.setAdapter(lanche2Adapterva);
        lanche2va.setOnItemSelectedListener(this);
        lanche2va.setEnabled(false);


        final Spinner jantarva = findViewById(R.id.spinner_jantarva);
        ArrayAdapter<CharSequence> jantarAdapterva = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        jantarAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jantarva.setAdapter(jantarAdapterva);
        jantarva.setOnItemSelectedListener(this);
        jantarva.setEnabled(false);

        final Spinner sonoTurnosva = findViewById(R.id.spinner_sono_turnosva);
        ArrayAdapter<CharSequence> sonoTurnosAdapterva = ArrayAdapter.createFromResource(this,R.array.turnos_sono,android.R.layout.simple_spinner_item);
        sonoTurnosAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTurnosva.setAdapter(sonoTurnosAdapterva);
        sonoTurnosva.setOnItemSelectedListener(this);
        sonoTurnosva.setEnabled(false);

        final Spinner sonoTempova = findViewById(R.id.spinner_tempo_sonova);
        ArrayAdapter<CharSequence> sonoTempoAdapterva = ArrayAdapter.createFromResource(this,R.array.tempo_sono,android.R.layout.simple_spinner_item);
        sonoTempoAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTempova.setAdapter(sonoTempoAdapterva);
        sonoTempova.setOnItemSelectedListener(this);
        sonoTempova.setEnabled(false);

        final Spinner evacucaova = findViewById(R.id.spinner_evacuacaova);
        ArrayAdapter<CharSequence> evacuacaoAdapterva = ArrayAdapter.createFromResource(this,R.array.evacuacao,android.R.layout.simple_spinner_item);
        evacuacaoAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evacucaova.setAdapter(evacuacaoAdapterva);
        evacucaova.setOnItemSelectedListener(this);
        evacucaova.setEnabled(false);

        final Spinner febreTempva = findViewById(R.id.spinner_febre_tempva);
        ArrayAdapter<CharSequence> febreTempAdapterva = ArrayAdapter.createFromResource(this,R.array.febre_temp,android.R.layout.simple_spinner_item);
        febreTempAdapterva.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        febreTempva.setAdapter(febreTempAdapterva);
        febreTempva.setOnItemSelectedListener(this);
        febreTempva.setEnabled(false);

        final String oquefoi=TelaInicialAluno.pesquisaAgenda;
        observacoesva.setText(oquefoi);

        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(oquefoi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);
                    edtDataRotinava.setText(dados.getDataAgenda());
                    edtLanche1va.setText(dados.getRefeicaoLanche1());
                    lancheva.setSelection(dados.getSpinnerrefeicaoLanche1());
                    edtAlmocova.setText(dados.getRefeicaoAlmoco());
                    almoçova.setSelection(dados.getSpinnerrefeicaoAlmoco());
                    edtLanche2va.setText(dados.getRefeicaoLanche2());
                    lanche2va.setSelection(dados.getSpinnerrefeicaoLanche2());
                    edtJantarva.setText(dados.getRefeicaoJantar());
                    jantarva.setSelection(dados.getSpinnerrefeicaoJantar());
                    sonoTempova.setSelection(dados.getSpinnerTempoSono());
                    sonoTurnosva.setSelection(dados.getSpinnerSono());
                    evacucaova.setSelection(dados.getSpinnerEvacuacao());
                    String febre = dados.getFebre_sim_nao();
                    observacoesva.setText(dados.getFebre_sim_nao());
                    if (febre.equals("sim")) rbFebreSimva.setChecked(true); else rbFebreNaova.setChecked(true);
                    febreTempva.setSelection(dados.getSpinnerTemperatura());
                    edtHoraFebreva.setText(dados.getHoraFebre());
                    medicacaoFebreva.setText(dados.getMedicacaoFebre());
                    atividadeDiaManhava.setText(dados.getAtividadeDiaManha());
                    atividadeDiaTardeva.setText(dados.getAtividadeDiaTarde());
                    atividadeEspecializadava.setText(dados.getAtividadeEspecializada());
                    observacoesva.setText(dados.getObservacoes());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

}