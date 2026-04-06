package ru.job4j.algo.sliding.window;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MainTest {

    @Test
    public void whenIntervalsOverlapThenFindMaxOverlapInterval() {
        List<Main.Interval> intervals = new ArrayList<>();
        intervals.add(new Main.Interval(1, 4));
        intervals.add(new Main.Interval(2, 6));
        intervals.add(new Main.Interval(3, 5));
        intervals.add(new Main.Interval(7, 8));

        int[] result = Main.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(3, 4);
    }

    @Test
    public void whenSequentialIntervalsThenFindMaxOverlapInterval() {
        List<Main.Interval> intervals = new ArrayList<>();
        intervals.add(new Main.Interval(1, 3));
        intervals.add(new Main.Interval(2, 4));
        intervals.add(new Main.Interval(3, 5));
        intervals.add(new Main.Interval(4, 6));

        int[] result = Main.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(2, 3);
    }

    @Test
    public void whenNonOverlappingIntervalsThenFindMaxOverlapInterval() {
        List<Main.Interval> intervals = new ArrayList<>();
        intervals.add(new Main.Interval(1, 10));
        intervals.add(new Main.Interval(2, 3));
        intervals.add(new Main.Interval(4, 5));
        intervals.add(new Main.Interval(6, 7));
        intervals.add(new Main.Interval(8, 9));

        int[] result = Main.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(2, 3);
    }

    @Test
    public void whenSingleIntervalThenFindMaxOverlapInterval() {
        List<Main.Interval> intervals = new ArrayList<>();
        intervals.add(new Main.Interval(1, 10));

        int[] result = Main.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(1, 10);
    }

    @Test
    public void whenNoIntervalsThenFindMaxOverlapInterval() {
        List<Main.Interval> intervals = new ArrayList<>();

        int[] result = Main.findMaxOverlapInterval(intervals);
        assertThat(result).containsExactly(-1, -1);
    }
}