/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prueba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nodo {
 
    
private final String tipo;
private final String valor;
private final int fila;
private final int columna;
private final List<Nodo> hijos;

public Nodo(String tipo, String valor, int fila, int columna) {
    this.tipo = tipo;
    this.valor = valor;
    this.fila = fila;
    this.columna = columna;
    this.hijos = new ArrayList<>();
}

public void agregarHijo(Nodo hijo) {
    if (hijo != null) {
        hijos.add(hijo);
    }
}

public String getTipo() {
    return tipo;
}

public String getValor() {
    return valor;
}

public int getFila() {
    return fila;
}

public int getColumna() {
    return columna;
}

public List<Nodo> getHijos() {
    return Collections.unmodifiableList(hijos);
}

public String imprimirArbol() {
    StringBuilder resultado = new StringBuilder();
    imprimirArbol(resultado, "", true);
    return resultado.toString();
}

private void imprimirArbol(
        StringBuilder resultado,
        String prefijo,
        boolean ultimo
) {
    resultado.append(prefijo);
    resultado.append(ultimo ? "└── " : "├── ");
    resultado.append(tipo);

    if (valor != null && !valor.isEmpty()) {
        resultado.append(": ").append(valor);
    }

    resultado.append(" [Línea: ")
             .append(fila)
             .append(", Columna: ")
             .append(columna)
             .append("]")
             .append(System.lineSeparator());

    for (int i = 0; i < hijos.size(); i++) {
        Nodo hijo = hijos.get(i);
        boolean ultimoHijo = i == hijos.size() - 1;

        hijo.imprimirArbol(
                resultado,
                prefijo + (ultimo ? "    " : "│   "),
                ultimoHijo
        );
    }
}
    
}
