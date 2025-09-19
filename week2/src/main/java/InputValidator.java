import domain.Ingredient;
import domain.IngredientCatalog;

import java.util.NoSuchElementException;
import java.util.Set;

public class InputValidator {
    // 값이 없는 경우 체크
    public static void validateEmpty(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("입력값이 비어있습니다.");
        }
    }

    public static void validateAlreadyInput(Set<Ingredient> ingredients ,Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            throw new IllegalStateException("이미 입력한 재료입니다.");
        }
    }

    public static void validateExist(Ingredient ingredient) {
        if (!IngredientCatalog.isValidIngredient(ingredient)) {
            throw new NoSuchElementException("존재하지 않는 재료입니다.");
        }
    }

    public static boolean validateYesOrNo(String input) {
        if(input.equals("y")) {
            return true;
        } else if (input.equals("n")) {
            return false;
        } else {
            // 이외의 값 입력받으면 예외 던진다
            throw new IllegalArgumentException("y 또는 n만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }


}
