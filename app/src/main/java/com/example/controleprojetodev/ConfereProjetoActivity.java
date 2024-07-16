package com.example.controleprojetodev;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.example.controleprojetodev.modelo.Projeto;
import com.example.controleprojetodev.persistencia.ProjetosDatabase;
import com.example.controleprojetodev.utils.UtilsGUI;

import java.util.List;

public class ConfereProjetoActivity extends AppCompatActivity {

    private ListView listViewProjetos;

    private ProjetoAdapter listaAdapter;

    private List<Projeto> listaProjeto;

    private int posicaoSelecionada = -1;

    private View viewSelecionada;

    private ActionMode actionMode;

    private boolean ordenacaoAscendente = true;

    private static final int REQUEST_NOVO_PROJETO    = 1;
    private static final int REQUEST_EDITAR_PROJETO = 2;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.principal_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditar){
                editarProjeto();
                mode.finish();
                return true;
            }else
            if (idMenuItem == R.id.menuItemExcluir){
                excluirProjeto(mode);
                return true;
            }else{
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewProjetos.setEnabled(true);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confere_projeto);


        //testar a persistencia
        listViewProjetos = findViewById(R.id.listViewProjeto);

        listViewProjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    posicaoSelecionada = position;
                    editarProjeto();
                }
        });

        listViewProjetos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           int position,
                                           long id) {

                if (actionMode != null){
                    return false;
                }

                posicaoSelecionada = position;

                view.setBackgroundColor(Color.LTGRAY);

                viewSelecionada = view;

                listViewProjetos.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return false;
            }
        });
        //ordenarLista();
        popularLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void popularLista(){

        ProjetosDatabase db = ProjetosDatabase.getDatabase(this);
        if (ordenacaoAscendente){
            listaProjeto = db.getProjetosDao().queryAllAscending();

        }else{
            listaProjeto = db.getProjetosDao().queryAllDownward();
        }

        listaAdapter = new ProjetoAdapter(this, listaProjeto);
        listViewProjetos.setAdapter(listaAdapter);
    }

    ActivityResultLauncher<Intent> launcherNovoProjeto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK){

                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null){
                            long id   = bundle.getLong(CadastroProjetoActivity.ID);

                            ProjetosDatabase db = ProjetosDatabase.getDatabase(ConfereProjetoActivity.this);

                            Projeto projeto = db.getProjetosDao().queryForId(id);

                              listaProjeto.add(projeto);

                            listaAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
    public void novaPessoa(View view){
        CadastroProjetoActivity.novoProjeto(this, launcherNovoProjeto);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int click = item.getItemId();
        if (click == R.id.menuItemAdicionar) {
            CadastroProjetoActivity.novoProjeto(this, launcherNovoProjeto);
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    ActivityResultLauncher<Intent> launcherEditarProjeto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK){

                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null){
                            long id   = bundle.getLong(CadastroProjetoActivity.ID);

                            ProjetosDatabase db = ProjetosDatabase.getDatabase(ConfereProjetoActivity.this);

                            Projeto projetoEditado = db.getProjetosDao().queryForId(id);

                            listaProjeto.set(posicaoSelecionada, projetoEditado);

                            posicaoSelecionada = -1;

                           // ordenarLista();
                        }
                    }
                }
            });


    private void editarProjeto(){

        Projeto projeto = listaProjeto.get(posicaoSelecionada);

            CadastroProjetoActivity.editarProjeto(this, launcherEditarProjeto, projeto);
    }

    private void excluirProjeto(final ActionMode mode){
       final  Projeto projeto = listaProjeto.get(posicaoSelecionada);

        String msg = getString(R.string.tem_certeza_de_que_deseja_exluir_este_projeto) +"\n " + projeto.getNome();
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        ProjetosDatabase db = ProjetosDatabase.getDatabase(ConfereProjetoActivity.this);

                        int qtAlterada = db.getProjetosDao().delete(projeto);
                        if (qtAlterada >0){
                            listaProjeto.remove(posicaoSelecionada);
                            listaAdapter.notifyDataSetChanged();
                            mode.finish();
                        }else{
                            UtilsGUI.aviso(ConfereProjetoActivity.this, R.string.erro_ao_tentar_apagar);
                        }

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        UtilsGUI.confirmaAcao(this, msg, listener);

    }

}
