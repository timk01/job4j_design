package ru.job4j.ood.ocp;

import java.util.List;

public class FiguresImplemetation {

    private interface Drawable {
        String draw();
    }

    private static class Rectangle implements Drawable {

        @Override
        public String draw() {
            return "rectangle";
        }
    }

    public static void main(String[] args) {
        List<Rectangle> rectangles = List.of(new Rectangle());
        rectangles.forEach(Rectangle::draw);
    }
}