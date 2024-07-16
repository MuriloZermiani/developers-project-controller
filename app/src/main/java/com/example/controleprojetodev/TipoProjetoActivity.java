package com.example.controleprojetodev;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controleprojetodev.modelo.TipoProjeto;

import java.util.ArrayList;

public class TipoProjetoActivity extends AppCompatActivity {

    private ListView listViewTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tipo_projeto);
        listViewTipos = findViewById(R.id.listViewTipos);
        listViewTipos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id)
            {
                String tipo = String.valueOf((TipoProjeto) listViewTipos.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(),
                        tipo + " Foi clicado!",
                             Toast.LENGTH_SHORT).show();

            }
        });
        popularLista();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void popularLista(){
        String[] tipos   = getResources().getStringArray(R.array.tipoProjeto);
        int   [] idTipos  = getResources().getIntArray(R.array.idTipoProjeto);
       ArrayList<TipoProjeto> tiposProjeto = new ArrayList<>();
       for (int cont = 0; cont < tipos.length; cont++){
           tiposProjeto.add(new TipoProjeto(idTipos[cont], tipos[cont]));
       }

        ArrayAdapter<TipoProjeto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tiposProjeto);
        listViewTipos.setAdapter(adapter);

    }

}