package com.example.controleprojetodev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.controleprojetodev.modelo.Projeto;

import java.util.List;

public class ProjetoAdapter extends BaseAdapter {
    private Context context;

    private List<Projeto> projetos;

    private String[] tipos;


    private static class PessoaHolder {
        public TextView textViewValorNome;
        public TextView textViewValorTipo;
        public TextView textViewValorFinalidade;
        public TextView textViewValorFinalizado;
    }

    public ProjetoAdapter(Context context, List<Projeto> projetos){
        this.context = context;
        this.projetos = projetos;

        tipos = context.getResources().getStringArray(R.array.tipoProjeto);
    }

    @Override
    public int getCount() {
        return projetos.size();
    }

    @Override
    public Object getItem(int position) {
        return projetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PessoaHolder holder;

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_lista_projetos, parent, false);

            holder = new PessoaHolder();

            holder.textViewValorNome     = convertView.findViewById(R.id.textViewValorNome);
            holder.textViewValorTipo     = convertView.findViewById(R.id.textViewValorTipo);
            holder.textViewValorFinalidade = convertView.findViewById(R.id.textViewValorFinalidade);
            holder.textViewValorFinalizado = convertView.findViewById(R.id.textViewValorfinalizado);

            convertView.setTag(holder);

        }else{

            holder = (PessoaHolder) convertView.getTag();

        }

        Projeto projeto = projetos.get(position);

        holder.textViewValorNome.setText(projeto.getNome());
        holder.textViewValorTipo.setText(projeto.getTipo());
        if (projeto.isFinalizado()){
            holder.textViewValorFinalizado.setText(R.string.sim);
        }else{
            holder.textViewValorFinalizado.setText(R.string.nao);
        }

        holder.textViewValorFinalidade.setText(projeto.getFinalidade());
        return convertView;
    }
}
