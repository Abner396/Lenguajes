package com.mycompany.prueba;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Reporte {

    private List<Token> tokens;
    private List<Error> errores;

    public Reporte(List<Token> tokens, List<Error> errores) {

        this.tokens = tokens;
        this.errores = errores;
    }

    public void generarReporteTokens() throws IOException {

        FileWriter archivo = new FileWriter("reporte_tokens.html");

        archivo.write("<!DOCTYPE html>\n");
        archivo.write("<html>\n");

        archivo.write("<head>\n");
        archivo.write("<meta charset='UTF-8'>\n");
        archivo.write("<title>Reporte de Tokens</title>\n");
        archivo.write("</head>\n");

        archivo.write("<body>\n");

        archivo.write("<h1>Reporte de Tokens</h1>\n");

        archivo.write("<table border='1'>\n");

        archivo.write("<tr>\n");
        archivo.write("<th>No.</th>\n");
        archivo.write("<th>Lexema</th>\n");
        archivo.write("<th>Tipo</th>\n");
        archivo.write("<th>Fila</th>\n");
        archivo.write("<th>Columna</th>\n");
        archivo.write("</tr>\n");

        for (Token token : tokens) {

            archivo.write("<tr>\n");

            archivo.write("<td>"
                    + token.getNumero()
                    + "</td>\n");

            archivo.write("<td>"
                    + token.getLexema()
                    + "</td>\n");

            archivo.write("<td>"
                    + token.getTipo()
                    + "</td>\n");

            archivo.write("<td>"
                    + token.getFila()
                    + "</td>\n");

            archivo.write("<td>"
                    + token.getColumna()
                    + "</td>\n");

            archivo.write("</tr>\n");
        }

        archivo.write("</table>\n");

        archivo.write("</body>\n");
        archivo.write("</html>\n");

        archivo.close();

        System.out.println( "El report ha sido generado :) por fin");
    }
    
    public void generarReporteErrores() throws IOException {

    FileWriter archivo = new FileWriter("reporte_errores.html");

    archivo.write("<!DOCTYPE html>\n");
    archivo.write("<html>\n");

    archivo.write("<head>\n");
    archivo.write("<meta charset='UTF-8'>\n");
    archivo.write("<title>Reporte de Errores</title>\n");
    archivo.write("</head>\n");

    archivo.write("<body>\n");

    archivo.write("<h1>Reporte de Errores Léxicos</h1>\n");

    if (errores.isEmpty()) {

        archivo.write("<h2>No se encontraron errores léxicos.</h2>\n");

    } else {

        archivo.write("<table border='1'>\n");

        archivo.write("<tr>\n");
        archivo.write("<th>Lexema</th>\n");
        archivo.write("<th>Descripción</th>\n");
        archivo.write("<th>Fila</th>\n");
        archivo.write("<th>Columna</th>\n");
        archivo.write("</tr>\n");

        for (Error error : errores) {

            archivo.write("<tr>\n");

            archivo.write("<td>"
                    + error.getLexema()
                    + "</td>\n");

            archivo.write("<td>"
                    + error.getTipo()
                    + "</td>\n");

            archivo.write("<td>"
                    + error.getFila()
                    + "</td>\n");

            archivo.write("<td>"
                    + error.getColumna()
                    + "</td>\n");

            archivo.write("</tr>\n");
        }

        archivo.write("</table>\n");
    }

    archivo.write("</body>\n");
    archivo.write("</html>\n");

    archivo.close();

    System.out.println("El reporte de errores ha sido generado :)");
}
}