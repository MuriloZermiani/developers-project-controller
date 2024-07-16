package com.example.controleprojetodev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DirecionaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_direciona);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Chamar activity de cadastros
    public void startActivityCadastro(View view){
        Intent i = new Intent(this, CadastroProjetoActivity.class);
        startActivity(i);
    }
    //Chamar activity de conferência dos projetos já criados
    public void startActivityConfere(View view){
        Intent i = new Intent(this, ConfereProjetoActivity.class);
        startActivity(i);
    }

    //Chamar activity de conferência dos projetos já criados
    public void startActivityTipoProjeto(View view){
        Intent i = new Intent(this, TipoProjetoActivity.class);
        startActivity(i);
    }
}