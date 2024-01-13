package ru.job4j.assertj;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 8);
        Condition<Box> condition
                = new Condition<>(box1 -> box1.getClass().getSimpleName().equals("Box"),
                "it's not box");
        assertThat(box).is(condition);
        String name = box.whatsThis();
        assertThat(name).isNotBlank()
                .containsSequence("hedron")
                .endsWith("n")
                .doesNotContain("Cube")
                .isEqualTo("Tetrahedron");
    }

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        Condition<Box> condition
                = new Condition<>(box1 -> box1.getClass().getSimpleName().equals("Box"),
                "it's not box");
        assertThat(box).is(condition);
        String name = box.whatsThis();
        assertThat(name).isNotBlank()
                .containsSequence("here")
                .endsWith("re")
                .doesNotContain("Tetrahedron")
                .isEqualTo("Sphere");
    }

    @Test
    void isThisUnknownObj() {
        Box box = new Box(-1, -10);
        String name = box.whatsThis();
        assertThat(name).isNotBlank()
                .containsSequence("known")
                .endsWith("object")
                .doesNotContain("Tetrahedron")
                .isEqualTo("Unknown object");
    }

    @Test
    void sphereHasZeroVertices() {
        int vertex = 0;
        Box box = new Box(vertex, 64);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isNotPositive()
                .isNotNegative()
                .isGreaterThan(-1)
                .isLessThan(1)
                .isZero();
    }

    @Test
    void cubeHas8Vertices() {
        int vertex = 8;
        Box box = new Box(vertex, 64);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isNotZero()
                .isPositive()
                .isEven()
                .isGreaterThan(4)
                .isLessThan(9)
                .isEqualTo(vertex);
    }

    @Test
    void unknownObjHasNoVertices() {
        int vertex = -1;
        Box box = new Box(vertex, 64);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isNotZero()
                .isNotPositive()
                .isNegative()
                .isEqualTo(vertex);
    }

    @Test
    void sphereDoesExist() {
        Box box = new Box(0, 64);
        boolean exist = box.isExist();
        assertThat(exist).isNotEqualTo(false)
                .isEqualTo(true)
                .isTrue();
    }

    @Test
    void cubeDoesExist() {
        Box box = new Box(8, 64);
        boolean exist = box.isExist();
        assertThat(exist).isNotEqualTo(false)
                .isEqualTo(true)
                .isTrue();
    }

    @Test
    void unknownObjDoesntExist() {
        int vertex = -1;
        Box box = new Box(vertex, 64);
        boolean exist = box.isExist();
        assertThat(exist).isNotEqualTo(true)
                .isEqualTo(false)
                .isFalse();
    }

    @Test
    void getSphereArea() {
        Box box = new Box(0, 64);
        double area = box.getArea();
        double precision = 0.0001D;
        double expected = 51471.8540D;
        assertThat(area).isEqualTo(expected, withPrecision(precision))
                .isCloseTo(expected, Percentage.withPercentage(1.0D))
                .isGreaterThan(51000.0D)
                .isLessThan(52000.0D);
    }

    @Test
    void getCubeArea() {
        Box box = new Box(8, 64);
        double area = box.getArea();
        double precision = 0.0001D;
        double expected = 24576.0D;
        assertThat(area).isEqualTo(expected, withPrecision(precision))
                .isCloseTo(expected, Percentage.withPercentage(1.0D))
                .isGreaterThan(24500.0D)
                .isLessThan(24600.0D);
    }

    @Test
    void getUnknownObjArea() {
        Box box = new Box(-10, 64);
        double area = box.getArea();
        double precision = 0.0001D;
        double expected = 0.0D;
        assertThat(area).isEqualTo(expected, withPrecision(precision))
                .isCloseTo(expected, Percentage.withPercentage(1.0D))
                .isZero();
    }
}