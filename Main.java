import javax.imageio.IIOException;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static void saveGame(String path, GameProgress gp) throws IOException {
        try {
            new File(path).createNewFile();

        } catch (IIOException ex) {
            System.out.println(ex.getMessage());
        }
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String zipPath, String savePath) {
        File dir = new File(savePath);
        List<File> list = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isFile() && !file.getName().endsWith("zip")) {
                list.add(file);
            }
        }

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (File s : list) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    ZipEntry entry = new ZipEntry(s.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void deleteIfNotZip(String path) {
        for (File myFile : new File(path).listFiles()) {
            if (myFile.getName().endsWith("dat")) {
                myFile.delete();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "C:" + File.separator +"Games"+ File.separator + "savegames" + File.separator;
        GameProgress gp1 = new GameProgress(2099, 1, 46, 400.4);
        GameProgress gp2 = new GameProgress(2019, 2, 44, 600.3);
        GameProgress gp3 = new GameProgress(1980, 3, 34, 500.4);
        saveGame(path + "save1.dat", gp1);
        saveGame(path +  "save2.dat", gp2);
        saveGame(path + "save3.dat", gp3);
        zipFiles(path + "save.zip", path  );
        deleteIfNotZip(path  );


    }
}
