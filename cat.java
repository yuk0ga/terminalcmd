import java.applet.Applet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

/**
 * Created by koga on 2017/06/13.
 */

/*
abstract class Exit extends Applet implements KeyListener {
    public void init() {
        addKeyListener(this);
    }
    public void keyPressed(KeyEvent e) {
    }
}
*/
public class cat {
    public static void main(String[] args) {
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
            int x = 1;
            while(x < 10) {
                Scanner sc = new Scanner(System.in);
                String stdin = sc.nextLine();
                System.out.println(stdin);
                x++;
                //if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_D) {
                //    break;
                //}
            }
        }
    }
}
