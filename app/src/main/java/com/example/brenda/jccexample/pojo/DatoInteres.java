package com.example.brenda.jccexample.pojo;

public class DatoInteres {

	// A esto me refer�a cuando te dec�a que hac�a clases por cada Entidad en la BD de inter�s.
	private int idDatoInteres;
	private String datoInteres;
	private int paisIdPais;

	public int getIdDatoInteres() {
		return idDatoInteres;
	}
	public void setIdDatoInteres(int idDatoInteres) {
		this.idDatoInteres = idDatoInteres;
	}
	public String getDatoInteres() {
		return datoInteres;
	}
	public void setDatoInteres(String datoInteres) {
		this.datoInteres = datoInteres;
	}

	public int getPaisIdPais() {
		return paisIdPais;
	}

	public void setPaisIdPais(int paisIdPais) {
		this.paisIdPais = paisIdPais;
	}
}
