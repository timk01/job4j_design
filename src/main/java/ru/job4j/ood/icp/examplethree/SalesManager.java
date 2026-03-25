package ru.job4j.ood.icp.examplethree;

class SalesManager implements UniversalWorker {
    @Override
    public int writeCodeHoursPerDay() {
        return -1;
    }

    @Override
    public int closedDealsPerMonth() {
        return 20;
    }

    @Override
    public int managedPeopleCount() {
        return -1;
    }

    @Override
    public int architectureDocumentsPerQuarter() {
        return -1;
    }

    @Override
    public int supportTicketsPerDay() {
        return -1;
    }
}