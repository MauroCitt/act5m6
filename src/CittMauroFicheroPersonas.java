import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CittMauroFicheroPersonas {
    public static void main(String[] args) throws IOException {
        Scanner teclat = new Scanner(System.in);
        File fichero = new File("/home/alumne/Escriptori/personas");
        RandomAccessFile archivoFichero = new RandomAccessFile(fichero, "rw");

        generarPersonas(archivoFichero);
        archivoFichero.seek(0);
        leerFichero(archivoFichero);
        System.out.println("*******");
        System.out.println("Ingrese la posición que quiere leer: ");
        int pos = teclat.nextInt();
        int posBinario = pos * 158 - 158;
        leerFicheroPosicion(posBinario, archivoFichero);
        System.out.println("Ingrese el DNI que quiere leer: ");
        String dni = teclat.next();
        System.out.println("*******");
        leerFicheroDNI(dni, archivoFichero);
        archivoFichero.close();

    }

    public static  void generarPersonas(RandomAccessFile archivoFichero) throws IOException {
        List<Persona> personas = new ArrayList<>();

        // Crear objetos Persona con diferentes atributos
        String[] nombres = {"Mauro", "Estel", "Felipe", "Joan", "Edgar", "Luciana", "Nacho"};
        String[] dnis = {"123456789", "987654321", "555555555", "111222333", "999888777", "777777777", "888888888"};
        String[] telefonos = {"123-456-789", "987-654-321", "555-555-555", "111-222-333", "999-888-777", "777-777-777", "888-888-888"};
        int[] edades = {21, 19, 24, 33, 25, 15, 13};
        double[] altura = {1.70, 1.67, 1.73, 1.77, 1.70, 1.68, 1.58};
        double[] pesos = {68.9, 50.0, 71.0, 71.0, 68.5, 50.2, 61.2};

        for (int i = 0; i < 7; i++) {
            personas.add(new Persona(nombres[i], dnis[i], telefonos[i], edades[i], altura[i], pesos[i]));
        }

        StringBuffer buffer = null;

        for (Persona persona:
             personas) {
            buffer = new StringBuffer(persona.getNombre());
            buffer.setLength(50);
            archivoFichero.writeChars(buffer.toString());
            buffer = new StringBuffer(persona.getDNI());
            buffer.setLength(9);
            archivoFichero.writeChars(buffer.toString());
            buffer = new StringBuffer(persona.getTelefono());
            buffer.setLength(14);
            archivoFichero.writeChars(buffer.toString());
            archivoFichero.writeInt(persona.getEdad());
            archivoFichero.writeFloat((float) persona.getAltura());
            archivoFichero.writeFloat((float) persona.getPeso());

        }
    }

    public static void leerFichero(RandomAccessFile ficheroAleatorio) throws IOException {
        int apuntador = 0, edad;
        float altura, peso;
        char nombre[] = new char[50];
        char telefono[] = new char[14];
        char dni[] = new char[9];
        char aux;

        for (;;) {
            ficheroAleatorio.seek(apuntador);
            for (int i = 0; i < nombre.length; i++) {
                aux = ficheroAleatorio.readChar();
                nombre[i] = aux;
            }
            String nombres = new String(nombre);

            for (int i = 0; i < dni.length; i++) {
                aux = ficheroAleatorio.readChar();
                dni[i] = aux;
            }
            String dnis = new String(dni);

            for (int i = 0; i < telefono.length; i++) {
                aux = ficheroAleatorio.readChar();
                telefono[i] = aux;
            }
            String telefonos = new String(telefono);
            edad = ficheroAleatorio.readInt();
            altura = ficheroAleatorio.readFloat();
            peso = ficheroAleatorio.readFloat();

            System.out.println("Nombre: " + nombres.trim() + "\nDNI: " + dnis + "\nTeléfono: " + telefonos.trim() + "\nEdad: " + edad + "\nAltura: " + altura + "\nPeso: " + peso);
            apuntador+= 158;

            if(ficheroAleatorio.getFilePointer()==ficheroAleatorio.length())
                break;
        }
    }

    private static void leerFicheroPosicion(int pos, RandomAccessFile archivoAleatorio) throws IOException {
        int edad;
        float altura, peso;
        char nombre[] = new char[50];
        char telefono[] = new char[14];
        char dni[] = new char[9];
        char aux;

        for (;;) {
            archivoAleatorio.seek(pos);
            for (int i = 0; i < nombre.length; i++) {
                aux = archivoAleatorio.readChar();
                nombre[i] = aux;
            }
            String nombres = new String(nombre);

            for (int i = 0; i < dni.length; i++) {
                aux = archivoAleatorio.readChar();
                dni[i] = aux;
            }
            String dnis = new String(dni);

            for (int i = 0; i < telefono.length; i++) {
                aux = archivoAleatorio.readChar();
                telefono[i] = aux;
            }
            String telefonos = new String(telefono);
            edad = archivoAleatorio.readInt();
            altura = archivoAleatorio.readFloat();
            peso = archivoAleatorio.readFloat();

            System.out.println("Nombre: " + nombres.trim() + "\nDNI: " + dnis + "\nTeléfono: " + telefonos.trim() + "\nEdad: " + edad + "\nAltura: " + altura + "\nPeso: " + peso);
            break;
        }
    }

    private static void leerFicheroDNI(String dni, RandomAccessFile archivoAleatorio) throws IOException {
        int edad;
        float altura, peso;
        char nombre[] = new char[50];
        char telefono[] = new char[14];
        char dni2[] = new char[9];
        char aux;
        boolean encontrada = false;

        archivoAleatorio.seek(100);

        while (archivoAleatorio.getFilePointer() < archivoAleatorio.length() && !encontrada) {
            for (int i = 0; i < dni2.length; i++) {
                aux = archivoAleatorio.readChar();
                dni2[i] = aux;
            }
            String dnis = new String(dni2);

            if (dni.equalsIgnoreCase(dnis)) {
                encontrada = true;

                archivoAleatorio.seek(archivoAleatorio.getFilePointer() - 118);

                for (int i = 0; i < nombre.length; i++) {
                    aux = archivoAleatorio.readChar();
                    nombre[i] = aux;
                }
                String nombres = new String(nombre);

                for (int i = 0; i < dni2.length; i++) {
                    aux = archivoAleatorio.readChar();
                    dni2[i] = aux;
                }

                dnis = new String(dni2);

                for (int i = 0; i < telefono.length; i++) {
                    aux = archivoAleatorio.readChar();
                    telefono[i] = aux;
                }
                String telefonos = new String(telefono);

                edad = archivoAleatorio.readInt();
                altura = archivoAleatorio.readFloat();
                peso = archivoAleatorio.readFloat();

                System.out.println("Nombre: " + nombres.trim() + "\nDNI: " + dnis + "\nTeléfono: " + telefonos.trim() + "\nEdad: " + edad + "\nAltura: " + altura + "\nPeso: " + peso);
                break;  // Salir del bucle si se encuentra el DNI
            } else {
                archivoAleatorio.skipBytes(158 - 9 * 2);  // Saltar el resto del registro
            }
        }

        if (!encontrada) {
            System.out.println("DNI no encontrado");
        }
    }

}

