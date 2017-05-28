# SCR-Homework_2017
- 3 Sequenze Complesse composte ognuna da 1.000.000 di campioni 
- In particolare, ogni campione è composto da parte reale e Parte immaginaria e queste due sono separate da un tab
- Ogni sequenza presenta 4 osservazioni effettuate con SNR diversi: SNR(dB) : (-3,2,-8,-13)
- Metodo di Detection utilizzato: Energy Detection
- Valori fissati: n°Blocchi: 1000 / n° campione per blocco: 1000 / Probabilità Falso Allarme (PFA) = 0,01 / Potenza Segnale Utile (Pu) = 1
- Ipotesi H0 (R(i) = n(i)):
-   1)genero 1000 sequenze rumorose per ogni livello di SNR dato
-   2)Calcolo la potenza di ciascuna sequenza e la memorizzo in una lista di potenze
-   3)Ordino le potenze in modo crescente tramite Sort()
-   4)Calcolo della soglia*

- *: SNR=Pu/Pn con scala lineare.
     Pn = 1/SNR(L)=10^(-SNR(dB)/10) Questo valore è necessario per generare le sequenze di rumore
    Il metodo nextGaussian deve essere moltiplicato per sqrt(Pn/2) avendo due liste distinte per reali ed immaginari, 
    in modo da avere un modulo quadro di R(i) pari a Pn; Varianza=1 Valore Atteso=0 per la Gaussiana (AWGN)
    Le 1000 sequenze di rumore tenderanno ad una potenza media pari a Pn per infiniti campioni (nell'Ipotesi H0)

- Ipotesi H1 (R(i) = n(i) + s(i): All'aumentare dell'SNR (ovvero man mano che si analizzano le 4 sotto-sequenze, dalla più rumorosa in su)
  deve aumentare la Probabilità di Detection (Pd), ovvero Pr>soglia (potenza segnale R(i)) su almeno il 70% dei 1000 blocchi
  Il grafico della Pd in funzione del SNR tenderà a 1 per SNR->+inf e tenderà a Pfa per SNR->-inf 
  4 punti da interpolare per generare la curva che mostra questo andamento di ddp
  
 - Risultati: tabella finale con 3 righe, ognuna per sequenza con una colonna che mostra la Pd e la presenza o meno di Spectrum Hole a quel determinato SNR e una nota conclusiva in cui viene spiegato brevemente se il PU (Utente Primario) sta trasmettendo o no.
- SI: il SU (Utente Secondario) non potrà accedere in modo opportunistico allo spettro del canale, in quanto minerebbe drasticamente la qualità di entrambe le trasmissioni (del PU e del SU)
- NO: il SU potrà accedere al canale per trasmettere i suoi dati
