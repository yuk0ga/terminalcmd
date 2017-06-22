import java.io.File;

/**
 * Created by koga on 2017/06/13.
 */
public class ls {
    public static void main(String[] args) {
        if (args.length == 0) {         // if there isn't any argument
            String workingDir = System.getProperty("user.dir");     //user.dir is the current working directory
        File dir = new File(workingDir);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());         //getName gets only the file name. Without this, the whole path will be listed.
        }
        } else if ((args.length == 1) && args[0].contains("a") && args[0].contains("-")) {     //might not need "args.length == 1"
            String workingDir = System.getProperty("user.dir");
            File dir = new File(workingDir);
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else if ((args.length == 1) && args[0].contains("l") && args[0].contains("-")) {
            System.out.printf("This option is currently unavailable");
        } else if ((args.length == 1) && args[0].contains("t") && args[0].contains("-")) {
            System.out.printf("This option is currently unavailable");
        } else if ((args.length == 1) && args[0].contains("r") && args[0].contains("-")) {
            System.out.printf("This option is currently unavailable");
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
