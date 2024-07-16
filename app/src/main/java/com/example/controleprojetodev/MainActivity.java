package com.example.controleprojetodev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    public static final String ARQUIVO = "com.example.controleprojetodev.PREFERENCIAIS";
    private boolean typeMode = true;
    public static final String TYPE_MODE = "TYPE_MODE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        layout = findViewById((R.id.main));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chama finish() para fechar a atividade atual
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teste_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int click = item.getItemId();
        if(click == R.id.menuItemSobre){
            DetalheAppActivity.nova(this);
            return true;
        }else if(click == R.id.menuItemTheme){
            alterarModo();
            return(true);
        }else {
             return super.onOptionsItemSelected(item);
        }
    }

    //Chamar activity que ira direcionar para os cadastros
    public void startDirecionaActivity(View view){
        Intent i = new Intent(this, DirecionaActivity.class);
        startActivity(i);
    }

    //Chamar activity que ira direcionar para os cadastros
    public void startDetalheAppActivity(View view){
        Intent i = new Intent(this, DetalheAppActivity.class);
        startActivity(i);
    }

    //Usando Shared Preferences
    private void alterarModo(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        int theme = AppCompatDelegate.getDefaultNightMode();
        /*temas
        * -100 = Não especificado, padrão
        *  1   = light mode
        *  2   = night mode
        * */
        if(theme == -100 || theme ==1 || theme == 2){
            if(shared.getBoolean("dark_mode", true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("light_mode", true);
                editor.putBoolean("dark_mode", false);
            }else if(shared.getBoolean("light_mode", true)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("dark_mode", true);
                editor.putBoolean("light_mode", false);

            }

        }
        setContentView(R.layout.activity_main);

        editor.apply();
        editor.commit();

    }
}