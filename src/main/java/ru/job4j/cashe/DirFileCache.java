package ru.job4j.cashe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String data = null;
        Path path = Path.of(cachingDir).resolve(key);
        try {
            data = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}