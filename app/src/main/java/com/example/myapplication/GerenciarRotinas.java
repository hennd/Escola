package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class GerenciarRotinas extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,AdapterView.OnItemSelectedListener{

    EditText edtLanche1vag;
    EditText edtAlmocovag;
    EditText edtLanche2vag;
    EditText edtJantarvag;
    Spinner spinnerLanche1vag;
    Spinner spinnerAlmocovag;
    Spinner spinnerLanche2vag;
    Spinner spinnerJantarvag;
    Spinner spinnerSonovag;
    Spinner spinnerSonoTempovag;
    Spinner spinnerEvacuacaovag;
    RadioGroup rgFebrevag;
    RadioButton rbFebreSimvag;
    RadioButton rbFebreNaovag;
    EditText edtHoraFebrevag;
    Spinner spinnerFebreTemperaturavag;
    EditText medicacaoFebrevag;
    EditText atividadeDiaManhavag;
    EditText atividadeDiaTardevag;
    EditText atividadeEspecializadavag;
    EditText observacoesvag;
    EditText edtDataRotinavag;
    Button btnFebreHoraedita;
    Button btnSalvarEditar;
    String febre;
    DatabaseReference referenciaAlunoAgenda= FirebaseDatabase.getInstance().getReference("agendas");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_rotinas);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        btnSalvarEditar=(Button)findViewById(R.id.btnSalvareditado);
        btnFebreHoraedita=(Button)findViewById(R.id.btnHoraFebre2);
        edtDataRotinavag=(EditText)findViewById(R.id.edtDataRotinavag);
        edtDataRotinavag.setEnabled(false);
        edtLanche1vag = (EditText)findViewById(R.id.edtLanche1vag);
        edtLanche1vag.setEnabled(false);
        edtAlmocovag= (EditText)findViewById(R.id.edtAlmocovag);
        edtAlmocovag.setEnabled(false);
        edtLanche2vag = (EditText)findViewById(R.id.edtLanche2vag);
        edtLanche2vag.setEnabled(false);
        edtJantarvag = (EditText)findViewById(R.id.edtJantarvag);
        edtJantarvag.setEnabled(false);

        spinnerLanche1vag=(Spinner)findViewById(R.id.spinner_lanche1vag);
        spinnerAlmocovag=(Spinner)findViewById(R.id.spinner_almocovag);
        spinnerLanche2vag=(Spinner)findViewById(R.id.spinner_lanche2vag);
        spinnerJantarvag=(Spinner)findViewById(R.id.spinner_jantarvag);

        spinnerSonovag=(Spinner)findViewById(R.id.spinner_sono_turnosvag);
        spinnerSonoTempovag=(Spinner)findViewById(R.id.spinner_tempo_sonovag);

        spinnerEvacuacaovag=(Spinner)findViewById(R.id.spinner_evacuacaovag);
        edtHoraFebrevag=(EditText)findViewById(R.id.edtHoraFebrevag);
        rgFebrevag=(RadioGroup)findViewById(R.id.rgFebrevag);
        rbFebreSimvag=(RadioButton)findViewById(R.id.rbFebreSimvag);
        rbFebreNaovag=(RadioButton)findViewById(R.id.rbFebreNaovag);
        spinnerFebreTemperaturavag=(Spinner)findViewById(R.id.spinner_febre_tempvag);
        medicacaoFebrevag=(EditText)findViewById(R.id.edtMedicacaovag);
        atividadeDiaManhavag=(EditText)findViewById(R.id.edtAtividadedoDiaManhavag);
        atividadeDiaTardevag=(EditText)findViewById(R.id.edtAtividadedoDiaTardevag);
        atividadeEspecializadavag=(EditText)findViewById(R.id.edtAtividadeEspecializadavag);
        observacoesvag=(EditText)findViewById(R.id.edtObservacoesvag);

        final Spinner lanchevag = findViewById(R.id.spinner_lanche1vag);
        ArrayAdapter<CharSequence> lancheAdaptervag = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lancheAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanchevag.setAdapter(lancheAdaptervag);
        lanchevag.setOnItemSelectedListener(this);


        final Spinner almoçovag = findViewById(R.id.spinner_almocovag);
        ArrayAdapter<CharSequence> almocoAdaptervag = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        almocoAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        almoçovag.setAdapter(almocoAdaptervag);
        almoçovag.setOnItemSelectedListener(this);



        final Spinner lanche2vag = findViewById(R.id.spinner_lanche2vag);
        ArrayAdapter<CharSequence> lanche2Adaptervag = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        lanche2Adaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanche2vag.setAdapter(lanche2Adaptervag);
        lanche2vag.setOnItemSelectedListener(this);



        final Spinner jantarvag = findViewById(R.id.spinner_jantarvag);
        ArrayAdapter<CharSequence> jantarAdaptervag = ArrayAdapter.createFromResource(this,R.array.comeu,android.R.layout.simple_spinner_item);
        jantarAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jantarvag.setAdapter(jantarAdaptervag);
        jantarvag.setOnItemSelectedListener(this);


        final Spinner sonoTurnosvag = findViewById(R.id.spinner_sono_turnosvag);
        ArrayAdapter<CharSequence> sonoTurnosAdaptervag = ArrayAdapter.createFromResource(this,R.array.turnos_sono,android.R.layout.simple_spinner_item);
        sonoTurnosAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTurnosvag.setAdapter(sonoTurnosAdaptervag);
        sonoTurnosvag.setOnItemSelectedListener(this);


        final Spinner sonoTempovag = findViewById(R.id.spinner_tempo_sonovag);
        ArrayAdapter<CharSequence> sonoTempoAdaptervag = ArrayAdapter.createFromResource(this,R.array.tempo_sono,android.R.layout.simple_spinner_item);
        sonoTempoAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonoTempovag.setAdapter(sonoTempoAdaptervag);
        sonoTempovag.setOnItemSelectedListener(this);


        final Spinner evacucaovag = findViewById(R.id.spinner_evacuacaovag);
        ArrayAdapter<CharSequence> evacuacaoAdaptervag = ArrayAdapter.createFromResource(this,R.array.evacuacao,android.R.layout.simple_spinner_item);
        evacuacaoAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evacucaovag.setAdapter(evacuacaoAdaptervag);
        evacucaovag.setOnItemSelectedListener(this);


        final Spinner febreTempvag = findViewById(R.id.spinner_febre_tempvag);
        ArrayAdapter<CharSequence> febreTempAdaptervag = ArrayAdapter.createFromResource(this,R.array.febre_temp,android.R.layout.simple_spinner_item);
        febreTempAdaptervag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        febreTempvag.setAdapter(febreTempAdaptervag);
        febreTempvag.setOnItemSelectedListener(this);

        btnFebreHoraedita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(GerenciarRotinas.this, GerenciarRotinas.this, hour, minute, true);
                timePickerDialog.show();
            }
        });
        btnSalvarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarDadosEditado();
            }
        });



        final String oquefoi=TelaDiretoriaGerenciarRotinas.idagendabuscar;
        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(oquefoi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);
                    edtDataRotinavag.setText(dados.getDataAgenda());
                    edtLanche1vag.setText(dados.getRefeicaoLanche1());
                    lanchevag.setSelection(dados.getSpinnerrefeicaoLanche1());
                    edtAlmocovag.setText(dados.getRefeicaoAlmoco());
                    almoçovag.setSelection(dados.getSpinnerrefeicaoAlmoco());
                    edtLanche2vag.setText(dados.getRefeicaoLanche2());
                    lanche2vag.setSelection(dados.getSpinnerrefeicaoLanche2());
                    edtJantarvag.setText(dados.getRefeicaoJantar());
                    jantarvag.setSelection(dados.getSpinnerrefeicaoJantar());
                    sonoTempovag.setSelection(dados.getSpinnerTempoSono());
                    sonoTurnosvag.setSelection(dados.getSpinnerSono());
                    evacucaovag.setSelection(dados.getSpinnerEvacuacao());
                    febre = dados.getFebre_sim_nao();
                    if (febre.equals("sim")) rbFebreSimvag.setChecked(true); else rbFebreNaovag.setChecked(true);
                    febreTempvag.setSelection(dados.getSpinnerTemperatura());
                    edtHoraFebrevag.setText(dados.getHoraFebre());
                    medicacaoFebrevag.setText(dados.getMedicacaoFebre());
                    atividadeDiaManhavag.setText(dados.getAtividadeDiaManha());
                    atividadeDiaTardevag.setText(dados.getAtividadeDiaTarde());
                    atividadeEspecializadavag.setText(dados.getAtividadeEspecializada());
                    observacoesvag.setText(dados.getObservacoes());

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


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String hora= String.format("%02d:%02d",hourOfDay, minute);;

        edtHoraFebrevag.setText(hora);
    }



    public void SalvarDadosEditado(){


        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(TelaDiretoriaGerenciarRotinas.idagendabuscar).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);

                    edtDataRotinavag.setText(dados.getDataAgenda());

                    alunosSnapShot.getRef().child("atividadeDiaManha").setValue(atividadeDiaManhavag.getText().toString());
                    alunosSnapShot.getRef().child("atividadeDiaTarde").setValue(atividadeDiaTardevag.getText().toString());
                    alunosSnapShot.getRef().child("atividadeEspecializada").setValue(atividadeEspecializadavag.getText().toString());
                    if (rbFebreSimvag.isChecked()) {alunosSnapShot.getRef().child("febre_sim_nao").setValue("sim");} else alunosSnapShot.getRef().child("febre_sim_nao").setValue("nao");
                    alunosSnapShot.getRef().child("evacuacao").setValue(spinnerEvacuacaovag.getSelectedItem().toString());
                    alunosSnapShot.getRef().child("horaFebre").setValue(edtHoraFebrevag.getText().toString());
                    alunosSnapShot.getRef().child("medicacaoFebre").setValue(medicacaoFebrevag.getText().toString());
                    alunosSnapShot.getRef().child("observacoes").setValue(observacoesvag.getText().toString());
                    alunosSnapShot.getRef().child("sono").setValue(spinnerSonoTempovag.getSelectedItem().toString());
                    alunosSnapShot.getRef().child("spinnerEvacuacao").setValue(spinnerEvacuacaovag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerSono").setValue(spinnerSonovag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerTemperatura").setValue(spinnerFebreTemperaturavag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerTempoSono").setValue(spinnerSonoTempovag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerrefeicaoAlmoco").setValue(spinnerAlmocovag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerrefeicaoLanche1").setValue(spinnerLanche1vag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerrefeicaoLanche2").setValue(spinnerLanche2vag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("spinnerrefeicaoJantar").setValue(spinnerJantarvag.getSelectedItemPosition());
                    alunosSnapShot.getRef().child("temperatura").setValue(spinnerFebreTemperaturavag.getSelectedItem().toString());
                    alunosSnapShot.getRef().child("tempoSono").setValue(spinnerSonoTempovag.getSelectedItem().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}