package com.example.brenda.jccexample.pojo;

/**
 * Created by jcapiz on 8/10/17.
 */

public class ModismoRelacion {

    private int idModismo1;
    private int idModismo2;

    public ModismoRelacion(int idModismo1, int idModismo2) {
        this.idModismo1 = idModismo1;
        this.idModismo2 = idModismo2;
    }

    public int getIdModismo1() {
        return idModismo1;
    }

    public int getIdModismo2() {
        return idModismo2;
    }
}
