package de.adesso.brainysnake.sampleplayer;

import de.adesso.brainysnake.playercommon.*;
import de.adesso.brainysnake.playercommon.math.Point2D;

import java.awt.*;
import java.util.List;


/**
 * Implementiere hier deine Schlangensteuerung.
 */
public class YourPlayer implements BrainySnakePlayer {

    private PlayerState playerState;
    private PlayerView playerView;
    private Point2D closestPoint;

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
        int xDistanceToHead = 0;
        int yDistanceToHead = 0;

        for (Field field : this.playerView.getVisibleFields()) {
            if (field.getFieldType() == FieldType.POINT) {
                System.out.println(field.getFieldType());
                this.closestPoint = field.getPosition();
            }
        }
        System.out.println(this.closestPoint);
        if (this.closestPoint == snakeHead) {
            this.closestPoint = null;
        }

        if (this.closestPoint != null && !isAhead(this.closestPoint) && this.closestPoint != snakeHead) {
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
        } else if (this.playerState.getMovesRemaining() % 35 == 0 && !barrierAhead() && !barrierToTheRight()) {
            nextMove = turnRight(current);
        } else if (this.closestPoint == snakeHead) {
            this.closestPoint = null;
        }

        if (barrierAhead()) {
            nextMove = turnLeft(current);
            this.closestPoint = null;
        }

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
        if (this.playerView.getVisibleFields().get(23).getFieldType() == FieldType.LEVEL) {
            return true;
        } else {
            return false;
        }
    }

}
