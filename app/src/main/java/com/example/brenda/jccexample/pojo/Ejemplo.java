package com.example.brenda.jccexample.pojo;

/**
 * Created by jcapiz on 8/10/17.
 */

public class Ejemplo {

    private int idEjemplo;
    private String ejemplo;
    private int idModismo;

    public Ejemplo(int idEjemplo, String ejemplo, int idModismo) {
        this.idEjemplo = idEjemplo;
        this.ejemplo = ejemplo;
        this.idModismo = idModismo;
    }

    public Ejemplo(int idEjemplo) {
        this.idEjemplo = idEjemplo;
    }

    public int getIdEjemplo() {
        return idEjemplo;
    }

    public void setIdEjemplo(int idEjemplo) {
        this.idEjemplo = idEjemplo;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public int getIdModismo() {
        return idModismo;
    }

    public void setIdModismo(int idModismo) {
        this.idModismo = idModismo;
    }

    @Override
    public String toString(){
        return ejemplo;
    }
}
