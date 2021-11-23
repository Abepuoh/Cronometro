package es.iesfranciscodelosrios.cronometro.model;

public class SolicitaSuspender {

	private boolean suspendido; // false -> hilo est� corriendo - true -> est� suspendido
	
	

	public boolean isSuspendido() {
		return suspendido;
	}

	public synchronized void SetSuspendido(boolean b) {
		this.suspendido = b;
		notifyAll();
	}

	public synchronized void waitReanudar() throws InterruptedException {
		while(this.suspendido) {
			wait();
		}
	}
}
