package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JFrame {


    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t;
    private JPanel boardPanel, pointsPanel;
    private JLabel player1, player2, player1Points, player2Points, round, roundPlayer;
    private int p1Points, p2Points;

    public Board() {
        int pairs = 10;
        p1Points = 0;
        p2Points = 0;
        List<Card> cardsList = new ArrayList<Card>();
        List<Integer> cardVals = new ArrayList<Integer>();
        setLayout(new BorderLayout());
        boardPanel = new JPanel();
        pointsPanel = new JPanel();
        pointsPanel.setLayout(new GridLayout(1, 6));
        pointsPanel.setBackground(new Color(200, 233, 255));
        player1 = new JLabel("Gracz 1");
        player1Points = new JLabel("<html><font color=#000066>" + p1Points + "</font></html>");
        player2 = new JLabel("Gracz 2");
        player2Points = new JLabel("<html><font color=#000066>" + p2Points + "</font></html>");
        round = new JLabel("Tura: ");
        roundPlayer = new JLabel("<html><font color=#660000>" + isPlaying() + "</font></html>");
        player1.setFont(new Font("Serif", Font.PLAIN, 18));
        player2.setFont(new Font("Serif", Font.PLAIN, 18));
        player1Points.setFont(new Font("Serif", Font.PLAIN, 22));
        player2Points.setFont(new Font("Serif", Font.PLAIN, 22));
        round.setFont(new Font("Serif", Font.PLAIN, 18));
        roundPlayer.setFont(new Font("Serif", Font.PLAIN, 22));
        pointsPanel.add(player1);
        pointsPanel.add(player1Points);
        pointsPanel.add(player2);
        pointsPanel.add(player2Points);
        pointsPanel.add(round);
        pointsPanel.add(roundPlayer);
        add(boardPanel, BorderLayout.CENTER);
        add(pointsPanel, BorderLayout.SOUTH);
        boardPanel.setLayout(new GridLayout(4, 5));

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
        
        //Container pane = getContentPane();
        //pane.setLayout(new GridLayout(4, 5));
        for (Card c : cards) {
            c.setIcon(new ImageIcon(setBlank()));
           boardPanel.add(c);
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
    
    boolean player1IsMakingTurn = true;
    boolean player2IsMakingTurn = false;
    public void doTurn() {
    	System.out.println("1: " + p1Points + ", 2: " + p2Points);
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
    
    public boolean isPlayer1MakingTurn(){
    	if(player1IsMakingTurn == true && player2IsMakingTurn == false ){
    		return true;
    	} else if(player1IsMakingTurn == false && player2IsMakingTurn == false ){
    		return true;
    	}else{
    		return false;
    	}
    		
    }
    
    public boolean isPlayer2MakingTurn(){
    	if(player1IsMakingTurn == false && player2IsMakingTurn == true ){
    		return true;
    	} else{
    		return false;
    	}
    		
    }
    
    public String isPlaying(){
    	if(isPlayer1MakingTurn()){
    		return "Gracz 1";
    	} else if(isPlayer2MakingTurn()) {
    		return "Gracz 2";
    	}
		return null;
    }

    public void checkCards() {
    	
    	if(player1IsMakingTurn == true && player2IsMakingTurn == false ) {
        	player1IsMakingTurn = false;
        	player2IsMakingTurn = true;
        } else if(player1IsMakingTurn == false && player2IsMakingTurn == true ) {
        	player1IsMakingTurn = true;
        	player2IsMakingTurn = false;
        }
        if (c1.getId() == c2.getId()) {//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if(isPlayer1MakingTurn()){
            	p1Points++;
            	player1Points.setText("<html><font color=#006600>" + p1Points + "</font></html>");
            }else if(isPlayer2MakingTurn()){
            	p2Points++;
            	player2Points.setText("<html><font color=#000066>" + p2Points + "</font></html>");
            }
            if (this.isGameWon()) {
            	if(p1Points > p2Points){
            		JOptionPane.showMessageDialog(this, "Gracz 1 wygrywa!");
            	} else if(p1Points < p2Points){
            		JOptionPane.showMessageDialog(this, "Gracz 2 wygrywa!");
            	} else{
            		JOptionPane.showMessageDialog(this, "Remis!");
            	}
                System.exit(0);
            }
        } else {
            c1.setIcon(new ImageIcon(setBlank()));
            c2.setIcon(new ImageIcon(setBlank()));
        }
        c1 = null; //reset c1 and c2
        c2 = null;
        roundPlayer.setText("<html><font color=#660000>" + isPlaying() + "</font></html>");
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
