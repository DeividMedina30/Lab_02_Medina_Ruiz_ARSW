package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static PrimeFinderThread pft1;
    private static PrimeFinderThread pft2;
    private static PrimeFinderThread pft3;

    /**
     * Método que me permite iniciar los hilos
     * @param a1 - División de n / 3
     * @param a2 - Division de N/ 3 + a1
     * @param n - Número hasta donde se va a revisar.
     */
    private static void arrancarHilos(int a1, int a2, int n){
        pft1 = new PrimeFinderThread(0, a1);
        pft2 = new PrimeFinderThread(a1 + 1, a2);
        pft3 = new PrimeFinderThread(a2 + 1, n);
        pft1.start();
        pft2.start();
        pft3.start();
    }

    /**
     * Método que me permite esperar que el usuario digite enter para iniciar de nuevo.
     */
    private static void esperarPulsacionEnterUsuario(){
        try (Scanner t = new Scanner(System.in)) {
            String enterkey = t.nextLine();
            while (!enterkey.isEmpty()  ) {
                esperarPulsacionEnterUsuario();
            }
        }
        pft1.reanudarhilo();
        pft2.reanudarhilo();
        pft3.reanudarhilo();
    }

    /**
     * Método que me permite pausar los hilos.
     */
    private static void pausarHilos(){
        pft1.suspenderhilo();
        pft2.suspenderhilo();
        pft3.suspenderhilo();
        System.out.println("Se pausaron los hilos. Presione enter para reanudar");
        esperarPulsacionEnterUsuario();

    }

    public static void main(String[] args) throws InterruptedException {
        int n = 30000000;
        int a1 = n / 3 - 1;
        int a2 = n / 3 + a1;
        arrancarHilos(a1,a2, n);
        Timer temporizador = new Timer();
        TimerTask tareaEsperar = new TimerTask() {
            int cont = 0;
            @Override
            public void run() {
                if (cont == 1){
                    temporizador.cancel();
                    pausarHilos();
                }
                cont ++;
            }
        };
        temporizador.schedule(tareaEsperar,0,5000);
    }

}
