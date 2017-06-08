package test;

import javax.swing.*;

@SuppressWarnings("serial")
public class Card extends JButton{
    private int id;
    Icon front, back;
    private boolean matched = false;


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }


    public void setMatched(boolean matched){
        this.matched = matched;
    }

    public boolean getMatched(){
        return this.matched;
    }
}