/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Joseph
 */
public class Pieces {

    static int PlayerColor;
    static int numberOfPieces = 0;
    static int numberOfBlackPieces = 0;
    static int numberOfWhitePieces = 0;
    static String[][] BoardState = new String[8][8];
    static int turn = 0;

    private int xPosition;
    private int yPosition;
    String color;
    String pieceType;
    String[] piecesType = {"Pawn", "Castle", "Horse", "Bishop", "Queen", "King"};
    String[] Colors = {"White", "Black"};

    Pieces(String type, String color, int yPos, int xPos) throws Exception {
        boolean realPiece = false;
        for (String s : piecesType) {
            if (type.equals(s)) {
                realPiece = true;
            }
        }
        if (!color.equals("White") && !color.equals("Black")) {
            throw new Exception(color + " is not a valid color");
        }
        if (!realPiece) {
            throw new Exception(type + " is not a valid piece");
        }

        pieceType = type;
        this.color = color;
        xPosition = xPos;
        yPosition = yPos;
        BoardState[yPos][xPos] = type; //check if piece is present

        ++numberOfPieces;

        if (this.color.equals("White")) {
            ++numberOfWhitePieces;
        } else {
            ++numberOfBlackPieces;
        }
    }

    boolean legalMove(int xPos, int yPos) {
        if (xPos < 0 || xPos > 7) {
            throw new IndexOutOfBoundsException("piece must be within board width");
        }
        if (yPos < 0 || yPos > 7) {
            throw new IndexOutOfBoundsException("piece must be within board length");
        }
        if (yPos == yPosition && xPos == xPosition) {
            return false;
        }
        //CheckProperTurn
        if (turn % 2 == 0) {
            if (color.equals("Black")) {
                return false;
            }
        } else if (color.equals("White")) {
            return false;
        }
        //Check if Attacking Same Color Piece 
        if (AttackingWrongColor(xPos, yPos)) {
            return false;
        }

        switch (pieceType) {
            case "Pawn":
                return pawnMove(xPos, yPos); //movement type is determined by color
            case "Horse":
                return horseMove(xPos, yPos);
            case "Bishop":
                return bishopMove(xPos, yPos);
            case "Castle":
                return castleMove(xPos, yPos);
            case "Queen":
                return queenMove(xPos, yPos);
            case "King":
                return kingMove(xPos, yPos); //movement type is determined by color 
            default:
                System.out.println("invalid piece type");
                return false;
        }
    }
    boolean kingMove(int x, int y) { //Castling is not yet Coded
        if (Math.abs(xPosition - x) > 1 || Math.abs(yPosition -y) > 1) { 
            return false; 
        } else {
            return true; 
        }
    }
    boolean AttackingWrongColor(int x, int y) {
        if (BoardState[y][x] != null) {
            if (color.equals((String) BoardState[y][x])) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    boolean Attacking(int x, int y) {
        if (BoardState[y][x] != null) {
            return true;
        } else {
            return false;
        }
    }

    boolean pawnMove(int x, int y) {

        if (color.equals("White")) {
            return whitePawnMove(x, y);
        } else {
            return blackPawnMove(x, y);
        }
    }
//Need Enpaissent 
    boolean queenMove(int x, int y) {
        if (castleMove(x,y) || bishopMove(x,y)) {
            return true;
        } else {
            return false; 
        }
    }
    boolean castleMove(int x, int y) {
        if (xPosition != x && yPosition != y) { //check that at least one axis is static 
            return false;
        }
        int xChecker = yPosition;
        int yChecker = xPosition;
        while (xChecker != x || yChecker != y);
        {
            if (xChecker != x) {
                if (BoardState[yChecker][xChecker] != null) {
                    return false;
                }
                xChecker += Math.abs(xPosition - x) / (xPosition - x);
                yChecker += Math.abs(yPosition - y) / (yPosition - y);

            }
        }
        return true;
    }

    boolean whitePawnMove(int x, int y) {
        if (Math.abs(yPosition - y) > 2) {
            return false;
        }
        if (Attacking(x, y)) {
            if (Math.abs(xPosition - x) != 1 && (yPosition - y) != -1) {
                return false;
            }
        } else if (xPosition != x) {
            return false;
        } else if (yPosition == 1) {
            if ((yPosition - y) == -2) {
                return true;
            }
        } else if ((yPosition - y) == -1) {
            return true;
        } else {
            return false;
        }
        return false;
    }

    boolean blackPawnMove(int x, int y) {
        if (Math.abs(yPosition - y) > 2) {
            return false;
        }
        if (Attacking(x, y)) {
            if (Math.abs(xPosition - x) != 1 && (yPosition - y) != 1) {
                return false;
            }
        } else if (xPosition != x) {
            return false;
        } else if (yPosition == 1) {
            if ((yPosition - y) == 2) {
                return true;
            }
        } else if ((yPosition - y) == 1) {
            return true;
        } else {
            return false;
        }
        return false;
    }

    boolean horseMove(int x, int y) {
        if (Math.abs(xPosition - x) == 2 && Math.abs(yPosition - y) == 1) {
            return true;
        } else if (Math.abs(xPosition - x) == 1 && Math.abs(yPosition - y) == 2) {
            return true;
        }
        return false;
    }

    boolean bishopMove(int x, int y) {
        if (Math.abs(xPosition - x) != Math.abs(yPosition - y)) {
            return false;
        } else {
            int xChecker = xPosition;
            int yChecker = yPosition;

            while (xChecker != x);
            {
                if (xChecker != x) {
                    if (BoardState[yChecker][xChecker] != null) {
                        return false;
                    }
                    xChecker += Math.abs(xPosition - x) / (xPosition - x);
                    yChecker += Math.abs(yPosition - y) / (yPosition - y);

                }
            }
        }
        return true;
    }
}
