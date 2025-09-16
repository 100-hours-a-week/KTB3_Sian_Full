import java.util.Arrays;
import java.util.List;

public class Smoothie extends IcedDrink{
    public Smoothie(String name, String fruit) {
        super(name, Arrays.asList("요거트", "우유", "시럽", fruit));
    }
}
