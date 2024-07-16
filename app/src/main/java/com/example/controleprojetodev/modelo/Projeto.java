package com.example.controleprojetodev.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Projeto {

//    public static Comparator<Projeto> comparador = new Comparator<Projeto>() {
//        @Override
//        public int compare(Projeto projeto, Projeto t1) {
//            int compAlfabetica = projeto.getNome().compareToIgnoreCase(t1.getNome());
//
//            if (compAlfabetica == 0){
//
//                if (projeto.getId() < t1.getId()){
//                    return -1;
//                }
//            }else{
//                return compAlfabetica;
//            }
//            return 0;
//        }
//    };
    //public static Comparator ordenacaoCrescente = (Comparator<Projeto>) (projeto1, projeto2) -> projeto1.getNome().compareToIgnoreCase(projeto2.getNome());

    //public static Comparator ordenacaoDecrescente = (Comparator<Projeto>) (projeto1, projeto2) -> -1 * projeto1.getNome().compareToIgnoreCase(projeto2.getNome());

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    private String tipo;
    //private Integer tipo;
    private boolean finalizado;
    private String finalidade;

    public Projeto(String nome, String tipo, boolean finalizado, String finalidade) {
        this.nome = nome;
        this.tipo = tipo;
        this.finalizado = finalizado;
        this.finalidade = finalidade;
    }


    // Getters e Setters

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }


    public boolean isFinalizado() { return finalizado; }
    public void setFinalizado(boolean finalizado) { this.finalizado = finalizado; }

    public String getFinalidade() { return finalidade; }
    public void setFinalidade(String finalidade) { this.finalidade = finalidade; }

    //public  Boolean getFinalizado(){return finalizado;}
    public String toString(){
        return "Projeto: "+"\n Nome: " +getNome()+" \n Tipo: " + getTipo()+" \n Finalidade: " + getFinalidade()+" \n Finalizado? : " + isFinalizado();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
