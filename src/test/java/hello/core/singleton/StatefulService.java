package hello.core.singleton;

public class StatefulService {

//    private int price; // 상태를 유지하는 필드

    // 클라이언트의 의도는 이거임. 주문을 해서 값을 price 필드에 저장해놓고, getPrice()를 통해 꺼내고 싶었던거임.

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // 여기가 문제!
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
