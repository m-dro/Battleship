package battleship;

import battleship.helpers.CoordinateManager;
import battleship.helpers.Gamefield;
import battleship.helpers.Player;
import battleship.ships.Ship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeaBattle {
    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

    public void run(){
        setup(player1);
        setup(player2);
        playGame();
    }

    public void setup(Player player) {
        System.out.printf("%s, place your ships on the game field\n\n", player.getName());
        player.getManager().showGameField(player.getGamefield().getOpenGamefield());
        player.getManager().placeShips();
        waitForEnter();
    }

    public void waitForEnter() {
        System.out.println("\nPress Enter and pass the move to another player");
        System.out.println("...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

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

    public Player checkPlayer(int round) {
        if (round % 2 != 0) {
            return player1;
        } else {
            return player2;
        }
    }

    // ================================================================================

    public String readShot() {
        Scanner scanner = new Scanner(System.in);
        String coordinate = scanner.nextLine();
        if (isValid(coordinate)) {
            return  coordinate;
        } else {
            return readShot();
        }
    }

    public boolean isValid(String coordinate) {
        Pattern pattern = Pattern.compile("([ABCDEFGHIJ](10|[0-9])\\s?){1}");
        Matcher matcher = pattern.matcher(coordinate);
        return matcher.matches();
    }

    public boolean fight(Player player) {
        System.out.printf("%s, it's your turn:\n\n", player.getName());
        String shot = readShot();
        return checkShot(shot, player);
    }

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
     * @param gamefield the gamefield where to check for ships
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

    public void damageShip(String shot, Player opponent) {
        Ship damagedShip = whichShip(shot, opponent);
        damagedShip.getCoordinates().remove(shot);
        if (damagedShip.getCoordinates().size() == 0) {
            System.out.println("You sank a ship!");
        } else {
            System.out.println("You hit a ship!");
        }
    }

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
