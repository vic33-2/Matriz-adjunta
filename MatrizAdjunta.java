import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MatrizAdjunta {

    public static void main(String[] args) {
        String archivoEntrada = "matriz.txt";
        String archivoSalida = "matriz_adjunta.txt";
        double[][] matriz = leerMatriz(archivoEntrada);
        if (matriz != null) {
            System.out.println("Matriz original:");
            imprimirMatriz(matriz);

            double[][] adjunta = matrizAdjunta(matriz);
            if (adjunta != null) {
                System.out.println("\n Matriz adjunta:");
                imprimirMatriz(adjunta);

                escribirArchivo(adjunta, archivoSalida);
                System.out.println("\n Archivo generado: " + archivoSalida);
            } else {
                System.out.println("\n La matriz no es cuadrada. No se puede calcular la matriz adjunta.");
            }
        } else {
            System.out.println("Error al leer el archivo.");
        }
    }

    public static double[][] leerMatriz(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int filas = 0;


            while ((linea = br.readLine()) != null) {
                filas++;
            }

            double[][] matriz = new double[filas][];
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(nombreArchivo));
            int i = 0;
            while ((linea = br2.readLine()) != null) {
                String[] valores = linea.trim().split(" ");
                matriz[i] = new double[valores.length];
                for (int j = 0; j < valores.length; j++) {
                    matriz[i][j] = Double.parseDouble(valores[j]);
                }
                i++;
            }
            br2.close();

            return matriz;

        } 
        
        catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
            return null;
        }
    }

    public static double[][] matrizAdjunta(double[][] matriz) {
        int n = matriz.length;

        if (n != matriz[0].length) return null;

        double[][] cofactores = new double[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactores[i][j] = Math.pow(-1, i + j) * determinante(matrizMenor(matriz, i, j));
            }
        }

        double[][] adjunta = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjunta[i][j] = cofactores[j][i]; 
            }
        }

        return adjunta;
    }

    public static double[][] matrizMenor(double[][] matriz, int fila, int col) {
        int n = matriz.length;
        double[][] menor = new double[n - 1][n - 1];
        int r = 0;

        for (int i = 0; i < n; i++) {
            if (i == fila) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                menor[r][c] = matriz[i][j];
                c++;
            }
            r++;
        }
        return menor;
    }

    public static double determinante(double[][] matriz) {
        int n = matriz.length;
        if (n == 1) return matriz[0][0];
        if (n == 2) return (matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]);

        double det = 0;
        for (int j = 0; j < n; j++) {
            det += matriz[0][j] * Math.pow(-1, 0 + j) * determinante(matrizMenor(matriz, 0, j));
        }
        return det;
    }

        } catch (IOException e) {
            System.out.println("Error al escribir archivo: " + e.getMessage());
        }
    }
}
