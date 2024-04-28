package org.example.base.pattern;

public class TemplateMethodPattern {

    /**
     * 炒菜的抽象类
     *
     */
    public static abstract class CookAbstractClass {

        // 模板方法，定义为 final 固定流程，禁止被重写
        public final void cookProcess() {
            //第一步：倒油
            this.pourOil();
            //第二步：热油
            this.heatOil();
            //第三步：倒蔬菜
            this.pourVegetable();
            //第四步：倒调味料
            this.pourSauce();
            //第五步：翻炒
            this.fry();
        }

        // 第一步：倒油  -  基本方法 + 具体方法
        public void pourOil() {
            System.out.println("倒油");
        }

        //第二步：热油  -  基本方法 + 具体方法
        public void heatOil() {
            System.out.println("热油");
        }

        //第三步：倒蔬菜是不一样的（一个下包菜，一个是下菜心）  -  基本方法 + 抽象方法
        public abstract void pourVegetable();

        //第四步：倒调味料是不一样  -  基本方法 + 抽象方法
        public abstract void pourSauce();

        //第五步：翻炒  -  基本方法 + 具体方法
        public void fry(){
            System.out.println("炒啊炒啊炒到熟啊");
        }
    }

    /**
     * 具体子类 - 炒手撕包菜类
     *
     */
    public static class BaoCaiConcreteClass extends CookAbstractClass {

        @Override
        public void pourVegetable() {
            System.out.println("下锅的蔬菜是包菜");
        }

        @Override
        public void pourSauce() {
            System.out.println("下锅的酱料是辣椒");
        }
    }

    /**
     * 具体子类 - 炒蒜蓉菜心类
     *
     */
    public static class CaiXinConcreteClass extends CookAbstractClass {
        @Override
        public void pourVegetable() {
            System.out.println("下锅的蔬菜是菜心");
        }

        @Override
        public void pourSauce() {
            System.out.println("下锅的酱料是蒜蓉");
        }
    }

    /**
     * 测试类
     *
     */
    public static void main(String[] args) {
        System.out.println("------------------------- 第一盘菜 -------------------------");
        // 炒手撕包菜
        CookAbstractClass baoCai = new BaoCaiConcreteClass();
//        BaoCaiConcreteClass baoCai = new BaoCaiConcreteClass();
        baoCai.cookProcess();

        System.out.println("------------------------- 第二盘菜 -------------------------");
        // 炒蒜蓉菜心
        CookAbstractClass caiXin = new CaiXinConcreteClass();
//        CaiXinConcreteClass caiXin = new CaiXinConcreteClass();
        caiXin.cookProcess();
    }
}
