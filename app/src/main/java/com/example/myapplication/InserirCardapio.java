package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InserirCardapio extends AppCompatActivity {
    public static String key;
    EditText lanche1;
    EditText almoco;
    EditText lanche2;
    EditText jantar;
    Cardapio cardapioinserir;
    DatabaseReference reference;
    Button btninserirCardapio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_cardapio);
        lanche1=(EditText)findViewById(R.id.edtcLanche1);
        almoco=(EditText)findViewById(R.id.edtcAlmoco);
        lanche2=(EditText)findViewById(R.id.edtcLanche2);
        jantar=(EditText)findViewById(R.id.edtcJantar);
        btninserirCardapio=(Button)findViewById(R.id.btnInserirCardapio);
        cardapioinserir = new Cardapio();


        btninserirCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirCardapio();
            }
        });
    }

    public void inserirCardapio(){
        String lanc1,almoc,lanc2, jant;
        lanc1 = lanche1.getText().toString();
        almoc = almoco.getText().toString();
        lanc2 = lanche2.getText().toString();
        jant = jantar.getText().toString();

        if (TextUtils.isEmpty(lanc1)) {
            Toast.makeText(getApplicationContext(), "Insira os alimentos do Lanche 1", Toast.LENGTH_LONG).show();            return;
        }
        if (TextUtils.isEmpty(almoc)) {
            Toast.makeText(getApplicationContext(), "Insira os alimentos do Almoco ", Toast.LENGTH_LONG).show();            return;
        }
        if (TextUtils.isEmpty(lanc2)) {
            Toast.makeText(getApplicationContext(), "Insira os alimentos do Lanche 2", Toast.LENGTH_LONG).show();            return;
        }
        if (TextUtils.isEmpty(jant)) {
            Toast.makeText(getApplicationContext(), "Insira os alimentos do Jantar", Toast.LENGTH_LONG).show();            return;
        }


        cardapioinserir.setLanche1(lanche1.getText().toString());
        cardapioinserir.setAlmoco(almoco.getText().toString());
        cardapioinserir.setLanche2(lanche2.getText().toString());
        cardapioinserir.setJantar(jantar.getText().toString());

        reference= FirebaseDatabase.getInstance().getReference("cardapio");

        key = "-MB1oAIFi-Du_kUSUDh8";

        cardapioinserir.setKeyCardapio(key);
        reference.child(key).setValue(cardapioinserir);


    }
}