import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IcedDrink extends Drink{
    protected String temperature = "cold";
    public IcedDrink(String name, List<String> recipe) {
        super(name, recipe);
        this.recipe.add("얼음");
    }
}

