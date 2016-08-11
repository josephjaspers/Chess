package chess;

public class ChessPiece // And board
{

    private int piece; //name of piece
    private int xPos;
    private int xM; //xmove // locations of piece (x,y)
    private int yPos;
    private int yM; //ymove
    private int c; //color (0 = white 1 = black)
    public static int[][] Board = new int[8][8];
    public static int turncount = 0;
    private boolean legalmove;
    private boolean KingChecked = false;

    private boolean Passant = false;
    private boolean WhitePassant;
    private boolean BlackPassant;
    private int PassantX;
    private int PassantY;

    private boolean LWCastling = true;
    private boolean doLWCastling = false;
    private boolean RWCastling = true;
    private boolean doRWCastling = false;

    private boolean LBCastling = true;
    private boolean doLBCastling = false;
    private boolean RBCastling = true;
    private boolean doRBCastling = false;

    private int KingX;
    private int KingY;

    private int xMovementStore;
    private int yMovementStore;
    private int yPositionStore;
    private int xPositionStore;

    /* 
      0 = no piece (11 = black pawn, 10 = white pawn) etc
      10 = pawn
      20 = castle   (20 = white pawn, 21 = black pawn)
      30 = horse
      40 = bishop
      50 = king
      60 = queen
     */
    public int[][] StartBoard() //clears board (For new game) and initializes pieces
    {
        for (int x = 0; x < Board.length; x++) {
            for (int y = 0; y < Board.length; y++) {
                Board[x][y] = 0;
            }
        }

        for (int x = 0; x < Board.length; x++) //initiatlizes all pawns
        {
            Board[x][1] = 10;
            Board[x][6] = 11;
        }
        Board[0][0] = 20;
        Board[7][0] = 22; //intiatlizesrest of pieces
        Board[1][0] = 30;
        Board[6][0] = 30;
        Board[2][0] = 40;
        Board[5][0] = 40;
        Board[4][0] = 50;
        Board[3][0] = 60;

        Board[0][7] = 21;
        Board[7][7] = 23;
        Board[1][7] = 31;
        Board[6][7] = 31;
        Board[2][7] = 41;
        Board[5][7] = 41;
        Board[3][7] = 51;
        Board[4][7] = 61;

        LWCastling = true;
        RWCastling = true;
        LBCastling = true;
        RBCastling = true;
        return Board;
    }

    public int[][] getBoard() //returns Board
    {
        return Board;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getXm() {
        return xM;
    }

    public int getYm() {
        return yM;
    }

    public int getColor(int x, int y) {
        if (Board[x][y] == 0) {
            c = -1;
        } else if (Board[x][y] % 2 == 0) {
            c = 0;
        } else {
            c = 1;
        }

        return c;
    }

    public boolean LegalMove(int xPos, int yPos, int xM, int yM) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xM = xM;
        this.yM = yM;

        if (Board[xPos][yPos] == 0) {
            legalmove = false;
        } else if (getColor(xM, yM) == getColor(xPos, yPos)) {
            legalmove = false;
        } else if (turncount % 2 == 0) // checks whose turn it is 
        {
            switch (Board[xPos][yPos]) // sends to appropriate piece legalchecks
            {
                case 10:
                    legalmove = pawnW();
                    break;
                case 20:
                    legalmove = castle();
                    break;
                case 22:
                    legalmove = castle();
                    break;
                case 30:
                    legalmove = horse();
                    break;
                case 40:
                    legalmove = bishop();
                    break;
                case 50:
                    legalmove = king();
                    break;
                case 60:
                    legalmove = queen();
                    break;
                default:
                    legalmove = false;
            }
        } else {
            switch (Board[xPos][yPos]) {
                case 11:
                    legalmove = pawnB();
                    break;
                case 21:
                    legalmove = castle();
                    break;
                case 23:
                    legalmove = castle();
                    break;
                case 31:
                    legalmove = horse();
                    break;
                case 41:
                    legalmove = bishop();
                    break;
                case 51:
                    legalmove = king();
                    break;
                case 61:
                    legalmove = queen();
                    break;
                default:
                    legalmove = false;
            }
        }
        return legalmove;
    }

    public boolean pawnW() {
        if (yPos == 1 && yM - yPos == 2 && xPos == xM && Board[xPos][yPos + 1] == 0 && Board[xPos][yPos + 2] == 0) // two space move
        {
            legalmove = true;
        } else if (xM == xPos && yM - yPos == 1 && Board[xM][yM] == 0) // single space move
        {
            legalmove = true;
        } else if (Math.abs(xM - xPos) == 1 && yM - yPos == 1 && getColor(xM, yM) == 1) //capture diagnol
        {
            legalmove = true;
        } else if (Passant == true && Math.abs(PassantX - xPos) == 1 && xM == PassantX && yM == PassantY + 1 && yPos == 4)//passent move white
        {
            legalmove = true;
            WhitePassant = true;
        } else {
            legalmove = false;
        }

        if (legalmove == true && yM == 7) {
            Board[xPos][yPos] = 60;
        }

        return legalmove;
    }

