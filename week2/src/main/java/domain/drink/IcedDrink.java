package domain.drink;

import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.Set;

public class IcedDrink extends Drink {

    public IcedDrink(String name, Set<Ingredient> recipe) {
        super(name, recipe);
        this.addIngredient(IngredientCatalog.ICE); // 얼음 추가
    }

    @Override
    public boolean isCorrectRecipe(Set<Ingredient> ingredients, boolean isHeated, boolean isBlended) {
        return isCorrectIngredients(ingredients); // 재료 확인
    }
}

