package com.example.brenda.jccexample.pojo;

/**
 * Created by jcapiz on 13/11/17.
 */

public class Similar {

    private int idSimilar;
    private String similar;
    private int idModismo;

    public Similar(int idSimilar) {
        this.idSimilar = idSimilar;
    }

    public Similar(int idSimilar, String similar, int idModismo) {
        this.idSimilar = idSimilar;
        this.similar = similar;
        this.idModismo = idModismo;
    }

    public Similar() {
    }

    public int getIdSimilar() {
        return idSimilar;
    }

    public void setIdSimilar(int idSimilar) {
        this.idSimilar = idSimilar;
    }

    public String getSimilar() {
        return similar;
    }

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public int getIdModismo() {
        return idModismo;
    }

    public void setIdModismo(int idModismo) {
        this.idModismo = idModismo;
    }
}
