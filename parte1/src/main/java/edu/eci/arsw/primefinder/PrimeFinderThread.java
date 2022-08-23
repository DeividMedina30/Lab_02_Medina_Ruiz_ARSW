package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

  int cont = 5;

	int a,b;
  boolean running = true;
	
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

  public void run(){
    while (running){
      for (int i = a ; i <= b ; i++){						
        if (isPrime(i) && i != 1){
          primes.add(i);
          System.out.println(i);
        }
      }
    }
  }
	
	boolean isPrime(int n) {
	  if (n%2==0 && n != 2) return false;
	  for(int i = 3 ; i*i <= n ; i+=2) {
	    if(n % i==0)
	      return false;
	  }
	  return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

  public void kill(){
    this.running= false;
  }

  public void correr(){
    this.running = true;
  }
}
