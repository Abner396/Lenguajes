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
    private Nodo raizAST;
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
        raizAST = null;

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

    Token inicio = actual();

    raizAST = new Nodo(
            "PROGRAMA",
            "PromptZal",
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    Nodo nodoDirectivas = directivas();

    if (nodoDirectivas != null) {
        raizAST.agregarHijo(nodoDirectivas);
    }

    Nodo nodoAgentes = agentes();
    raizAST.agregarHijo(nodoAgentes);

    Nodo nodoEjecuciones = ejecuciones();
    raizAST.agregarHijo(nodoEjecuciones);

    Nodo nodoExportar = exportar();
    raizAST.agregarHijo(nodoExportar);

    if (actual() != null) {
        lanzarError("No se esperaba este elemento después de EXPORTAR");
    }
}

    // <directivas> -> DIRECTIVA CADENA <directivas> | ε
    private Nodo directivas() {

    Token inicio = actual();

    Nodo nodoDirectivas = new Nodo(
            "DIRECTIVAS",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    while (coincide(Tipo.DIRECTIVA)) {

        Token directiva = actual();
        avanzar();

        Token valor = actual();

        esperar(
                Tipo.CADENA,
                "una cadena después de la directiva"
        );

        Nodo nodoDirectiva = new Nodo(
                "DIRECTIVA",
                directiva.getLexema(),
                directiva.getFila(),
                directiva.getColumna()
        );

        nodoDirectiva.agregarHijo(
                new Nodo(
                        "CADENA",
                        valor.getLexema(),
                        valor.getFila(),
                        valor.getColumna()
                )
        );

        nodoDirectivas.agregarHijo(nodoDirectiva);
    }

    return nodoDirectivas;
}

    // <agentes> -> <agente> <mas_agentes>
    private Nodo agentes() {

    Token inicio = actual();

    Nodo nodoAgentes = new Nodo(
            "AGENTES",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    nodoAgentes.agregarHijo(agente());

    while (coincide(Tipo.AGENTE)) {
        nodoAgentes.agregarHijo(agente());
    }

    return nodoAgentes;
}

    // <agente> -> AGENTE ID LLAVE_A <contexto>
    //             <variables> <comandos> LLAVE_C
    private Nodo agente() {

    Token tokenAgente = actual();

    esperar(Tipo.AGENTE, "AGENTE");

    Token nombre = actual();
    esperar(Tipo.ID, "el nombre del agente");

    Nodo nodoAgente = new Nodo(
            "AGENTE",
            nombre.getLexema(),
            tokenAgente.getFila(),
            tokenAgente.getColumna()
    );

    esperar(Tipo.LLAVE_ABRIR, "la llave de apertura '{'");

    Nodo nodoContexto = contexto();
    nodoAgente.agregarHijo(nodoContexto);

    Nodo nodoVariables = variables();
    nodoAgente.agregarHijo(nodoVariables);

    Nodo nodoComandos = comandos();
    nodoAgente.agregarHijo(nodoComandos);

    esperar(Tipo.LLAVE_CERRAR, "la llave de cierre '}'");

    return nodoAgente;
}

    // <contexto> -> CONTEXTO IGUAL CADENA
    private Nodo contexto() {

    Token inicio = actual();

    esperar(Tipo.CONTEXTO, "CONTEXTO");
    esperar(Tipo.IGUAL, "el operador '='");

    Token valor = actual();

    esperar(Tipo.CADENA, "una cadena para el contexto");

    Nodo nodoContexto = new Nodo(
            "CONTEXTO",
            null,
            inicio.getFila(),
            inicio.getColumna()
    );

    nodoContexto.agregarHijo(
            new Nodo(
                    "CADENA",
                    valor.getLexema(),
                    valor.getFila(),
                    valor.getColumna()
            )
    );

    return nodoContexto;
}

    // <variables> -> VARIABLE ID IGUAL <expresion> <variables> | ε
    private Nodo variables() {

    Token inicio = actual();

    Nodo nodoVariables = new Nodo(
            "VARIABLES",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    while (coincide(Tipo.VARIABLE)) {

        Token tokenVariable = actual();
        esperar(Tipo.VARIABLE, "VARIABLE");

        Token nombre = actual();
        esperar(Tipo.ID, "el nombre de la variable");

        esperar(Tipo.IGUAL, "el operador '='");

        Nodo nodoVariable = new Nodo(
                "VARIABLE",
                nombre.getLexema(),
                tokenVariable.getFila(),
                tokenVariable.getColumna()
        );

        nodoVariable.agregarHijo(expresion());

        nodoVariables.agregarHijo(nodoVariable);
    }

    return nodoVariables;
}

    // <comandos> -> <comando> <mas_comandos>
    
    private Nodo comandos() {

    Token inicio = actual();

    Nodo nodoComandos = new Nodo(
            "COMANDOS",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

        while (coincide(Tipo.COMANDO_IA)) {
        nodoComandos.agregarHijo(comando());
        }

        return nodoComandos;
    }


    // <comando> -> COMANDO_IA <expresion>
    //              <conectores> FLECHA ID
    private Nodo comando() {

    Token inicio = actual();

    esperar(Tipo.COMANDO_IA, "un comando de IA");

    Nodo nodoComando = new Nodo(
            "COMANDO",
            inicio.getLexema(),
            inicio.getFila(),
            inicio.getColumna()
    );

    nodoComando.agregarHijo(expresion());

    Nodo nodoConectores = conectores();
    nodoComando.agregarHijo(nodoConectores);

    esperar(Tipo.FLECHA, "el operador de resultado '->'");

    Token resultado = actual();

    esperar(
            Tipo.ID,
            "el nombre de la variable de resultado"
    );

    nodoComando.agregarHijo(
            new Nodo(
                    "RESULTADO",
                    resultado.getLexema(),
                    resultado.getFila(),
                    resultado.getColumna()
            )
    );

    return nodoComando;
}

    // <conectores> -> CONECTOR <expresion> <conectores> | ε
    private Nodo conectores() {

    Token inicio = actual();

    Nodo nodoConectores = new Nodo(
            "CONECTORES",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    while (coincide(Tipo.CONECTOR)) {

        Token conector = actual();
        avanzar();

        Nodo nodoConector = new Nodo(
                "CONECTOR",
                conector.getLexema(),
                conector.getFila(),
                conector.getColumna()
        );

        nodoConector.agregarHijo(expresion());

        nodoConectores.agregarHijo(nodoConector);
    }

    return nodoConectores;
}

    // <expresion> -> <termino> <mas_terminos>
    private Nodo expresion() {

    Nodo nodoExpresion = new Nodo(
            "EXPRESION",
            null,
            actual() != null ? actual().getFila() : 1,
            actual() != null ? actual().getColumna() : 1
    );

    nodoExpresion.agregarHijo(termino());

    while (coincide(Tipo.MAS)) {

        Token operador = actual();
        avanzar();

        Nodo nodoSuma = new Nodo(
                "SUMA",
                operador.getLexema(),
                operador.getFila(),
                operador.getColumna()
        );

        nodoSuma.agregarHijo(termino());

        nodoExpresion.agregarHijo(nodoSuma);
    }

    return nodoExpresion;
}

    // <termino> -> CADENA | ID | NUMERO <unidad> | <cargar>
    
    private Nodo termino() {

    Token token = actual();

    if (coincide(Tipo.CADENA)) {

        avanzar();

        return new Nodo(
                "CADENA",
                token.getLexema(),
                token.getFila(),
                token.getColumna()
        );

    } else if (coincide(Tipo.ID)) {

        avanzar();

        return new Nodo(
                "ID",
                token.getLexema(),
                token.getFila(),
                token.getColumna()
        );

    } else if (coincide(Tipo.NUMERO)
            || coincide(Tipo.ENTERO)
            || coincide(Tipo.DECIMAL)) {

        avanzar();

        Nodo nodo = new Nodo(
                "NUMERO",
                token.getLexema(),
                token.getFila(),
                token.getColumna()
        );

        if (coincide(Tipo.ID)) {

            Token unidad = actual();

            nodo.agregarHijo(
                    new Nodo(
                            "UNIDAD",
                            unidad.getLexema(),
                            unidad.getFila(),
                            unidad.getColumna()
                    )
            );

            avanzar();
        }

        return nodo;

    } else if (coincide(Tipo.CARGAR)) {

        return cargar();

    } else {

        lanzarError(
                "Se esperaba una cadena, un identificador, "
                + "un número o CARGAR"
        );

        return null;
    }
    }


    // <unidad> -> ID | ε
    private Nodo unidad() {

    Token token = actual();

    if (coincide(Tipo.ID)) {

        avanzar();

        return new Nodo(
                "UNIDAD",
                token.getLexema(),
                token.getFila(),
                token.getColumna()
        );
    }

    return null;
}
    // <cargar> -> CARGAR PAR_A CADENA PAR_C
    private Nodo cargar() {

    Token inicio = actual();

    esperar(Tipo.CARGAR, "CARGAR");

    esperar(
            Tipo.PARENTESIS_ABRIR,
            "el paréntesis de apertura '('"
    );

    Token archivo = actual();

    esperar(
            Tipo.CADENA,
            "el nombre del archivo entre comillas"
    );

    esperar(
            Tipo.PARENTESIS_CERRAR,
            "el paréntesis de cierre ')'"
    );

    Nodo nodoCargar = new Nodo(
            "CARGAR",
            null,
            inicio.getFila(),
            inicio.getColumna()
    );

    nodoCargar.agregarHijo(
            new Nodo(
                    "ARCHIVO",
                    archivo.getLexema(),
                    archivo.getFila(),
                    archivo.getColumna()
            )
    );

    return nodoCargar;
}

    // <ejecuciones> -> EJECUTAR ID <mas_ejecuciones>
    private Nodo ejecuciones() {

    Token inicio = actual();

    Nodo nodoEjecuciones = new Nodo(
            "EJECUCIONES",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    esperar(Tipo.EJECUTAR, "EJECUTAR");

    Token nombre = actual();

    esperar(
            Tipo.ID,
            "el nombre del agente que se ejecutará"
    );

    nodoEjecuciones.agregarHijo(
            new Nodo(
                    "EJECUTAR",
                    nombre.getLexema(),
                    nombre.getFila(),
                    nombre.getColumna()
            )
    );

    while (coincide(Tipo.EJECUTAR)) {

        avanzar();

        Token siguiente = actual();

        esperar(
                Tipo.ID,
                "el nombre del agente que se ejecutará"
        );

        nodoEjecuciones.agregarHijo(
                new Nodo(
                        "EJECUTAR",
                        siguiente.getLexema(),
                        siguiente.getFila(),
                        siguiente.getColumna()
                )
        );
    }

    return nodoEjecuciones;
}

    
    private Nodo exportar() {

    Token inicio = actual();

    Nodo nodoExportar = new Nodo(
            "EXPORTACIONES",
            null,
            inicio != null ? inicio.getFila() : 1,
            inicio != null ? inicio.getColumna() : 1
    );

    esperar(Tipo.EXPORTAR, "EXPORTAR");

    Token nombre = actual();

    esperar(
            Tipo.ID,
            "el nombre del resultado que se exportará"
    );

    nodoExportar.agregarHijo(
            new Nodo(
                    "EXPORTAR",
                    nombre.getLexema(),
                    nombre.getFila(),
                    nombre.getColumna()
            )
    );

    while (coincide(Tipo.COMA)) {

        avanzar();

        Token siguiente = actual();

        esperar(
                Tipo.ID,
                "un identificador después de la coma"
        );

        nodoExportar.agregarHijo(
                new Nodo(
                        "EXPORTAR",
                        siguiente.getLexema(),
                        siguiente.getFila(),
                        siguiente.getColumna()
                )
        );
    }

    return nodoExportar;
}

    // Comprueba si el token actual tiene el tipo indicado.
    private boolean coincide(Tipo tipo) {
        Token token = actual();
        return token != null && token.getTipo() == tipo;
    }

    // Devuelve el token actual
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

    // Comprueba  el tipo de token esperado.
    private void esperar(Tipo tipo, String descripcion) {
        if (coincide(tipo)) {
            avanzar();
        } else {
            lanzarError("Se esperaba " + descripcion);
        }
    }

    
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
    
     public Nodo getRaizAST() {
        return raizAST;
    }
    
}
