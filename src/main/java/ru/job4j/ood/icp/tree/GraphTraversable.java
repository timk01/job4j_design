package ru.job4j.ood.icp.tree;

import java.util.Iterator;

public interface GraphTraversable<T> {
    Iterator<T> bfs();

    Iterator<T> dfs();

}
