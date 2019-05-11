// Colby Le
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TicTacNo {
    static char[][] board;
    static Socket s1;
    static InetAddress addr;
    static int maxDepth = 9;
    public static void main(String[] args) throws IOException {
        // instantiate and populate board matrix
        board = new char[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                board[i][j] = '-';
            }
        }


        try {
            s1 = new Socket("www.dagertech.net", 3800);
            addr = s1.getInetAddress();
            System.out.println("Connecting to " + addr + ":3800");

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s1.getInputStream(), "UTF-8"));
            PrintWriter pw1 = new PrintWriter(s1.getOutputStream(), true);
            DataOutputStream outToServer = new DataOutputStream(s1.getOutputStream());
            String serverOut;

            System.out.print("AI-COM/OTTO\nvs\n");
            while((serverOut = inFromServer.readLine()) != null) {
                System.out.println(serverOut + "\n");
            }

            outToServer.writeUTF("X\n");
            //pw1.write("X\n");
            serverOut = inFromServer.readLine();
            System.out.println(serverOut);

            do {
                // get client move
                makeMove();
                // print current state of board
                System.out.println("Client Move: ");
                printBoard();

                // pass move to server as 9Board
                outToServer.writeUTF(matrixToNBoard());
                serverOut = inFromServer.readLine();
                if(serverOut.length() == 9) {
                    NBoardToMatrix(serverOut);
                    System.out.println("Server Move: ");
                    printBoard();
                }
                else {
                    while((serverOut) != null) {
                        System.out.println(serverOut);
                    }
                }
                //pw1.println("version\n");

            } while(!serverOut.contains("Wins!"));
            s1.close();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static String matrixToNBoard() {
        // instantiate StringBuilder sb
        StringBuilder sb = new StringBuilder();
        // iterate through matrix
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                // if an 'X' or 'O' is encountered, write to sb
                if(board[i][j] == 'X') sb.append('X');
                else if(board[i][j] == 'O') sb.append('O');
                // if any other value is encountered, convert matrix indices to integer value and write value to sb
                else {
                    int wr=0;
                    switch(i) {
                        case 0:
                            wr = i+j+1;
                            break;
                        case 1:
                            wr = i+j+3;
                            break;
                        case 2:
                            wr = i+j+5;
                            break;
                    }
                    sb.append(wr);
                }
            }
        }
        // return string
        return sb.toString();
    }

    static void NBoardToMatrix(String NBoard) {
        // iterate through 9Board
        // if 9Board contains an X or O,
        // replace value at corresponding position of board matrix with appropriate character
        for(int i=0; i< NBoard.length(); i++) {
            if(NBoard.charAt(i) == 'X') {
                switch(i) {
                    case 0:
                        board[0][0] = 'X';
                        break;
                    case 1:
                        board[0][1] = 'X';
                        break;
                    case 2:
                        board[0][2] = 'X';
                        break;
                    case 3:
                        board[1][0] = 'X';
                        break;
                    case 4:
                        board[1][1] = 'X';
                        break;
                    case 5:
                        board[1][2] = 'X';
                        break;
                    case 6:
                        board[2][0] = 'X';
                        break;
                    case 7:
                        board[2][1] = 'X';
                        break;
                    case 8:
                        board[2][2] = 'X';
                        break;
                }

            }

            else if(NBoard.charAt(i) == 'O') {
                switch(i) {
                    case 0:
                        board[0][0] = 'O';
                        break;
                    case 1:
                        board[0][1] = 'O';
                        break;
                    case 2:
                        board[0][2] = 'O';
                        break;
                    case 3:
                        board[1][0] = 'O';
                        break;
                    case 4:
                        board[1][1] = 'O';
                        break;
                    case 5:
                        board[1][2] = 'O';
                        break;
                    case 6:
                        board[2][0] = 'O';
                        break;
                    case 7:
                        board[2][1] = 'O';
                        break;
                    case 8:
                        board[2][2] = 'O';
                        break;
                }
            }
        }
    }


    static void printBoard() {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                if(board[i][j] == '-') System.out.print("  ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int evaluate() {
        return 0;
    }

    static void makeMove() {
        int best = -20000,
                depth = maxDepth,
                score, mi=0, mj=0;
        for(int i=0; i<3;i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j]=='-') {
                    // make move
                    board[i][j] = 'X';
                    score = min(depth-1);
                    if(score>best) {
                        mi = i;
                        mj = j;
                        best = score;
                    }
                    // undo move
                    board[i][j] = '-';
                }
            }
        }
        board[mi][mj] = 'x';
    }

    static int min(int depth) {
        int best = 20000, score;
        if(checkWin() != 0) return checkWin();
        if(depth == 0) return evaluate();
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == '-') {
                    // make move
                    board[i][j] = 'O';
                    score = max(depth-1);
                    if(score<best) best = score;
                    // undo move
                    board[i][j] = '-';
                }
            }
        }
        return best;
    }

    static int max(int depth) {
        int best = -20000, score;
        if(checkWin() != 0) return checkWin();
        if(depth == 0) return evaluate();
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == 0) {
                    //make move
                    board[i][j] = 'X';
                    score = min(depth-1);
                    if(score>best) best = score;
                    board[i][j] = '-';
                }
            }
        }
        return best;
    }

    static int checkWin() {
        // client wins
        if ((board[0][0]==1)&&(board[0][1]==1)&&(board[0][2]==1)
                || (board[1][0]=='X')&&(board[1][1]=='X')&&(board[1][2]=='X')
                || (board[2][0]=='X')&&(board[2][1]=='X')&&(board[2][2]=='X')
                || (board[0][0]=='X')&&(board[1][0]=='X')&&(board[2][0]=='X')
                || (board[0][1]=='X')&&(board[1][1]=='X')&&(board[2][1]=='X')
                || (board[0][2]=='X')&&(board[1][2]=='X')&&(board[2][2]=='X')
                || (board[0][0]=='X')&&(board[1][1]=='X')&&(board[2][2]=='X')
                || (board[0][2]=='X')&&(board[1][1]=='X')&&(board[2][0]=='X')) {
            return 5000;
        }
        // server wins
        if ((board[0][0]=='O')&&(board[0][1]=='O')&&(board[0][2]=='O')
                || (board[1][0]=='O')&&(board[1][1]=='O')&&(board[1][2]=='O')
                || (board[2][0]=='O')&&(board[2][1]=='O')&&(board[2][2]=='O')
                || (board[0][0]=='O')&&(board[1][0]=='O')&&(board[2][0]=='O')
                || (board[0][1]=='O')&&(board[1][1]=='O')&&(board[2][1]=='O')
                || (board[0][2]=='O')&&(board[1][2]=='O')&&(board[2][2]=='O')
                || (board[0][0]=='O')&&(board[1][1]=='O')&&(board[2][2]=='O')
                || (board[0][2]=='O')&&(board[1][1]=='O')&&(board[2][0]=='O')) {
            return -5000;
        }
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                if (board[i][j]=='-') return 0;
            }
        // draw
        return 1;
    }

    static int checkGameOver() {
        printBoard();
        if(checkWin() == 5000) System.out.println("Client wins");
        if(checkWin() == -5000) System.out.println("Server wins");
        if(checkWin() == 1) System.out.println("Draw");
        return 0;
    }
}
