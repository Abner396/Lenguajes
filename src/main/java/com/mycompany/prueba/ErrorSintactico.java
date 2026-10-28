/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prueba;

/**
 *
 * @author abner
 */
public class ErrorSintactico {
    
    private final String mensaje;
    private final int fila;
    private final int columna;

    public ErrorSintactico(String mensaje, int fila, int columna) {
        this.mensaje = mensaje;
        this.fila = fila;
        this.columna = columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTipo() {
        return "Error sintáctico";
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return mensaje;
    }
    
}
