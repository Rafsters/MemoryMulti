package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Card extends JButton{
    private int id;
    private boolean matched = false;
    Image pikachu, bulbasaur;


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setCardImage(int id){
        try {
            pikachu = ImageIO.read(getClass().getResource("/res/pikachu.jpg"));
            bulbasaur = ImageIO.read(getClass().getResource("/res/bulbasaur.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        switch (id) {
            case 0:
                this.setIcon(new ImageIcon(pikachu));
                break;
            case 1:
                this.setIcon(new ImageIcon(bulbasaur));
                break;
        }
    }

    public void setMatched(boolean matched){
        this.matched = matched;
    }

    public boolean getMatched(){
        return this.matched;
    }
}