package model;

import java.util.ArrayList;
import java.util.List;

public class Segnale{
	private List<Double> parteReale;
	private List<Double> parteImmaginaria;
	private int lunghezza;


	public Segnale(int length){
		this.parteReale = new ArrayList<Double>(length);
		this.parteImmaginaria = new ArrayList<Double>(length);
		this.lunghezza = length;
	}

	public Segnale(int length, List<Double> parteReale, List<Double> parteImmaginaria) {
		this.lunghezza = length;
		this.parteReale = parteReale;
		this.parteImmaginaria = parteImmaginaria;
	}

	public List<Double> getParteReale() {
		return parteReale;
	}

	public void setParteReale(List<Double> parteReale) {
		this.parteReale = parteReale;
	}

	public List<Double> getParteImmaginaria() {
		return parteImmaginaria;
	}

	public void setParteImmaginaria(List<Double> parteImmaginaria) {
		this.parteImmaginaria = parteImmaginaria;
	}

	public int getLunghezza(){
		return this.lunghezza;
	}

	public void setLunghezza(int lunghezza) {
		this.lunghezza = lunghezza;
	}

	public double potenza() { //P = 1/N*(Sommatoria[per i che va da 1 a N]((Re(i))^2)+(Imm(i))^2))
		double potenza=0;		
		for(int i=0; i<this.getLunghezza(); i++) {
			potenza+=(Math.pow(this.parteReale.get(i),2) + Math.pow(this.parteImmaginaria.get(i),2));
		}	
		return potenza/this.getLunghezza();
	}

	private List<List<Double>> SequenzaSeparataInBlocchi(List<Double> campioni, int numeroBlocchi) {	//partendo da 10^6 campioni
		int numeroCampioni = campioni.size()/numeroBlocchi;
		List<List<Double>> sequenzaSeparata = new ArrayList<List<Double>>(numeroBlocchi);//numero blocchi = 1000
		for(int i=0;i<numeroBlocchi;i++) {
			List<Double> temp = new ArrayList<Double>(numeroCampioni);   //numero campioni per blocco = 1000 
			for(int j=0;j<numeroCampioni;j++) {	
				temp.add(campioni.get(j+i*numeroCampioni));	//j[0 a 999]+i[0 a 999]*1.000																					//
				//per prelevare campione corretto
			}
			sequenzaSeparata.add(temp);
		}		
		return sequenzaSeparata;
	}

	public List<Segnale> separaSegnaleInBlocchi(int numeroBlocchi) {
		List<Segnale> BlocchiSegnale = new ArrayList<Segnale>(numeroBlocchi);
		List<List<Double>> BlocchiReali = this.SequenzaSeparataInBlocchi(this.getParteReale(), numeroBlocchi);
		List<List<Double>> BlocchiImmaginari = this.SequenzaSeparataInBlocchi(this.getParteImmaginaria(), numeroBlocchi);
		//Ho separato singolarmente la parte reale ed immaginaria
		for(int i=0;i<numeroBlocchi;i++) {
			BlocchiSegnale.add(new Segnale(this.lunghezza/numeroBlocchi));
			List<Double> reali = BlocchiReali.get(i); 
			List<Double> immaginari = BlocchiImmaginari.get(i);
			BlocchiSegnale.get(i).setParteReale(reali);
			BlocchiSegnale.get(i).setParteImmaginaria(immaginari);
		}
		return BlocchiSegnale;
	}




}
