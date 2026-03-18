package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Move;
import model.Pokemon;
import util.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokeApiService {

    public static Pokemon getPokemon(String name){

        try{

            String json = HttpUtil.get("https://pokeapi.co/api/v2/pokemon/"+name);

            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            String pokemonName = obj.get("name").getAsString();

            int hp = obj.getAsJsonArray("stats")
                    .get(0).getAsJsonObject()
                    .get("base_stat").getAsInt();

            int attack = obj.getAsJsonArray("stats")
                    .get(1).getAsJsonObject()
                    .get("base_stat").getAsInt();

            String sprite = obj.getAsJsonObject("sprites")
                    .get("front_default").getAsString();

            JsonArray movesJson = obj.getAsJsonArray("moves");

            List<Move> moves = new ArrayList<>();

            Random rand = new Random();

            for(int i=0;i<4 && i<movesJson.size();i++){

                int index = rand.nextInt(movesJson.size());

                String moveName = movesJson
                        .get(index).getAsJsonObject()
                        .getAsJsonObject("move")
                        .get("name").getAsString();

                moves.add(new Move(moveName,40+rand.nextInt(40),80+rand.nextInt(20)));
            }

            return new Pokemon(pokemonName,hp,attack,sprite,moves);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}