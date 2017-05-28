package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rumore extends Segnale{
	private static final int POTENZASEGNALE = 1;  
	private double SNR;

	public Rumore(int lunghezza) {
		super(lunghezza);
	}

	public Rumore(int lunghezza, double snr) {
		super(lunghezza);
		this.SNR = snr;
		this.setParteReale(this.CreaSequenzaRumorosa(lunghezza, snr));
		this.setParteImmaginaria(this.CreaSequenzaRumorosa(lunghezza, snr));
	}

	public double getSNR() {
		return this.SNR;
	}

	private List<Double> CreaSequenzaRumorosa(int lunghezza, double snr) {
		List<Double> sequenza = new ArrayList<Double>(lunghezza);
		Random r = new Random(); //serve per generare randomicamente le sequenze rumorose
		double snr_lineare = Math.pow(10, snr/10); //l'snr di partenza è in dB, noi dobbiamo linearizzarlo
		double potenzaRumore = POTENZASEGNALE/snr_lineare;	//PN
		for(int i=0;i<lunghezza;i++){
			//il metodo nextGaussian() crea una sequenza con valor medio = 0 e PN = 1
			//per convertirlo in quello desiderato dobbiamo moltiplicarlo per la radice di PN
			//Dividiamo PN per 2 perchè abbiamo diviso le sequenze
			sequenza.add(r.nextGaussian()*Math.sqrt(potenzaRumore/2)); 
		}		                                                      
		return sequenza;
	}
}



