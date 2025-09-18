import domain.Ingredient;
import domain.drink.Drink;
import domain.drink.DrinkCatalog;

import java.util.Set;

public class Game {
    public void startGame() {
        // 배경음 스레드
        BGMPlayer bgm = new BGMPlayer();
        bgm.start();

        // 인트로 (게임 설명)
        UI.Intro();

        // "시작" 입력되면 게임 start
        InputUtil.validateAnswer("시작하려면 \"시작\"을 입력해주세요.", "시작");

        // 콘솔 비우기
        UI.cleanConsole();

        // 주문 랜덤 생성
        Drink order = DrinkCatalog.getRandomDrink();
        UI.printOrder(order);

        // 재료 목록 출력
        UI.printIngredientSet();

        // 재료 입력받기
        Set<Ingredient> userIngredients =  InputUtil.inputIngredients(order.getIngredients().size());

        // 음료 데울지 묻기
        boolean isHeated;
        isHeated = InputUtil.AskYesOrNo("음료를 데울까요?");

        // 음료 믹서에 갈지 결정
        boolean isBlended;
        isBlended = InputUtil.AskYesOrNo("음료를 믹서에 갈까요?");

        // 결과 출력
        UI.printResult(order.isCorrectRecipe(userIngredients, isHeated, isBlended));

    }
}
