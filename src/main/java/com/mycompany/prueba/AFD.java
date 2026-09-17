/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prueba;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AFD {

    private String nombre_dot = "afd_promptzal.dot";
    private String nombre_png = "afd_promptzal.png";

    public void generarAFD() throws IOException {

        StringBuilder dot = new StringBuilder();

        dot.append("digraph AFD_PromptZal {\n");
        dot.append("    rankdir=LR;\n");
        dot.append("    node [shape=circle];\n");

        // Estado inicial
        dot.append("    inicio [shape=point];\n");
        dot.append("    inicio -> q0;\n");

        // Estados de aceptación
        dot.append("    q1 [shape=doublecircle];\n");
        dot.append("    q2 [shape=doublecircle];\n");
        dot.append("    q4 [shape=doublecircle];\n");
        dot.append("    q6 [shape=doublecircle];\n");
        dot.append("    q8 [shape=doublecircle];\n");
        dot.append("    q14 [shape=doublecircle];\n");
        dot.append("    q15 [shape=doublecircle];\n");
        dot.append("    q16 [shape=doublecircle];\n");

        // q0 - Estado inicial
        agregarTransicion(dot, "q0", "q1", "LETRA, _");
        agregarTransicion(dot, "q0", "q2", "DIGITO");
        agregarTransicion(dot, "q0", "q5", "\"");
        agregarTransicion(dot, "q0", "q14", "=");
        agregarTransicion(dot, "q0", "q15", "+");
        agregarTransicion(dot, "q0", "q7", "-");
        agregarTransicion(dot, "q0", "q9", "@");
        agregarTransicion(dot, "q0", "q10", "/");
        agregarTransicion(dot, "q0", "q16", "{, }, (, ), ,");
        agregarTransicion(dot, "q0", "q0","ESPACIO, TAB, SALTO DE LINEA, RETORNO");

        // q1 - Identificador
        agregarTransicion(dot, "q1", "q1", "LETRA, DIGITO, _");

        // q2 - Entero
        agregarTransicion(dot, "q2", "q2", "DIGITO");
        agregarTransicion(dot, "q2", "q3", ".");

        // q3 - Punto decimal pendiente
        agregarTransicion(dot, "q3", "q4", "DIGITO");

        // q4 - Decimal
        agregarTransicion(dot, "q4", "q4", "DIGITO");

        // q5 - Interior de cadena
        agregarTransicion(dot, "q5", "q5","CARACTER EXCEPTO \" Y SALTO DE LINEA");
        agregarTransicion(dot, "q5", "q6", "\"");

        // q7 - Después de '-'
        agregarTransicion(dot, "q7", "q8", ">");

        // q9 - Directiva
        agregarTransicion(dot, "q9", "q9", "LETRA, DIGITO, _");

        // q10 - Después de '/'
        agregarTransicion(dot, "q10", "q11", "/");
        agregarTransicion(dot, "q10", "q12", "*");

        // q11 - Comentario de línea
        agregarTransicion(dot, "q11", "q11", "CUALQUIER CARACTER");
        agregarTransicion(dot, "q11", "q0", "SALTO DE LINEA");

        // q12 - Comentario de bloque
        agregarTransicion(dot, "q12", "q12","CUALQUIER CARACTER EXCEPTO *");
        agregarTransicion(dot, "q12", "q13", "*");

        // q13 - Posible cierre de comentario
        agregarTransicion(dot, "q13", "q0", "/");
        agregarTransicion(dot, "q13", "q13", "*");
        agregarTransicion(dot, "q13", "q12", "OTRO");

        dot.append("}\n");

        // Crear archivo DOT
        File archivo_dot = new File(nombre_dot);

        try (FileWriter escritor = new FileWriter(archivo_dot)) {
            escritor.write(dot.toString());
        }

        // Generar imagen PNG
        generarImagen();

        System.out.println("AFD generado correctamente.");
        System.out.println("Archivo DOT: " + archivo_dot.getAbsolutePath());
        System.out.println("Imagen PNG: " + new File(nombre_png).getAbsolutePath());
    }

    private void agregarTransicion(StringBuilder dot, String origen, String destino, String etiqueta) {

        String etiqueta_escapada = etiqueta.replace("\\", "\\\\").replace("\"", "\\\"");

        dot.append("    ").append(origen).append(" -> ").append(destino).append(" [label=\"").append(etiqueta_escapada).append("\"];\n");
    }

    private void generarImagen() throws IOException {

        ProcessBuilder proceso = new ProcessBuilder("dot","-Tpng",nombre_dot,"-o",nombre_png);

        proceso.inheritIO();

        try {

            Process proceso_ejecutado = proceso.start();

            int resultado = proceso_ejecutado.waitFor();

            if (resultado != 0) {
                throw new IOException("Graphviz no pudo generar la imagen del AFD.");
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new IOException("La generación del AFD fue interrumpida.", e);
        }
    }
}