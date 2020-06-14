package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Agenda extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    Button btnFebrehora;
    EditText edtFebrehora;
    int hora, minuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

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




        btnFebrehora=findViewById(R.id.btnHoraFebre);
        edtFebrehora=findViewById(R.id.edtHoraFebre);
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
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}