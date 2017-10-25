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

    private static String workingDirPath;
    private static File dir;
    private static File[] files;
    private static ArrayList<String> lists;
    private static ArrayList<String> fileNames;


    static File[] defaultSetup(String workingDir) {
        workingDirPath = workingDir;
        dir = new File(workingDir);
        files = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.isHidden();    //returns unhidden files only
            }
        });
        return files;
    }

    static ArrayList<String> listFileNames() {
        String fileName;
        fileNames = new ArrayList<>();
        for (File file : files) {
            fileName = String.format("%3s", file.getName() + "  ");
            fileNames.add(fileName);
//            System.out.printf(); //getName gets only the file name. Without this, the whole path will be listed.
        }
//        System.out.println();
        return fileNames;
    }

    static File[] listAllFiles() {
        files = dir.listFiles();
        return files;
    }

    static File[] sortByTime() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {                                       //sets two comparators
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());     //
            }
        });
        return files;
    }

    static File[] reverseOrder() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f2.getName().compareTo(f1.getName());
            }
        });
        return files;
    }

    static File[] reverseTimeOrder() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {                                       //sets two comparators
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());     //
            }
        });
        return files;
    }

    static ArrayList<String> listLongFormat() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm");
        lists = new ArrayList<>();
        String listLine;
        String typeOfFile;
        String numberOfLinks;
        String ownerName;
        String group;
        String size;
        String lastModified;
        String fileName;
        
        for (File file : files) {

            //type of file
            if (file.isFile()) {
                typeOfFile = "-";
            } else if (file.isDirectory()) {
                typeOfFile = "d";
            } else {
                typeOfFile = "l";
            }

            //permission(group)
            Path path = Paths.get(file.toString());
            Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(path);
            typeOfFile += PosixFilePermissions.toString(permissions) + "  ";

            //number of links
            if (file.isDirectory()) {
                numberOfLinks = String.format("%3s", file.list().length + 2 + " ");
            } else {
                numberOfLinks = String.format("%3s", "1" + " ");
            }
            //owner
            UserPrincipal owner = Files.getOwner(path);
            ownerName = owner.getName() + "  ";

            //group
            PosixFileAttributes attr = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
            group = attr.group().getName() + "  ";

            //size
            size = String.format("%5s", file.length() + " ");

            //last modified
            lastModified = sdf.format(file.lastModified()) + " ";

            //name of file
            fileName = file.getName();

            //form one line
            listLine = typeOfFile + numberOfLinks + ownerName + group + size + lastModified + fileName;
            lists.add(listLine);
        }
        return lists;
    }

     static File[] setCalledDir(String argDir) {
        try {
            dir = new File(argDir);
            files = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return !file.isHidden();    //returns unhidden files only
                }
            });
        } catch (NullPointerException e) {
            System.out.println("No such file found");
        }
        return files;
    }


    public static void main(String[] args) throws IOException {
        defaultSetup(System.getProperty("user.dir"));   //user.dir is the current working directory

        if (args.length == 0) {     // ls only
            listFileNames();
            for (String filename: fileNames) {
                System.out.printf(filename);
            }
            System.out.println();
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
                listFileNames();
                for (String filename: fileNames) {
                    System.out.printf(filename);
                }
                System.out.println();
            }
            if (args[0].contains("l") && args[0].contains("-")) {       // -l
                listLongFormat();
                for (String list : lists) {
                    System.out.println(list);
                }
            }
            if (!args[0].contains("-")) {         //if file is specified
                String calledDirPath = workingDirPath + "/" + args[0];
                setCalledDir(calledDirPath);
                listFileNames();
                for (String filename: fileNames) {
                    System.out.printf(filename);
                }
                System.out.println();
            }
        }
    }
}