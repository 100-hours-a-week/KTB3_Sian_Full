package domain.drink;

import domain.Ingredient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Drink {
    private final String name;
    private Set<Ingredient> ingredients = new HashSet<Ingredient>(); // 음료에 들어갈 재료

    public Drink(String name, Set<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = new HashSet<>(ingredients);
    }

    public String getName() {
        return this.name;
    }

    public void printRecipe() {
        System.out.print(this.name + ": ");
        for(Ingredient ing : this.ingredients) {
            System.out.print(ing.getName() + " ");
        }
        System.out.println();
    }

    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }

    // 레시피에 재료 추가
    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    // 입력받은 재료가 레시피와 맞는지 확인
    public boolean isCorrectIngredients(Set<Ingredient> ingredients) {
        return ingredients.equals(this.ingredients);
    }

    // 레시피 확인 인터페이스 (재료, 데우기 여부, 블렌딩 여부)
    public abstract boolean isCorrectRecipe(Set<Ingredient> ingredients, boolean isHeated, boolean isBlended);

}
