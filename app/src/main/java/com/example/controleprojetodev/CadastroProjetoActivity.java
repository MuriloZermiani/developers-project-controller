package com.example.controleprojetodev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controleprojetodev.modelo.Projeto;
import com.example.controleprojetodev.persistencia.ProjetosDatabase;
import com.example.controleprojetodev.utils.UtilsGUI;

import java.util.ArrayList;

public class CadastroProjetoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private RadioGroup radioGroupTipo;
    private RadioButton radioButtonDesktop, radioButtonWeb, radioButtonMobile, radioButtonGame;
    private CheckBox checkBoxStatus;
    private Spinner spinnerUso;
    private Button buttonSalvar, buttonLimpar;

    private Projeto projetoOriginal;
    private int modo;

    private Projeto projeto;

    public static String ID = "ID";

    public static final String MODO = "MODO";

    public static final int NOVO = 1;

    public static final int EDITAR = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro_projeto);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Views
        editTextNome       = findViewById(R.id.editTextNome);
        radioGroupTipo     = findViewById(R.id.radioGroupTipo);
        radioButtonDesktop = findViewById(R.id.radioButtonDesktop);
        radioButtonWeb     = findViewById(R.id.radioButtonWeb);
        radioButtonMobile  = findViewById(R.id.radioButtonMobile);
        radioButtonGame    = findViewById(R.id.radioButtonGame);
        checkBoxStatus     = findViewById(R.id.checkBoxStatus);
        spinnerUso         = findViewById(R.id.spinnerUso);
        buttonSalvar       = findViewById(R.id.buttonSalvar);
        buttonLimpar       = findViewById(R.id.buttonLimpar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null ){
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.novo_projeto));
                popularSpinner(null);

            } else if (modo == EDITAR) {
                setTitle(getString(R.string.editar_projeto));

                long id = bundle.getLong(ID);

                ProjetosDatabase db = ProjetosDatabase.getDatabase(this);

                projetoOriginal = db.getProjetosDao().queryForId(id);


                editTextNome.setText(projetoOriginal.getNome());
                checkBoxStatus.setChecked(projetoOriginal.isFinalizado());


                String finalidadeSelecionada = projetoOriginal.getFinalidade();
                popularSpinner(finalidadeSelecionada);

                String tipo = projetoOriginal.getTipo();
                if (tipo.equals(radioButtonDesktop.getText().toString())) {
                    radioButtonDesktop.setChecked(true);
                } else if (tipo.equals(radioButtonWeb.getText().toString())) {
                    radioButtonWeb.setChecked(true);
                } else if (tipo.equals(radioButtonMobile.getText().toString())) {
                    radioButtonMobile.setChecked(true);
                } else if (tipo.equals(radioButtonGame.getText().toString())) {
                    radioButtonGame.setChecked(true);
                }

            }

        }

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCadastro();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCadastro();
            }
        });
    }

    private void limparCadastro() {
        editTextNome.setText("");
        radioGroupTipo.clearCheck();
        checkBoxStatus.setChecked(false);
        spinnerUso.setSelection(0);
        Toast.makeText(this, "Formulário limpo", Toast.LENGTH_SHORT).show();
    }

    private void salvarCadastro() {
       // long id   = 1;
        String nome = editTextNome.getText().toString();
        int selectedGenderId = radioGroupTipo.getCheckedRadioButtonId();
        String tipo = ((RadioButton) findViewById(radioGroupTipo.getCheckedRadioButtonId())).getText().toString();
        boolean termosAceitos = checkBoxStatus.isChecked();
        String finalidade = spinnerUso.getSelectedItem().toString();

        if (nome.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            if (nome.isEmpty()) {
                editTextNome.requestFocus();
            } else if (selectedGenderId == -1) {
                radioGroupTipo.requestFocus();
            }
        } else {
            // Processe os dados
            Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show();

            if (modo == EDITAR                                    &&
                    nome.equals(projetoOriginal.getNome())        &&
                    tipo.equals(projetoOriginal.getTipo())          &&
                    finalidade.equals(projetoOriginal.getFinalidade()) &&
                    termosAceitos == projetoOriginal.isFinalizado()){
                cancelar();
                return;
            }

            Intent resultIntent = new Intent();
//
            ProjetosDatabase db = ProjetosDatabase.getDatabase(this);

            if (modo == NOVO){
                 Projeto projeto = new Projeto(nome, tipo, termosAceitos, finalidade);

                long novoId = db.getProjetosDao().insert(projeto);
                if (novoId <= 0){
                    UtilsGUI.aviso(this, R.string.erro_ao_tentar_inserir);
                    return;
                }
                projeto.setId(novoId);
                resultIntent.putExtra(ID, projeto.getId());
            }else{

                Projeto projetoAlterado = new Projeto(nome, tipo, termosAceitos,finalidade);

                projetoAlterado.setId(projetoOriginal.getId());

                 int qtLinhaAlterada = db.getProjetosDao().update(projetoAlterado);
                 if (qtLinhaAlterada == 0){
                    UtilsGUI.aviso(this, R.string.erro_ao_tentar_alterar);
                    return;
                 }
                 resultIntent.putExtra(ID, projetoAlterado.getId());
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        }

    }

    //Adapter para popular o spinner
    private void popularSpinner(String finalidadeSelecionada){
        ArrayList<String> ddl = new ArrayList<>();
        ddl.add(getString(R.string.uso_pessoal));
        ddl.add(getString(R.string.uso_corporativo));
        ddl.add(getString(R.string.uso_educacional));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ddl);
        spinnerUso.setAdapter(adapter);
        //em caso de edição, posicionar na opçao confrome já cadastrado
        if (finalidadeSelecionada != null) {
            int spinnerPosition = adapter.getPosition(finalidadeSelecionada);
            if (spinnerPosition >= 0) {
                spinnerUso.setSelection(spinnerPosition);
            }
        }
    }


    public static void novoProjeto(AppCompatActivity activity, ActivityResultLauncher<Intent> launcher){

        Intent intent = new Intent(activity, CadastroProjetoActivity.class);
        intent.putExtra(MODO, NOVO);
        launcher.launch(intent);
    }

    public static void editarProjeto(AppCompatActivity activity, ActivityResultLauncher<Intent> launcher, Projeto projeto){

        Intent intent = new Intent(activity, CadastroProjetoActivity.class);

        intent.putExtra(MODO, EDITAR);
        intent.putExtra(ID, projeto.getId());

        launcher.launch(intent);
    }

    public void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}