    public boolean pawnB() {
        if (yPos == 6 && yM - yPos == -2 && xPos == xM && Board[xPos][yPos - 1] == 0 && Board[xPos][yPos - 2] == 0) // two space move
        {
            legalmove = true;
        } else if (xM == xPos && yM - yPos == -1 && Board[xM][yM] == 0) // single space
        {
            legalmove = true;
        } else if (Math.abs(xM - xPos) == 1 && yM - yPos == -1 && getColor(xM, yM) == 0) // capture
        {
            legalmove = true;
        } else if (Passant == true && Math.abs(PassantX - xPos) == 1 && xM == PassantX && yM == PassantY - 1 && yPos == 3)//passent move white
        {
            legalmove = true;
            BlackPassant = true;
        } else {
            legalmove = false;
        }

        if (legalmove == true && yM == 0) {
            Board[xPos][yPos] = 61;
        }

        return legalmove;
    }

    public boolean castle() 
    {
        if (xPos != xM && yPos == yM) {
            int x = xPos;
            do {
                x -= Math.abs(xPos - xM) / (xPos - xM);
                if (x == xM) {
                    legalmove = true;
                    break;
                } else if (Board[x][yPos] != 0) {
                    legalmove = false;
                    break;
                }
            } while (x != xM);
        } else if (yPos != yM && xPos == xM) {
            int y = yPos;
            do {
                y -= Math.abs(yPos - yM) / (yPos - yM);
                if (y == yM) {
                    legalmove = true;
                    break;
                } else if (Board[xPos][y] != 0) {
                    legalmove = false;
                    break;
                }
            } while (y != yM);
        } else {
            legalmove = false;
        }
        return legalmove;
    }

    public boolean horse() {
        if (Math.abs(xPos - xM) == 1 && Math.abs(yPos - yM) == 2) {
            legalmove = true;
        } else if (Math.abs(xPos - xM) == 2 && Math.abs(yPos - yM) == 1) {
            legalmove = true;
        } else {
            legalmove = false;
        }

        return legalmove;
    }

    public boolean bishop() {
        if (Math.abs(xPos - xM) == Math.abs(yPos - yM)) {
            int x = xPos;
            int y = yPos;
            do {
                if (xPos - xM > 0) {
                    x--;
                } else {
                    x++;
                }
                if (yPos - yM > 0) {
                    y--;
                } else {
                    y++;
                }
                if (x == xM && y == yM) {
                    legalmove = true;
                    break;
                } else if (Board[x][y] != 0) {
                    legalmove = false;
                    break;
                }
            } while (x > 0 && y > 0);
        } else {
            legalmove = false;
        }
        return legalmove;
    }

    public boolean king() 
    {
        if (Math.abs(xPos - xM) <= 1 && Math.abs(yPos - yM) <= 1) {
            legalmove = true;
        } else if (Board[1][0] == 0 && Board[2][0] == 0 && Board[3][0] == 0 && LWCastling == true && xPos - xM == 2 && yPos == yM) {
            legalmove = true;  //most castling at move
        } else if (Board[6][0] == 0 && Board[5][0] == 0 && RWCastling == true && xPos - xM == -2 && yPos == yM) {
            legalmove = true;  //most castling at move
        } else if (Board[1][7] == 0 && Board[2][7] == 0 && LBCastling == true && xPos - xM == 2 && yPos == yM) {
            legalmove = true;  //most castling at move
        } else if (Board[6][7] == 0 && Board[5][7] == 0 && Board[4][7] == 0 && RBCastling == true && xPos - xM == -2 && yPos == yM) {
            legalmove = true;  //most castling at move
        } else {
            legalmove = false;
        }

        return legalmove;
    }

    public boolean queen() {
        if (xPos != xM && yPos == yM) {
            int x = xPos;
            do {
                x -= Math.abs(xPos - xM) / (xPos - xM);
                if (x == xM) {
                    legalmove = true;
                    break;
                } else if (Board[x][yPos] != 0) {
                    legalmove = false;
                    break;
                }
            } while (x != xM);
        } else if (yPos != yM && xPos == xM) {
            int y = yPos;
            do {
                y -= Math.abs(yPos - yM) / (yPos - yM);
                if (y == yM) {
                    legalmove = true;
                    break;
                } else if (Board[xPos][y] != 0) {
                    legalmove = false;
                    break;
                }
            } while (y != yM);
        } else if (Math.abs(xPos - xM) == Math.abs(yPos - yM)) {
            int x = xPos;
            int y = yPos;
            do {
                if (xPos - xM > 0) {
                    x--;
                } else {
                    x++;
                }
                if (yPos - yM > 0) {
                    y--;
                } else {
                    y++;
                }
                if (x == xM && y == yM) {
                    legalmove = true;
                    break;
                } else if (Board[x][y] != 0) {
                    legalmove = false;
                    break;
                }
            } while (x > 0 && y > 0);
        } else {
            legalmove = false;
        }

        return legalmove;
    }

