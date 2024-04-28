package org.example.base.pattern;

public class AdapterPattern {

    // 已存在的 LegacyRectangle 类
    static class LegacyRectangle {
        public void display(int x1, int y1, int x2, int y2) {
            System.out.println("LegacyRectangle: Point1(" + x1 + ", " + y1 + "), Point2(" + x2 + ", " + y2 + ")");
        }
    }

    // 统一的 Shape 接口
    interface Shape {
        void draw(int x, int y, int width, int height);
    }

    // 适配器类，将 LegacyRectangle 适配到 Shape 接口上
    static class RectangleAdapter implements Shape {

        private LegacyRectangle legacyRectangle;

        public RectangleAdapter(LegacyRectangle legacyRectangle) {
            this.legacyRectangle = legacyRectangle;
        }

        @Override
        public void draw(int x, int y, int width, int height) {
            int x2 = x + width;
            int y2 = y + height;
            legacyRectangle.display(x, y, x2, y2);
        }
    }

    // 在这个示例中，LegacyRectangle是已经存在的类，而RectangleAdapter是适配器类，用于将LegacyRectangle适配到Shape接口上。
    // 客户端代码通过使用适配器来画一个矩形，实际上是在调用了LegacyRectangle的display方法，但是通过适配器，它符合了Shape接口的标准。
    public static class AdapterPatternExample {
        public static void main(String[] args) {
            LegacyRectangle legacyRectangle = new LegacyRectangle();

            Shape shapeAdapter = new RectangleAdapter(legacyRectangle);

            shapeAdapter.draw(10, 20, 5, 8);
        }
    }
}
