package ru.job4j.cashe.beerfactory;

import java.util.List;
import java.util.Random;

public class BeerFacttoryOriginal {

    private static BeerFactory beerFactory;

    public static void main(String[] args) {
        BeerFacttoryOriginal beerFacttoryOriginal = new BeerFacttoryOriginal();
        beerFacttoryOriginal.init();
    }

    public void init() {
        int[] values = new int[100];
        for (int i = 0; i < 10; i++) {
            values[i] = new Random().nextInt(2);
            beerFactory = values[i] == 0 ? new AlcoholBeer() : new NonAlcoholBeer();
            beerFactory.deliverBeer();
        }
    }
}

class AlcoholBeer extends BeerFactory implements Beer {
    private String type;
    private List<String> ingredientds;
    private double price;

    public AlcoholBeer() {
    }

    public AlcoholBeer(String type, List<String> ingredientds, double price) {
        this.type = type;
        this.ingredientds = ingredientds;
        this.price = price;
    }

    public void deliveryToShops() {
        System.out.println("alcohol beer is delivered;");
    }

    @Override
    public Beer createBeer() {
        System.out.println("AlcoholBeer creating in process");
        return new AlcoholBeer(
                "Statriy melnik",
                List.of("Water", "Barley Malt", "Hops"),
                70.0
        );
    }
}

class NonAlcoholBeer extends BeerFactory implements Beer {
    private String type;
    private List<String> ingredientds;
    private double price;
    private boolean sellToUnderage;

    public NonAlcoholBeer() {
    }

    public NonAlcoholBeer(String type, List<String> ingredientds, double price) {
        this.type = type;
        this.ingredientds = ingredientds;
        this.price = price;
        sellToUnderage = true;
    }

    public void deliveryToShops() {
        System.out.println("non-alcohol beer is delivered;");
    }

    @Override
    public Beer createBeer() {
        System.out.println("non-alcoholBeer creating in process");
        return new NonAlcoholBeer(
                "Statriy melnik wih alkohol < 0.5",
                List.of("Water", "brewing barley malt", "hop products"),
                50.0
        );
    }
}

abstract class BeerFactory {
    private String companyName;

    public void deliverBeer() {
        System.out.println("beer is created;");
        Beer thisBeer = createBeer();
        System.out.println("beer is being delivered;");
        thisBeer.deliveryToShops();
    }

    abstract public Beer createBeer();
}