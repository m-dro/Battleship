package battleship;

import battleship.helpers.CoordinateManager;
import battleship.helpers.Gamefield;
import battleship.helpers.Player;
import battleship.ships.Ship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Course: JetBrains Academy, Java Developer Track
 * Project: Battleship
 * Purpose: A console-based program to simulate the classic game Battleships.
 *
 * SeaBattle is the main class representing the game.
 * It holds references to:
 * <ul>
 *     <li>players (Player 1 and Player 2)</li>
 * </ul>
 *
 * @author Mirek Drozd
 * @version 1.1
 */
public class SeaBattle {
    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

    /**
     * Starts the game.
     * After each player puts their ships on their gamefield, the game begins.
     */
    public void run(){
        setup(player1);
        setup(player2);
        playGame();
    }

    /**
     * Asks the player to place their ships on the gamefield.
     * Then waits for the player to hit ENTER (RETURN) so that the other player can do the same.
     *
     * @param player who decides where to put their ships on the gamefield.
     */
    public void setup(Player player) {
        System.out.printf("%s, place your ships on the game field\n\n", player.getName());
        player.getManager().showGameField(player.getGamefield().getOpenGamefield());
        player.getManager().placeShips();
        waitForEnter();
    }

    /**
     * Waits for the player to hit enter, to pass the move to the other player.
     * Both players play on the same machine, and this mechanisms prevents
     * players from cheating by seeing the other player's gamefield.
     */
    public void waitForEnter() {
        System.out.println("\nPress Enter and pass the move to another player");
        System.out.println("...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    /**
     * Selects players one by one so that they can play, and displays 2 gamefields:
     * - one showing their opponent's gamefield, hidden after 'fog of war' with any previous
     *  hits and/or misses marked; and
     *  - one showing their own gamefield, with all their ships, with any previous
     *  hits and/or misses by their opponent.
     *
     * To play the game, players take turns at firing their cannons at the other player's gamefield.
     * After one player sanks the last ship of the other player, the game ends.
     */
    public void playGame() {
        Player currentPlayer;
        int round = 1;

        while (true) {
            currentPlayer = checkPlayer(round);
            currentPlayer.getManager().showGameField(currentPlayer.getGamefield().getCoveredGamefield());
            System.out.println("---------------------");
            currentPlayer.getManager().showGameField((currentPlayer.getGamefield().getOpenGamefield()));
            if (fight(currentPlayer) == false) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            round++;
            waitForEnter();
        }
    }

    /**
     * Checks whose turn it is (which player is to play).
     *
     * @param round of the game (Player 1's turn is in odd-numbered rounds, Player 2's in even-numbered rounds)
     * @return player whose turn it is
     */
    public Player checkPlayer(int round) {
        if (round % 2 != 0) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Reads the coordinates of the shot fired by the player, and makes sure they are valid
     * (within the gamefield area)
     *
     * @return coordinates of the shot
     */
    public String readShot() {
        Scanner scanner = new Scanner(System.in);
        String coordinate = scanner.nextLine();
        if (isValid(coordinate)) {
            return  coordinate;
        } else {
            return readShot();
        }
    }

    /**
     * Checks if the coordinates entered by the player are valid.
     * Valid coordinate is a letter from A to J followed by a number between 1 and 10.
     *
     * @param coordinate to be checked for validity
     * @return true if coordinates are valid
     *         false otherwise
     */
    public boolean isValid(String coordinate) {
        Pattern pattern = Pattern.compile("([ABCDEFGHIJ](10|[0-9])\\s?){1}");
        Matcher matcher = pattern.matcher(coordinate);
        return matcher.matches();
    }

    /**
     * Asks the player whose turn it is to fire their cannons.
     *
     * @param player whose turn it is
     * @return true if, after the player fires, the other player still has some ships left
     *         false otherwise
     */
    public boolean fight(Player player) {
        System.out.printf("%s, it's your turn:\n\n", player.getName());
        String shot = readShot();
        return checkShot(shot, player);
    }

    /**
     * Checks if the shot was a hit or a miss.
     * In other words, if there was some ship where the shot was fired, or not.
     *
     * @param shot fired by the player
     * @param player who fired the shot
     * @return true if the shot was a hit
     *         false otherwise
     */
    public boolean checkShot(String shot, Player player) {
        CoordinateManager manager = player.getManager();
        char[][] playerGamefield = player.getGamefield().getCoveredGamefield();
        char[][] opponentGamefield;
        Player opponent;
        if (player.equals(player1)) {
            opponentGamefield = player2.getGamefield().getOpenGamefield();
            opponent = player2;
        } else {
            opponentGamefield = player1.getGamefield().getOpenGamefield();
            opponent = player1;
        }

        int row = manager.numericalCoordinate(shot.substring(0,1));
        int column = Integer.parseInt(shot.substring(1));

        /*
        if there was a 'O' where the fire was shot,
        this means that there was a ship, and it's a hit (marked with 'X').
        Otherwise, it's a miss (marked with 'M').
         */

        if (opponentGamefield[row][column] == 'O'){
            playerGamefield[row][column] = 'X';
            opponentGamefield[row][column] = 'X';
            manager.showGameField(playerGamefield);
            if (anyShipsLeft(opponentGamefield) == false) {
                return false;
            } else {
                damageShip(shot, opponent);
            }
        } else {
            playerGamefield[row][column] = 'M';
            opponentGamefield[row][column] = 'M';
            manager.showGameField(playerGamefield);
            System.out.println("\nYou missed!");
        }

        return true;
    }

    /**
     * Checks if there are any ships left that haven't been sank yet.
     *
     * @param gamefield where to check for ships
     * @return true if there are still some ships alive, or
     *         false otherwise
     */
    public boolean anyShipsLeft(char[][] gamefield) {
        for (int i = 0; i < gamefield.length; i++) {
            for (int j = 0; j < gamefield[i].length; j++) {
                if (gamefield[i][j] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Inflicts damage on the ship, and tells the user the extent of damage (hit or sank).
     * If this was the last element of the ship that was still afloat (marked with 'O'),
     * the ship is considered to have been sank, otherwise it's just been hit.
     *
     * @param shot fired by the player
     * @param opponent whose ship is being fired at
     */
    public void damageShip(String shot, Player opponent) {
        Ship damagedShip = whichShip(shot, opponent);
        damagedShip.getCoordinates().remove(shot);
        if (damagedShip.getCoordinates().size() == 0) {
            System.out.println("You sank a ship!");
        } else {
            System.out.println("You hit a ship!");
        }
    }

    /**
     * Determines which ship is being fired at.
     *
     * @param shot fired by the player
     * @param player whose ship is being targeted
     * @return ship that is targeted
     */
    public Ship whichShip (String shot, Player player) {
        Ship[] ships = player.getShips();
        for (Ship ship : ships) {
            if (ship.getCoordinates().contains(shot)) {
                return ship;
            }
        }
        return null;
    }
}
