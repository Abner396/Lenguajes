package com.mycompany.prueba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Lector {
    
    private BufferedReader archivo;
    private int caracter_actual;
    private int fila;
    private int columna;
    
    
    public Lector(String ruta) throws IOException {
        archivo = new BufferedReader(new FileReader(ruta));

        fila = 1;
        columna = 0;

        avanzar();
    }
    
    public Lector(String contenido, boolean desdeTexto) {

        archivo = new BufferedReader(new StringReader(contenido));

        fila = 1;
        columna = 0;

        try {
            avanzar();
        } catch (IOException e) {
            
        }
    }
    

    public void avanzar() throws IOException {

        caracter_actual = archivo.read();

        if (caracter_actual == '\n') {
            fila++;
            columna = 0; 
        } else if (caracter_actual != -1) {
            columna++;
        }
    }
    
    public char getCaracter_actual() {
        return (char) caracter_actual;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean finArchivo() {
        return caracter_actual == -1;
    }

    public void cerrar() throws IOException {
        archivo.close();
    }
}
