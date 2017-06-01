package com.codecool.jopburndown.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Motivator {

    private Deque<String> motivationMessages = new LinkedList<>();

    public Motivator() {
        motivationMessages.add("Az élettel az ember úgy van, sodródik, mint bacilus a h*gyban. - Blöró");
        motivationMessages.add("Ne legyél csirke, me' a csirkéket megeszik! - Ismeretlen, alkalmi ivócimbora");
        motivationMessages.add("Zöld sör után, a hó is zöld. - Ezt most találtam ki. ");
        motivationMessages.add("-'Nem vagyunk egyformák.'\n-'De, én egyforma vagyok!'\n- Kocsmai évődés lezárása.");
        motivationMessages.add("Most nem motivállak, mert nem érdemled meg.");
        motivationMessages.add("...Már nyergelem a korongokat, hogy te jól mulass! Ééén a leeemeezlovas!\n - Dévényi Tibi bácsi");
        motivationMessages.add("...Walker egy kopó, egy texasi, retteg tőle minden rossz arcú faszi!\n" +
                "Ha meglátod a jellegzetes alapállást, hát jól vigyázz, mert kezdődik a kalapálás!... - Tomek44");
        motivationMessages.add("Tofu! - Ezt demotiválónak szántam, mert a tofu az rossz.");
        motivationMessages.add("Kódolni jó, mert közben marhaságokat lehet írni Deque-kba!");
    }

    public String getMotivationMessage() {
        Random randomNum = new Random();
        int actualRandomNum = randomNum.nextInt(6);
        List list = (LinkedList) motivationMessages;
        return list.get(actualRandomNum).toString();
    }

    public void setMotivationMessages(String motivationMessages) {
        this.motivationMessages.addFirst(motivationMessages);
        this.motivationMessages.removeLast();
    }
}
