import java.sql.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);

        // 음료 메뉴 생성
        List<Drink> menu = new ArrayList<>();
        Smoothie strawberrySmoothie = new Smoothie("딸기요거트스무디", "딸기");
        Smoothie mangoSmoothie = new Smoothie("망고요거트스무디", "망고");
        Smoothie blueberrySmoothie = new Smoothie("블루베리요거트스무디", "블루베리");
        Ade lemonAde = new Ade("레몬에이드", "레몬");
        Ade orangeAde = new Ade("오렌지에이드", "오렌지");
        Ade cherryAde = new Ade("체리에이드", "체리");
        IcedDrink IcedCafeLatte = new IcedDrink("아이스카페라떼", Arrays.asList("우유", "에스프레소"));
        IcedDrink IcedAmericano = new IcedDrink("아이스아메리카노", Arrays.asList("물", "에스프레소"));
        HotDrink HotCafeLatte = new HotDrink("핫카페라떼", Arrays.asList("우유", "에스프레소"));
        HotDrink HotAmericano = new HotDrink("핫아메리카노", Arrays.asList("물", "에스프레소"));

        menu.add(strawberrySmoothie);
        menu.add(mangoSmoothie);
        menu.add(blueberrySmoothie);
        menu.add(lemonAde);
        menu.add(orangeAde);
        menu.add(cherryAde);
        menu.add(IcedCafeLatte);
        menu.add(IcedAmericano);
        menu.add(HotCafeLatte);
        menu.add(HotAmericano);

        // 인트로,  게임 설명
        System.out.println("어서오세요~ 춘식 카페입니다! (^._.^)ﾉ");
        System.out.println("----------------------------------------------");
        System.out.println("음료 레시피");
        System.out.println("----------------------------------------------");
        System.out.println("아이스아케리카노 -> 물, 얼음, 에스프레소");
        System.out.println("핫카페라떼 -> 얼음, 에스프레소");
        System.out.println("딸기요거트스무디 -> 얼음, 우유, 요거트, 딸기, 시럽");
        System.out.println("레몬에이드 -> 얼음, 탄산수, 레몬, 시럽");
        System.out.println("----------------------------------------------");
        System.out.println("게임 방법");
        System.out.println("손님이 주문한 음료에 들어가는 재료를 입력하면 성공.");
        System.out.println("순서는 상관없어요. 재료를 하나씩 입력해주세요.");
        System.out.println("----------------------------------------------");

        String start = "";

        while (!start.equals("시작")) {
            System.out.println("시작하려면 \"시작\"을 입력해주세요.");
            System.out.print(">>> ");
            start = sc.nextLine();
        }

        // 주문 랜덤 생성
        Random rand = new Random();
        Drink order = menu.get(rand.nextInt(menu.size()));

        for (int i=0; i<10; i++) {
            System.out.println();
        }

        System.out.println("손님: " + order.getName() + " 한 잔 주세요.");
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("들어가는 재료");
        System.out.println("----------------------------------------------");
        System.out.println("물 | 우유 | 얼음 | 탄산수 | 에스프레소");
        System.out.println("딸기 | 망고 | 블루베리 | 오렌지 | 레몬 | 체리");
        System.out.println("요거트 | 시럽");
        System.out.println("----------------------------------------------");
        System.out.println();

        Set<String> ingredients = new HashSet<>();
        for (int i=0; i<order.getRecipe().size(); i++) {
            System.out.print("재료 입력>>> ");
            ingredients.add(sc.nextLine().trim());
        }

        System.out.println();
        if(ingredients.equals(order.getRecipe())) {
            System.out.println("손님: 맛있어요 또올게요!");
        } else {
            System.out.println("손님: 제가 주문한건 이게 아닌데요...");
            System.out.println(order.getName() + "에 들어가는 재료: " + order.getRecipe());
        }


    }
}
