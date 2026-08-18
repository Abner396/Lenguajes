package com.mycompany.prueba;

import java.io.File;
import java.util.Scanner;


public class Buscador {

    private Scanner scanner;

    public Buscador() {
        scanner = new Scanner(System.in);
    }

    public String buscarArchivo() {

        while (true) {

            System.out.println("Ingrese la ruta del archivo  .pz:");
            String ruta = scanner.nextLine();

            File archivo = new File(ruta);

            // Comprobar si el archivo existe
            if (!archivo.exists()) {
                System.out.println("el archivo no existe.");
                System.out.println();
                continue;
            }

          
            if (!archivo.isFile()) {
                System.out.println("la ruta indicada no corresponde a un archivo.");
                System.out.println();
                continue;
            }

            if (!ruta.toLowerCase().endsWith(".pz")) {
                System.out.println("el archivo debe tener la extensión .pz.");
                System.out.println();
                continue;
            }

            System.out.println("Archivo encontrado");
            System.out.println();

            return ruta;
        }
    }
    
}
