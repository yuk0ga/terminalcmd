import java.io.*;
import java.util.Scanner;


/**
 * Created by koga on 2017/06/13.
 */

public class cat{
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            File file = new File(args[0]);
        try {
            FileReader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else {
                Scanner sc = new Scanner(System.in);
                while (sc.hasNext()) {
                    String stdin = sc.nextLine();
                    System.out.println(stdin);
                }
        }
    }
}
