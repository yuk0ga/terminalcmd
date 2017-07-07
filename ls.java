import java.io.File;
import java.io.*;
import java.util.*;
import java.util.Comparator;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.text.SimpleDateFormat;
//import org.apache.commons.io.FileUtils;



/**
 * Created by koga on 2017/06/13.
 */
public class ls {
    private static String workingDir = System.getProperty("user.dir");   //user.dir is the current working directory

    public static void main(String[] args) throws IOException {

        // ls only
        if (args.length == 0) {
            File dir = new File(workingDir);
            File[] files = dir.listFiles(new FileFilter() {     //hides system files
                public boolean accept(File file) {
                    return !file.isHidden();
                }
            });
            for (File file : files) {
                System.out.println(file.getName() + "     "); //getName gets only the file name. Without this, the whole path will be listed.
            }
        } else {

            // -a
            if (args[0].contains("a") && args[0].contains("-")) {
                File dir = new File(workingDir);
                File[] files = dir.listFiles();
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }

            // -l
            if (args[0].contains("l") && args[0].contains("-")) {
                File dir = new File(workingDir);
                File[] files = dir.listFiles(new FileFilter() {     //hides system files
                    public boolean accept(File file) {
                        return !file.isHidden();
                    }
                });
                SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm");
                for (File file : files) {

                    //type of file
                    if (file.isFile()) {
                        System.out.printf("-");
                    } else if (file.isDirectory()) {
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
                    UserPrincipal owner = Files.getOwner(path);
                    System.out.printf(owner.getName() + "  ");

                    //group
                    PosixFileAttributes attr = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
                    System.out.printf(attr.group().getName() + "  ");

                    //size
                    System.out.printf("%5s", file.length() + " ");

                    //last modified
                    System.out.printf(sdf.format(file.lastModified()) + " ");

                    //name of file
                    System.out.println(file.getName());
                }
            }

            // -t
            if (args[0].contains("t") && args[0].contains("-")) {
                File dir = new File(workingDir);
                File[] files = dir.listFiles(new FileFilter() {     //hides system files
                    public boolean accept(File file) {
                        return !file.isHidden();
                    }
                });
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File f1, File f2) {
                        return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
                    }
                });
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }

            // -r
            if (args[0].contains("r") && args[0].contains("-")) {
            File dir = new File(workingDir);
            File[] files = dir.listFiles(new FileFilter() {     //hides system files
                public boolean accept(File file) {
                    return !file.isHidden();
                }
            });
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File f1, File f2) {
                        return f2.getName().compareTo(f1.getName());
                    }
                });
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }

             //file specified
            if (!args[0].contains("-")) {
                try {
                    String calledDir = System.getProperty("user.dir") + "/" + args[0];
                    File argdir = new File(calledDir);
                    File[] argfiles = argdir.listFiles();
                    for (File argfile : argfiles) {
                        System.out.println(argfile.getName());
                    }
                } catch (NullPointerException e) {
                    System.out.println("No such file found");
                }
            }
        }
    }
}

//今のやり方だと、-latrとかやったときに-aのみ実行されてしまう。(6/22)