/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voittovalinta;

import java.util.*;
import kortit.Kasi;
import kortit.Kortti;
import kortit.Korttipakka;

/**
 * Toimii rajapintana pelaajan käden ja korttipakan välillä. Jakaa uudet kortit
 * käteen ja sekoittaa vanhat kortit kädestä pakkaan.
 */
public class Jakaja {

    public Jakaja() {
    }

    /**
     * Jakaa uuden käden. Jos kädessä ei ole yhtään korttia, jaetaan uudet viisi
     * korttia, muussa tapauksessa jaetaan lukitsemattomien korttien tilalle
     * uudet kortit, jonka jälkeen sekoitetaan vanhat kortit takaisin pakkaan.
     *
     * @param k käsi, johon jaetaan
     * @param p pakka, josta jaetaan
     */
    public void uusiKasi(Kasi k, Korttipakka p) {
        ArrayList<Kortti> poistettavat = new ArrayList<>();
        int uudetKortit = 0;
        if (k.listaaKortit().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                jaaKortti(k, p);
            }
        } else {
            for (Iterator<Kortti> it = k.listaaKortit().iterator();it.hasNext();){
                Kortti g = it.next();
                if(!g.olenkoLukittu()){
                    poistettavat.add(g);
                    uudetKortit++;
                    it.remove();
                    
                }
            }
            for (int i = 0; i < uudetKortit; i++) {
                jaaKortti(k, p);
            }
            for(Kortti c:poistettavat){
                sekoitaKorttiPakkaan(c, p);
            }
        }
    }

    /**
     * Jakaa käteen k pakan p päällimmäisen kortin.
     *
     * @param k käsi, johon jaetaan
     * @param p korttipakka, josta uusi kortti jaetaan
     */
    private void jaaKortti(Kasi k, Korttipakka p) {
        k.otaKateen(p.PaallimmaisinKortti());
    }

    /**
     * Sekoittaa kortin k korttipakkaan p.
     *
     * @param k kortti, joka sekoitetaan pakkaan
     * @param p pakka, johon kortti sekoitetaan
     */
    private void sekoitaKorttiPakkaan(Kortti k, Korttipakka p) {
        p.lisaaKortti(k);
        p.sekoitaPakka();
    }
}
