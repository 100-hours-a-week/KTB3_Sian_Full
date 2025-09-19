package domain;
import java.util.Set;


public class IngredientCatalog {
    // 재료 객체
    public static final Ingredient WATER = new Ingredient("물");
    public static final Ingredient MILK = new Ingredient("우유");
    public static final Ingredient ICE = new Ingredient("얼음");
    public static final Ingredient SPARKLING = new Ingredient("탄산수");
    public static final Ingredient ESPRESSO = new Ingredient("에스프레소");
    public static final Ingredient STRAWBERRY = new Ingredient("딸기");
    public static final Ingredient MANGO = new Ingredient("망고");
    public static final Ingredient BLUEBERRY = new Ingredient("블루베리");
    public static final Ingredient ORANGE = new Ingredient("오렌지");
    public static final Ingredient LEMON = new Ingredient("레몬");
    public static final Ingredient CHERRY = new Ingredient("체리");
    public static final Ingredient YOGURT = new Ingredient("요거트");
    public static final Ingredient SYRUP = new Ingredient("시럽");

    // 모든 재료 목록
    public static final Set<Ingredient> ALL_INGREDIENTS = Set.of(
            WATER, MILK, ICE, SPARKLING, ESPRESSO,
            STRAWBERRY, MANGO, BLUEBERRY, ORANGE, LEMON, CHERRY,
            YOGURT, SYRUP
    );

    // 카탈로그에 입력받은 재료가 존재하는지 확인
    public static boolean isValidIngredient(Ingredient input) {
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            if (ingredient.equals(input)) {
                return true;
            }
        }
        return false;
    }

    // 문자열 -> 재료 객체로 변환
    public static Ingredient toIngredient(String name) {
        for (Ingredient ingredient: ALL_INGREDIENTS) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 재료입니다.");
    }

    // 재료 목록 출력
    public static void printIngredientsSet() {
        int count = 0;
        for(Ingredient ing : ALL_INGREDIENTS) {
            System.out.print(ing.getName() + " ");
            count++;

            // 줄바꿈
            if (count % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
