package domain.drink;

import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.Set;

public class Smoothie extends IcedDrink {
    public Smoothie(String name, Ingredient fruit) {
        super(name, Set.of(IngredientCatalog.YOGURT, IngredientCatalog.MILK, IngredientCatalog.SYRUP, fruit)); // 요거트, 우유, 시럽, 과일 추가
    }

    @Override
    public boolean isCorrectRecipe(Set<Ingredient> ingredients, boolean isHeated, boolean isBlended) {
        return (isCorrectIngredients(ingredients) && isBlended); // 재료 & 블렌딩 확인
    }

    @Override
    public void printRecipe() {
        super.printRecipe();
        System.out.println("+ 블렌딩하기");
    }
}
