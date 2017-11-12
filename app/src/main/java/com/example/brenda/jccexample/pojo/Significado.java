package com.example.brenda.jccexample.pojo;

/**
 * Created by jcapiz on 8/10/17.
 */

public class Significado {

    private int idSignificado;
    private String significado;
    private int idModismo;

    public Significado(int idSignificado, String significado, int idModismo) {
        this.idSignificado = idSignificado;
        this.significado = significado;
        this.idModismo = idModismo;
    }

    public Significado(int idSignificado) {
        this.idSignificado = idSignificado;
    }

    public int getIdSignificado() {
        return idSignificado;
    }

    public void setIdSignificado(int idSignificado) {
        this.idSignificado = idSignificado;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }

    public int getIdModismo() {
        return idModismo;
    }

    public void setIdModismo(int idModismo) {
        this.idModismo = idModismo;
    }
}
