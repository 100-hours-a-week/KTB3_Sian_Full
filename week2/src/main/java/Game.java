import domain.Ingredient;
import domain.drink.Drink;
import domain.drink.DrinkCatalog;

import java.util.Set;

public class Game {

    public void startGame() {
        try {
            // 배경음 스레드
            BGMPlayer bgm = new BGMPlayer();
            bgm.start();

            // 인트로 (게임 설명)
            UI.intro();

            // "시작" 입력되면 게임 start (여기는 시간 무제한)
            InputUtil.validateAnswer("시작하려면 \"시작\"을 입력해주세요.", "시작");

            // 콘솔 비우기
            UI.cleanConsole();

            // 주문 랜덤 생성
            Drink order = DrinkCatalog.getRandomDrink();
            UI.printOrder(order);

            // 재료 목록 출력
            UI.printIngredientSet();

            // 손님의 인내심 타이머 스레드 시작
            Timer timer = new Timer();
            timer.start();

            // 재료 입력
            Set<Ingredient> userIngredients = null; // 입력받은 재료 Set
            try {
                userIngredients = InputUtil.inputIngredients(order.getIngredients().size(), timer);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                return; // 게임 종료
            }


            // 음료 데우기 , 블렌딩 여부 입력
            boolean isHeated;
            boolean isBlended;
            try {
                isHeated = InputUtil.askYesOrNo("음료를 데울까요?", timer);
                isBlended = InputUtil.askYesOrNo("음료를 믹서에 갈까요?", timer);
            } catch (IllegalArgumentException e) {
                return; // 게임 종료
            }


            // 결과 출력
            UI.printResult(order.isCorrectRecipe(userIngredients, isHeated, isBlended));

            // 게임 정상 종료 → 타이머 중단
            timer.stop();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
