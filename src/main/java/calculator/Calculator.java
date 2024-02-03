package calculator;

import java.util.ArrayList;

public class Calculator {
    private final int personCount;
    private final ArrayList<Goods> m_goods = new ArrayList<>();

    public Calculator(int personCount) {
        this.personCount = personCount;
    }

    public void addGoods(Goods goods) {
        m_goods.add(goods);
    }

    public ArrayList<Goods> getAllGoods() {
        return new ArrayList<>(m_goods);
    }

    public double getOnePersonPrice() {
        double ret = .0;

        for (Goods item : m_goods) {
            ret += item.getPrice();
        }

        return ret / personCount;
    }
}
