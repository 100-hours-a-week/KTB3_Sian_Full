import java.util.List;

public class HotDrink extends Drink{
    private String temperature = "hot";

    public HotDrink(String name, List<String> recipe){
        super(name, recipe);
    }
}
