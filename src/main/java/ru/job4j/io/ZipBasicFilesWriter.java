package ru.job4j.io;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBasicFilesWriter {

    private static final int BUFFER_SIZE = 4096;

    /**
     * the one read with classic predetermined bufferSize and loop
     * @param sources
     * @param target
     */

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip =
                     new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path path : sources) {
                zip.putNextEntry(new ZipEntry(path.toString()));
                try (BufferedInputStream bufferedInputStream =
                             new BufferedInputStream(new FileInputStream(path.toFile()))) {
                    byte[] bytesIn = new byte[BUFFER_SIZE];
                    int read = 0;
                    while ((read = bufferedInputStream.read(bytesIn)) != -1) {
                        zip.write(bytesIn, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getName()));
            try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(output.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ZipBasicFilesWriter zip = new ZipBasicFilesWriter();
        ArgsName threeParams = ArgsName.of(
                new String[]{args[0], args[1], args[2]}
        );
        String searchPath = threeParams.get("d");
        String excludeExtension = threeParams.get("e");
        String nameOfZippedProject = threeParams.get("o");

        Path start = Paths.get(searchPath);
        Predicate<Path> excludePredicateExt = path ->
                !path.getFileName().toFile().toString().endsWith(excludeExtension);
        List<Path> list = Search.search(start, excludePredicateExt);
        zip.packFiles(list, new File(nameOfZippedProject));
    }
}