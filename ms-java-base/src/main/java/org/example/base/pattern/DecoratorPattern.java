package org.example.base.pattern;

public class DecoratorPattern {

    // 定义一个咖啡接口
    interface Coffee {
        double cost();
        String desc();
    }

    // 实现基本的咖啡类
    static class SimpleCoffee implements Coffee {

        @Override
        public double cost() {
            return 2.0;
        }

        @Override
        public String desc() {
            return "Simple Coffee";
        }
    }

    // 创建装饰器抽象类
    static abstract class CoffeeDecorator implements Coffee {

        protected Coffee decoratedCoffee;

        public CoffeeDecorator(Coffee coffee) {
            this.decoratedCoffee = coffee;
        }

        @Override
        public double cost() {
            return decoratedCoffee.cost();
        }

        @Override
        public String desc() {
            return decoratedCoffee.desc();
        }
    }

    // 创建 装饰 Milk 的具体的修饰器类
    static class MilkDecorator extends CoffeeDecorator {

        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public double cost() {
            return super.cost() + 0.5;
        }

        @Override
        public String desc() {
            return super.desc() + ", with Milk";
        }
    }

    // 创建 装饰 Sugar 的具体的修饰器类
    static class SugarDecorator extends CoffeeDecorator {

        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public double cost() {
            return super.cost() + 0.2;
        }

        @Override
        public String desc() {
            return super.desc() + ", with Sugar";
        }
    }

    // 在这个示例中，Coffee 接口定义了基本的咖啡功能。SimpleCoffee 类实现了基本的咖啡。
    // CoffeeDecorator 是装饰器的抽象类，它维护一个被装饰的咖啡对象。
    // MilkDecorator 和 SugarDecorator 分别实现了具体的装饰器，通过在原始咖啡上添加新的功能。
    public static class DecoratorPatternExample {
        public static void main(String[] args) {
            Coffee simpleCoffee = new SimpleCoffee();
            System.out.println("SimpleCoffee - Cost: $" + simpleCoffee.cost() + ", Desc: " + simpleCoffee.desc());

            Coffee milkCoffee = new MilkDecorator(simpleCoffee);
            System.out.println("MilkCoffee - Cost: $" + milkCoffee.cost() + ", Desc: " + milkCoffee.desc());

            Coffee milkSugarCoffee = new SugarDecorator(milkCoffee);
            System.out.println("MilkSugarCoffee - Cost: $" + milkSugarCoffee.cost() + ", Desc: " + milkSugarCoffee.desc());
        }
    }
}
