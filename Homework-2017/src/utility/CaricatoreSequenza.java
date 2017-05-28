package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import model.Segnale;
//Classe che serve a caricare le 3 sequenze complesse da analizzare tramite il metodo dell'Energy Detection
public class CaricatoreSequenza {
	private int lunghezzaSequenza;
	
	public CaricatoreSequenza(int lunghezza) {
		this.lunghezzaSequenza = lunghezza;
	}
	
	public int getSequenceLen() {
		return lunghezzaSequenza;
	}

	public String[] leggiSequenza(String path) {
		String[] righeSequenza = new String[lunghezzaSequenza];		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String rigaCorrente = br.readLine();
			int i=0;
			while(rigaCorrente != null) {
				righeSequenza[i] = rigaCorrente;
				rigaCorrente = br.readLine();
				i++;
			}
			br.close();
		} catch (IOException e){
			System.out.println("Path del file errato");
		}
		return righeSequenza;
	}
	
	public Segnale parseRighe(String[] righe, String separatore) {
		Segnale s = new Segnale(righe.length);
		List<Double> reali = s.getParteReale();
		List<Double> imm = s.getParteImmaginaria();
		for(String riga : righe) {
			String[] coppia = riga.split(separatore);
			reali.add(Double.parseDouble(coppia[0]));
			imm.add(Double.parseDouble(coppia[1]));
		}
		return s;
	}

}