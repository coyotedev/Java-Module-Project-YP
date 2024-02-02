import static java.lang.Math.floor;

import java.util.Scanner;

import calculator.Calculator;
import calculator.Goods;

public class Main {

    private static final String MESSAGE_HOW_MUCH_PERSON = "На скольких человек необходимо разделить счёт?";
    private static final String MESSAGE_SUFFIX_RETRY = " Пожалуйста, повторите попытку...";
    private static final String MESSAGE_PERSON_COUNT_INCORRECT = "Количество человек, разделяющих счет, должно быть больше 1." + MESSAGE_SUFFIX_RETRY;
    private static final String MESSAGE_INVALID_INPUT = "Осуществлен некорректный ввод." + MESSAGE_SUFFIX_RETRY;
    private static final String MESSAGE_INVALID_INPUT_NEGATIVE_PRICE = "Цена товара не может быть меньше 0." + MESSAGE_SUFFIX_RETRY;
    private static final String MESSAGE_GOODS_REQUEST_NAME = "Введите название товара:";
    private static final String MESSAGE_GOODS_REQUEST_PRICE = "И его стоимость в формате 00.00 (рубли.копейки):";
    private static final String MESSAGE_GOODS_REQUEST_NAME_INVALID = "Название не должно быть пустым. " + MESSAGE_SUFFIX_RETRY;
    private static final String MESSAGE_GOODS_ADDED = "Товар успешно добавлен! Для того, чтобы добавить ещё один товар - введите любой символ. Введите \"Завершить\", чтобы завершить ввод товаров.";
    private static final String MESSAGE_GOODS_SHOW_TITLE = "Добавленные товары:";
    private static final String MESSAGE_GOODS_SHOW_ITEM_FORMAT = "Название: %s, Цена: %.2f";
    private static final String MESSAGE_PART_PRICE_FORMAT = "Каждый человек должен заплатить %.2f %s";
    private static final String RUBLES_ROOT = "руб";
    private static final String[] RUBLES_SUFFIX = {"ль", "ля", "лей"};
    private static final int PERSON_COUNT_CORRECT = 2;
    private static final String SAFE_WORD = "завершить";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator(requestPersonCount(scanner));

        requestGoods(scanner, calculator);

        showResult(calculator);

        scanner.close();
    }

    private static void showResult(Calculator calculator) {
        System.out.println(MESSAGE_GOODS_SHOW_TITLE);

        for (Goods item : calculator.getAllGoods()) {
            System.out.println(String.format(MESSAGE_GOODS_SHOW_ITEM_FORMAT, item.getName(), item.getPrice()));
        }

        double onePersonPrice = calculator.getOnePersonPrice();
        System.out.println(String.format(MESSAGE_PART_PRICE_FORMAT, onePersonPrice, getRublesText(onePersonPrice)));
    }

    private static String getRublesText(double price) {
        String ret = RUBLES_ROOT;

        double priceTransform = floor(price) % 100;
        if (priceTransform > 19) priceTransform = priceTransform % 10;

        if (priceTransform == 0 || priceTransform > 4) {
            ret += RUBLES_SUFFIX[2];
        } else if (priceTransform == 1) {
            ret += RUBLES_SUFFIX[0];
        } else {
            ret += RUBLES_SUFFIX[1];
        }

        return ret;
    }

    private static void requestGoods(Scanner scanner, Calculator calculator) {
        String safeWord = "";

        while (!safeWord.equalsIgnoreCase(SAFE_WORD)) {
            System.out.println(MESSAGE_GOODS_REQUEST_NAME);
            String goodsName = requestName(scanner);

            System.out.println(MESSAGE_GOODS_REQUEST_PRICE);
            double goodsPrice = requestPrice(scanner);

            calculator.addGoods(new Goods(goodsName, goodsPrice));

            System.out.println(MESSAGE_GOODS_ADDED);
            safeWord = scanner.nextLine();
        }
    }

    private static double requestPrice(Scanner scanner) {
        double ret = .0;

        try {
            String input = scanner.nextLine();
            double inputConverted = Double.parseDouble(input);
            String[] doubleParts = input.split("\\.");
            if (doubleParts.length == 1 || (doubleParts.length > 1 && doubleParts[1].length() != 2)) {
                throw new IllegalStateException();
            }
            while (inputConverted < 0) {
                System.out.println(MESSAGE_INVALID_INPUT_NEGATIVE_PRICE);
                input = scanner.nextLine();
                inputConverted = Double.parseDouble(input);
            }
            ret = inputConverted;
        } catch (Exception e) {
            System.out.println(MESSAGE_INVALID_INPUT);
            return requestPrice(scanner);
        }

        return ret;
    }

    private static String requestName(Scanner scanner) {
        String ret = scanner.nextLine();

        while (ret.isBlank()) {
            System.out.println(MESSAGE_GOODS_REQUEST_NAME_INVALID);
            ret = scanner.nextLine();
        }

        return ret.trim();
    }

    private static int requestPersonCount(Scanner scanner) {
        int ret = 0;

        try {
            while (ret < PERSON_COUNT_CORRECT) {
                System.out.println(MESSAGE_HOW_MUCH_PERSON);
                String input = scanner.nextLine();
                ret = Integer.parseInt(input);
                if (ret < PERSON_COUNT_CORRECT) {
                    System.out.println(MESSAGE_PERSON_COUNT_INCORRECT);
                }
            }
        } catch (Exception e) {
            System.out.println(MESSAGE_INVALID_INPUT);
            return requestPersonCount(scanner);
        }

        return ret;
    }
}