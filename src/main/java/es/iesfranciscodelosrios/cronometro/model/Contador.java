package es.iesfranciscodelosrios.cronometro.model;

import java.util.Observable;
import java.util.Observer;

import es.iesfranciscodelosrios.cronometro.PrimaryController;

public class Contador extends Observable implements Runnable {

	private int contador;
	private SolicitaSuspender suspendido = new SolicitaSuspender();
	
	private Observer observer;

	public Contador() {
		this.contador = 0;
		this.suspendido.SetSuspendido(false);
	}

	public SolicitaSuspender getSuspendido() {
		return suspendido;
	}

	public void setSuspendido(SolicitaSuspender suspendido) {
		this.suspendido = suspendido;
	}
	
	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;	
	}

	/**
	 * Método que cuenta los segundos y lo notifica a su observador.
	 */
	@Override
	public void run() {
		while (!this.suspendido.isSuspendido()) {
			contador++;
			setChanged();
			notifyObservers();
			try {
				Thread.sleep(1000);
				this.suspendido.waitReanudar();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public synchronized void addObserver(Observer o) {
		this.observer = o;
	}

	@Override
	public void notifyObservers() {
		if(observer != null) {
			observer.update(this, contador);
		}
	}
	
	
	

}
