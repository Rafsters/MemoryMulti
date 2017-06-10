package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JFrame {


    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t;


    public Board() {
        int pairs = 10;
        List<Card> cardsList = new ArrayList<Card>();
        List<Integer> cardVals = new ArrayList<Integer>();

        for (int i = 0; i < pairs; i++) {
            cardVals.add(i);
            cardVals.add(i);
        }
        Collections.shuffle(cardVals);

        for (int val : cardVals) {
            Card c = new Card();
            c.setId(val);
            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectedCard = c;
                    doTurn();
                }
            });
            cardsList.add(c);
        }
        this.cards = cardsList;
        //set up the timer
        t = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                checkCards();
            }
        });

        t.setRepeats(false);

        //set up the board itself
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 5));
        for (Card c : cards) {
            c.setIcon(new ImageIcon(setBlank()));
            pane.add(c);
        }
        setTitle("MemoryMulti");
    }

    public Image setBlank() {
        Image card = null;
        try {
            card = ImageIO.read(getClass().getResource("/res/card.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return card;
    }

    public void doTurn() {
        if (c1 == null && c2 == null) {
            c1 = selectedCard;
            c1.setCardImage(c1.getId());
        }

        if (c1 != null && c1 != selectedCard && c2 == null) {
            c2 = selectedCard;
            c2.setCardImage(c2.getId());
            t.start();

        }
    }

    public void checkCards() {
        if (c1.getId() == c2.getId()) {//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()) {
                JOptionPane.showMessageDialog(this, "You win!");
                System.exit(0);
            }
        } else {
            c1.setIcon(new ImageIcon(setBlank()));
            c2.setIcon(new ImageIcon(setBlank()));
        }
        c1 = null; //reset c1 and c2
        c2 = null;
    }

    public boolean isGameWon() {
        for (Card c : this.cards) {
            if (c.getMatched() == false) {
                return false;
            }
        }
        return true;
    }

}
