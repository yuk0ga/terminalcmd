import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;



/**
 * Created by koga on 2017/06/13.
 */
public class ls {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {         // if there isn't any argument
            String workingDir = System.getProperty("user.dir");     //user.dir is the current working directory
        File dir = new File(workingDir);
        File[] files = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return !file.isHidden();
                }
            });
        for (File file : files) {
            System.out.println(file.getName() + "     "); //getName gets only the file name. Without this, the whole path will be listed.
        }
        } else if (args[0].contains("a") && args[0].contains("-")) {    // -a
            String workingDir = System.getProperty("user.dir");
            File dir = new File(workingDir);
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else if (args[0].contains("l") && args[0].contains("-")) {    // -l
            String workingDir = System.getProperty("user.dir");
            File dir = new File(workingDir);
            File[] files = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return !file.isHidden();
                }
            });
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm");
            String username = System.getProperty("user.name");
            for (File file : files) {
                System.out.printf(username + "\t");
                System.out.printf(file.length() + "\t");
                System.out.printf(sdf.format(file.lastModified()) + "\t");
                System.out.println(file.getName());
            }
        } else if (args[0].contains("t") && args[0].contains("-")) {    // -t
            System.out.println("This option is currently unavailable");
        } else if (args[0].contains("r") && args[0].contains("-")) {    // -r
            System.out.println("This option is currently unavailable");
        }
        else {
            String calledDir = System.getProperty("user.dir")+ "/" + args[0];
            File argdir = new File(calledDir);
            File[] argfiles = argdir.listFiles();
            for (File argfile: argfiles) {
                System.out.println(argfile.getName());
            }
        }
    }
}

//今のやり方だと、-latrとかやったときに-aのみ実行されてしまう。(6/22)