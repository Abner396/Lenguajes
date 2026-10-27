package com.mycompany.prueba;

import com.mycompany.prueba.lexico.AnalizadorLexico;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PruebaSintactica {

    public static void main(String[] args) throws IOException {

        String codigo =
                "@modelo \"claude-sonnet-4-6\"\n"
              + "@rol \"analista de datos\"\n"
              + "@formato \"markdown\"\n"
              + "\n"
              + "// Agente que prepara el analisis de un archivo de ventas\n"
              + "AGENTE analista {\n"
              + "    contexto = \"Eres un analista de datos experto\"\n"
              + "    variable ventas = CARGAR(\"ventas.csv\")\n"
              + "\n"
              + "    PREGUNTAR \"Cuales son las 3 tendencias principales?\" "
              + "SOBRE ventas -> tendencias\n"
              + "    RESUMIR tendencias EN 100 palabras -> resumen\n"
              + "}\n"
              + "EJECUTAR analista\n"
              + "EXPORTAR resumen";

        AnalizadorLexico lexer =
                new AnalizadorLexico(new StringReader(codigo));

        List<Token> tokens = new ArrayList<>();
        Token token;

        while ((token = lexer.siguienteToken()) != null) {
            if (token.getTipo() == Tipo.EOF) {
                break;
            }

            tokens.add(token);
        }

        if (!lexer.getErrores().isEmpty()) {
            System.out.println("ERRORES LEXICOS:");

            lexer.getErrores().forEach(System.out::println);

            System.out.println(
                    "No se ejecutó el análisis sintáctico porque "
                    + "existen errores léxicos."
            );

            return;
        }

        AnalizadorSintactico parser =
                new AnalizadorSintactico(tokens);

        boolean correcto = parser.analizar();

        if (correcto) {
            System.out.println(
                    "ANALISIS SINTACTICO CORRECTO"
            );
            System.out.println(
                    "El programa cumple la gramática de PromptZal."
            );
        } else {
            System.out.println(
                    "ERRORES SINTACTICOS:"
            );

            parser.getErrores().forEach(System.out::println);
        }
    }
}