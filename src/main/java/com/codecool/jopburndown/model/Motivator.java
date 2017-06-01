package com.codecool.jopburndown.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class responsible for the great motivational messages under the main page of the game.
 */
public class Motivator {
    private static Motivator instance = null;
    private Deque<String> motivationMessages = new LinkedList<>();

    private Motivator() {
        motivationMessages.add("Ne legyél csirke, me' a csirkéket megeszik! - Ismeretlen, alkalmi ivócimbora");
        motivationMessages.add("Zöld sör után, a hó is zöld. - ezt most találtam ki");
        motivationMessages.add("Az élettel az ember úgy van, sodródik, mint bacilus a h*gyban. - Blöró");
        motivationMessages.add("-'Nem vagyunk egyformák.'\n-'De, én egyforma vagyok!'\n- Kocsmai évődés lezárása.");
        motivationMessages.add("Most nem motivállak, mert nem érdemled meg.");
        motivationMessages.add("...Már nyergelem a korongokat, hogy te jól mulass! Ééén a leeemeezlovas!\n - Dévényi Tibi bácsi");
        motivationMessages.add("Aki másnak vermet ás, az nem magának ássa a vermet! - ismeretlen bölcselő");
        motivationMessages.add("Tofu! - Ezt demotiválónak szántam, mert a tofu az rossz.");
        motivationMessages.add("Kódolni jó, mert közben marhaságokat lehet írni Deque-kba!");
        motivationMessages.add("Aki feladja, az hamarabb megy haza!- Konfúciusz");
        motivationMessages.add("Ne legyél b*lfasz, inkább legyél király! - Szókratész");
        motivationMessages.add("Nyomd már meg Build it! feliratú gombot, aztán játszá'! - a rosszhangulatú szerver");
    }

    /**
     * This method responsible for singletonish operation. If it have an instance
     * it don't make a new one.
     *
     * @return Motivator
     */
    public static Motivator getInstance() {
        if (instance == null) {
            instance = new Motivator();
        }
        return instance;
    }

    /**
     * This method bring back a randomly choosed element from the motivationMessages deque.
     *
     * @return String
     */
    public String getMotivationMessage() {
        Random randomNum = new Random();
        int actualRandomNum = randomNum.nextInt(9);
        List list = (LinkedList) motivationMessages;
        return list.get(actualRandomNum).toString();
    }

    /**
     * @param motivationMessages for the page.
     */
    public void setMotivationMessages(String motivationMessages) {
        this.motivationMessages.addFirst(motivationMessages);
        this.motivationMessages.removeLast();
    }


}
