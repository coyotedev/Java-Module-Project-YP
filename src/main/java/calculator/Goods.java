package calculator;

public class Goods {
     private final String name;
     private final double price;

     public Goods(String name, double price) {
         this.name = name;
         this.price = price;
     }

     public String getName() {
         return this.name;
     }

     public double getPrice() {
         return this.price;
     }
}
