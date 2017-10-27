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
        temporaryFolder.newFolder("folder");
        temporaryFolder.newFile("file1");
        temporaryFolder.newFile("file2");
        temporaryFolder.newFile("file3");
        temporaryFolder.newFile(".hidden");
        workingDir = temporaryFolder.getRoot().getAbsolutePath();
        Path path = Paths.get(temporaryFolder.getRoot().getAbsolutePath());
        owner = Files.getOwner(path);
        PosixFileAttributes attr = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
        group = attr.group().getName();
        time = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("MM dd HH:mm");
        Ls.defaultSetup(workingDir);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDefaultSetup() {
        assertThat(Ls.defaultSetup(workingDir).length, is(4));
    }

    @Test
    public void testListFileNames() {
        assertThat(Ls.listFileNames().get(0), is(not(containsString("/"))));
    }

    @Test
    public void testListAllFiles() {
        assertThat(Ls.listAllFiles().length, is(5));
    }

    @Test
    public void testSortByTime() {
        assertThat(Ls.sortByTime()[0].getName(), is("file1"));
    }

    @Test
    public void testReverseOrder() {
        assertThat(Ls.reverseOrder()[0].getName(), is("folder"));
    }

    @Test
    public void testReverseTimeOrder() {
        Ls.sortByTime();
        Ls.reverseOrder();
        assertThat(Ls.reverseTimeOrder()[0].getName(), is("folder"));
    }

    @Test
    public void testListLongFormat() throws IOException{
        assertThat(Ls.listLongFormat().get(0), is(
                containsString("-rw-r--r--   1 "
                + owner + "  "
                + group + "  "
                + "   0 "
                + time.format(formatter)
                + " file1")));
    }

    @Test
    public void testListCalledFiles() {
        assertThat(Ls.setCalledDir(workingDir + "/folder").length, is(0));
    }
}