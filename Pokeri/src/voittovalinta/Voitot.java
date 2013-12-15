/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voittovalinta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import kortit.Kasi;
import kortit.Kortti;

/**
 * Voitot-luokka huolehtii pelaajan rahojen käsittelystä ja toimii
 * voitontarkistuslogiikkana, eli selvittää, mikä voitto (jos mikään) pelaajan
 * kädessä on.
 *
 */
public class Voitot {

    /**
     * Pelaajan pelissä käyttämä getPanos.
     */
    private int panos;
    /**
     * Viimeisimmän pelaajan saaman voiton suuruus.
     */
    private int viimeisinVoitto;
    /**
     * Kaikki pelaajalle kertyneet voittorahat. Voivat vähentyä myös, mikäli
     * pelaaja pelaa nekin.
     */
    private int voitot;
    /**
     * Pelaajan koneeseen syöttämät getRahat.
     */
    private int rahat;

    /**
     * Luodaan uusi getVoitot-olio, johon talletetaan getRahat-parametrin verran
     * alkupääomaa. Alussa getPanos on 20 senttiä, ja viimeisin voitto sekä
     * getVoitot ovat nollassa.
     *
     * @param getRahat
     */
    public Voitot(int rahat) {
        this.panos = 20;
        this.viimeisinVoitto = 0;
        this.voitot = 0;
        this.rahat = rahat;
    }

