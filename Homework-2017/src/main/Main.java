package main;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import ipotesi.IpotesiH0;
import ipotesi.IpotesiH1;
import model.Segnale;
import utility.CaricatoreSequenza;

public class Main {
	public static final int NUMERO_CAMPIONI = 1000000;
	public static final int NUMERO_SEQUENZE = 3;
	public static final int NUMERO_OSSERVAZIONI = 4;
	public static final int NUMERO_BLOCCHI = 1000;
	public static final List<Double> LIVELLI_SNR = Arrays.asList(-3.0, +2.0, -8.0, -13.0);
	private static final double PERCENTUALE_SICUREZZA_DETECTION = 70.0;

	public static void main(String[] args) {
		CaricatoreSequenza cs = new CaricatoreSequenza(NUMERO_CAMPIONI);
		IpotesiH0 HP0 = new IpotesiH0();
		IpotesiH1 HP1 = new IpotesiH1();


		//Mappa con chiavi gli SNR e valori le Pd ordinate per SNR crescenti
		TreeMap<Double,Double> SNRToDetection = new TreeMap<Double,Double>();

		//Calcolo delle soglie generando 1000 sequenze di rumore per ogni SNR		
		List<Double> valori_soglie = HP0.calcoloSoglie(LIVELLI_SNR);

		for(int i=0;i<valori_soglie.size();i++) {
			System.out.println("SNR = "+LIVELLI_SNR.get(i)+" Soglia = "+valori_soglie.get(i));
		}   

		for(int seq=1;seq<=NUMERO_SEQUENZE;seq++) {	//controllo le 3 sequenze
			System.out.print("Sequenza "+seq+" [ ");
			for(int soglia=0;soglia<NUMERO_OSSERVAZIONI;soglia++) {	//leggo le 4 osservazioni di ogni sequenza e le confronto con le 4 soglie
				//Carico il file contenente parte reale e imm. dentro un Segnale e separato da un tab
				Segnale s = cs.parseRighe(cs.leggiSequenza("Sequenze/Sequenza_"+seq+"/output_"+(soglia+1)+".dat"),"\t");

				//Devo dividere il Segnale 1000 blocchi da 1000 campioni rispettivamente
				List<Segnale> blocchi_Segnale = s.separaSegnaleInBlocchi(NUMERO_BLOCCHI);

				//Probabilità di detection di segnale utile immerso in rumore gaussiano e conseguente presenza o meno di SpectrumHole
				double probabilitaDetection = HP1.calcolaProbabilitaDetection(blocchi_Segnale, valori_soglie.get(soglia));
				boolean spectrumHole = HP1.isSpectrumHole(valori_soglie.get(soglia), s);

				//Aggiungo alla mappa i valori di SNR e Pd                        
				SNRToDetection.put(LIVELLI_SNR.get(soglia), probabilitaDetection);

				System.out.print("Osservazione "+(soglia+1)+": Pd="+probabilitaDetection + "% - ");
				System.out.print("SpectrumHole = " + spectrumHole + "\t");  //False = Canale Occupato - True = Canale Libero

			}

			System.out.println(" ]");

			//risultato corrisponde al verdetto finale sulla presenza o meno del PU nel Canale avendo settato la percentuale di sicurezza di Detection al 70%
			boolean risultato = HP1.risultatoAnalisiSequenza(SNRToDetection, PERCENTUALE_SICUREZZA_DETECTION);

			System.out.println("Canale occupato dal Primario: "+risultato);
		}
	}


}



