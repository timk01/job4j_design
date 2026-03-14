package ru.job4j.ood.srp.currency;

public class InMemoryCurrencyConverter implements CurrencyConverter {
    private static final int CURRENCIES_COUNT = Currency.values().length;
    private final double[][] conversionTable = new double[CURRENCIES_COUNT][CURRENCIES_COUNT];

    public InMemoryCurrencyConverter() {
        this.conversionTable[Currency.RUB.ordinal()][Currency.USD.ordinal()] = 0.0162;
        this.conversionTable[Currency.RUB.ordinal()][Currency.EUR.ordinal()] = 0.0166;
        this.conversionTable[Currency.USD.ordinal()][Currency.EUR.ordinal()] = 1.0218;
        this.conversionTable[Currency.USD.ordinal()][Currency.RUB.ordinal()] = 65D;
        this.conversionTable[Currency.EUR.ordinal()][Currency.USD.ordinal()] = 0.9786;
        this.conversionTable[Currency.EUR.ordinal()][Currency.RUB.ordinal()] = 63D;
    }

    @Override
    public double convert(Currency source, double sourceValue, Currency target) {
        return sourceValue * conversionTable[source.ordinal()][target.ordinal()];
    }
}
