package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataQuantidade {
	private GregorianCalendar data;
	private int quantidade;
	public DataQuantidade(String dataStr, int quantidade) {
		super();
		String[] campos = dataStr.split("/");
		int dia = Integer.parseInt(campos[0]);
		int mes = Integer.parseInt(campos[1]);
		int ano = Integer.parseInt(campos[2]);
		this.data = new GregorianCalendar();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH,mes-1);
		data.set(Calendar.DAY_OF_MONTH, dia);
		
		this.quantidade = quantidade;
	}
	public DataQuantidade() {
		
	}
	public GregorianCalendar getData() {
		return data;
	}
	public void setData(GregorianCalendar data) {
		this.data = data;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		return "(" + df.format(this.data.getTime()) + "," + this.quantidade + ")";
	}
	
	
}
