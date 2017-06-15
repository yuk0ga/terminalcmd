import java.io.File;

/**
 * Created by koga on 2017/06/13.
 */
public class ls {
    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        File dir = new File(workingDir);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}
