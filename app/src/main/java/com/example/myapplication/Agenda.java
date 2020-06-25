package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Agenda extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    Button btnFebrehora;

    Button btnSalvar;
    EditText edtLanche1;
    EditText edtAlmoco;
    EditText edtLanche2;
    EditText edtJantar;
    Spinner spinnerLanche1;
    Spinner spinnerAlmoco;
    Spinner spinnerLanche2;
    Spinner spinnerJantar;
    Spinner spinnerSono;
    Spinner spinnerSonoTempo;
    Spinner spinnerEvacuacao;
    RadioGroup rgFebre;
    RadioButton rbFebreSim;
    RadioButton rbFebreNao;
    EditText edtFebrehora;
    Spinner spinnerFebreTemperatura;
    EditText medicacaoFebre;
    EditText atividadeDiaManha;
    EditText atividadeDiaTarde;
    EditText atividadeEspecializada;
    EditText observacoes;
    Button btnVoltar;
    private DadosAgenda dadosAgenda;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    TextView dataedt;
    private Button botaoData;
    private TextView textoData;
    private DatePickerDialog datapicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int month = c.get(Calendar.MONTH);
        final int year = c.get(Calendar.YEAR);

        dataedt=(TextView)findViewById(R.id.txtData);
        edtLanche1 = (EditText)findViewById(R.id.edtLanche1);
        edtAlmoco = (EditText)findViewById(R.id.edtAlmoco);
        edtLanche2 = (EditText)findViewById(R.id.edtLanche2);
        edtJantar = (EditText)findViewById(R.id.edtJantar);

        spinnerLanche1=(Spinner)findViewById(R.id.spinner_lanche1);
        spinnerAlmoco=(Spinner)findViewById(R.id.spinner_almoco);
        spinnerLanche2=(Spinner)findViewById(R.id.spinner_lanche2);
        spinnerJantar=(Spinner)findViewById(R.id.spinner_jantar);

        spinnerSono=(Spinner)findViewById(R.id.spinner_sono_turnos);
        spinnerSonoTempo=(Spinner)findViewById(R.id.spinner_tempo_sono);

        spinnerEvacuacao=(Spinner)findViewById(R.id.spinner_evacuacao);
        btnVoltar=(Button)findViewById(R.id.btnBack);
        rgFebre=(RadioGroup)findViewById(R.id.rgFebre);
        rbFebreSim=(RadioButton)findViewById(R.id.rbFebreSim);
        rbFebreNao=(RadioButton)findViewById(R.id.rbFebreNao);
        spinnerFebreTemperatura=(Spinner)findViewById(R.id.spinner_febre_temp);
        medicacaoFebre=(EditText)findViewById(R.id.edtMedicacao);
        atividadeDiaManha=(EditText)findViewById(R.id.edtAtividadedoDiaManha);
        atividadeDiaTarde=(EditText)findViewById(R.id.edtAtividadedoDiaTarde);
        atividadeEspecializada=(EditText)findViewById(R.id.edtAtividadeEspecializada);
        observacoes=(EditText)findViewById(R.id.edtObservacoes);

        btnFebrehora=(Button) findViewById(R.id.btnHoraFebre);
        edtFebrehora=(EditText) findViewById(R.id.edtHoraFebre);
        btnSalvar= (Button) findViewById(R.id.btnSalvar);

        botaoData=(Button)findViewById(R.id.btnDATA);
        textoData=(TextView)findViewById(R.id.txtData);

        Spinner lanche = findViewById(R.id.spinner_lanche1);
        ArrayAdapter<CharSequence> lancheAdapter = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lancheAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanche.setAdapter(lancheAdapter);
        lanche.setOnItemSelectedListener(this);

        Spinner almoço = findViewById(R.id.spinner_almoco);
        ArrayAdapter<CharSequence> almocoAdapter = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        almocoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        almoço.setAdapter(almocoAdapter);
        almoço.setOnItemSelectedListener(this);

        Spinner lanche2 = findViewById(R.id.spinner_lanche2);
        ArrayAdapter<CharSequence> lanche2Adapter = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lanche2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanche2.setAdapter(lanche2Adapter);
        lanche2.setOnItemSelectedListener(this);

        Spinner jantar = findViewById(R.id.spinner_jantar);
        ArrayAdapter<CharSequence> jantarAdapter = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        jantarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jantar.setAdapter(jantarAdapter);
        jantar.setOnItemSelectedListener(this);


        Spinner sonoTurnos = findViewById(R.id.spinner_sono_turnos);
        ArrayAdapter<CharSequence> sonoTurnosAdapter = ArrayAdapter.createFromResource(this,R.array.turnos_sono,android.R.layout.simple_spinner_item);
        sonoTurnosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTurnos.setAdapter(sonoTurnosAdapter);
        sonoTurnos.setOnItemSelectedListener(this);


        Spinner sonoTempo = findViewById(R.id.spinner_tempo_sono);
        ArrayAdapter<CharSequence> sonoTempoAdapter = ArrayAdapter.createFromResource(this,R.array.tempo_sono,android.R.layout.simple_spinner_item);
        sonoTempoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTempo.setAdapter(sonoTempoAdapter);
        sonoTempo.setOnItemSelectedListener(this);

        Spinner evacucao = findViewById(R.id.spinner_evacuacao);
        ArrayAdapter<CharSequence> evacuacaoAdapter = ArrayAdapter.createFromResource(this,R.array.evacuacao,android.R.layout.simple_spinner_item);
        evacuacaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evacucao.setAdapter(evacuacaoAdapter);
        evacucao.setOnItemSelectedListener(this);


        Spinner febreTemp = findViewById(R.id.spinner_febre_temp);
        ArrayAdapter<CharSequence> febreTempAdapter = ArrayAdapter.createFromResource(this,R.array.febre_temp,android.R.layout.simple_spinner_item);
        febreTempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        febreTemp.setAdapter(febreTempAdapter);
        febreTemp.setOnItemSelectedListener(this);




        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        dadosAgenda = new DadosAgenda();


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAgenda();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaDiretoria();
            }
        });
        botaoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datapicker =new DatePickerDialog(Agenda.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        textoData.setText(mDay + "/" +(mMonth+1) + "/" + mYear);
                    }
                },day,month,year);
                datapicker.show();
            }
        });



        btnFebrehora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(Agenda.this, Agenda.this, hour, minute, true);
            timePickerDialog.show();
            }
        });

    }



    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimerPickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String hora= String.format("%02d:%02d",hourOfDay, minute);;

        edtFebrehora.setText(hora);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void salvarAgenda(){
        String temfebre="talvez", data;
        String edtlanche1, edtalmoco, edtlanche2, edtjantar, rgfebre, rbfebreSim,rbfebreNao,medicacaofebre,temperatura,sono;
        String atividadediamanha,atividadediatarde,atividadeespecializada,edobservacoes,edtfebrehora;
        Integer spinnerlanche1,spinneralmoco,spinnerlanche2,spinnerjantar, spinnersono,spinnersonotempo,spinnerevacuacao,spinnerfebretemperatura;

        data=textoData.getText().toString();
        edtlanche1 = edtLanche1.getText().toString();
        edtalmoco = edtAlmoco.getText().toString();
        edtlanche2 = edtLanche2.getText().toString();
        edtjantar = edtJantar.getText().toString();


        if(rbFebreSim.isChecked()) {
            temfebre="sim";
        }
        else if(rbFebreNao.isChecked()){

            temfebre="não";
        }

        sono=spinnerSonoTempo.getSelectedItem().toString();
        temperatura=spinnerFebreTemperatura.getSelectedItem().toString();
        medicacaofebre = medicacaoFebre.getText().toString();
        atividadediamanha=atividadeDiaManha.getText().toString();
        atividadediatarde=atividadeDiaTarde.getText().toString();
        atividadeespecializada=atividadeEspecializada.getText().toString();
        edobservacoes=observacoes.getText().toString();
        edtfebrehora=edtFebrehora.getText().toString();
        spinnerlanche1=spinnerLanche1.getSelectedItemPosition();
        spinneralmoco=spinnerAlmoco.getSelectedItemPosition();
        spinnerlanche2=spinnerLanche2.getSelectedItemPosition();
        spinnerjantar=spinnerJantar.getSelectedItemPosition();
        spinnersono=spinnerSono.getSelectedItemPosition();
        spinnersonotempo=spinnerSonoTempo.getSelectedItemPosition();
        spinnerevacuacao=spinnerEvacuacao.getSelectedItemPosition();
        spinnerfebretemperatura=spinnerFebreTemperatura.getSelectedItemPosition();


        dadosAgenda.setAtividadeDiaManha(atividadediamanha);
        dadosAgenda.setAtividadeDiaTarde(atividadediatarde);
        dadosAgenda.setAtividadeEspecializada(atividadeespecializada);
        dadosAgenda.setDataAgenda(data);
        dadosAgenda.setEvacuacao(spinnerEvacuacao.getSelectedItem().toString());
        dadosAgenda.setSpinnerEvacuacao(spinnerevacuacao);
        dadosAgenda.setObservacoes(edobservacoes);
        dadosAgenda.setHoraFebre(edtfebrehora);
        dadosAgenda.setMedicacaoFebre(medicacaofebre);
        dadosAgenda.setRefeicaoLanche1(edtlanche1);
        dadosAgenda.setRefeicaoAlmoco(edtalmoco);
        dadosAgenda.setRefeicaoLanche2(edtlanche2);
        dadosAgenda.setRefeicaoJantar(edtjantar);
        dadosAgenda.setSpinnerEvacuacao(spinnerevacuacao);
        dadosAgenda.setSpinnerrefeicaoLanche1(spinnerlanche1);
        dadosAgenda.setSpinnerrefeicaoAlmoco(spinneralmoco);
        dadosAgenda.setSpinnerrefeicaoLanche2(spinnerlanche2);
        dadosAgenda.setSpinnerrefeicaoJantar(spinnerjantar);
        dadosAgenda.setTempoSono(spinnersonotempo.toString());
        dadosAgenda.setSpinnerSono(spinnersono);
        dadosAgenda.setSpinnerTempoSono(spinnersonotempo);
        dadosAgenda.setTemperatura(temperatura);
        dadosAgenda.setSpinnerTemperatura(spinnerfebretemperatura);
        dadosAgenda.setSono(spinnerSonoTempo.getSelectedItem().toString());
        dadosAgenda.setTempoSono(sono);
        dadosAgenda.setFebre_sim_nao(temfebre);

        myRef = database.getReference("Agenda");
        String key = myRef.child("Agenda").push().getKey();

        dadosAgenda.setKeyAgenda(key);

        myRef.child(key).setValue(dadosAgenda);





    }

    public void voltarTelaDiretoria() {
        Intent intent = new Intent(Agenda.this, MenuDiretoria.class);
        startActivity(intent);
        finish();
    }
}