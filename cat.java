import java.io.*;

/**
 * Created by koga on 2017/06/13.
 */
public class cat {
    public static void main(String[] args) {
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
    }
}
