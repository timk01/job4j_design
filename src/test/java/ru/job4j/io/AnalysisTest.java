package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AnalysisTest {

    @Test
    void unavailable(@TempDir Path tempDir) throws IOException {
        File sourceFile = tempDir.resolve("server.log").toFile();
        try (PrintWriter printWriter =
                     new PrintWriter(
                             new BufferedOutputStream(
                                     new FileOutputStream(sourceFile)))) {
            printWriter.println("200 10:56:01");
            printWriter.println("500 10:57:01");
            printWriter.println("400 10:58:01");
            printWriter.println("500 10:59:01");
            printWriter.println("400 11:01:02");
            printWriter.println("300 11:02:02");
        }

        File targetFile = tempDir.resolve("target.csv").toFile();
        new Analysis().unavailable(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath());

        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(targetFile))) {
            bufferedReader.lines()
                    .forEach(list::add);
        }

        assertThat(list).hasSize(1);
        assertThat(list.get(0)).isEqualTo("10:57:01;11:02:02");
    }

}