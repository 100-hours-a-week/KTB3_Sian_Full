import java.util.List;

public class HotDrink extends Drink{
    protected String temperature = "hot";

    public HotDrink(String name, List<String> recipe){
        super(name, recipe);
    }
}
