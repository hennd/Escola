package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class Dash extends AppCompatActivity {


    Spinner spinner;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        spinner = (Spinner) findViewById(R.id.spinnerTeste);
        databaseReference = FirebaseDatabase.getInstance().getReference("alunos");
        String usuario;
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(Dash.this,
                    android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        spinner.setAdapter(adapter);
        retrieveData();
    }
    public void deslogar(View view){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Dash.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }
    public void retrieveData(){
                   listener = databaseReference.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for (DataSnapshot item : dataSnapshot.getChildren()) {
                                       Aluno a = item.getValue(Aluno.class);
                                       spinnerDataList.add(a.getNomeAluno());
                                       spinnerDataList.add(a.getKeyUser());
                                   }

                       }
                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
    }
}