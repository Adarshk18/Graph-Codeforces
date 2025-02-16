import java.io.*;
import java.util.*;

public class BrogrammingContest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());
        while (t-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();

            int moves = 0;
            char prevChar = s.charAt(0);
            for (int i = 1; i < n; i++) {
                if (s.charAt(i) != prevChar) {
                    moves++;
                    prevChar = s.charAt(i);
                }
            }

           
            if (s.charAt(0) == '1') {
                moves++;
            }

            pw.println(moves);
        }
        pw.flush();
        pw.close();
    }
}