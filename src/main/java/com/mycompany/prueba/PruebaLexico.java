package com.mycompany.prueba;

import com.mycompany.prueba.lexico.AnalizadorLexico;
import java.io.StringReader;

public class PruebaLexico {

    public static void main(String[] args) throws Exception {

        String entrada = "nombre + agente1";

        AnalizadorLexico analizador =
                new AnalizadorLexico(new StringReader(entrada));

        Token token;

        System.out.println("TOKENS RECONOCIDOS");
        System.out.println("--------------------------------");

        do {
            token = analizador.siguienteToken();

            System.out.println(
                    token.getNumero()
                    + " | "
                    + token.getLexema()
                    + " | "
                    + token.getTipo()
                    + " | Fila: "
                    + token.getFila()
                    + " | Columna: "
                    + token.getColumna()
            );

        } while (token.getTipo() != Tipo.EOF);
    }
}