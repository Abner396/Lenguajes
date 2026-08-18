package com.mycompany.prueba;

public class Error {
    
    private String lexema;
    private String tipo;
    private int fila;
    private int columna;

    public Error(String lexema, String tipo, int fila, int columna) {

        this.lexema = lexema;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {

        return "ERROR | " + lexema + " | " + tipo  + " | " + fila + " | "  + columna;
 }   
}
    
