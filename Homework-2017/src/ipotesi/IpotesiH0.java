package ipotesi;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import model.Rumore;
        //HP0: in questa ipotesi devo generare le varie sequenze rumorose per poter calcolare la soglia "eta" per poterla utilizzare
        //nell'HP1 per determinare se vi è o no lo Spectrum Hole nel canale del primario. I vari passi da seguire sono stati numerati nei vari commenti di questa classe
public class IpotesiH0 {
	private static final int NUMERO_SEQUENZE_RUMORE = 1000;
	private static final int NUMERO_CAMPIONI_RUMORE = 1000;
	private static final double PROBABLITA_FALSO_ALLARME = 0.01; //10^-2
	
	//PASSO 1 - Genero 1000 sequenze rumorose
	public List<Rumore> creaSequenzeRumorose(int numeroSequenze,int lunghezzaSequenza,double snr) {
		List<Rumore> sequenzeRumorose = new ArrayList<Rumore>(numeroSequenze);	
		for(int i=0;i<numeroSequenze;i++) {
			sequenzeRumorose.add(new Rumore(lunghezzaSequenza, snr));	//Rumore si occupa di creare la sequenza casuale con snr indicato
		}	
		return sequenzeRumorose;
	}
	
	public List<Double> calcoloSoglie(List<Double> livelli_SNR) {
		List<Double> valori_soglie = new ArrayList<Double>(livelli_SNR.size());
		for(int snr=0;snr<livelli_SNR.size();snr++) {
			//Genero 1000 sequenze di rumore da 1000 campioni ciascuna con SNR dato
			List<Rumore> sequenzeRumore = creaSequenzeRumorose(NUMERO_SEQUENZE_RUMORE, NUMERO_CAMPIONI_RUMORE, livelli_SNR.get(snr));
			List<Double> potenzeSequenze = new ArrayList<Double>(sequenzeRumore.size());		
			//PASSO 2 - Popolo la lista delle potenze con quelle di ogni sequenza di rumore
			for(int i = 0; i<sequenzeRumore.size(); i++) {
				potenzeSequenze.add(i, sequenzeRumore.get(i).potenza());
			}
			//PASSO 3 - Ordino le potenze calcolate in ordine crescente
			Collections.sort(potenzeSequenze);
			//Listta da 0 a 999, solo 10 devono essere sopra soglia, 990..999 
			//PASSO 4 - calcolo della Soglia 
			valori_soglie.add(snr, potenzeSequenze.get((NUMERO_SEQUENZE_RUMORE-1)-(int)(NUMERO_SEQUENZE_RUMORE*PROBABLITA_FALSO_ALLARME)));	
		}
		return valori_soglie;
	}
	

}
