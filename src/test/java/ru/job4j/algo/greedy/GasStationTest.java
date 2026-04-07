package ru.job4j.algo.greedy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GasStationTest {
    @Test
    void whenThatTestCase1() {
        GasStation gasStation = new GasStation();
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void whenThatTestCase2() {
        GasStation gasStation = new GasStation();
        int[] gas = {2, 3, 4};
        int[] cost = {3, 4, 3};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenThatTestCase3() {
        GasStation gasStation = new GasStation();
        int[] gas = {5, 1, 2, 3, 4};
        int[] cost = {4, 4, 1, 5, 1};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void whenThatTestCase4() {
        GasStation gasStation = new GasStation();
        int[] gas = {3, 1, 1};
        int[] cost = {1, 2, 2};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void whenThatTestCase5() {
        GasStation gasStation = new GasStation();
        int[] gas = {4, 5, 2, 6, 5, 3};
        int[] cost = {3, 2, 7, 3, 2, 9};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenThatTestCase6() {
        GasStation gasStation = new GasStation();
        int[] gas = {5, 0, 9, 4, 3, 3, 9, 9, 1, 2};
        int[] cost = {6, 7, 5, 9, 5, 8, 7, 1, 10, 5};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenThatTestCase7() {
        GasStation gasStation = new GasStation();
        int[] gas = {4, 0, 1};
        int[] cost = {3, 2, 1};
        int result = gasStation.canCompleteCircuit(gas, cost);
        assertThat(result).isEqualTo(-1);
    }
}