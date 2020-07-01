package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MostrarRotina extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtLanche1v;
    EditText edtAlmocov;
    EditText edtLanche2v;
    EditText edtJantarv;
    Spinner spinnerLanche1v;
    Spinner spinnerAlmocov;
    Spinner spinnerLanche2v;
    Spinner spinnerJantarv;
    Spinner spinnerSonov;
    Spinner spinnerSonoTempov;
    Spinner spinnerEvacuacaov;
    RadioGroup rgFebrev;
    RadioButton rbFebreSimv;
    RadioButton rbFebreNaov;
    EditText edtHoraFebrev;
    Spinner spinnerFebreTemperaturav;
    EditText medicacaoFebrev;
    EditText atividadeDiaManhav;
    EditText atividadeDiaTardev;
    EditText atividadeEspecializadav;
    EditText observacoesv;
    EditText edtDataRotinav;
    private DadosAgenda dadosAgendav;
    private FirebaseAuth mAuthv;
    private FirebaseDatabase databasev;
    private DatabaseReference myRefv;
    private DatePickerDialog datapickerv;


    DatabaseReference referenciaAlunoAgenda= FirebaseDatabase.getInstance().getReference("agendas");
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_rotina);



        edtDataRotinav=(EditText)findViewById(R.id.edtDataRotinav);
        edtDataRotinav.setEnabled(false);
        edtLanche1v = (EditText)findViewById(R.id.edtLanche1v);
        edtLanche1v.setEnabled(false);
        edtAlmocov = (EditText)findViewById(R.id.edtAlmocov);
        edtAlmocov.setEnabled(false);
        edtLanche2v = (EditText)findViewById(R.id.edtLanche2v);
        edtLanche2v.setEnabled(false);
        edtJantarv = (EditText)findViewById(R.id.edtJantarv);
        edtJantarv.setEnabled(false);

        spinnerLanche1v=(Spinner)findViewById(R.id.spinner_lanche1v);
        spinnerAlmocov=(Spinner)findViewById(R.id.spinner_almocov);
        spinnerLanche2v=(Spinner)findViewById(R.id.spinner_lanche2v);
        spinnerJantarv=(Spinner)findViewById(R.id.spinner_jantarv);

        spinnerSonov=(Spinner)findViewById(R.id.spinner_sono_turnosv);
        spinnerSonoTempov=(Spinner)findViewById(R.id.spinner_tempo_sonov);

        spinnerEvacuacaov=(Spinner)findViewById(R.id.spinner_evacuacaov);
        edtHoraFebrev=(EditText)findViewById(R.id.edtHoraFebrev);
        rgFebrev=(RadioGroup)findViewById(R.id.rgFebrev);
        rbFebreSimv=(RadioButton)findViewById(R.id.rbFebreSimv);
        rbFebreNaov=(RadioButton)findViewById(R.id.rbFebreNaov);
        spinnerFebreTemperaturav=(Spinner)findViewById(R.id.spinner_febre_tempv);
        medicacaoFebrev=(EditText)findViewById(R.id.edtMedicacaov);
        atividadeDiaManhav=(EditText)findViewById(R.id.edtAtividadedoDiaManhav);
        atividadeDiaTardev=(EditText)findViewById(R.id.edtAtividadedoDiaTardev);
        atividadeEspecializadav=(EditText)findViewById(R.id.edtAtividadeEspecializadav);
        observacoesv=(EditText)findViewById(R.id.edtObservacoesv);

        final Spinner lanchev = findViewById(R.id.spinner_lanche1v);
        ArrayAdapter<CharSequence> lancheAdapterv = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lancheAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanchev.setAdapter(lancheAdapterv);
        lanchev.setOnItemSelectedListener(this);
        lanchev.setEnabled(false);

        final Spinner almoçov = findViewById(R.id.spinner_almocov);
        ArrayAdapter<CharSequence> almocoAdapterv = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        almocoAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        almoçov.setAdapter(almocoAdapterv);
        almoçov.setOnItemSelectedListener(this);
        almoçov.setEnabled(false);


        final Spinner lanche2v = findViewById(R.id.spinner_lanche2v);
        ArrayAdapter<CharSequence> lanche2Adapterv = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lanche2Adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanche2v.setAdapter(lanche2Adapterv);
        lanche2v.setOnItemSelectedListener(this);
        lanche2v.setEnabled(false);


        final Spinner jantarv = findViewById(R.id.spinner_jantarv);
        ArrayAdapter<CharSequence> jantarAdapterv = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        jantarAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jantarv.setAdapter(jantarAdapterv);
        jantarv.setOnItemSelectedListener(this);
        jantarv.setEnabled(false);

        final Spinner sonoTurnosv = findViewById(R.id.spinner_sono_turnosv);
        ArrayAdapter<CharSequence> sonoTurnosAdapterv = ArrayAdapter.createFromResource(this,R.array.turnos_sono,android.R.layout.simple_spinner_item);
        sonoTurnosAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTurnosv.setAdapter(sonoTurnosAdapterv);
        sonoTurnosv.setOnItemSelectedListener(this);
        sonoTurnosv.setEnabled(false);

        final Spinner sonoTempov = findViewById(R.id.spinner_tempo_sonov);
        ArrayAdapter<CharSequence> sonoTempoAdapterv = ArrayAdapter.createFromResource(this,R.array.tempo_sono,android.R.layout.simple_spinner_item);
        sonoTempoAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTempov.setAdapter(sonoTempoAdapterv);
        sonoTempov.setOnItemSelectedListener(this);
        sonoTempov.setEnabled(false);

        final Spinner evacucaov = findViewById(R.id.spinner_evacuacaov);
        ArrayAdapter<CharSequence> evacuacaoAdapterv = ArrayAdapter.createFromResource(this,R.array.evacuacao,android.R.layout.simple_spinner_item);
        evacuacaoAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evacucaov.setAdapter(evacuacaoAdapterv);
        evacucaov.setOnItemSelectedListener(this);
        evacucaov.setEnabled(false);

        final Spinner febreTempv = findViewById(R.id.spinner_febre_tempv);
        ArrayAdapter<CharSequence> febreTempAdapterv = ArrayAdapter.createFromResource(this,R.array.febre_temp,android.R.layout.simple_spinner_item);
        febreTempAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        febreTempv.setAdapter(febreTempAdapterv);
        febreTempv.setOnItemSelectedListener(this);
        febreTempv.setEnabled(false);

        final String oquefoi=TelaInicialProfessor.nomealunoselecionado+TelaInicialProfessor.dataselecionada;
        observacoesv.setText(oquefoi);

        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(oquefoi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);
                    edtDataRotinav.setText(dados.getDataAgenda());
                    edtLanche1v.setText(dados.getRefeicaoLanche1());
                    lanchev.setSelection(dados.getSpinnerrefeicaoLanche1());
                    edtAlmocov.setText(dados.getRefeicaoAlmoco());
                    almoçov.setSelection(dados.getSpinnerrefeicaoAlmoco());
                    edtLanche2v.setText(dados.getRefeicaoLanche2());
                    lanche2v.setSelection(dados.getSpinnerrefeicaoLanche2());
                    edtJantarv.setText(dados.getRefeicaoJantar());
                    jantarv.setSelection(dados.getSpinnerrefeicaoJantar());
                    sonoTempov.setSelection(dados.getSpinnerTempoSono());
                    sonoTurnosv.setSelection(dados.getSpinnerSono());
                    evacucaov.setSelection(dados.getSpinnerEvacuacao());
                    String febre = dados.getFebre_sim_nao();
                    observacoesv.setText(dados.getFebre_sim_nao());
                    if (febre.equals("sim")) rbFebreSimv.setChecked(true); else rbFebreNaov.setChecked(true);
                    febreTempv.setSelection(dados.getSpinnerTemperatura());
                    edtHoraFebrev.setText(dados.getHoraFebre());
                    medicacaoFebrev.setText(dados.getMedicacaoFebre());
                    atividadeDiaManhav.setText(dados.getAtividadeDiaManha());
                    atividadeDiaTardev.setText(dados.getAtividadeDiaTarde());
                    atividadeEspecializadav.setText(dados.getAtividadeEspecializada());
                    observacoesv.setText(dados.getObservacoes());

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