package model;

import java.util.List;

public class Pokemon {

    private String name;
    private int hp;
    private int attack;
    private String sprite;
    private List<Move> moves;

    public Pokemon(String name, int hp, int attack, String sprite, List<Move> moves){
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.sprite = sprite;
        this.moves = moves;
    }

    public String getName(){
        return name;
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getAttack(){
        return attack;
    }

    public String getSprite(){
        return sprite;
    }

    public List<Move> getMoves(){
        return moves;
    }
}