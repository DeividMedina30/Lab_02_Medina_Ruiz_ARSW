package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 *
 * @author rlopez
 *
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private boolean detenerse;
	int vel;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.detenerse = false;

	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			if (detenerse){
				synchronized (this){
					wait();
				}
			}else {
				Thread.sleep(100);
				carril.setPasoOn(paso++);
				carril.displayPasos(paso);
				if (paso == carril.size()) {
					carril.finish();
					synchronized (regl) {
						int ubicacion = regl.getUltimaPosicionAlcanzada();
						regl.setUltimaPosicionAlcanzada(ubicacion + 1);
						System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
						if (ubicacion == 1) {
							regl.setGanador(this.getName());
						}
					}
				}
			}
		}
	}

	synchronized void detenerGalgos(){
		this.detenerse = true;
	}

	synchronized void reanudarGalgos(){
		this.detenerse = false;
		notifyAll();
	}


	@Override
	public void run() {

		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
