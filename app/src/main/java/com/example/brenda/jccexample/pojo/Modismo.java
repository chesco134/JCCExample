package com.example.brenda.jccexample.pojo;

import java.io.Serializable;

/**
 * Created by jcapiz on 8/10/17.
 */

public class Modismo implements Serializable {

    private int idModismo;
    private String expresion;
    private int pais;

    public Modismo(int idModismo, String expresion, int pais) {
        this.idModismo = idModismo;
        this.expresion = expresion;
        this.pais = pais;
    }

    public Modismo(int idModismo) {
        this.idModismo = idModismo;
    }

    public int getIdModismo() {
        return idModismo;
    }

    public void setIdModismo(int idModismo) {
        this.idModismo = idModismo;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public int getPais() {
        return pais;
    }

    public void setPais(int pais) {
        this.pais = pais;
    }

    @Override
    public String toString(){
        return expresion;
    }
}
