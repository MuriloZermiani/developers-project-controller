//package com.example.controleprojetodev.persistencia;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.controleprojetodev.modelo.Projeto;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class ProjetoDAO {
//    public static final String TABELA       = "PROJETO";
//    public static final String NOME         = "NOME";
//    public static final String ID           = "ID";
//    public static final String TIPO         = "TIPO";
//    public static final String FINALIZADO   = "FINALIZADO";
//    public static final String FINALIDADE   = "FINALIDADE";
//
//
//    private ProjetosDatabase conexao;
//    public List<Projeto> lista;
//
//    public ProjetoDAO(ProjetosDatabase projetosDatabase){
//        conexao = projetosDatabase;
//        lista   = new ArrayList<>();
//    }
//
//    public void criarTabela(SQLiteDatabase database){
//
//        String sql = "CREATE TABLE " + TABELA + "(" +
//                ID    + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                NOME  + " TEXT NOT NULL, " +
//                TIPO + " STRING, "+
//                FINALIZADO +" INTEGER, " +
//                FINALIDADE + " STRING)" ;
//
//
//        database.execSQL(sql);
//    }
//
//    public void apagarTabela(SQLiteDatabase database){
//
//        String sql = "DROP TABLE IF EXISTS " + TABELA;
//
//        database.execSQL(sql);
//    }
//
//    public boolean inserir(Projeto projeto){
//
//        ContentValues values = new ContentValues();
//
//        values.put(NOME, projeto.getNome());
//        values.put(TIPO, projeto.getTipo());
//        values.put(FINALIZADO, projeto.isFinalizado());
//        values.put(FINALIDADE, projeto.getFinalidade());
//
//        long id = conexao.getWritableDatabase().insert(TABELA,
//                null,
//                values);
//
//        projeto.setId(id);
//
//        lista.add(projeto);
//
//        ordenarLista();
//
//        return true;
//    }
//
//    public boolean alterar(Projeto projeto){
//
//        ContentValues values = new ContentValues();
//
//        values.put(NOME, projeto.getNome());
//        values.put(TIPO, projeto.getTipo());
//        values.put(FINALIZADO, projeto.isFinalizado());
//        values.put(FINALIDADE, projeto.getFinalidade());
//
//
//        String[] args = {String.valueOf(projeto.getId())};
//
//        conexao.getWritableDatabase().update(TABELA,
//                values,
//                ID + " = ?",
//                args);
//
//        ordenarLista();
//
//        return true;
//    }
//
//    public boolean apagar(Projeto projeto){
//
//        String[] args = {String.valueOf(projeto.getId())};
//
//        conexao.getWritableDatabase().delete(TABELA,
//                ID + " = ?",
//                args);
//        lista.remove(projeto);
//
//        return true;
//    }
//
//    public void carregarTudo(){
//
//        lista.clear();
//
//        String sql = "SELECT * FROM " + TABELA + " ORDER BY " + NOME;
//
//        Cursor cursor = conexao.getReadableDatabase().rawQuery(sql, null);
//
//        int colunaNome  = cursor.getColumnIndex(NOME);
//        int colunaId    = cursor.getColumnIndex(ID);
//        int colunaTipo = cursor.getColumnIndex(TIPO);
//        int colunaFinalizado = cursor.getColumnIndex(FINALIZADO);
//        int colunaFinalidade = cursor.getColumnIndex(FINALIDADE);
//
//        while(cursor.moveToNext()){
//            long id = cursor.getLong(colunaId);
//            String nome = cursor.getString(colunaNome);
//            String tipo = cursor.getString(colunaTipo);
//            boolean finalizado = cursor.getInt(colunaFinalizado) != 0;
//            String finalidade = cursor.getString(colunaFinalidade);
//            Projeto projeto = new Projeto(nome, tipo, finalizado, finalidade);
//
//            lista.add(projeto);
//        }
//
//        cursor.close();
//
//        ordenarLista();
//    }
//
//    public Projeto projetoPorId(long id){
//
//        for (Projeto p: lista){
//
//            if (p.getId() == id){
//                return p;
//            }
//        }
//
//        return null;
//    }
//
//    public void ordenarLista(){
//        Collections.sort(lista, Projeto.comparador);
//    }
//}
