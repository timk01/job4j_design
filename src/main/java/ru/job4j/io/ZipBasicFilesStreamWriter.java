package ru.job4j.io;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBasicFilesStreamWriter {

    private static final int BUFFER_SIZE = 4096;

    /**
     * the one to read with classic predetermined bufferSize and loop
     * @param sources
     * @param target
     */

    public void packFilesViaBuffer(List<Path> sources, File target) {
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

    /**
     * the one to read/write with streams API
     * @param sources
     * @param target
     */

    public void packFilesViaStream(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            sources.forEach(path -> {
                try {
                    zip.putNextEntry(new ZipEntry(path.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedInputStream output =
                             new BufferedInputStream(new FileInputStream(path.toFile()))) {
                    zip.write(output.readAllBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
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
        ZipBasicFilesStreamWriter zip = new ZipBasicFilesStreamWriter();
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
        zip.packFilesViaStream(list, new File(nameOfZippedProject));
    }
}