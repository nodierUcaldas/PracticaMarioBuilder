package expertsystemapp;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import expertsystem.Motor;
import expertsystem.Rule;
import expertsystem.IFact;
import expertsystem.HumanInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Main implements HumanInterface {

    public Scanner keyboard = new Scanner(System.in);
    public LinkedList<String> listaReglas;
    // "rules.txt"

    public LinkedList<String> obtenerReglas(String texto) throws FileNotFoundException, IOException {
        listaReglas = new LinkedList<String>();
        File archivo = new File(texto);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        //regla R;

        while ((linea = br.readLine()) != null) {
            /* String[] L = new String[2];
            L = linea.split(":");
            
            R = new regla();
            R.setNombre(L[0]);
            R.setArgumento(L[1]);*/
            //System.out.println(linea);
            listaReglas.add(linea);
        }
        /*
        for (int i = 0; i < listaReglas.size(); i++) {
            System.out.println(listaReglas.get(i).toString());
        }
         */
        return listaReglas;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<String> listaReglas;
        Main app = new Main();
        listaReglas = app.obtenerReglas("rules.txt");
        app.run(listaReglas);
    }

    // Funcionamiento del programa, con el ejemplo de las polígonos
    public void run(LinkedList<String> lista) {
        // Creación del motor
        Motor motor = new Motor(this);

        // Agregar las reglas
        //System.out.println("Agregando reglas...");
        JOptionPane.showMessageDialog(null, "Agregando reglas...");
        for (int i = 0; i < lista.size(); i++) {
            motor.addRule(lista.get(i));
        }

        /* 
        motor.addRule("R1 : IF (Orden=3(¿Cuál es el orden?)) THEN  Triángulo");
        motor.addRule("R2 : IF (Triángulo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Triángulo Rectángulo");
        motor.addRule("R3 : IF (Triángulo AND Lados Iguales=2(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Isósceles");
        motor.addRule("R4 : IF (Triángulo rectángulo AND Triángulo Isósceles) THEN Triángulo Rectángulo Isósceles");
        motor.addRule("R5 : IF (Triángulo AND Lados Iguales=3(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Equilátero");
        motor.addRule("R6 : IF (Orden=4(¿Cuál es el orden?)) THEN Cuadrilátero");
        motor.addRule("R7 : IF (Cuadrilátero AND Lados Paralelos=2(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Trapecio");
        motor.addRule("R8 : IF (Cuadrilátero AND Lados Paralelos=4(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Paralelogramo");
        motor.addRule("R9 : IF (Paralelogramo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Rectángulo");
        motor.addRule("R10 : IF (Paralelogramo AND Lados Iguales=4(¿Cuántos lados iguales tiene la figura?)) THEN Rombo");
        motor.addRule("R11 : IF (Rectángulo AND Rombo THEN Cuadrado");
         */
        // Resolución
        String salir;
        do {
            //System.out.println("\n** Resolución **");
            JOptionPane.showMessageDialog(null, "\n** Resolución **");
            motor.solve();
            //System.out.println("¿Desea salir? (s/n)");
            salir = JOptionPane.showInputDialog("¿Desea salir? (s/n)");
            salir.trim();
            //System.out.println(salir);
            // } while (!keyboard.next().equalsIgnoreCase("s"));
            //salir="s";
        } while (!salir.equalsIgnoreCase("s"));

    }

    // Pide una valor entero al usuario, sin verificaciones (0 en caso de problema)
    @Override
    public int askIntValue(String question) {
        //System.out.println(question);
        int numero;
        String salir = JOptionPane.showInputDialog(question);
        salir.trim();
        numero = Integer.parseInt(salir);

        try {
           // return keyboard.nextInt();
            return numero;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Solicita un valor booleano, con sí (verdadero) o no. 
    // Se ignorarn los errores (devuelve falso)
    @Override
    public boolean askBoolValue(String question) {
        try {
            //System.out.println(question + " (si, no)");
            String salir = JOptionPane.showInputDialog(question + " (si, no)");
            salir.trim();
            //String res = keyboard.next();
            String res = salir;
            return (res.equals("si"));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Muestra la lista de hechos de nivel >0 y por orden decreciente de nivel
    @Override
    public void printFacts(List<IFact> facts) {
        String res = "Solución(s) encontrada(s) : \n";
        Collections.sort(facts, (IFact f1, IFact f2) -> {
            return Integer.compare(f2.getLevel(), f1.getLevel());
        });
        for (IFact f : facts) {
            if (f.getLevel() != 0) {
                res += f.toString() + "\n";
            }
        }
        //System.out.println(res);
        JOptionPane.showMessageDialog(null, res);
    }

    // Muestra las reglas contenidas en la base
    @Override
    public void printRules(List<Rule> rules) {
        String res = "";
        for (Rule r : rules) {
            res += r.toString() + "\n";
        }
        //System.out.println(res);
        JOptionPane.showMessageDialog(null, res);
    }
}
