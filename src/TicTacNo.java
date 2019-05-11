// Colby Le
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TicTacNo {
    static char[][] board;
    public static void main(String[] args) throws IOException {
        board = new char[3][3];
        try {
            Socket s1 = new Socket("www.dagertech.net", 3800);
            InetAddress addr = s1.getInetAddress();

            System.out.println("Connecting to " + addr + ":3800");

            InputStreamReader isr = new InputStreamReader(s1.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            PrintWriter pw1 = new PrintWriter(s1.getOutputStream(), true);
            //pw1.println("version\n");
            String serverOut = "";
            while((serverOut = br.readLine()) != null) {
                System.out.println(serverOut);
            }
            s1.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printBoard() {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                if(board[i][j] == '-') System.out.print("  ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void getServerMove() {

    }

    public static void makeMove() {

    }

    public static int min(int depth) {
        return 0;
    }

    public static int max(int depth) {
        return 0;
    }

    public static int checkWin() {
        return 0;
    }

    public static int checkGameOver() {
        return 0;
    }
}
