/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kortit;

import java.util.*;

/**
 *
 * Korttipakka koostuu 52 kortti-oliosta, jotka luodaan korttipakan
 * konstruktorissa. Sisältää myös metodit päällimmäisen kortin jakamiseen, pakan
 * sekoittamiseen ja kortin lisäämiseen takaisin pakkaan
 */
public class Korttipakka {

    /**
     * ArrayList, johon tallennetaan pakan kaikki 52 kortti-oliota.
     */
    private ArrayList<Kortti> pakka;

    /**
     * Luodaan pakka ja sen kortit sisäkkäisillä for-silmukoilla, joissa
     * jokaista neljää maata kohden luodaan 13 korttia.
     */
    public Korttipakka() {

        this.pakka = new ArrayList<>();


        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 13; i++) {

                pakka.add(new Kortti(j, i));

            }
        }
    }

    /**
     * Lisätään Kortti pakkaan (kun jaetaan uusi käsi yms.)
     *
     * @param Kortti jakajan antama Kortti
     */
    public void lisaaKortti(Kortti kortti) {
        this.pakka.add(kortti);
    }

    /**
     * Otetaan pakan päällimmäinen (array listin indeksissä nolla sijaitseva)
     * Kortti.
     *
     * @return Kortti, joka sijaitsee pakan indeksissä 0
     */
    public Kortti PaallimmaisinKortti() {
        Kortti k;
        k = this.pakka.get(0);
        this.pakka.remove(0);
        return k;
    }

    /**
     * Laskee pakassa olevien korttien määrän, lähinnä testausta varten.
     *
     * @return pakan koko numerona
     */
    public int paljonkoKortteja() {
        return this.pakka.size();
    }

    /**
     * Listaa pakassa olevat kortit, lähinnä testausta varten.
     */
    public ArrayList<Kortti> mitkaKortit() {
        return this.pakka;
    }

    /**
     * Sekoittaa pakan.
     */
    public void sekoitaPakka() {
        Collections.shuffle(pakka);
    }

    /**
     * Tulostetaan pakassa olevat kortit siinä järjestyksessä kuin ne ovat.
     * Testausta varten.
     */
    @Override
    public String toString() {
        String kortit = "";
        for (Kortti k : pakka) {
            kortit = (kortit + "\n" + k.toString());

        }
        return kortit;
    }
}
