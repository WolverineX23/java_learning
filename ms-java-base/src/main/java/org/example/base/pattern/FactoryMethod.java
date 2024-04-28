package org.example.base.pattern;

public class FactoryMethod {

    // 图形接口
    interface Shape {
        void draw();
    }

    // 实现两个具体的图形类，分别是 Circle（圆形）和 Rectangle（矩形）
    static class Circle implements Shape {

        @Override
        public void draw() {
            System.out.println("Drawing a Circle");
        }
    }

    static class Rectangle implements Shape {

        @Override
        public void draw() {
            System.out.println("Drawing a Rectangle");
        }
    }

    // 抽象工厂类 ShapeFactory
    // 定义了一个抽象的工厂方法 createShape, 子类将实现这个方法来创建具体的图形对象
    static abstract class ShapeFactory {
        abstract Shape createShape();
    }

    static class CircleFactory extends ShapeFactory {

        @Override
        Shape createShape() {
            return new Circle();
        }
    }

    static class RectangleFactory extends ShapeFactory {

        @Override
        Shape createShape() {
            return new Rectangle();
        }
    }

    static class FactoryMethodExample {
        public static void main(String[] args) {
            ShapeFactory circleFactory = new CircleFactory();
            Shape circle = circleFactory.createShape();
            circle.draw();

            ShapeFactory rectangleFactory = new RectangleFactory();
            Shape rectangle = rectangleFactory.createShape();
            rectangle.draw();
        }
    }
}
