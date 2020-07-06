package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TelaInicialAluno extends AppCompatActivity  {

    String alunoselec;
    public static String pesquisaAgenda;
    Boolean passou=false;
    Button btnsairaluno;
    TextView txttitulo;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private Button dprotinaaluno;
    private EditText edtDataRotinaAluno;
    private Button visualizarRotina;
    final Calendar c = Calendar.getInstance();
    final int day = c.get(Calendar.DAY_OF_MONTH);
    final int month = c.get(Calendar.MONTH);
    final int year = c.get(Calendar.YEAR);
    private DatePickerDialog datapicker;
    private Date DataAtual = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_aluno);
        btnsairaluno=(Button)findViewById(R.id.btnSairAluno);
        mAuth = FirebaseAuth.getInstance();
        dprotinaaluno=(Button)findViewById(R.id.dpRotinaAluno);
        edtDataRotinaAluno=(EditText)findViewById(R.id.edtDataRotinaAluno);
        edtDataRotinaAluno.setEnabled(false);
        visualizarRotina=(Button)findViewById(R.id.btnVisualizarRotinaAluno);
        txttitulo=(TextView)findViewById(R.id.txtTituloAluno);
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

        String emaillogado = mAuth.getCurrentUser().getEmail();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("alunos").orderByChild("emailAluno").equalTo(emaillogado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    Aluno aluno = alunosSnapShot.getValue(Aluno.class);

                    alunoselec=aluno.getNomeAluno();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        dprotinaaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datapicker=new DatePickerDialog(TelaInicialAluno.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        edtDataRotinaAluno.setText(mDay + "/" +(mMonth+1) + "/" + mYear);
                    }
                },day,month,year);
                datapicker.updateDate(anoagoraint,mesagoraint-1,diaagoraint);
                datapicker.show();
            }
        });


        btnsairaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sairAluno();
            }
        });

    }
    public void sairAluno(){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(TelaInicialAluno.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Saindo", Toast.LENGTH_LONG).show();
    }

    public void visualizarRotinaAluno(View View){

        pesquisaAgenda= alunoselec+edtDataRotinaAluno.getText().toString();


        DatabaseReference referenciaAlunoAgenda= FirebaseDatabase.getInstance().getReference("agendas");
        referenciaAlunoAgenda.orderByChild("nomeDataAgenda").equalTo(pesquisaAgenda).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alunosSnapShot : dataSnapshot.getChildren()) {
                    DadosAgenda dados = alunosSnapShot.getValue(DadosAgenda.class);
                    passou = true;

                }
                if(passou==true) {

                    Intent intent = new Intent(TelaInicialAluno.this, MostrarRotinaAluno.class);
                    startActivity(intent);
                    finish();


                }else {
                    Toast.makeText(TelaInicialAluno.this, "Não há rotinas na data Selecionada", Toast.LENGTH_LONG).show();


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }
}