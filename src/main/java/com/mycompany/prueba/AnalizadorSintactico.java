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
import java.util.List;


public class AnalizadorSintactico {
    
    private final List<Token> tokens;
    private final List<String> errores = new ArrayList<>();
    private final List<ErrorSintactico> detallesErrores = new ArrayList<>();
    private int posicion = 0;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Inicia el análisis sintáctico.
    // Devuelve true si el programa cumple la gramática.
    public boolean analizar() {
        errores.clear();
        detallesErrores.clear();
        posicion = 0;

        try {
         programa();
            return true;

        } catch (ErrorParser ex) {

            errores.add(ex.getMessage());
            detallesErrores.add(ex.getError());

            return false;
        }
    }

    // <programa> -> <directivas> <agentes>
    //               <ejecuciones> <exportar> EOF
    private void programa() {
        directivas();
        agentes();
        ejecuciones();
        exportar();

        if (actual() != null) {
            lanzarError("No se esperaba este elemento después de EXPORTAR");
        }
    }

    // <directivas> -> DIRECTIVA CADENA <directivas> | ε
    private void directivas() {
        while (coincide(Tipo.DIRECTIVA)) {
            avanzar();
            esperar(Tipo.CADENA, "una cadena después de la directiva");
        }
    }

    // <agentes> -> <agente> <mas_agentes>
    private void agentes() {
        agente();

        while (coincide(Tipo.AGENTE)) {
            agente();
        }
    }

    // <agente> -> AGENTE ID LLAVE_A <contexto>
    //             <variables> <comandos> LLAVE_C
    private void agente() {
        esperar(Tipo.AGENTE, "AGENTE");
        esperar(Tipo.ID, "el nombre del agente");
        esperar(Tipo.LLAVE_ABRIR, "la llave de apertura '{'");

        contexto();
        variables();
        comandos();

        esperar(Tipo.LLAVE_CERRAR, "la llave de cierre '}'");
    }

    // <contexto> -> CONTEXTO IGUAL CADENA
    private void contexto() {
        esperar(Tipo.CONTEXTO, "CONTEXTO");
        esperar(Tipo.IGUAL, "el operador '='");
        esperar(Tipo.CADENA, "una cadena para el contexto");
    }

    // <variables> -> VARIABLE ID IGUAL <expresion> <variables> | ε
    private void variables() {
        while (coincide(Tipo.VARIABLE)) {
            avanzar();
            esperar(Tipo.ID, "el nombre de la variable");
            esperar(Tipo.IGUAL, "el operador '='");
            expresion();
        }
    }

    // <comandos> -> <comando> <mas_comandos>
    private void comandos() {
        comando();

        while (coincide(Tipo.COMANDO_IA)) {
            comando();
        }
    }

    // <comando> -> COMANDO_IA <expresion>
    //              <conectores> FLECHA ID
    private void comando() {
        esperar(Tipo.COMANDO_IA, "un comando de IA");

        expresion();
        conectores();

        esperar(Tipo.FLECHA, "el operador de resultado '->'");
        esperar(Tipo.ID, "el nombre de la variable de resultado");
    }

    // <conectores> -> CONECTOR <expresion> <conectores> | ε
    private void conectores() {
        while (coincide(Tipo.CONECTOR)) {
            avanzar();
            expresion();
        }
    }

    // <expresion> -> <termino> <mas_terminos>
    private void expresion() {
        termino();

        while (coincide(Tipo.MAS)) {
            avanzar();
            termino();
        }
    }

    // <termino> -> CADENA | ID | NUMERO <unidad> | <cargar>
    private void termino() {
        if (coincide(Tipo.CADENA)) {
            avanzar();
        } else if (coincide(Tipo.ID)) {
            avanzar();
        } else if (coincide(Tipo.NUMERO)) {
            avanzar();
            unidad();
        } else if (coincide(Tipo.CARGAR)) {
            cargar();
        } else {
            lanzarError(
                "Se esperaba una cadena, un identificador, un número o CARGAR"
            );
        }
    }

    // <unidad> -> ID | ε
    private void unidad() {
        if (coincide(Tipo.ID)) {
            avanzar();
        }
    }

    // <cargar> -> CARGAR PAR_A CADENA PAR_C
    private void cargar() {
        esperar(Tipo.CARGAR, "CARGAR");
        esperar(Tipo.PARENTESIS_ABRIR, "el paréntesis de apertura '('");
        esperar(Tipo.CADENA, "el nombre del archivo entre comillas");
        esperar(Tipo.PARENTESIS_CERRAR, "el paréntesis de cierre ')'");
    }

    // <ejecuciones> -> EJECUTAR ID <mas_ejecuciones>
    private void ejecuciones() {
        esperar(Tipo.EJECUTAR, "EJECUTAR");
        esperar(Tipo.ID, "el nombre del agente que se ejecutará");

        while (coincide(Tipo.EJECUTAR)) {
            avanzar();
            esperar(Tipo.ID, "el nombre del agente que se ejecutará");
        }
    }

    // <exportar> -> EXPORTAR ID <mas_ids>
    private void exportar() {
        esperar(Tipo.EXPORTAR, "EXPORTAR");
        esperar(Tipo.ID, "el nombre del resultado que se exportará");

        while (coincide(Tipo.COMA)) {
            avanzar();
            esperar(Tipo.ID, "un identificador después de la coma");
        }
    }

    // Comprueba si el token actual tiene el tipo indicado.
    private boolean coincide(Tipo tipo) {
        Token token = actual();
        return token != null && token.getTipo() == tipo;
    }

    // Devuelve el token actual sin consumirlo.
    private Token actual() {
        if (posicion >= tokens.size()) {
            return null;
        }

        return tokens.get(posicion);
    }

    // Consume el token actual.
    private void avanzar() {
        if (posicion < tokens.size()) {
            posicion++;
        }
    }

    // Comprueba y consume el tipo de token esperado.
    private void esperar(Tipo tipo, String descripcion) {
        if (coincide(tipo)) {
            avanzar();
        } else {
            lanzarError("Se esperaba " + descripcion);
        }
    }

    // Genera un error con la ubicación del token actual.
    private void lanzarError(String mensaje) {

    Token token = actual();

    if (token != null) {

        String descripcion =
        mensaje
        + ". Se encontró '" + token.getLexema() + "'"
        + " (" + token.getTipo() + ").";

        throw new ErrorParser(
                new ErrorSintactico(
                        descripcion,
                        token.getFila(),
                        token.getColumna()
                )
        );
    }

    int fila = 1;
    int columna = 1;

    if (!tokens.isEmpty()) {

        Token ultimo = tokens.get(tokens.size() - 1);

        fila = ultimo.getFila();
        columna = ultimo.getColumna()
                + ultimo.getLexema().length();
    }

    String descripcion =
        "Fin del archivo: " + mensaje + ".";

    throw new ErrorParser(
            new ErrorSintactico(
                    descripcion,
                    fila,
                    columna
            )
    );
}

    // Devuelve los errores encontrados.
    public List<String> getErrores() {
        return new ArrayList<>(errores);
    }
    
    public List<ErrorSintactico> getDetallesErrores() {
    return new ArrayList<>(detallesErrores);
}

    // Excepción interna utilizada para detener el análisis
    // cuando se encuentra un error sintáctico.
    private static class ErrorParser extends RuntimeException {

    private final ErrorSintactico error;

    public ErrorParser(ErrorSintactico error) {
        super(error.getMensaje());
        this.error = error;
    }

    public ErrorSintactico getError() {
        return error;
    }
}
    
}
