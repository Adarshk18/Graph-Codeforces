import java.io.*;

public class AmoguLanguage {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        

        int t = Integer.parseInt(br.readLine().trim()); 

        for (int i = 0; i < t; i++) {
            String singular = br.readLine().trim();
            String plural = convertToPlural(singular); 
            pw.println(plural); 
        }
        pw.flush();
    }

    private static String convertToPlural(String singular) {
        return singular.substring(0, singular.length() - 2) + "i";
    }
}