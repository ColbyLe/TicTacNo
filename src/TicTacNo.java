// Colby Le
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TicTacNo {
    static char[][] board;
    static Socket s1;
    static InetAddress addr;
    static int maxDepth = 9;
    public static void main(String[] args) throws IOException {
        board = new char[3][3];
        try {
            s1 = new Socket("www.dagertech.net", 3800);
            addr = s1.getInetAddress();

            System.out.println("Connecting to " + addr + ":3800");

            InputStreamReader isr = new InputStreamReader(s1.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw1 = new PrintWriter(s1.getOutputStream(), true);
            String serverOut = "";

            do {
                serverOut = "";
                //pw1.println("version\n");
                while((serverOut = br.readLine()) != null) {
                    System.out.println(serverOut);
                }
                pw1.print('x');
            } while(!serverOut.contains("Wins!"));

            s1.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int matrixToNBoard() {

        return 0;
    }

    static void NBoardToMatrix(String NBoard) {
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

            }
            else {

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

    static void getServerMove() {

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
