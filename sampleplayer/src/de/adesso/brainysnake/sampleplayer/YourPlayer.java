package de.adesso.brainysnake.sampleplayer;

import de.adesso.brainysnake.playercommon.*;
import de.adesso.brainysnake.playercommon.math.Point2D;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Implementiere hier deine Schlangensteuerung.
 */
public class YourPlayer implements BrainySnakePlayer {

    private PlayerState playerState;
    private PlayerView playerView;
    private Point2D closestPoint;
    private int movesUntilTurn = 50;

    @Override
    public String getPlayerName() {
        return "xXKillah420BlaZeITXx";
    }

    @Override
    public boolean handlePlayerStatusUpdate(PlayerState playerState) {
        this.playerState = playerState;
        this.playerView = playerState.getPlayerView();
        return true;
    }

    @Override
    public PlayerUpdate tellPlayerUpdate() {
        Orientation current = this.playerView.getCurrentOrientation();
        Orientation nextMove = current;
        Point2D snakeHead = this.playerState.getPlayersHead();
        int xDistanceToHead;
        int yDistanceToHead;

        for (Field field : this.playerView.getVisibleFields()) {
            if (field.getFieldType() == FieldType.POINT) {
                System.out.println(field.getFieldType());
                this.closestPoint = field.getPosition();
            }
        }


        if (this.closestPoint != null && !isAhead(this.closestPoint) && !this.closestPoint.equals(snakeHead)) {
            xDistanceToHead = snakeHead.getX() - this.closestPoint.getX();
            yDistanceToHead = snakeHead.getY() - this.closestPoint.getY();
            switch (current) {
                case UP:
                    if (xDistanceToHead > 0) {
                        nextMove = turnLeft(current);
                    } else if (xDistanceToHead < 0) {
                        nextMove = turnRight(current);
                    } else {
                        nextMove = current;
                    }
                    break;
                case DOWN:
                    if (xDistanceToHead > 0) {
                        nextMove = turnRight(current);
                    } else if (xDistanceToHead < 0) {
                        nextMove = turnLeft(current);
                    } else {
                        nextMove = current;
                    }
                    break;
                case LEFT:
                    if (yDistanceToHead > 0) {
                        nextMove = turnLeft(current);
                    } else if (yDistanceToHead < 0) {
                        nextMove = turnRight(current);
                    } else {
                        nextMove = current;
                    }
                    break;
                case RIGHT:
                    if (yDistanceToHead > 0) {
                        nextMove = turnRight(current);
                    } else if (yDistanceToHead < 0) {
                        nextMove = turnLeft(current);
                    } else {
                        nextMove = current;
                    }
                    break;
            }
        } else if (this.closestPoint != null && this.closestPoint.equals(snakeHead)) {
            this.closestPoint = null;
            System.out.println("GEFRESSEN");
        } else if (this.playerState.getMovesRemaining() % movesUntilTurn == 0 && barrierToTheRight()) {
            nextMove = turnLeft(current);
            generateMovesUntilTurn();
        }

        if (barrierAhead()) {
            nextMove = turnLeft(current);
            generateMovesUntilTurn();
            this.closestPoint = null;
        }

        System.out.println(this.closestPoint);

        return new PlayerUpdate(nextMove);
    }

    private boolean isAhead(Point2D point) {
        for (int i = 2; i <= 22; i += 5) {
            if (point == this.playerView.getVisibleFields().get(i).getPosition()) {
                return true;
            }
        }
        return false;
    }

    private Orientation turnLeft(Orientation current) {
        switch (current) {
            case UP:
                return Orientation.LEFT;
            case DOWN:
                return Orientation.RIGHT;
            case LEFT:
                return Orientation.DOWN;
            case RIGHT:
                return Orientation.UP;
        }
        return null;
    }

    private Orientation turnRight(Orientation current) {
        switch (current) {
            case UP:
                return Orientation.RIGHT;
            case DOWN:
                return Orientation.LEFT;
            case LEFT:
                return Orientation.UP;
            case RIGHT:
                return Orientation.DOWN;
        }
        return null;
    }

    private boolean barrierAhead() {
        // Check if the visible field in front of the snake's head is a barrier
        if (this.playerView.getVisibleFields().get(22).getFieldType() == FieldType.LEVEL) {
            return true;
        } else {
            return false;
        }
    }

    private boolean barrierToTheRight() {
        // Check if the visible field to the right of the snake's head is a barrier
        if (this.playerView.getVisibleFields().get(23).getFieldType() == FieldType.LEVEL && !barrierAhead()) {
            return true;
        } else {
            return false;
        }
    }

    // Generates a pseudo random number between 40 and 80
    // Used to determine the number of moves after which the snake (randomly) makes a right turn
    private void generateMovesUntilTurn() {
        this.movesUntilTurn = ThreadLocalRandom.current().nextInt(40, 81);
    }

}
