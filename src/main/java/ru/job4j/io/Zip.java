package ru.job4j.io;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<Path> sources, File target) {
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

    public static void main(String[] args) throws IOException, URISyntaxException {
        Zip zip = new Zip();

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