    public void movePiece() // moves piece also checks for castling 
    {
        turncount++;

        if (Board[xPositionStore][yPositionStore] == 20) {
            LWCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 22) {
            RWCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 21) {
            LBCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 23) {
            RBCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 50 && xMovementStore - xPositionStore == -2) /// this is for white castling
        {
            Board[0][0] = 0;
            Board[3][0] = 20;
            LWCastling = false;
            RWCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 50 && xMovementStore - xPositionStore == 2) {
            Board[7][0] = 0;
            Board[5][0] = 22;
            LWCastling = false;
            RWCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 50) {
            RWCastling = false;
            LWCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 51 && xMovementStore - xPositionStore == -2) // black castling 
        {
            Board[0][7] = 0;
            Board[2][7] = 21;
            LBCastling = false;
            RBCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 51 && xMovementStore - xPositionStore == 2) {
            Board[7][7] = 0;
            Board[4][7] = 23;
            LBCastling = false;
            RBCastling = false;
        } else if (Board[xPositionStore][yPositionStore] == 51) {
            RBCastling = false;
            LBCastling = false;
        }

        if (Board[xPositionStore][yPositionStore] == 10 && yPositionStore - yMovementStore == -2) {
            Passant = true;
            PassantX = xMovementStore;
            PassantY = yMovementStore;
        } else if (Board[xPositionStore][yPositionStore] == 11 && yPositionStore - yMovementStore == 2) {
            Passant = true;
            PassantX = xMovementStore;
            PassantY = yMovementStore;
        } else if (WhitePassant == true && Board[xPositionStore][yPositionStore] == 10 && yPositionStore - yMovementStore == 2) {
            Board[PassantX][PassantY] = 0;
        } else if (BlackPassant == true && Board[xPositionStore][yPositionStore] == 11) {
            Board[PassantX][PassantY] = 0;
        } else {
            Passant = false;
            WhitePassant = false;
            BlackPassant = false;
        }

        Board[xMovementStore][yMovementStore] = Board[xPositionStore][yPositionStore];
        Board[xPositionStore][yPositionStore] = 0;
    }

    public void setKingLocation() //set location of the king during the turn
    {
        for (int x = 0; x < Board.length; x++) {
            for (int y = 0; y < Board.length; y++) {
                if (Board[x][y] == 51 - turncount % 2) {
                    KingX = x;
                    KingY = y;
                    System.out.println("" + x + "," + y);
                }
            }
        }
    }

    public int getKingX() {
        return KingX;
    }

    public int getKingY() {
        return KingY;
    }

    public boolean KingCheck() {
        KingChecked = false;
        int[][] TempBoard = new int[8][8];

        xPositionStore = xPos;
        yPositionStore = yPos;
        xMovementStore = xM;
        yMovementStore = yM;

        turncount++;

        for (int x = 0; x < Board.length; x++) // Copy tempboard as storage for the old Board
        {
            for (int y = 0; y < Board.length; y++) {
                TempBoard[x][y] = Board[x][y];
            }
        }

        Board[xMovementStore][yMovementStore] = Board[xPositionStore][yPositionStore]; //temp move piece to check if legal
        Board[xPositionStore][yPositionStore] = 0;

        for (int x = 0; x < Board.length; x++) {
            for (int y = 0; y < Board.length; y++) {
                setKingLocation(); //must be done everytime in case kings moves

                if (LegalMove(x, y, KingX, KingY) == true) {
                    System.out.println("KingChecked is True");
                    KingChecked = true;
                }
            }
        }

        for (int x = 0; x < Board.length; x++) // reset old board
        {
            for (int y = 0; y < Board.length; y++) {
                Board[x][y] = TempBoard[x][y];
            }
        }
        turncount--;
        return KingChecked;
    }

    public boolean CheckMate() {
        boolean Checkmate = true;
        for (int x = 0; x < Board.length; x++) //tile
        {
            for (int y = 0; y < Board.length; y++) {
                for (int xP = 0; xP < Board.length; xP++) {
                    for (int yP = 0; yP < Board.length; yP++) {
                        if (LegalMove(x, y, xP, yP) == true && KingCheck() == false) {
                            Checkmate = false;
                        }
                    }
                }
            }
        }
        return Checkmate;
    }

}
