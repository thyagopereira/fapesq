package com.example.demo.util;

import java.util.GregorianCalendar;

public class SimpleRecord {
	//Data, Bairro, Qtde casos no bairro, Qtde óbitos
	private GregorianCalendar dataNotificacao;
	private String bairro;
	private int numeroCasos;
	private int numeroObitos;
	public GregorianCalendar getDataNotificacao() {
		return dataNotificacao;
	}
	public void setDataNotificacao(GregorianCalendar dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getNumeroCasos() {
		return numeroCasos;
	}
	public void setNumeroCasos(int numeroCasos) {
		this.numeroCasos = numeroCasos;
	}
	public int getNumeroObitos() {
		return numeroObitos;
	}
	public void setNumeroObitos(int numeroObitos) {
		this.numeroObitos = numeroObitos;
	}
	
}
