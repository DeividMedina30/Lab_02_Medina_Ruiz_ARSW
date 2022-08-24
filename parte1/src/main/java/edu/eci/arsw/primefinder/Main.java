package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static PrimeFinderThread pft1;
    private static PrimeFinderThread pft2;
    private static PrimeFinderThread pft3;

    private static void arrancarHilos(int a1, int a2, int n){
        pft1 = new PrimeFinderThread(0, a1);
        pft2 = new PrimeFinderThread(a1 + 1, a2);
        pft3 = new PrimeFinderThread(a2 + 1, n);
        pft1.start();
        pft2.start();
        pft3.start();
    }

    private static void esperarPulsacionEnterUsuario(){
        try (Scanner t = new Scanner(System.in)) {
            String enterkey = t.nextLine();
            while (!enterkey.isEmpty()  ) {
                esperarPulsacionEnterUsuario();
            }
        }
        pft1.renaudarhilo();
        pft2.renaudarhilo();
        pft3.renaudarhilo();
    }

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
