package domain.drink;

import domain.Ingredient;

import java.util.List;
import java.util.Set;

public class HotDrink extends Drink {
    public HotDrink(String name, Set<Ingredient> recipe){
        super(name, recipe);
    }

    @Override
    public boolean isCorrectRecipe(Set<Ingredient> ingredients, boolean isHeated, boolean isBlended) {
        return (isCorrectIngredients(ingredients) && isHeated); // 재료 & 데우기 확인
    }

    @Override
    public void printRecipe() {
        super.printRecipe();
        System.out.println("+ 데우기");
    }
}
