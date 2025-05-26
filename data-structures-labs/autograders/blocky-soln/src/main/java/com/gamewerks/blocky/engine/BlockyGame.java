package com.gamewerks.blocky.engine;

import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;

public class BlockyGame {
    private static final int LOCK_DELAY_LIMIT = 30;
    
    private Board board;
    private Piece activePiece;
    private Direction movement;
    
    private int lockCounter;
  
    ///// BEGIN BAG SHUFFLING CODE    
    private PieceKind[] bag;
    private int bagIndex;

    // From: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    private void bagShuffle() {
        for (int i = bag.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            PieceKind tmp = bag[i];
            bag[i] = bag[j];
            bag[j] = tmp;
        }
    }

    private PieceKind drawFromBag() {
        if (bagIndex >= bag.length) {
            bagShuffle();
            bagIndex = 0;
        }
        return bag[bagIndex++];
    }
    ///// END BAG SHUFFLING CODE
    
    public BlockyGame() {
        board = new Board();
        movement = Direction.NONE;
        lockCounter = 0;

        ///// BEGIN BAG INITIALIZATION CODE
        bag = new PieceKind[] {
            PieceKind.I, PieceKind.J, PieceKind.L, PieceKind.O,
            PieceKind.S, PieceKind.T, PieceKind.Z
        };
        bagShuffle();
        bagIndex = 0;
        ///// END BAG INITIALIZATION CODE
        
        trySpawnBlock();
    }
    
    private void trySpawnBlock() {
        if (activePiece == null) {
            /* activePiece = new Piece(PieceKind.I, new Position(Constants.BOARD_HEIGHT - 1, Constants.BOARD_WIDTH / 2 - 2)); */
            activePiece = new Piece(drawFromBag(), new Position(Constants.BOARD_HEIGHT - 1, Constants.BOARD_WIDTH / 2 - 2));
            if (board.collides(activePiece)) {
                System.exit(0);
            }
        }
    }
    
    private void processMovement() {
        /* ... */
        if (activePiece == null) { return; }
        Position nextPos;
        switch(movement) {
        case NONE:
            nextPos = activePiece.getPosition();
            break;
        case LEFT:
            nextPos = activePiece.getPosition().add(0, -1);
            break;
        case RIGHT:
            nextPos = activePiece.getPosition().add(0, 1);
            /* ... */
            break;
        default:
            throw new IllegalStateException("Unrecognized direction: " + movement.name());
        }
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            activePiece.moveTo(nextPos);
        }
    }
    
    private void processGravity() {
        Position nextPos = activePiece.getPosition().add(-1, 0);
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            lockCounter = 0;
            activePiece.moveTo(nextPos);
        } else {
            if (lockCounter < LOCK_DELAY_LIMIT) {
                lockCounter += 1;
            } else {
                board.addToWell(activePiece);
                lockCounter = 0;
                activePiece = null;
            }
        }
    }
    
    private void processClearedLines() {
        board.deleteRows(board.getCompletedRows());
    }
    
    public void step() {
        trySpawnBlock();
        processGravity();
        /* ... */
        processMovement();
        processClearedLines();
    }
    
    public boolean[][] getWell() {
        return board.getWell();
    }
    
    public Piece getActivePiece() { return activePiece; }
    public void setDirection(Direction movement) { this.movement = movement; }
    public void rotatePiece(boolean dir) {
        /* activePiece.rotate(dir); */
        activePiece.rotate(dir);
        if (board.collides(activePiece)) {
            activePiece.rotate(!dir);
        }
    }
}
