package battle;

import model.Move;
import model.Pokemon;

import java.util.Random;

public class BattleSystem {

    private static Random random = new Random();

    public static String attack(Pokemon attacker, Pokemon defender, Move move){

        int hitChance = random.nextInt(100);

        if(hitChance > move.getAccuracy()){
            return attacker.getName()+" usou "+move.getName()+" mas ERROU!";
        }

        int damage = (attacker.getAttack() + move.getPower()) / 3;

        defender.setHp(Math.max(defender.getHp() - damage,0));

        return attacker.getName()+" usou "+move.getName()+" causando "+damage+" de dano!";
    }

    public static boolean isDead(Pokemon p){
        return p.getHp() <= 0;
    }
}