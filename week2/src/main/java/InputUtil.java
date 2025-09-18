import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    // y 또는 n 입력 받아 boolean 값으로 리턴
    public static boolean AskYesOrNo(String description) throws InputExceptions {
        System.out.println(description);
        System.out.print("y 또는 n을 입력해주세요>>> ");
        String input = sc.nextLine().trim().toLowerCase(); // 앞뒤 공백, 대소문자 무시

        if(input.equals("y")) {
            return true;
        } else if (input.equals("n")) {
            return false;
        }

        // 이외의 값 입력받으면 예외 던진다
        throw new InputExceptions("y 또는 n만 입력할 수 있습니다.");
    }

    // 정해진 값을 입력받는 메서드
    public static void validateAnswer(String description, String answer) throws InputExceptions {
        System.out.println(description);
        System.out.print("입력해주세요>>> ");
        String input = sc.nextLine();

        if(!input.equals(answer)) {
            throw new InputExceptions("잘못된 값입니다. 다시 입력해주세요.");
        }
    }

    // 재료를 입력받는 메서드
    public static Set<Ingredient> inputIngredients(int count) {
        Set<Ingredient> ingredients = new HashSet<>();

        while (ingredients.size() < count) {
            System.out.print("재료 입력>>> ");
            String input = sc.nextLine().trim();

            // 비어있는 값인지 확인
            if (input.isEmpty()) {
                throw new IllegalArgumentException("입력값이 비어있습니다.");
            }

            // 문자열 -> 재료 객체로 변환
            Ingredient ingredient = IngredientCatalog.toIngredient(input);

            // 이미 입력한 재료인지 확인
            if (ingredients.contains(ingredient)) {
                throw new IllegalStateException("이미 입력한 재료입니다.");
            }

            // 재료 카탈로그에 입력값 존재하는지 확인
            if (!IngredientCatalog.isValidIngredient(ingredient)) {
                throw new NoSuchElementException("존재하지 않는 재료입니다.");
            }

            ingredients.add(ingredient);
        }

        return ingredients;
    }
}
