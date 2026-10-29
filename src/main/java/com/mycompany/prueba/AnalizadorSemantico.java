/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prueba;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AnalizadorSemantico {
    
    private final List<String> errores;

    public AnalizadorSemantico() {
        errores = new ArrayList<>();
    }

    
    public boolean analizar(Nodo raiz) {
        errores.clear();

        if (raiz == null) {
            errores.add("No existe un AST para analizar.");
            return false;
        }

        Nodo nodoAgentes = buscarHijo(raiz, "AGENTES");
        Nodo nodoEjecuciones = buscarHijo(raiz, "EJECUCIONES");
        Nodo nodoExportaciones = buscarHijo(raiz, "EXPORTACIONES");

        Set<String> agentesDeclarados = new LinkedHashSet<>();

        //comprobar las declaraciones de agentes.
        if (nodoAgentes != null) {
            for (Nodo agente : nodoAgentes.getHijos()) {
                if (!"AGENTE".equals(agente.getTipo())) {
                    continue;
                }

                String nombre = agente.getValor();

                if (nombre == null || nombre.trim().isEmpty()) {
                    registrarError(
                            agente,
                            "Se encontró un agente sin nombre."
                    );
                    continue;
                }

                if (!agentesDeclarados.add(nombre)) {
                    registrarError(
                            agente,
                            "El agente '" + nombre
                            + "' está declarado más de una vez."
                    );
                }
            }
        }

        //analizar las variables y los comandos.
        if (nodoAgentes != null) {
            for (Nodo agente : nodoAgentes.getHijos()) {
                if ("AGENTE".equals(agente.getTipo())) {
                    analizarAgente(agente);
                }
            }
        }

        //verificar las ejecuciones.
        if (nodoEjecuciones != null) {
            for (Nodo ejecucion : nodoEjecuciones.getHijos()) {
                if (!"EJECUTAR".equals(ejecucion.getTipo())) {
                    continue;
                }

                String nombre = ejecucion.getValor();

                if (nombre == null
                        || !agentesDeclarados.contains(nombre)) {
                    registrarError(
                            ejecucion,
                            "Se intenta ejecutar el agente '"
                            + nombre + "', pero no está declarado."
                    );
                }
            }
        }

        // Cuarta pasada: verificar las exportaciones.
        if (nodoExportaciones != null) {
            for (Nodo exportacion : nodoExportaciones.getHijos()) {
                if (!"EXPORTAR".equals(exportacion.getTipo())) {
                    continue;
                }

                String nombre = exportacion.getValor();

                if (nombre == null
                        || !resultadoProducido(raiz, nombre)) {
                    registrarError(
                            exportacion,
                            "Se intenta exportar el resultado '"
                            + nombre + "', pero ningún comando lo produce."
                    );
                }
            }
        }

        return errores.isEmpty();
    }

    
    private void analizarAgente(Nodo agente) {
        Nodo nodoVariables = buscarHijo(agente, "VARIABLES");
        Nodo nodoComandos = buscarHijo(agente, "COMANDOS");

        Set<String> variables = new HashSet<>();
        Set<String> resultados = new HashSet<>();

        // Registrar las variables del agente y detectar duplicados.
        if (nodoVariables != null) {
            for (Nodo variable : nodoVariables.getHijos()) {
                if (!"VARIABLE".equals(variable.getTipo())) {
                    continue;
                }

                String nombre = variable.getValor();

                if (nombre == null || nombre.trim().isEmpty()) {
                    registrarError(
                            variable,
                            "Se encontró una variable sin nombre."
                    );
                    continue;
                }

                if (!variables.add(nombre)) {
                    registrarError(
                            variable,
                            "La variable '" + nombre
                            + "' está declarada más de una vez."
                    );
                }
            }
        }

        if (nodoComandos == null) {
            return;
        }

        
        for (Nodo comando : nodoComandos.getHijos()) {
            if (!"COMANDO".equals(comando.getTipo())) {
                continue;
            }

            // Revisar identificadores utilizados por el comando.
            comprobarReferencias(
                    comando,
                    comando,
                    variables,
                    resultados
            );

            // Registrar el resultado que produce el comando.
            Nodo resultado = buscarHijo(comando, "RESULTADO");

            if (resultado != null) {
                String nombreResultado = resultado.getValor();

                if (nombreResultado == null
                        || nombreResultado.trim().isEmpty()) {
                    registrarError(
                            resultado,
                            "El comando produce un resultado sin nombre."
                    );
                } else if (!resultados.add(nombreResultado)) {
                    registrarError(
                            resultado,
                            "El resultado '" + nombreResultado
                            + "' se produce más de una vez."
                    );
                }
            }
        }
    }

    
    private void comprobarReferencias(
            Nodo nodo,
            Nodo comando,
            Set<String> variables,
            Set<String> resultados
    ) {
        if (nodo == null) {
            return;
        }

        if (nodo != comando && "RESULTADO".equals(nodo.getTipo())) {
            return;
        }

        if ("ID".equals(nodo.getTipo())) {
            String nombre = nodo.getValor();

            if (nombre != null
                    && !variables.contains(nombre)
                    && !resultados.contains(nombre)) {
                registrarError(
                        nodo,
                        "El identificador '" + nombre
                        + "' se utiliza sin haber sido declarado "
                        + "ni producido previamente."
                );
            }
        }

        for (Nodo hijo : nodo.getHijos()) {
            comprobarReferencias(
                    hijo,
                    comando,
                    variables,
                    resultados
            );
        }
    }

    
    private boolean resultadoProducido(
            Nodo raiz,
            String nombreBuscado
    ) {
        if (nombreBuscado == null) {
            return false;
        }

        Nodo nodoAgentes = buscarHijo(raiz, "AGENTES");

        if (nodoAgentes == null) {
            return false;
        }

        for (Nodo agente : nodoAgentes.getHijos()) {
            if (!"AGENTE".equals(agente.getTipo())) {
                continue;
            }

            Nodo comandos = buscarHijo(agente, "COMANDOS");

            if (comandos == null) {
                continue;
            }

            for (Nodo comando : comandos.getHijos()) {
                Nodo resultado = buscarHijo(comando, "RESULTADO");

                if (resultado != null
                        && nombreBuscado.equals(resultado.getValor())) {
                    return true;
                }
            }
        }

        return false;
    }

    
    private Nodo buscarHijo(Nodo padre, String tipo) {
        if (padre == null) {
            return null;
        }

        for (Nodo hijo : padre.getHijos()) {
            if (tipo.equals(hijo.getTipo())) {
                return hijo;
            }
        }

        return null;
    }

    
    private void registrarError(Nodo nodo, String mensaje) {
        errores.add(
                "Error semántico en línea "
                + nodo.getFila()
                + ", columna "
                + nodo.getColumna()
                + ": "
                + mensaje
        );
    }

    
    public List<String> getErrores() {
        return new ArrayList<>(errores);
    }

    
    public String imprimirErrores() {
        if (errores.isEmpty()) {
            return "No se encontraron errores semánticos.";
        }

        StringBuilder resultado = new StringBuilder();

        resultado.append("ERRORES SEMÁNTICOS\n");

        for (String error : errores) {
            resultado.append(error)
                    .append(System.lineSeparator());
        }

        resultado.append("Total de errores: ")
                .append(errores.size());

        return resultado.toString();
    }
    
}
