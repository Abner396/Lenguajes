package com.mycompany.prueba;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analizador {

    private Lector lector;
    private List<Token> tokens;
    private List<Error> errores;
    private int numero_token;

    public Analizador(Lector lector) {

        this.lector = lector;
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.numero_token = 1;
    }

    public void analizar() throws IOException {

        while (!lector.finArchivo()) {

            char Caracter_actual = lector.getCaracter_actual();

            if (Caracter_actual == ' ' || Caracter_actual == '\t'  || Caracter_actual == '\n' || Caracter_actual == '\r') {

                lector.avanzar();

            } else if (esInicioIdentificador(Caracter_actual)) {

                reconocerIdentificador();


            } else if (esDigito(Caracter_actual)) {

                reconocerNumero();

            } else if (Caracter_actual == '"') {

                reconocerCadena();

            } else if (Caracter_actual == '=') {

                reconocerAsignacion();
                
            } else if (Caracter_actual == '+') {

                reconocerConcatenacion();

            } else if (Caracter_actual == '-') {

                reconocerFlecha();

            } else if (Caracter_actual == '@') {

                reconocerDirectiva();

            } else if (Caracter_actual == '/') {

                reconocerComentario();

            } else if (Caracter_actual == '{' || Caracter_actual == '}' || Caracter_actual == '(' || Caracter_actual == ')' || Caracter_actual == ',' || Caracter_actual == ';') {

                reconocerDelimitador();

              } else {

                reconocerErrorCaracter();
            }
             }
    }

    
    private void reconocerIdentificador() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        while (!lector.finArchivo() && esParteIdentificador(lector.getCaracter_actual())) {

            lexema += lector.getCaracter_actual();
            lector.avanzar();
        }

        Tipo tipo = determinarTipo(lexema);
        Token token = new Token(numero_token, lexema, tipo, fila_inicial, columna_inicial);
        tokens.add(token);

        numero_token++;
        System.out.println(token);
    }

    private void reconocerNumero() throws IOException {

    int fila_inicial = lector.getFila();
    int columna_inicial = lector.getColumna();
    String lexema = "";

    // Parte entera
    while (!lector.finArchivo() && esDigito(lector.getCaracter_actual())) {

        lexema += lector.getCaracter_actual();
        lector.avanzar();
    }

        // Comprobamos si existe punto decimal
        if (!lector.finArchivo() && lector.getCaracter_actual() == '.') {

        lexema += lector.getCaracter_actual();
        lector.avanzar();

        // Debe existir al menos un dígito después del punto
        if (!lector.finArchivo() && esDigito(lector.getCaracter_actual())) {

            while (!lector.finArchivo() && esDigito(lector.getCaracter_actual())) {

                lexema += lector.getCaracter_actual();
                lector.avanzar();
            }

            Token token = new Token(numero_token, lexema, Tipo.DECIMAL, fila_inicial, columna_inicial);

            tokens.add(token);
            numero_token++;

            System.out.println(token);

        } else {

            Error error = new Error(lexema, "Numero decimal incompleto", fila_inicial, columna_inicial);

            errores.add(error);
            System.out.println(error);
        }

        } else {

        Token token = new Token( numero_token, lexema, Tipo.ENTERO, fila_inicial, columna_inicial);

        tokens.add(token);
        numero_token++;
        System.out.println(token);
        }
    }
    private void reconocerCadena() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        lexema += lector.getCaracter_actual();

        lector.avanzar();

        boolean cerrada = false;

        while (!lector.finArchivo()) {

            char Caracter_actual = lector.getCaracter_actual();

            if (Caracter_actual == '"') {

                lexema += Caracter_actual;
                lector.avanzar();
                cerrada = true;
                break;
            }
                //saltando de linea
            if (Caracter_actual == '\n') {
                    
                break;
            }

            lexema += Caracter_actual;

            lector.avanzar();
        }

        if (cerrada) {

            Token token = new Token(numero_token, lexema, Tipo.CADENA, fila_inicial, columna_inicial);
            tokens.add(token);
            numero_token++;
            System.out.println(token);

          } else {

            Error error = new Error(lexema, "Cadena sin cerrar", fila_inicial, columna_inicial);
            errores.add(error);
            System.out.println(error);

            if (!lector.finArchivo() && lector.getCaracter_actual() == '\n') {

                lector.avanzar();
            }
           }
    }

    
    private void reconocerAsignacion() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        lexema += lector.getCaracter_actual();
        lector.avanzar();
        Token token = new Token( numero_token, lexema, Tipo.ASIGNACION, fila_inicial, columna_inicial);
        tokens.add(token);

        numero_token++;

        System.out.println(token);
    }

   
    private void reconocerConcatenacion() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

         String lexema = "";
 
        lexema += lector.getCaracter_actual();
            lector.avanzar();

        Token token = new Token( numero_token, lexema, Tipo.CONCATENACION, fila_inicial, columna_inicial);
        tokens.add(token);

          numero_token++;

        System.out.println(token);
        //porque no funcionaaaaaaaaaaa debi haberme hecho chef
    }

      private void reconocerFlecha() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        
        lexema += lector.getCaracter_actual();

        lector.avanzar();

        
        if (!lector.finArchivo() && lector.getCaracter_actual() == '>') {

            lexema += lector.getCaracter_actual();
            lector.avanzar();
            Token token = new Token( numero_token, lexema, Tipo.CONECTOR, fila_inicial, columna_inicial);
            tokens.add(token);

            numero_token++;

            System.out.println(token);

        } else {

            Error error = new Error( lexema, "Caracter '-' no reconocido", fila_inicial, columna_inicial);

            errores.add(error);

            System.out.println(error);
        }
    }

     private void reconocerDirectiva() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        
        lexema += lector.getCaracter_actual();
        lector.avanzar();

         while (!lector.finArchivo() && esParteIdentificador(lector.getCaracter_actual())) {

        lexema += lector.getCaracter_actual();
        lector.avanzar();
        }

        if (lexema.equals("@modelo") || lexema.equals("@rol") || lexema.equals("@formato")) {

            Token token = new Token( numero_token, lexema, Tipo.DIRECTIVA, fila_inicial, columna_inicial);

            tokens.add(token);

            numero_token++;

            System.out.println(token);

        } else {

            Error error = new Error(lexema, "Directiva no reconocida", fila_inicial,  columna_inicial);

            errores.add(error);

            System.out.println(error);
        }
    }

      private void reconocerComentario() throws IOException {
          String prueba = "/";

    int fila_inicial = lector.getFila();
    int columna_inicial = lector.getColumna();

    String lexema = "";

    
    lexema += lector.getCaracter_actual();

    lector.avanzar();

   if (!lector.finArchivo() && lector.getCaracter_actual() == '/') {

       
        lexema += lector.getCaracter_actual();

        lector.avanzar();

        
        while (!lector.finArchivo() && lector.getCaracter_actual() != '\n') {

            lector.avanzar();
        }


    } else if (!lector.finArchivo()  && lector.getCaracter_actual() == '*') {

       
        lexema += lector.getCaracter_actual();

        lector.avanzar();

        boolean cerrado = false;

        while (!lector.finArchivo()) {

            char Caracter_actual = lector.getCaracter_actual();

           
            if (Caracter_actual == '*') {
                lexema += Caracter_actual;
                lector.avanzar();

                if (!lector.finArchivo() && lector.getCaracter_actual() == '/') {

                   lexema += lector.getCaracter_actual();
                    lector.avanzar();

                   cerrado = true;

                    break;
                }

            } else {
                lexema += Caracter_actual;
                lector.avanzar();
            }
        }
        if (cerrado) {
                        //bueno aqui no tengo que hacer nada creo...
            } else {

            Error error = new Error( lexema, "Comentario de bloque sin cerrar", fila_inicial, columna_inicial);

            errores.add(error);

            System.out.println(error);
        }

         } else {

        Error error = new Error( lexema, "Caracter '/' no reconocido", fila_inicial,  columna_inicial);

        errores.add(error);

        System.out.println(error);
    }
}

  private void reconocerDelimitador() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        lexema += lector.getCaracter_actual();

        lector.avanzar();

        Token token = new Token( numero_token, lexema, Tipo.DELIMITADOR, fila_inicial, columna_inicial);

        tokens.add(token);

        numero_token++;

        System.out.println(token);
    }

     private void reconocerErrorCaracter() throws IOException {

        int fila_inicial = lector.getFila();
        int columna_inicial = lector.getColumna();

        String lexema = "";

        lexema += lector.getCaracter_actual();

        lector.avanzar();

        Error error = new Error(lexema, "Caracter no reconocido", fila_inicial, columna_inicial);

        errores.add(error);

        System.out.println(error);
    }

     private Tipo determinarTipo(String lexema) {

        switch (lexema) {

            
            case "AGENTE":
            case "contexto":
            case "variable":
            case "EJECUTAR":
            case "EXPORTAR":

                return Tipo.PALABRA_RESERVADA;

            
            case "PREGUNTAR":
            case "GENERAR":
            case "RESUMIR":
            case "ANALIZAR":
            case "TRADUCIR":
            case "CLASIFICAR":
            case "EXTRAER":
            case "CODIFICAR":

                return Tipo.COMANDO_IA;

           
            case "SOBRE":
            case "DESDE":
            case "EN":
            case "COMO":
            

                return Tipo.CONECTOR;

            case "CARGAR":

                return Tipo.FUNCION;

            
            default:

                return Tipo.IDENTIFICADOR;
        }
    }

     public boolean esLetra(char caracter) {

        return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
    }

   public boolean esInicioIdentificador(char caracter) {

        return esLetra(caracter) || caracter == '_';
    }

    
    public boolean esParteIdentificador(char caracter) {

        return esLetra(caracter) || esDigito(caracter) || caracter == '_';
    }

    public boolean esDigito(char caracter) {

        return caracter >= '0' && caracter <= '9';
    }

    public List<Token> getTokens() {

        return tokens;
    }

     public List<Error> getErrores() {

        return errores;
    }
}