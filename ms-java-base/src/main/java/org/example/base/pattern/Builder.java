package org.example.base.pattern;

import lombok.Setter;

public class Builder {

    // 定义房屋类 House，它具有多个属性，如地基、结构、屋顶和装修。
    @Setter
//    @lombok.Builder
    static class House {
        private String foundation;
        private String structure;
        private String roof;
        private String interior;

        @Override
        public String toString() {
            return "House [foundation=" + foundation + ", structure=" + structure + ", roof=" + roof + ", interior=" + interior + "]";
        }
    }

    // 创建一个抽象建造者类 HouseBuilder, 定义了构建房屋的方法
    static abstract class HouseBuilder {

        protected House house = new House();

        public abstract void buildFoundation();
        public abstract void buildStructure();
        public abstract void buildRoof();
        public abstract void buildInterior();

        public House getHouse() {
            return house;
        }
    }

    // 创建两个具体的建造者类 ConcreteHouseBuilder 和 LuxuryHouseBuilder
    // 分别实现了不同类型房屋的构建过程。
    // 具体建造者类 - 普通房屋
    static class ConcreteHouseBuilder extends HouseBuilder {

        @Override
        public void buildFoundation() {
            house.setFoundation("Standard Foundation");
        }

        @Override
        public void buildStructure() {
            house.setStructure("Standard Structure");
        }

        @Override
        public void buildRoof() {
            house.setRoof("Standard Roof");
        }

        @Override
        public void buildInterior() {
            house.setInterior("Standard Interior");
        }
    }

    // 具体建造者类 - 豪华房屋
    static class LuxuryHouseBuilder extends HouseBuilder {

        @Override
        public void buildFoundation() {
            house.setFoundation("Strong Foundation");
        }

        @Override
        public void buildStructure() {
            house.setStructure("Reinforced Structure");
        }

        @Override
        public void buildRoof() {
            house.setRoof("Elegant Roof");
        }

        @Override
        public void buildInterior() {
            house.setInterior("Luxury Interior");
        }
    }

    static class Director {
        private HouseBuilder builder;

        public Director(HouseBuilder builder) {
            this.builder = builder;
        }

        public House constructHouse() {
            builder.buildFoundation();
            builder.buildStructure();
            builder.buildRoof();
            builder.buildInterior();
            return builder.getHouse();
        }
    }

    // 这个示例演示了如何使用建造者模式创建不同类型的房屋，每种房屋类型的建造过程都由相应的具体建造者类负责实现，而指导者类负责协调建造过程。
    static class BuilderPatternExample {
        public static void main(String[] args) {
            HouseBuilder concreteBuilder = new ConcreteHouseBuilder();
            Director concreteDirector = new Director(concreteBuilder);
            House concreteHouse = concreteDirector.constructHouse();
            System.out.println("Concrete House: " + concreteHouse);

            HouseBuilder luxuryBuilder = new LuxuryHouseBuilder();
            Director luxuryDirector = new Director(luxuryBuilder);
            House luxuryHouse = luxuryDirector.constructHouse();
            System.out.println("Luxury House: " + luxuryHouse);

            /* @Builder
            House house = House.builder()
                    .foundation("")
                    .structure("")
                    .roof("")
                    .interior("")
                    .build();
             */
        }
    }

}
