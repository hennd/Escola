package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuDiretoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diretoria);

    }



    public void sair(View view){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MenuDiretoria.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }
}