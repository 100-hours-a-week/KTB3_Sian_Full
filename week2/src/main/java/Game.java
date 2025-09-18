import domain.Ingredient;
import domain.drink.Drink;
import domain.drink.DrinkCatalog;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class Game {
    public void startGame() {
        // 인트로 (게임 설명)
        UI.Intro();

        // "시작" 입력되면 게임 start
        while(true) {
            try {
                InputUtil.validateAnswer("시작하려면 \"시작\"을 입력해주세요.", "시작");
                break;
            } catch (InputExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        // 콘솔 비우기
        UI.cleanConsole();

        // 주문 랜덤 생성
        Drink order = DrinkCatalog.getRandomDrink();
        UI.printOrder(order);

        // 재료 목록 출력
        UI.printIngredientSet();

        // 재료 입력받기
        Set<Ingredient> userIngredients = new HashSet<>(); // 사용자로부터 입력 받은 재료
        while(true) {
            try {
                userIngredients = InputUtil.inputIngredients(order.getIngredients().size());
                break;
            } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        }

        // 음료 데울지 묻기
        boolean isHeated;
        while(true) {
            try {
                // inputUtil로 입력 받아오기
                isHeated = InputUtil.AskYesOrNo("음료를 데울까요?");
                break;
            } catch(InputExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        // 음료 믹서에 갈지 결정
        boolean isBlended;
        while(true) {
            try {
                isBlended = InputUtil.AskYesOrNo("음료를 믹서에 갈까요?");
                break;
            } catch(InputExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        // 결과 출력
        UI.printResult(order.isCorrectRecipe(userIngredients, isHeated, isBlended));

    }
}
