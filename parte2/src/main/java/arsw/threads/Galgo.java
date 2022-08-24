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
	int vel;
	private boolean suspender;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		suspender= false;
	}

	public void corra() throws InterruptedException {

		while (paso < carril.size()) {
			this.vel = RandomGenerator.nextInt(100);
			Thread.sleep(vel);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			synchronized (this){
				while(suspender){
					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
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


	@Override
	public void run() {
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void suspenderHilo(){
		suspender = true;
	}

	public void reanudarHilo() {
		suspender= false;
		notify();
	}

}