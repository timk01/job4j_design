package ru.job4j.ood.icp.examplethree;

public class Developer implements UniversalWorker {
    @Override
    public int writeCodeHoursPerDay() {
        return 5;
    }

    @Override
    public int closedDealsPerMonth() {
        return -1;
    }

    @Override
    public int managedPeopleCount() {
        return -1;
    }

    @Override
    public int architectureDocumentsPerQuarter() {
        return 2;
    }

    @Override
    public int supportTicketsPerDay() {
        return 0;
    }
}
