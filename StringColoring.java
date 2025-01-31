import java.util.*;

public class StringColoring {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        String s = in.next();

        char[] result = new char[n];
        char maxIn0 = 'a'-1;
        char maxIn1 = 'a'-1;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if(ch>=maxIn0){
                result[i] = '0';
                maxIn0 = ch;
            }else if(ch>=maxIn1){
                result[i] = '1';
                maxIn1 = ch;
            }else{
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
        System.out.println(new String(result));
    }
}
