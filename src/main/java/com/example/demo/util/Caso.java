package com.example.demo.util;

import java.io.Serializable;
import java.util.Date;

public class Caso implements Serializable {
    private String cep;
    private Date data;

    public Caso (Date data, String cep) {
        super();
        this.data = data;
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data: " + this.data + System.lineSeparator() +
               "Cep:" + this.cep;
    }
}