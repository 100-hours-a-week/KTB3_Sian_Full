import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Drink {
    private String name;
    private Set<String> recipe = new HashSet<String>(); // 음료 재료

    public Drink(String name, List<String> recipe) {
        this.name = name;
        this.recipe = new HashSet<>(recipe);
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getRecipe() {
        return this.recipe;
    }

    public void addIngredient(String ingredient){
        this.recipe.add(ingredient);
    }


}
