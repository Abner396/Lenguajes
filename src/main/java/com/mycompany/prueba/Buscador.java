package com.mycompany.prueba;

import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


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
    
    public String seleccionarArchivo() {

        JFileChooser explorador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos PromptZal (*.pz)", "pz");
        explorador.setFileFilter(filtro);

        int opcion = explorador.showOpenDialog(null);
        if (opcion == JFileChooser.APPROVE_OPTION) {

            String ruta = explorador.getSelectedFile().getAbsolutePath();

            if (!ruta.toLowerCase().endsWith(".pz")) {
                return null;
            }

            return ruta;
        }

            return null;
    }
    
    public String leerArchivo(String ruta) throws IOException {

        BufferedReader archivo = new BufferedReader(new FileReader(ruta));
        StringBuilder contenido = new StringBuilder();
        String linea;

        while ((linea = archivo.readLine()) != null) {

            contenido.append(linea);
            contenido.append("\n");
        }

        archivo.close();
        return contenido.toString();
    }
    
    public void guardarArchivo(String ruta, String contenido) throws IOException {

        java.io.BufferedWriter archivo = new java.io.BufferedWriter(new java.io.FileWriter(ruta));

        archivo.write(contenido);
        archivo.close();
    }
    
}
