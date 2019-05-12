// Colby Le
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TicTacNo {
    static char[][] board;
    public static void main(String[] args) throws IOException {
        // instantiate and populate board matrix
        board = new char[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                board[i][j] = '-';
            }
        }
        try {
            // instantiate network object stuff
            Socket s1 = new Socket("www.dagertech.net", 3800);
            InetAddress addr = s1.getInetAddress();

            System.out.println("Connecting to " + addr + ":3800");

            System.out.println("\nThis program will always pick the first available space.");
            System.out.println("It's designed to lose.\n");

            InputStreamReader isr = new InputStreamReader(s1.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            PrintWriter pw1 = new PrintWriter(s1.getOutputStream(), true);

            // choose X
            pw1.println("X\n");
            System.out.println(br.readLine());
            String serverOut;
            String nBoard;
            char move;

            System.out.println(br.readLine());

            // continue while server sends non-null values
            while((serverOut = br.readLine()) != null) {
                // if length of output is 9, check if win message
                if(serverOut.length() == 9) {
                    // if win message, print message and break loop
                    if(serverOut.charAt(serverOut.length()-1) == '!') {
                        System.out.println(serverOut);
                        break;
                    }

                    // if not win message, convert board to matrix and print
                    nBoard = serverOut;
                    NBoardToMatrix(nBoard);
                    printBoard();

                    // iterate through board and select first available position as move
                    for(int i=0; i<nBoard.length(); i++) {
                        if((nBoard.charAt(i) != 'X')&&(nBoard.charAt(i) != 'O')) {
                            move = nBoard.charAt(i);
                            // send move to server
                            pw1.println(move + "\n");
                            break;
                        }
                    }
                }

                // if length of output is greater than 9, print output
                else {
                    System.out.println(serverOut);
                }
            }
            // close socket
            s1.close();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        // iterate through matrix and print value at current position
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                if(board[i][j] == '-') System.out.print("- ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
