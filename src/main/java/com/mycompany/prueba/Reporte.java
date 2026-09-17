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
    
    public void generarReporteEstadisticas() {
        
        int directivas = 0;
        int palabras_reservadas = 0;
        int comandos_ia = 0;
        int conectores = 0;
        int identificadores = 0;
        int cadenas = 0;
        int enteros = 0;
        int decimales = 0;
        int operadores = 0;
        int delimitadores = 0;
        int funciones = 0;
        int asignaciones = 0;
        int concatenaciones = 0;
        
         for (Token token : tokens) {

            switch (token.getTipo()) {

                case DIRECTIVA:
                    directivas++;
                    break;

                case PALABRA_RESERVADA:
                    palabras_reservadas++;
                    break;

                case COMANDO_IA:
                    comandos_ia++;
                    break;

                case CONECTOR:
                    conectores++;
                    break;

                case IDENTIFICADOR:
                    identificadores++;
                    break;

                case CADENA:
                    cadenas++;
                    break;

                case ENTERO:
                    enteros++;
                    break;

                case DECIMAL:
                    decimales++;
                    break;

                case OPERADOR:
                    operadores++;
                     break;

                case DELIMITADOR:
                     delimitadores++;
                    break;

                case FUNCION:
                    funciones++;
                    break;

                case ASIGNACION:
                    asignaciones++;
                    break;

                case CONCATENACION:
                     concatenaciones++;
                    break;
            }
        }
         
         int total_tokens = tokens.size();
        int total_errores = errores.size();
        int total_lineas = 1;

        for (Token token : tokens) {
            if (token.getFila() > total_lineas) {
                total_lineas = token.getFila();
            }
        }

        for (Error error : errores) {
            if (error.getFila() > total_lineas) {
                total_lineas = error.getFila();
            }
        }
        
        //nuevo
        try {

        java.io.FileWriter archivo = new java.io.FileWriter("reporte_estadisticas.html");

        archivo.write("""
    <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>Reporte de Estadísticas - PromptZal</title>

            <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 30px;
        }

        .contenedor {
            width: 90%;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        h1 {
            text-align: center;
            color: #2c3e50;
        }

        h2 {
            color: #34495e;
            margin-top: 30px;
        }

        .resumen {
            display: flex;
            justify-content: space-around;
            gap: 20px;
            margin: 25px 0;
          }

        .tarjeta {
            flex: 1;
            padding: 20px;
            text-align: center;
            border-radius: 10px;
            background-color: #ecf0f1;
            border-left: 6px solid #3498db;
        }

        .tarjeta h3 {
            margin: 0;
            color: #34495e;
        }

    .tarjeta p {
        font-size: 28px;
        font-weight: bold;
        margin: 10px 0 0 0;
        color: #2c3e50;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
                      
    th {
        background-color: #2c3e50;
        color: white;
        padding: 12px;
        text-align: left;
    }

    td {
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }

    tr:hover {
        background-color: #f1f1f1;
    }

    .cantidad {
       text-align: center;
        font-weight: bold;
        }

    .directiva {
        background-color: #e8dff5;
    }

.reservada {
    background-color: #d6eaf8;
}

.comando {
    background-color: #d5f5e3;
}

.conector {
    background-color: #fcf3cf;
}

.identificador {
    background-color: #fdebd0;
}

.cadena {
    background-color: #fadbd8;
}

.entero {
    background-color: #d4efdf;
}

.decimal {
    background-color: #d1f2eb;
}

.operador {
    background-color: #e8daef;
}

.delimitador {
    background-color: #f9e79f;
}

.funcion {
    background-color: #aed6f1;
}

.asignacion {
    background-color: #d7bde2;
}

.concatenacion {
    background-color: #a9dfbf;
}

</style>
</head>

<body>

<div class="contenedor">

<h1>Reporte de Estadísticas</h1>

<div class="resumen">

<div class="tarjeta">
<h3>Total de tokens</h3>
<p>""" + total_tokens + """
</p>
</div>

<div class="tarjeta">
<h3>Total de errores</h3>
<p>""" + total_errores + """
</p>
</div>

<div class="tarjeta">
<h3>Total de líneas</h3>
<p>""" + total_lineas + """
</p>
</div>

</div>

<h2>Frecuencia de tipos de token</h2>

<table>

<tr>
<th>Tipo de token</th>
<th>Cantidad</th>
</tr>

<tr class="directiva">
<td>DIRECTIVA</td>
<td class="cantidad">""" + directivas + """
</td>
</tr>

<tr class="reservada">
<td>PALABRA_RESERVADA</td>
<td class="cantidad">""" + palabras_reservadas + """
</td>
</tr>

<tr class="comando">
<td>COMANDO_IA</td>
<td class="cantidad">""" + comandos_ia + """
</td>
</tr>

<tr class="conector">
<td>CONECTOR</td>
<td class="cantidad">""" + conectores + """
</td>
</tr>

<tr class="identificador">
<td>IDENTIFICADOR</td>
<td class="cantidad">""" + identificadores + """
</td>
</tr>

<tr class="cadena">
<td>CADENA</td>
<td class="cantidad">""" + cadenas + """
</td>
</tr>

<tr class="entero">
<td>ENTERO</td>
<td class="cantidad">""" + enteros + """
</td>
</tr>

<tr class="decimal">
<td>DECIMAL</td>
<td class="cantidad">""" + decimales + """
</td>
</tr>

<tr class="operador">
<td>OPERADOR</td>
<td class="cantidad">""" + operadores + """
</td>
</tr>

<tr class="delimitador">
<td>DELIMITADOR</td>
<td class="cantidad">""" + delimitadores + """
</td>
</tr>

<tr class="funcion">
<td>FUNCION</td>
<td class="cantidad">""" + funciones + """
</td>
</tr>

<tr class="asignacion">
<td>ASIGNACION</td>
<td class="cantidad">""" + asignaciones + """
</td>
</tr>

<tr class="concatenacion">
<td>CONCATENACION</td>
<td class="cantidad">""" + concatenaciones + """
</td>
</tr>

</table>

</div>

</body>
</html>
""");

        archivo.close();

        System.out.println("Reporte de estadísticas generado correctamente.");

    } catch (java.io.IOException e) {

        System.out.println("Error al generar el reporte de estadísticas:");
        System.out.println(e.getMessage());
    }
        
    }
}
