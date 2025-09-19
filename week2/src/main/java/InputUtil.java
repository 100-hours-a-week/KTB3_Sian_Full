import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    // y 또는 n 입력 받아 boolean 값으로 리턴
    public static boolean askYesOrNo(String description, Timer timer) {
        // 입력 전 타이머 확인
        timer.checkTimeout();
        while (true) {
            // 입력중에 타임아웃 된 경우, 입력값 검증하지 않고 종료
            System.out.println(description);
            System.out.print("y 또는 n을 입력해주세요>>> ");
            String input = sc.nextLine().trim().toLowerCase(); // 앞뒤 공백, 대소문자 무시
            timer.checkTimeout();

            try {
                return InputValidator.validateYesOrNo(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    // 정해진 값을 입력받는 메서드
    public static void validateAnswer(String description, String answer) {
        while(true) {
            System.out.println(description);
            System.out.print("입력해주세요>>> ");
            String input = sc.nextLine();

            if(input.equals(answer)) {
                break;
            }
            System.out.println("잘못된 값입니다. 다시 입력해주세요.");
            System.out.println();
        }
    }

    // 재료를 입력받는 메서드
    public static Set<Ingredient> inputIngredients(int count, Timer timer) {
        Set<Ingredient> ingredients = new HashSet<>();
        // 입력 전 타이머 확인
        timer.checkTimeout();
        while (ingredients.size() < count) {

            try {
                System.out.print("재료 입력>>> ");
                String input = sc.nextLine().trim();

                // 시간 초과 시, 유효성 검사 없이 종료
                timer.checkTimeout();

                // 비어있는 값인지 확인
                InputValidator.validateEmpty(input);

                // 문자열 -> 재료 객체로 변환
                Ingredient ingredient = IngredientCatalog.toIngredient(input);

                // 이미 입력한 재료인지 확인
                InputValidator.validateAlreadyInput(ingredients, ingredient);

                // 재료 카탈로그에 입력값 존재하는지 확인
                InputValidator.validateExist(ingredient);

                ingredients.add(ingredient);

            } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
                System.out.println(e.getMessage());
                // 시간초과시, 예외 상황에서도 종료
                timer.checkTimeout();

            }
        }

        return ingredients;
    }

}
