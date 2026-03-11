import api.PokeApiService;
import battle.BattleSystem;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Move;
import model.Pokemon;

import java.util.Random;

public class GameUI extends Application {

    private Pokemon player;
    private Pokemon enemy;

    private Move playerMove1;
    private Move playerMove2;
    private Move playerMove3;
    private Move playerMove4;

    private Label playerHp;
    private Label enemyHp;

    private TextArea battleLog;

    private ImageView playerImg;
    private ImageView enemyImg;

    private Button move1;
    private Button move2;
    private Button move3;
    private Button move4;

    private boolean battleEnded = false;

    @Override
    public void start(Stage stage){

        Label title = new Label("Digite o nome ou ID do Pokémon");

        TextField pokemonInput = new TextField();
        pokemonInput.setPromptText("Ex: pikachu ou 25");

        Button startBattle = new Button("Iniciar batalha");

        playerImg = new ImageView();
        enemyImg = new ImageView();

        playerHp = new Label();
        enemyHp = new Label();

        battleLog = new TextArea();
        battleLog.setEditable(false);
        battleLog.setPrefHeight(200);

        move1 = new Button();
        move2 = new Button();
        move3 = new Button();
        move4 = new Button();

        disableMoves();

        startBattle.setOnAction(e -> {

            String chosen = pokemonInput.getText().toLowerCase().trim();

            if(chosen.isEmpty()){
                log("Digite um Pokémon!");
                return;
            }

            player = PokeApiService.getPokemon(chosen);

            if(player == null){
                log("Pokémon não encontrado.");
                return;
            }

            int randomId = new Random().nextInt(1025) + 1;
            enemy = PokeApiService.getPokemon(String.valueOf(randomId));

            playerImg.setImage(new Image(player.getSprite()));
            enemyImg.setImage(new Image(enemy.getSprite()));

            playerHp.setText("HP: " + player.getHp());
            enemyHp.setText("HP: " + enemy.getHp());

            battleLog.clear();

            log("Você escolheu " + player.getName());
            log("Um " + enemy.getName() + " apareceu!");

            // MOVES FIXOS DO JOGADOR
            playerMove1 = player.getMoves().get(0);
            playerMove2 = player.getMoves().get(1);
            playerMove3 = player.getMoves().get(2);
            playerMove4 = player.getMoves().get(3);

            move1.setText(playerMove1.getName());
            move2.setText(playerMove2.getName());
            move3.setText(playerMove3.getName());
            move4.setText(playerMove4.getName());

            enableMoves();

            battleEnded = false;
        });

        move1.setOnAction(e -> attack(playerMove1));
        move2.setOnAction(e -> attack(playerMove2));
        move3.setOnAction(e -> attack(playerMove3));
        move4.setOnAction(e -> attack(playerMove4));

        VBox root = new VBox(
                title,
                pokemonInput,
                startBattle,
                enemyImg,
                enemyHp,
                playerImg,
                playerHp,
                move1,
                move2,
                move3,
                move4,
                battleLog
        );

        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        Scene scene = new Scene(root,420,650);

        stage.setTitle("Pokemon Battle");
        stage.setScene(scene);
        stage.show();
    }

    private void attack(Move move){

        if(battleEnded) return;

        String result = BattleSystem.attack(player,enemy,move);

        log(result);

        enemyHp.setText("HP: "+enemy.getHp());

        if(BattleSystem.isDead(enemy)){
            log("Você venceu!");
            battleEnded = true;
            disableMoves();
            return;
        }

        Move enemyMove = enemy.getMoves()
                .get(new Random().nextInt(enemy.getMoves().size()));

        String enemyResult = BattleSystem.attack(enemy,player,enemyMove);

        log(enemyResult);

        playerHp.setText("HP: "+player.getHp());

        if(BattleSystem.isDead(player)){
            log("Você perdeu!");
            battleEnded = true;
            disableMoves();
        }
    }

    private void disableMoves(){
        move1.setDisable(true);
        move2.setDisable(true);
        move3.setDisable(true);
        move4.setDisable(true);
    }

    private void enableMoves(){
        move1.setDisable(false);
        move2.setDisable(false);
        move3.setDisable(false);
        move4.setDisable(false);
    }

    private void log(String message){
        battleLog.appendText(message + "\n");
    }

    public static void main(String[] args){
        launch();
    }
}