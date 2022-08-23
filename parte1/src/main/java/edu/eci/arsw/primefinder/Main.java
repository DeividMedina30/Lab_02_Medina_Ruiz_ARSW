package edu.eci.arsw.primefinder;

public class Main {

	public static void main(String[] args) {
    int n=30000000;
    int a1 = n/3 -1;
    int a2 = n/3 + a1;
		
		PrimeFinderThread pft1 = new PrimeFinderThread(0, a1);
    PrimeFinderThread pft2 = new PrimeFinderThread(a1+1, a2);
    PrimeFinderThread pft3 = new PrimeFinderThread(a2+1, n);
		
		pft1.start();
    pft2.start();
    pft3.start();
	}
}