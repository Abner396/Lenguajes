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
        archivo.write("<html lang='es'>\n");

        archivo.write("<head>\n");
        archivo.write("<meta charset='UTF-8'>\n");
        archivo.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
        archivo.write("<title>Reporte de Tokens</title>\n");

        archivo.write("<style>\n");

        archivo.write("body {\n");
        archivo.write("    font-family: Arial, sans-serif;\n");
        archivo.write("    background-color: #f4f6f8;\n");
        archivo.write("    margin: 40px;\n");
        archivo.write("}\n");

        archivo.write("h1 {\n");
        archivo.write("    text-align: center;\n");
        archivo.write("    color: #2c3e50;\n");
        archivo.write("}\n");

        archivo.write("table {\n");
        archivo.write("    width: 100%;\n");
        archivo.write("    border-collapse: collapse;\n");
        archivo.write("    background-color: white;\n");
        archivo.write("    margin-top: 25px;\n");
        archivo.write("}\n");

        archivo.write("th {\n");
        archivo.write("    background-color: #2c3e50;\n");
        archivo.write("    color: white;\n");
        archivo.write("    padding: 12px;\n");
        archivo.write("    border: 1px solid #ddd;\n");
        archivo.write("}\n");

        archivo.write("td {\n");
        archivo.write("    padding: 10px;\n");
        archivo.write("    border: 1px solid #ddd;\n");
        archivo.write("    text-align: center;\n");
        archivo.write("}\n");

        archivo.write(".lexema {\n");
        archivo.write("    text-align: left;\n");
        archivo.write("    font-family: monospace;\n");
        archivo.write("    font-weight: bold;\n");
        archivo.write("}\n");

        
          //Colores para los tokens
        archivo.write(".directiva {\n");
        archivo.write("    background-color: #aed6f1;\n");
        archivo.write("    color: #154360;\n");
        archivo.write("}\n");

        archivo.write(".reservada {\n");
        archivo.write("    background-color: #d2b4de;\n");
        archivo.write("    color: #512e5f;\n");
        archivo.write("}\n");

        archivo.write(".comando {\n");
        archivo.write("    background-color: #a9dfbf;\n");
        archivo.write("    color: #145a32;\n");
        archivo.write("}\n");

        archivo.write(".conector {\n");
        archivo.write("    background-color: #f8c471;\n");
        archivo.write("    color: #784212;\n");
        archivo.write("}\n");

        archivo.write(".identificador {\n");
        archivo.write("    background-color: #d5dbdb;\n");
        archivo.write("    color: #34495e;\n");
        archivo.write("}\n");

        archivo.write(".cadena {\n");
        archivo.write("    background-color: #a9cce3;\n");
        archivo.write("    color: #1b4f72;\n");
        archivo.write("}\n");

        archivo.write(".entero {\n");
        archivo.write("    background-color: #f9e79f;\n");
        archivo.write("    color: #7d6608;\n");
        archivo.write("}\n");

        archivo.write(".decimal {\n");
        archivo.write("    background-color: #a3e4d7;\n");
        archivo.write("    color: #117864;\n");
        archivo.write("}\n");

        archivo.write(".asignacion {\n");
        archivo.write("    background-color: #f5b7b1;\n");
        archivo.write("    color: #7b241c;\n");
        archivo.write("}\n");

        archivo.write(".concatenacion {\n");
        archivo.write("    background-color: #d7bde2;\n");
        archivo.write("    color: #4a235a;\n");
        archivo.write("}\n");

        archivo.write(".delimitador {\n");
        archivo.write("    background-color: #edbb99;\n");
        archivo.write("    color: #6e2c00;\n");
        archivo.write("}\n");

        archivo.write(".funcion {\n");
        archivo.write("    background-color: #76d7c4;\n");
        archivo.write("    color: #0b5345;\n");
        archivo.write("}\n");

        archivo.write("</style>\n");
        archivo.write("</head>\n");

        archivo.write("<body>\n");

        archivo.write("<h1>Reporte de Tokens</h1>\n");

        archivo.write("<table>\n");

        archivo.write("<tr>\n");
        archivo.write("<th>No.</th>\n");
        archivo.write("<th>Lexema</th>\n");
        archivo.write("<th>Tipo</th>\n");
        archivo.write("<th>Fila</th>\n");
        archivo.write("<th>Columna</th>\n");
        archivo.write("</tr>\n");

        for (Token token : tokens) {

            String clase = obtenerClaseToken(token.getTipo().toString());
            archivo.write("<tr class='" + clase + "'>\n");
            archivo.write("<td>" + token.getNumero() + "</td>\n");
            archivo.write("<td class='lexema'>" + escaparHTML(token.getLexema()) + "</td>\n");
            archivo.write("<td>" + token.getTipo() + "</td>\n");
            archivo.write("<td>" + token.getFila() + "</td>\n");
            archivo.write("<td>" + token.getColumna() + "</td>\n");
            archivo.write("</tr>\n");
        }

        archivo.write("</table>\n");
        archivo.write("</body>\n");
        archivo.write("</html>\n");
        archivo.close();

        System.out.println("El reporte de tokens se hizo correctamente");
    }

    public void generarReporteErrores() throws IOException {

        FileWriter archivo = new FileWriter("reporte_errores.html");

        archivo.write("<!DOCTYPE html>\n");
        archivo.write("<html lang='es'>\n");

        archivo.write("<head>\n");
        archivo.write("<meta charset='UTF-8'>\n");
        archivo.write("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
        archivo.write("<title>Reporte de Errores Léxicos</title>\n");

        archivo.write("<style>\n");

        archivo.write("body {\n");
        archivo.write("    font-family: Arial, sans-serif;\n");
        archivo.write("    background-color: #f4f6f8;\n");
        archivo.write("    margin: 40px;\n");
        archivo.write("}\n");

        archivo.write("h1 {\n");
        archivo.write("    text-align: center;\n");
        archivo.write("    color: #2c3e50;\n");
        archivo.write("}\n");

        archivo.write("table {\n");
        archivo.write("    width: 100%;\n");
        archivo.write("    border-collapse: collapse;\n");
        archivo.write("    background-color: white;\n");
        archivo.write("    margin-top: 25px;\n");
        archivo.write("}\n");

        archivo.write("th {\n");
        archivo.write("    background-color: #922b21;\n");
        archivo.write("    color: white;\n");
        archivo.write("    padding: 12px;\n");
        archivo.write("    border: 1px solid #ddd;\n");
        archivo.write("}\n");

        archivo.write("td {\n");
        archivo.write("    padding: 10px;\n");
        archivo.write("    border: 1px solid #ddd;\n");
        archivo.write("    text-align: center;\n");
        archivo.write("}\n");

        archivo.write(".lexema {\n");
        archivo.write("    text-align: left;\n");
        archivo.write("    font-family: monospace;\n");
        archivo.write("}\n");

        
          //Colores para errores

        archivo.write(".directiva-error {\n");
        archivo.write("    background-color: #f5b7b1;\n");
        archivo.write("    color: #7b241c;\n");
        archivo.write("}\n");

        archivo.write(".decimal-error {\n");
        archivo.write("    background-color: #f8c471;\n");
        archivo.write("    color: #784212;\n");
        archivo.write("}\n");

        archivo.write(".guion-error {\n");
        archivo.write("    background-color: #d2b4de;\n");
        archivo.write("    color: #512e5f;\n");
        archivo.write("}\n");

        archivo.write(".caracter-error {\n");
        archivo.write("    background-color: #d7bde2;\n");
        archivo.write("    color: #4a235a;\n");
        archivo.write("}\n");

        archivo.write(".cadena-error {\n");
        archivo.write("    background-color: #aed6f1;\n");
        archivo.write("    color: #154360;\n");
        archivo.write("}\n");

        archivo.write(".comentario-error {\n");
        archivo.write("    background-color: #a9dfbf;\n");
        archivo.write("    color: #145a32;\n");
        archivo.write("}\n");

        archivo.write(".sin-errores {\n");
        archivo.write("    width: 80%;\n");
        archivo.write("    margin: 30px auto;\n");
        archivo.write("    padding: 20px;\n");
        archivo.write("    text-align: center;\n");
        archivo.write("    background-color: #d5f5e3;\n");
        archivo.write("    color: #196f3d;\n");
        archivo.write("    border: 1px solid #82e0aa;\n");
        archivo.write("    border-radius: 5px;\n");
        archivo.write("}\n");

        archivo.write("</style>\n");
        archivo.write("</head>\n");
        archivo.write("<body>\n");
        archivo.write("<h1>Reporte de Errores Léxicos</h1>\n");

        if (errores.isEmpty()) {

            archivo.write("<div class='sin-errores'>\n");
            archivo.write("<h2>No se encontraron errores léxicos.</h2>\n");
            archivo.write("</div>\n");

        } else {

            archivo.write("<table>\n");

            archivo.write("<tr>\n");
            archivo.write("<th>Lexema</th>\n");
            archivo.write("<th>Descripción</th>\n");
            archivo.write("<th>Fila</th>\n");
            archivo.write("<th>Columna</th>\n");
            archivo.write("</tr>\n");

            for (Error error : errores) {

                String clase = obtenerClaseError(error.getTipo());

                archivo.write("<tr class='" + clase + "'>\n");
                archivo.write("<td class='lexema'>" + escaparHTML(error.getLexema()) + "</td>\n");
                archivo.write("<td>" + escaparHTML(error.getTipo()) + "</td>\n");
                archivo.write("<td>" + error.getFila() + "</td>\n");
                archivo.write("<td>" + error.getColumna() + "</td>\n");
                archivo.write("</tr>\n");
            }

            archivo.write("</table>\n");
        }

        archivo.write("</body>\n");
        archivo.write("</html>\n");
        archivo.close();

        System.out.println("El reporte de errores ha sido generado correctamente.");
    }

    private String obtenerClaseToken(String tipo) {

        if (tipo.equals("DIRECTIVA")) {
            return "directiva";

        } else if (tipo.equals("PALABRA_RESERVADA")) {
            return "reservada";

        } else if (tipo.equals("COMANDO_IA")) {
            return "comando";

        } else if (tipo.equals("CONECTOR")) {
            return "conector";

        } else if (tipo.equals("IDENTIFICADOR")) {
            return "identificador";

        } else if (tipo.equals("CADENA")) {
            return "cadena";

        } else if (tipo.equals("ENTERO")) {
            return "entero";

        } else if (tipo.equals("DECIMAL")) {
            return "decimal";

        } else if (tipo.equals("ASIGNACION")) {
            return "asignacion";

        } else if (tipo.equals("CONCATENACION")) {
            return "concatenacion";

        } else if (tipo.equals("DELIMITADOR")) {
            return "delimitador";

        } else if (tipo.equals("FUNCION")) {
            return "funcion";
        }

        return "";
    }

    private String obtenerClaseError(String tipo) {

        if (tipo.equals("Directiva no reconocida")) {

            return "directiva-error";

        } else if (tipo.equals("Numero decimal incompleto")) {

            return "decimal-error";

        } else if (tipo.equals("Caracter '-' no reconocido")) {

            return "guion-error";

        } else if (tipo.equals("Caracter no reconocido")) {

            return "caracter-error";

        } else if (tipo.equals("Cadena sin cerrar")) {

            return "cadena-error";

        } else if (tipo.equals("Comentario de bloque sin cerrar")) {

            return "comentario-error";
        }

        return "";
    }

    private String escaparHTML(String texto) {

        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
}
