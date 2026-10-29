package com.mycompany.prueba;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

public class AST {

    private final String nombre_dot = "ast_promptzal.dot";
    private final String nombre_png = "ast_promptzal.png";

    private final Map<Nodo, String> identificadores = new IdentityHashMap<>();
    private int contador_nodos = 0;

    public void generarAST(Nodo raiz) throws IOException {

        if (raiz == null) {
            throw new IllegalArgumentException(
                    "No se puede generar el AST porque la raíz es null."
            );
        }

        identificadores.clear();
        contador_nodos = 0;

        StringBuilder dot = new StringBuilder();

        dot.append("digraph AST_PromptZal {\n");
        dot.append("    graph [ordering=\"out\", ranksep=0.6, nodesep=0.35];\n");
        dot.append("    node [shape=box, style=\"rounded,filled\", ")
           .append("fillcolor=\"#EAF2F8\", color=\"#2874A6\", ")
           .append("fontname=\"Arial\", fontsize=10];\n");
        dot.append("    edge [color=\"#566573\", arrowsize=0.7];\n");
        dot.append("    rankdir=TB;\n\n");

        agregarNodo(dot, raiz);

        dot.append("}\n");

        File archivo_dot = new File(nombre_dot);

        try (FileWriter escritor = new FileWriter(archivo_dot)) {
            escritor.write(dot.toString());
        }

        generarImagen();

        System.out.println("AST generado correctamente.");
        System.out.println("Archivo DOT: " + archivo_dot.getAbsolutePath());
        System.out.println("Imagen PNG: " + new File(nombre_png).getAbsolutePath());
    }

    private String agregarNodo(StringBuilder dot, Nodo nodo) {

        String id = "n" + contador_nodos++;
        identificadores.put(nodo, id);

        String etiqueta = nodo.getTipo();

        if (nodo.getValor() != null && !nodo.getValor().isEmpty()) {
            etiqueta += "\\n" + nodo.getValor();
        }

        etiqueta += "\\nLínea: " + nodo.getFila()
                + " | Columna: " + nodo.getColumna();

        dot.append("    ").append(id)
           .append(" [label=\"")
           .append(escapar(etiqueta))
           .append("\"];\n");

        for (Nodo hijo : nodo.getHijos()) {
            String idHijo = agregarNodo(dot, hijo);

            dot.append("    ")
               .append(id)
               .append(" -> ")
               .append(idHijo)
               .append(";\n");
        }

        return id;
    }

    private String escapar(String texto) {

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private void generarImagen() throws IOException {

        ProcessBuilder proceso = new ProcessBuilder(
                "dot",
                "-Tpng",
                nombre_dot,
                "-o",
                nombre_png
        );

        proceso.inheritIO();

        try {
            Process proceso_ejecutado = proceso.start();
            int resultado = proceso_ejecutado.waitFor();

            if (resultado != 0) {
                throw new IOException(
                        "Graphviz no pudo generar la imagen del AST."
                );
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IOException(
                    "La generación del AST fue interrumpida.",
                    e
            );
        }
    }
}