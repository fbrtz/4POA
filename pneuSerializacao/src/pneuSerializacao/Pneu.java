package pneuSerializacao;

import java.io.Serializable;

public class Pneu implements Serializable {

	private String fabricante;
	private String modelo;
	private int aro;
	private int vidaUtilKm;

	public Pneu(String fabricante, String modelo, int aro, int vidaUtilKm) {
		super();
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.aro = aro;
		this.vidaUtilKm = vidaUtilKm;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAro() {
		return aro;
	}

	public void setAro(int aro) {
		this.aro = aro;
	}

	public int getVidaUtilKm() {
		return vidaUtilKm;
	}

	public void setVidaUtilKm(int vidaUtilKm) {
		this.vidaUtilKm = vidaUtilKm;
	}

	@Override
	public String toString() {
		return "Pneu [fabricante=" + fabricante + ", modelo=" + modelo + ", aro=" + aro + ", vidaUtilKm=" + vidaUtilKm
				+ "]";
	}

}
