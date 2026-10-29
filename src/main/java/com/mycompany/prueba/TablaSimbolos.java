/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prueba;

/**
 *
 * @author abner
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TablaSimbolos {
    
    private final Map<String, Simbolo> simbolos;

    public TablaSimbolos() {
        simbolos = new LinkedHashMap<>();
    }

    /**
     * Registra un identificador en la tabla.
     *
     * @return true si se registró correctamente;
     *         false si el identificador ya existe.
     */
    public boolean declarar(
            String nombre,
            String tipo,
            int fila,
            int columna
    ) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (simbolos.containsKey(nombre)) {
            return false;
        }

        Simbolo simbolo = new Simbolo(
                nombre,
                tipo,
                fila,
                columna
        );

        simbolos.put(nombre, simbolo);

        return true;
    }

    /**
     * Busca un identificador por su nombre.
     */
    public Simbolo buscar(String nombre) {
        return simbolos.get(nombre);
    }

    /**
     * Comprueba si un identificador está registrado.
     */
    public boolean existe(String nombre) {
        return simbolos.containsKey(nombre);
    }

    /**
     * Devuelve la cantidad de símbolos registrados.
     */
    public int cantidad() {
        return simbolos.size();
    }

    /**
     * Devuelve los símbolos sin permitir modificar la colección.
     */
    public List<Simbolo> obtenerSimbolos() {
        return Collections.unmodifiableList(
                new ArrayList<>(simbolos.values())
        );
    }

    /**
     * Genera una representación textual de la tabla.
     */
    public String imprimirTabla() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("TABLA DE SÍMBOLOS\n");
        resultado.append(
                "--------------------------------------------------\n"
        );
        resultado.append(
                String.format(
                        "%-20s %-15s %-10s%n",
                        "IDENTIFICADOR",
                        "TIPO",
                        "LÍNEA"
                )
        );
        resultado.append(
                "--------------------------------------------------\n"
        );

        for (Simbolo simbolo : simbolos.values()) {
            resultado.append(
                    String.format(
                            "%-20s %-15s %-10d%n",
                            simbolo.getNombre(),
                            simbolo.getTipo(),
                            simbolo.getFila()
                    )
            );
        }

        resultado.append(
                "--------------------------------------------------\n"
        );
        resultado.append(
                "Total de símbolos: "
        ).append(cantidad()).append("\n");

        return resultado.toString();
    }

    /**
 * Construye la tabla de símbolos a partir del AST.
 */
public void construirDesdeAST(Nodo raiz) {
    simbolos.clear();
    recorrerAST(raiz);
}

/**
 * Recorre recursivamente el árbol para identificar símbolos.
 */
private void recorrerAST(Nodo nodo) {
    if (nodo == null) {
        return;
    }

    String tipo = nodo.getTipo();
    String nombre = nodo.getValor();

    if (nombre != null && !nombre.trim().isEmpty()) {

        switch (tipo) {
            case "AGENTE":
                declarar(
                        nombre,
                        "AGENTE",
                        nodo.getFila(),
                        nodo.getColumna()
                );
                break;

            case "VARIABLE":
                declarar(
                        nombre,
                        "VARIABLE",
                        nodo.getFila(),
                        nodo.getColumna()
                );
                break;

            case "RESULTADO":
                declarar(
                        nombre,
                        "RESULTADO",
                        nodo.getFila(),
                        nodo.getColumna()
                );
                break;

            default:
                break;
        }
    }

    for (Nodo hijo : nodo.getHijos()) {
        recorrerAST(hijo);
    }
}
    
    /**
     * Representa un símbolo individual.
     */
    public static class Simbolo {

        private final String nombre;
        private final String tipo;
        private final int fila;
        private final int columna;

        public Simbolo(
                String nombre,
                String tipo,
                int fila,
                int columna
        ) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.fila = fila;
            this.columna = columna;
        }

        public String getNombre() {
            return nombre;
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
    }
    
    
}
