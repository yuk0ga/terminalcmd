import java.io.*;
import java.util.Scanner;


/**
 * Created by koga on 2017/06/13.
 */

public class cat{
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {                  //if there IS an argument
            File file = new File(args[0]);      //tells that the argument is a file.
        try {
            FileReader r = new FileReader(file);        //reads file
            BufferedReader br = new BufferedReader(r);      //buffers file to speed up operation.
            String line;
            while ((line = br.readLine()) != null) {        //prints each line inside of a file until there is no line left.
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();        // displays error details
        } catch (IOException e) {
            e.printStackTrace();        // displays error details
        }
        } else {
                Scanner sc = new Scanner(System.in);        // scans input from keyboard
                while (sc.hasNext()) {                 // while scanner scans something. (ends with Ctrl + D)
                    String stdin = sc.nextLine();       // sets scanned value as string
                    System.out.println(stdin);      //prints string
                }
        }
    }
}
