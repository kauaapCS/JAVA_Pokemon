import api.PokeApiService;
import battle.BattleSystem;
import model.Pokemon;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu Pokémon:");

        String name = scanner.nextLine();

        Pokemon player = PokeApiService.getPokemon(name);

        if(player == null){
            System.out.println("Erro ao carregar seu Pokémon.");
            return;
        }

        System.out.println("Você escolheu: " + player.getName());

        Random random = new Random();

        int randomId = random.nextInt(151) + 1;

        Pokemon enemy = PokeApiService.getPokemon(String.valueOf(randomId));

        if(enemy == null){
            System.out.println("Erro ao carregar Pokémon inimigo.");
            return;
        }

        System.out.println("\nUm Pokémon selvagem apareceu!");
        System.out.println("É um " + enemy.getName() + "!");

        while(true){

            System.out.println("\n1 - Atacar");

            int option = scanner.nextInt();

            if(option == 1){

                BattleSystem.attack(player, enemy);

                if(BattleSystem.isDead(enemy)){
                    System.out.println("Você venceu!");
                    break;
                }

                BattleSystem.attack(enemy, player);

                if(BattleSystem.isDead(player)){
                    System.out.println("Você perdeu!");
                    break;
                }

            }

        }

    }

}