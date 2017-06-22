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
        } else {
            String calledDir = System.getProperty("user.dir")+ "/" + args[0];
            File argdir = new File(calledDir);
            File[] argfiles = argdir.listFiles();
            for (File argfile: argfiles) {
                System.out.println(argfile.getName());
            }
        }
    }
}
