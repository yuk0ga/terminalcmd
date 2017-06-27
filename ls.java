import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Set;
import java.text.SimpleDateFormat;



/**
 * Created by koga on 2017/06/13.
 */
public class ls {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {         // if there isn't any argument
            String workingDir = System.getProperty("user.dir");     //user.dir is the current working directory
        File dir = new File(workingDir);
        File[] files = dir.listFiles(new FileFilter() {     //hides system files
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
            File[] files = dir.listFiles(new FileFilter() {     //hides system files
                public boolean accept(File file) {
                    return !file.isHidden();
                }
            });
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm");
            String username = System.getProperty("user.name");
            for (File file : files) {
                //type of file
                if (file.isFile()){
                    System.out.printf("-");
                } else if (file.isDirectory()){
                    System.out.printf("d");
                } else {
                    System.out.printf("l");
                }

                //permission(group)
                Path path = Paths.get(file.toString());
                Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(path);
                System.out.print(PosixFilePermissions.toString(permissions) + "  ");

                //number of links
                if (file.isDirectory()) {
                    System.out.print(file.list().length + 2 + " ");
                } else {
                    System.out.printf("1" + " ");
                }
                //owner
                System.out.printf(username + "  ");
                //Unknown 10 digit value
                System.out.printf("1294217014" + "  ");
                //size
                System.out.printf("%5s", file.length() + " ");
                //last modified
                System.out.printf(sdf.format(file.lastModified()) + " ");
                //name of file
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