    /**
     * Luokan päämetodi, jossa käydään systemaattisesti läpi mahdolliset
     * getVoitot epätodennäköisimmästä todennäköisimpään ja katsotaan, täyttääkö
     * kädessä olevat kortit jonkin voittavan käden edellytykset.
     *
     * @param k käsi, jota halutaan tarkastella
     * @return voittosumma, johon kyseinen voitto oikeuttaa käytössä olevalla
     * panoksella.
     */
    public int tulikoVoittoa(Kasi k) {
        int voitto;
        if (onkoKuningasVariSuora(k)) {
            voitto = (this.panos * 50);
            this.viimeisinVoitto = voitto;
            this.voitot+= voitto;
            return voitto;
        } else if (onkoVariSuora(k)) {
            voitto = (this.panos * 40);
            this.viimeisinVoitto =+voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoTaysKasi(k)) {
            voitto = (this.panos * 10);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoNeloset(k)) {
            voitto = (this.panos * 20);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoVari(k)) {
            voitto = (this.panos * 5);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoSuora(k)) {
            voitto = (this.panos * 4);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoKolmoset(k)) {
            voitto = (this.panos * 2);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else if (onkoKaksiParia(k)) {
            voitto = (this.panos * 2);
            this.viimeisinVoitto = voitto;
            this.voitot +=voitto;
            return voitto;
        } else {

            return 0;
        }
    }

    /**
     * Nostaa panosta kahdellakymmenellä sentillä. Jos getPanos on jo euron, tai
     * jos getPanos on liian suuri pelaajan varoihin (syötetyt
     * getRahat+getVoitot) nähden, palataan takaisin 20 senttiin.
     */
    public void kasvataPanosta() {
        if (this.panos == 100 || this.panos + 20 > this.rahat + this.voitot) {
            this.panos = 20;
        } else {
            this.panos = this.panos + 20;
        }
    }

    /**
     * Palauttaa panoksen takaisin 20 senttiin.
     */
    public void nollaaPanos() {
        this.panos = 20;
    }

    public int getViimeisinVoitto() {
        return this.viimeisinVoitto;
    }

    public int getVoitot() {
        return this.voitot;
    }

    public int getPanos() {
        return this.panos;
    }

    public int getRahat() {
        return this.rahat;
    }

    /**
     * Lisää koneeseen 20 senttiä rahaa.
     */
    public void lisaaRahaa() {
        this.rahat += 20;
    }

    /**
     * Ottaa pelaajan koneeseen syöttämistä rahoista panoksen verran rahaa.
     *
     * @return true, jos toimenpide onnistui, eli jos pelaajalla oli tarpeeksi
     * rahaa. false muulloin, ja tällöin ei myöskään tehdä mitään.
     */
    public boolean otaRahaa() {
        if ((this.rahat + this.voitot) >= this.panos) {
            this.rahat = this.rahat - this.panos;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kuin otaRahaa, mutta käyttää pelaajan pankkina sekä pelaajan koneeseen
     * syöttämiä rahoja, että hänen keräämiään voittoja.
     *
     * @param erotus negatiivinen arvo x: pelaajan rahat - panos = x. Lisätään
     * erotus voittoihin, jolloin voittojen määrä siis vähenee erotuksen verran.
     */
    public void otaVoitoista(int erotus) {
        this.voitot += erotus;
        this.rahat = 0;
    }

    /**
     * Testaa, onko kädessä värin muodostavat kortit, eli onko kaikkien kädessä
     * olevien korttien maa sama.
     *
     * @param k Käsi, jota halutaan tarkastella
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoVari(Kasi k) {
        Kortti c = k.getKortti(0);
        int maa;
        maa = c.haeMaa();
        for (Kortti g : k.listaaKortit()) {
            if ((g.haeMaa()) != maa) {
                return false;
            }
        }
        return true;
    }

    /**
     * Testaa, onko kädessä suoran muodostavat kortit, eli muodostuuko korttien
     * arvoista peräkkäinen lukujen jono.
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoSuora(Kasi k) {
        ArrayList<Integer> arvot = new ArrayList();
        for (Kortti g : k.listaaKortit()) {
            arvot.add(g.haeArvo());
        }
        Collections.sort(arvot);
        for (int i = 0; i < 4; i++) {
            if (arvot.get(i) != arvot.get(i + 1) - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Testaa, onko kädessä täyskäden muodostavat kortit, eli löytyykö kädestä
     * sekä kolmoset, että kahdesta jäljellä olevasta kortista pari.
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoTaysKasi(Kasi k) {
        HashMap<Integer, Integer> arvot = new HashMap<>();
        ArrayList<Integer> maarat = new ArrayList<>();
        int apu;
        for (Kortti g : k.listaaKortit()) {
            if (!arvot.containsKey(g.haeArvo())) {
                arvot.put(g.haeArvo(), 1);
            } else {
                apu = arvot.get(g.haeArvo());
                arvot.put(g.haeArvo(), (apu + 1));
            }
        }
        if (arvot.size() == 2) {
            for (int arvo : arvot.values()) {
                maarat.add(arvo);
            }
            Collections.sort(maarat);
            if (maarat.get(0) == 2) {
                if (maarat.get(1) == 3) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Testaa, onko kädessä kolme samanarvoista korttia.
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoKolmoset(Kasi k) {
        HashMap<Integer, Integer> arvot = new HashMap<>();
        ArrayList<Integer> maarat = new ArrayList<>();
        int apu;
        for (Kortti g : k.listaaKortit()) {
            if (!arvot.containsKey(g.haeArvo())) {
                arvot.put(g.haeArvo(), 1);
            } else {
                apu = arvot.get(g.haeArvo());
                arvot.put(g.haeArvo(), (apu + 1));
            }
        }
        if (arvot.size() == 3) {
            for (int arvo : arvot.values()) {
                maarat.add(arvo);
            }
            Collections.sort(maarat);
            if (maarat.get(0) == 1) {
                if (maarat.get(1) == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Testaa, onko kädessä kaksi samoista arvoista muodostuvaa paria.
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoKaksiParia(Kasi k) {
        if (onkoKolmoset(k) == false) {
            ArrayList<Integer> arvot = new ArrayList();
            for (Kortti g : k.listaaKortit()) {
                if (!arvot.contains(g.haeArvo())) {
                    arvot.add(g.haeArvo());
                }
            }
            if (arvot.size() == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Testaa, onko kädessä neljä samanarvoista korttia.
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoNeloset(Kasi k) {
        HashMap<Integer, Integer> arvot = new HashMap<>();
        int apu;
        for (Kortti g : k.listaaKortit()) {
            if (!arvot.containsKey(g.haeArvo())) {
                arvot.put(g.haeArvo(), 1);
            } else {
                apu = arvot.get(g.haeArvo());
                arvot.put(g.haeArvo(), (apu + 1));
            }
        }
        for (int arvo : arvot.values()) {
            if (arvo == 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * Testaa, onko kädessä värisuora, eli sekä väri, että suora
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoVariSuora(Kasi k) {
        if (onkoVari(k) == true && onkoSuora(k) == true) {
            return true;
        }
        return false;
    }

    /**
     * Testaa, muodostavatko kädessä olevat kortit sekä värin, että suoran, ja
     * ovatko korttien arvot korkeimmat mahdolliset (esim. ♥10, ♥J, ♥Q, ♥K, ♥A)
     *
     * @param k Käsi, jota halutaan tarkastella.
     * @return true tai false riippuen siitä, oliko kädessä tämä voitto.
     */
    private boolean onkoKuningasVariSuora(Kasi k) {
        if (onkoVariSuora(k) == true) {
            for (Kortti g : k.listaaKortit()) {
                if (g.haeArvo() == 12 || g.haeArvo() == 11 || g.haeArvo() == 10 || g.haeArvo() == 9 || g.haeArvo() == 8) {
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
