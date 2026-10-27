package com.mycompany.prueba.lexico;

import com.mycompany.prueba.Token;
import com.mycompany.prueba.Tipo;
import com.mycompany.prueba.ErrorL;

import java.util.ArrayList;
import java.util.List;

%%

%public
%class AnalizadorLexico
%unicode
%line
%column
%type Token
%function siguienteToken

%{
    private int numeroToken = 0;

    private final List<ErrorL> errores = new ArrayList<>();

    /**
     * Crea un token con su lexema y posición.
     * Las filas y columnas se cuentan desde uno.
     */
    private Token crearToken(Tipo tipo, String lexema) {
        return new Token(
            ++numeroToken,
            lexema,
            tipo,
            yyline + 1,
            yycolumn + 1
        );
    }

    /**
     * Registra un error léxico.
     */
    private void registrarError(String lexema, String descripcion) {
        errores.add(
            new ErrorL(
                lexema,
                descripcion,
                yyline + 1,
                yycolumn + 1
            )
        );
    }

    /**
     * Devuelve los errores léxicos encontrados.
     */
    public List<ErrorL> getErrores() {
        return errores;
    }
%}

/* ==========================================
   EXPRESIONES REGULARES
   ========================================== */

Letra = [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ_]
Digito = [0-9]

Identificador = {Letra}({Letra}|{Digito})*

Numero = {Digito}+("."{Digito}+)?

Cadena = \"([^\"\\\r\n]|\\.)*\"

CadenaMalFormada = \"([^\"\\\r\n]|\\.)*

Directiva = "@"{Identificador}

Espacio = [ \t\f\r\n]+

ComentarioLinea = "//"[^\r\n]*

ComentarioBloque = "/*"([^*]|\*+[^*/])*\*+"/"

%%

/* ==========================================
   ESPACIOS Y COMENTARIOS
   ========================================== */

{Espacio} {
    /* Se ignoran los espacios en blanco. */
}

{ComentarioLinea} {
    /* Se ignoran los comentarios de una línea. */
}

{ComentarioBloque} {
    /* Se ignoran los comentarios de varias líneas. */
}


/* ==========================================
   DIRECTIVAS
   ========================================== */

{Directiva} {
    return crearToken(Tipo.DIRECTIVA, yytext());
}


/* ==========================================
   PALABRAS RESERVADAS
   ========================================== */

"AGENTE" {
    return crearToken(Tipo.AGENTE, yytext());
}

"CONTEXTO" | "contexto" {
    return crearToken(Tipo.CONTEXTO, yytext());
}

"VARIABLE" | "variable" {
    return crearToken(Tipo.VARIABLE, yytext());
}

"EJECUTAR" {
    return crearToken(Tipo.EJECUTAR, yytext());
}

"EXPORTAR" {
    return crearToken(Tipo.EXPORTAR, yytext());
}


/* ==========================================
   COMANDOS DE INTELIGENCIA ARTIFICIAL
   ========================================== */

"PREGUNTAR" |
"GENERAR" |
"RESUMIR" |
"ANALIZAR" |
"TRADUCIR" |
"CLASIFICAR" |
"EXTRAER" |
"CODIFICAR" {
    return crearToken(Tipo.COMANDO_IA, yytext());
}


/* ==========================================
   CONECTORES
   ========================================== */

"SOBRE" |
"DESDE" |
"EN" |
"COMO" {
    return crearToken(Tipo.CONECTOR, yytext());
}


/* ==========================================
   FUNCIONES
   ========================================== */

"CARGAR" {
    return crearToken(Tipo.CARGAR, yytext());
}


/* ==========================================
   OPERADORES
   ========================================== */

"->" {
    return crearToken(Tipo.FLECHA, yytext());
}

"=" {
    return crearToken(Tipo.IGUAL, yytext());
}

"+" {
    return crearToken(Tipo.MAS, yytext());
}


/* ==========================================
   LLAVES, PARÉNTESIS Y COMAS
   ========================================== */

"{" {
    return crearToken(Tipo.LLAVE_ABRIR, yytext());
}

"}" {
    return crearToken(Tipo.LLAVE_CERRAR, yytext());
}

"(" {
    return crearToken(Tipo.PARENTESIS_ABRIR, yytext());
}

")" {
    return crearToken(Tipo.PARENTESIS_CERRAR, yytext());
}

"," {
    return crearToken(Tipo.COMA, yytext());
}


/* ==========================================
   CADENAS DE TEXTO
   ========================================== */

{Cadena} {
    return crearToken(Tipo.CADENA, yytext());
}

{CadenaMalFormada} {
    registrarError(
        yytext(),
        "Cadena de texto sin cerrar"
    );
}


/* ==========================================
   NÚMEROS
   ========================================== */

{Numero} {
    return crearToken(Tipo.NUMERO, yytext());
}


/* ==========================================
   IDENTIFICADORES
   ========================================== */

{Identificador} {
    return crearToken(Tipo.ID, yytext());
}


/* ==========================================
   CARACTERES NO RECONOCIDOS
   ========================================== */

[^] {
    registrarError(
        yytext(),
        "Carácter no reconocido"
    );
}


/* ==========================================
   FIN DE LA ENTRADA
   ========================================== */

<<EOF>> {
    return crearToken(Tipo.EOF, "");
}