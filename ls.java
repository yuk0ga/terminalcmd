import java.io.File;
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



/**
 * Created by koga on 2017/06/13.
 */
public class ls {

    static String workingDir;
    private static String calledDirName;
    private static File dir;
    private static File[] files;


    private static void defautSetup() {
        workingDir = System.getProperty("user.dir");
        dir = new File(workingDir);
        files = dir.listFiles(new FileFilter() {     //hides system files
            public boolean accept(File file) {
                return !file.isHidden();
            }
        });
    }

    private static void listNamesOnly() {
        for (File file : files) {
            System.out.printf("%3s", file.getName() + "  "); //getName gets only the file name. Without this, the whole path will be listed.
        }
        System.out.println();
    }

    private static void listAllFiles() {
        files = dir.listFiles();
    }

    private static void sortByTime() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {                                       //sets two comparators
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());     //
            }
        });
    }

    private static void reverseOrder() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f2.getName().compareTo(f1.getName());
            }
        });
    }

    private static void reverseTimeOrder() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {                                       //sets two comparators
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());     //
            }
        });
    }

    private static void listLongFormat() throws IOException{
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
                System.out.printf("%3s", file.list().length + 2 + " ");
            } else {
                System.out.printf("%3s", "1" + " ");
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

    private static void listCalledFiles() {
        try {
            File argdir = new File(calledDirName);
            File[] argfiles = argdir.listFiles();
            for (File argfile : argfiles) {
                System.out.println(argfile.getName());
            }
        } catch (NullPointerException e) {
            System.out.println("No such file found");
        }
    }


    public static void main(String[] args) throws IOException {
        defautSetup();   //user.dir is the current working directory

        if (args.length == 0) {     // ls only
            listNamesOnly();
        } else {
            if (args[0].contains("a") && args[0].contains("-")) {       // -a
                listAllFiles();
            }
            if (args[0].contains("t") && args[0].contains("-")) {       // -t
                sortByTime();
            }
            if (args[0].contains("r") && args[0].contains("-")) {        // -r
                reverseOrder();
            }
            if (args[0].contains("r") && args[0].contains("t") && args[0].contains("-")) {        // -r & -t
                reverseTimeOrder();
            }
            if (!args[0].contains("l") && args[0].contains("-")) {        // if -l is not used
                listNamesOnly();
            }
            if (args[0].contains("l") && args[0].contains("-")) {       // -l
                listLongFormat();
            }
            if (!args[0].contains("-")) {         //if file is specified
                calledDirName = workingDir + "/" + args[0];
                listCalledFiles();
            }
        }
    }
}