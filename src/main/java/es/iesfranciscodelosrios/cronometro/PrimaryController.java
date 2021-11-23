package es.iesfranciscodelosrios.cronometro;

import java.util.Observable;
import java.util.Observer;

import es.iesfranciscodelosrios.cronometro.model.Contador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PrimaryController implements Observer {

	@FXML
	private Button btn_iniciar;

	@FXML
	private Button btn_parar;

	@FXML
	private Button btn_reiniciar;

	@FXML
	private Text txt_contador;

	public static Contador c1;
	public static Thread t;
	public static int segundos;

	/**
	 * false significa que el cronometro está corriendo 
	 * true significa que el cronometro está detenido
	 */
	private boolean btn_parar_state = false;

	/**
	 * Se ejecuta al iniciar la aplicación
	 * Deshabilita los botones de parar y reiniciar
	 */
	public void initialize() {
		this.btn_parar.setDisable(true);
		this.btn_reiniciar.setDisable(true);
	}

	/**
	 * Método que se ejecuta cuando pulsamos en el boton "para/continuar"
	 * Depende del estado del boton para realizar dos funciones:
	 * Cuando el estado es true significa que el hilo está corriendo, por tanto
	 * suspendemos el hilo y cambiamos el estado del boton y cambiamos los textos de los
	 * botones
	 */
	public void onParar() {
		// miramos el estado del botón para decidir que hacer
		if (!this.btn_parar_state) {
			// suspendemos el hilo
			c1.getSuspendido().SetSuspendido(true);
			// ponemos el estado del boton en true
			this.btn_parar_state = true;
			// cambiamos el texto
			this.btn_parar.setText("CONTINUAR");
			this.btn_iniciar.setText("DETENIDO");
		} else {
			// reanudamos el hilo
			c1.getSuspendido().SetSuspendido(false);
			this.btn_parar_state = false;
			// cambiamos el texto
			this.btn_parar.setText("PARAR");
			this.btn_iniciar.setText("CONTANDO");
		}
	}

	/**
	 * Método que se ejecuta cuando pulsamos "iniciar"
	 * Instancia el contador y el hilo, añadiendo al 
	 * controlador como observador del contador y empieza
	 * la ejecución del hilo.
	 * 
	 * Cambia a "contando" y deshabilita el botón "inicio"
	 * habilita el botón "parar" y "reiniciar"
	 */
	public void onIniciar() {
		c1 = new Contador();
		t = new Thread(c1);
		c1.addObserver(this);
		t.start();
		
		//Cambiamos el texto
		this.btn_iniciar.setText("CONTANDO");
		//habilito y deshabilito botones
		this.btn_iniciar.setDisable(true);
		this.btn_parar.setDisable(false);
		this.btn_reiniciar.setDisable(false);
		//Cambiamos el estado del boton "parar"
		this.btn_parar_state = false;
	}

	/**
	 * Método que se ejecuta cuando se pulsa en "reiniciar"
	 * Suspende el hilo y lo interrumpe
	 * Setea el contador a 0
	 * Pone el estado del boton parar a false
	 * habilita el boton "iniciar"
	 * deshabilita el boton "parar" y "reiniciar"
	 * cambia el texto de "iniciar" y "parar"
	 */
	public void onReiniciar() {
		txt_contador.setText("00:00:00");
		
		c1.getSuspendido().SetSuspendido(true);
		t.interrupt();
		c1.setContador(0);

		//habilita y deshabilita
		this.btn_parar_state = true;
		this.btn_iniciar.setDisable(false);
		this.btn_parar.setDisable(true);
		this.btn_reiniciar.setDisable(true);
		//cambia texto de los botones
		this.btn_iniciar.setText("INICIAR");
		this.btn_parar.setText("PARAR");

	}

	/**
	 * Recibe los segundos y los formatea para mostrarlos
	 * en la vista
	 * 
	 * @param contador segundos del contador.
	 */
	public void setText(int contador) {
		this.txt_contador.setText(formatSeconds(contador));
	}

	/**
	 * Recibe segundos y devuelve un String formateado en HH:MM:SS
	 * 
	 * @param timeInSeconds segundos
	 * @return segundos formateados en HH:MM:SS
	 */
	public static String formatSeconds(int timeInSeconds) {
		int hours = timeInSeconds / 3600;
		int secondsLeft = timeInSeconds - hours * 3600;
		int minutes = secondsLeft / 60;
		int seconds = secondsLeft - minutes * 60;

		String formattedTime = "";
		if (hours < 10)
			formattedTime += "0";
		formattedTime += hours + ":";

		if (minutes < 10)
			formattedTime += "0";
		formattedTime += minutes + ":";

		if (seconds < 10)
			formattedTime += "0";
		formattedTime += seconds;

		return formattedTime;
	}

	/**
	 * Método que setea el texto cada vez que cambia el contador.
	 * Método de la clase observer.
	 */
	@Override
	public void update(Observable o, Object arg) {
		setText((int) arg);
	}

}
