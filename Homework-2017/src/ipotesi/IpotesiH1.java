package ipotesi;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import model.Segnale;

public class IpotesiH1 {
	//l'ipotesi H1 prevede di verificare se il canale è libero dal PU e consente quindi al SU di potervi accedere in modo opportunistico
	//in pratica, calcoliamo la Detection del segnale e verifichiamo (in ogni osservazione) se vi è uno spectrum hole (detto anche White Space) nel canale del primario

	//La detection Pd è il valore di riferimento per comprendere se nel canale è in corso o meno la trasmissione del primario
	public double calcolaProbabilitaDetection(List<Segnale> blocchiSequenza, double soglia) {
		double contaSopraSoglia = 0;	//Conto quante volte la potenza di segnale > soglia (No white space)
		for(Segnale s : blocchiSequenza) {
			double pot = s.potenza();	//Calcolo potenza di ogni singola sequenza dei blocchi
			if(pot > soglia)	//Se la potenza del singolo blocco (o Segnale) è sopra la soglia ho segnale utile
				contaSopraSoglia++;
		}
		return (contaSopraSoglia/blocchiSequenza.size())*100;
	}

	public boolean isSpectrumHole(double soglia, Segnale s){
		double potenza = s.potenza();
		return potenza<soglia;
	}

	//Metodo che mi ritorna la conferma o la presenza del PU nel canale dati i risultati delle 4 osservazioni
	public boolean risultatoAnalisiSequenza(TreeMap<Double,Double> SNRToDetection, double percentualeSicurezza) {
		boolean verifica = true;
		//Lista delle Percenduali Detection ordinate per SNR crescente
		LinkedList<Double> detections = new LinkedList<Double>(SNRToDetection.values());
		Double detection = detections.removeFirst();
		while(detections.size()>0 && verifica) {
			Double temp = detections.removeFirst();
			if(temp < detection || temp < percentualeSicurezza) //se le Pd non sono crescenti con gli SNR e gli SNR sono sotto la soglia di sicurezza 
				verifica = false;                               //(stimata intorno al 70%), allora il PU non è presente nel canale 
			detection = temp;                                            
		}
		return verifica;
	}

}
