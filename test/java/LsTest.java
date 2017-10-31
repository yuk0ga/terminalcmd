import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by koga on 2017/10/18.
 */
public class LsTest {

    private String workingDir;
    private LocalDateTime time;
    private DateTimeFormatter formatter;
    private UserPrincipal owner;
    private String group;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        Ls ls = new Ls();
        temporaryFolder.newFolder("folder");
        temporaryFolder.newFile("file1");
        temporaryFolder.newFile("file2");
        temporaryFolder.newFile("file3");
        temporaryFolder.newFile(".hidden");
        workingDir = temporaryFolder.getRoot().getAbsolutePath();
        Path path = Paths.get(workingDir);
        owner = Files.getOwner(path);
        PosixFileAttributes attr = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
        group = attr.group().getName();
        time = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("MM dd HH:mm");
        ls.setWorkingDirectory(workingDir);
        ls.defaultSetup();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDefaultSetup() {
        Ls ls = new Ls();
        assertThat(ls.defaultSetup().length, is(4));
    }

    @Test
    public void testListFileNames() {
        Ls ls = new Ls();
        assertThat(ls.listFileNames().get(0), is(not(containsString("/"))));
    }

    @Test
    public void testListAllFiles() {
        Ls ls = new Ls();
        assertThat(ls.listAllFiles().length, is(5));
    }

    @Test
    public void testSortByTime() {
        Ls ls = new Ls();
        assertThat(ls.sortByTime()[0].getName(), is("file1"));
    }

    @Test
    public void testReverseOrder() {
        Ls ls = new Ls();
        assertThat(ls.reverseOrder()[0].getName(), is("folder"));
    }

    @Test
    public void testReverseTimeOrder() {
        Ls ls = new Ls();
        ls.sortByTime();
        ls.reverseOrder();
        assertThat(ls.reverseTimeOrder()[0].getName(), is("folder"));
    }

    @Test
    public void testListLongFormat() throws IOException{
        Ls ls = new Ls();
        assertThat(ls.listLongFormat().get(0), is(
                containsString("-rw-r--r--   1 "
                + owner + "  "
                + group + "  "
                + "   0 "
                + time.format(formatter)
                + " file1")));
    }

    @Test
    public void testListCalledFiles() {
        Ls ls = new Ls();
        assertThat(ls.setCalledDir(workingDir + "/folder").length, is(0));
    }
}