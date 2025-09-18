package domain.drink;

import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.*;

public class DrinkCatalog {
    // 음료 객체
    public static final Drink STRAWBERRY_SMOOTHIE = new Smoothie("딸기 요거트 스무디", IngredientCatalog.STRAWBERRY);
    public static final Drink BLUEBERRY_SMOOTHIE = new Smoothie("블루베리 요거트 스무디", IngredientCatalog.BLUEBERRY);
    public static final Drink MANGO_SMOOTHIE = new Smoothie("망고 요거트 스무디", IngredientCatalog.MANGO);
    public static final Drink ORANGE_ADE = new Ade("오렌지 에이드", IngredientCatalog.ORANGE);
    public static final Drink LEMON_ADE = new Ade("레몬 에이드", IngredientCatalog.LEMON);
    public static final Drink CHERRY_ADE = new Ade("체리 에이드", IngredientCatalog.CHERRY);
    public static final Drink ICED_AMERICANO = new IcedDrink("아이스 아메리카노", Set.of(IngredientCatalog.WATER, IngredientCatalog.ESPRESSO));
    public static final Drink ICED_LATTE = new IcedDrink("아이스 카페라떼", Set.of(IngredientCatalog.MILK, IngredientCatalog.ESPRESSO));
    public static final Drink HOT_AMERICANO = new HotDrink("핫 아메리카노", Set.of(IngredientCatalog.WATER, IngredientCatalog.ESPRESSO));
    public static final Drink HOT_LATTE = new HotDrink("핫 카페라떼", Set.of(IngredientCatalog.MILK, IngredientCatalog.ESPRESSO));

    // 음료 객체 목록
    private static final Set<Drink> ALL_DRINKS = Set.of(
            STRAWBERRY_SMOOTHIE, BLUEBERRY_SMOOTHIE, MANGO_SMOOTHIE,
            ORANGE_ADE, LEMON_ADE, CHERRY_ADE,
            ICED_AMERICANO, ICED_LATTE, HOT_AMERICANO, HOT_LATTE
    );

    // 메뉴 렌덤으로 뽑기
    public static Drink getRandomDrink() {
        List<Drink> drinks = new ArrayList<>(ALL_DRINKS); // 리스트로 변환
        Random random = new Random();
        int index = random.nextInt(drinks.size());

        return drinks.get(index);
    }


}
