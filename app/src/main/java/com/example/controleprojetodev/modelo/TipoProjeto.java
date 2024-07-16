package com.example.controleprojetodev.modelo;

public class TipoProjeto {
    private int id;
    private String tipo;

    public TipoProjeto(int id, String tipo){
        setId(id);
        setTipo(tipo);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString(){
        return getId()+" - " + getTipo();
    }
}
