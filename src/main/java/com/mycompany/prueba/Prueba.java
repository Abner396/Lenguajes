package com.mycompany.prueba;

import java.io.IOException;
import java.util.Scanner;

public class Prueba {
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        menu();
    }
    
    public static void menu(){
        
        int opcion;
        
        do{
            System.out.println("SELECCIONE EL NUMERO DE LA ACCION QUE DESEE HACER");
            System.out.println("1. cargar archvio");
            System.out.println("2. salir");
            
            opcion = scanner.nextInt();
            scanner.nextLine();
            
        }while(opcion<1 || opcion >2);
        
        switch(opcion){
            
            case 1:
                
                iniciar();
                break;
                
            case 2:
                System.out.println("Saliendo del programa");
                System.exit(0);
                
                break;
        }
        
    }
    
    public static void iniciar(){
        try {
            Buscador buscador = new Buscador();

            String ruta = buscador.buscarArchivo();

            System.out.println("Archivo seleccionado:");
            System.out.println(ruta);
            System.out.println();
            Lector lector = new Lector(ruta);
            Analizador analizador = new Analizador(lector);

            
            System.out.println("LEYEND EL ARCHIVO...");
            System.out.println();

            
            analizador.analizar();

          
            System.out.println("ANALISIS TERMINADO");
            System.out.println();

            Reporte reporte = new Reporte(analizador.getTokens(), analizador.getErrores());

            reporte.generarReporteTokens();
            reporte.generarReporteErrores();

            System.out.println();
            

        } catch (IOException e) {

            System.out.println();
            System.out.println("ERROR AL LEER EL ARCHIVO:");
            System.out.println(e.getMessage());
        }
        
        menu();
    }
}