import domain.IngredientCatalog;
import domain.drink.Drink;
import domain.drink.DrinkCatalog;

public class UI {
    public static void Intro() {
        System.out.println("어서오세요~ 춘식 카페입니다! (^._.^)ﾉ");
        System.out.println("----------------------------------------------");
        System.out.println("음료 레시피");
        System.out.println("----------------------------------------------");
        DrinkCatalog.ICED_AMERICANO.printRecipe();
        DrinkCatalog.HOT_LATTE.printRecipe();
        DrinkCatalog.STRAWBERRY_SMOOTHIE.printRecipe();
        DrinkCatalog.LEMON_ADE.printRecipe();
        System.out.println("----------------------------------------------");
        System.out.println("[게임 방법]");
        System.out.println("1. 손님이 주문한 음료에 들어갈 재료를 순서에 상관없이 하나씩 입력한다.");
        System.out.println("2. 음료를 데운다. (선택)");
        System.out.println("3. 재료를 블렌더에 넣고 간다. (선택)");
        System.out.println("----------------------------------------------");
    }

    public static void cleanConsole() {
        for (int i=0; i<10; i++) {
            System.out.println();
        }
    }

    // 주문 생성
    public static void printOrder(Drink order) {
        System.out.println("손님: " + order.getName() + " 한 잔 주세요.");
    }

    // 재료 목록 출력
    public static void printIngredientSet() {
        System.out.println("----------------------------------------------");
        System.out.println("재료 목록");
        System.out.println("----------------------------------------------");
        IngredientCatalog.printIngredientsSet();
        System.out.println("----------------------------------------------");
        System.out.println();
    }

    // 결과 출력
    public static void printResult(boolean success) {
        System.out.println();
        if (success) {
            System.out.println("손님: 맛있어요 또올게요!");
        } else {
            System.out.println("손님: 제가 주문한건 이게 아닌데요...");
        }
    }


}
