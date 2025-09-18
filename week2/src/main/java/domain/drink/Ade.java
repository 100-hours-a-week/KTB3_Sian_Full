package domain.drink;

import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.Arrays;
import java.util.Set;

public class Ade extends IcedDrink {
    public Ade(String name, Ingredient fruit) {
        super(name, Set.of(IngredientCatalog.SPARKLING, IngredientCatalog.SYRUP, fruit)); // 탄산수, 시럽, 과일 추가
    }
}
