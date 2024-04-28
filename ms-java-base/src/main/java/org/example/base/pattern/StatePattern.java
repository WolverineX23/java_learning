package org.example.base.pattern;


public class StatePattern {

    // 环境角色类
    public static class Context {
        //定义出所有的电梯状态
        public final static OpenningState OPENNING_STATE= new OpenningState();//开门状态，这时候电梯只能关闭
        public final static ClosingState CLOSING_STATE= new ClosingState();//关闭状态，这时候电梯可以运行、停止和开门
        public final static RunningState RUNNING_STATE= new RunningState();//运行状态，这时候电梯只能停止
        public final static StoppingState STOPPING_STATE= new StoppingState();//停止状态，这时候电梯可以开门、运行

        //定义一个当前电梯状态
        private LiftState liftState;

        public LiftState getLiftState() {
            return this.liftState;
        }

        public void setLiftState(LiftState liftState) {
            //当前环境改变
            this.liftState = liftState;
            //把当前的环境通知到各个实现类中
            this.liftState.setContext(this);
        }

        public void open() {
            this.liftState.open();
        }

        public void close() {
            this.liftState.close();
        }

        public void run() {
            this.liftState.run();
        }

        public void stop() {
            this.liftState.stop();
        }
    }

    //抽象状态类
    static abstract class LiftState {
        //定义一个环境角色，也就是封装状态的变化引起的功能变化
        protected Context context;

        public void setContext(Context context) {
            this.context = context;
        }

        //电梯开门动作
        public abstract void open();

        //电梯关门动作
        public abstract void close();

        //电梯运行动作
        public abstract void run();

        //电梯停止动作
        public abstract void stop();
    }

    // 具体状态类：开门状态
    static class OpenningState extends LiftState {
        //开启当然可以关闭了，我就想测试一下电梯门开关功能
        @Override
        public void open() {
            System.out.println("电梯门开启...");
        }

        @Override
        public void close() {
            //状态修改
            super.context.setLiftState(Context.CLOSING_STATE);
            //动作委托为CloseState来执行，也就是委托给了ClosingState子类执行这个动作
            super.context.getLiftState().close();
        }

        //电梯门不能开着就跑，这里什么也不做
        @Override
        public void run() {
            //do nothing
        }

        //开门状态已经是停止的了
        @Override
        public void stop() {
            //do nothing
        }
    }

    // 具体状态类：关门状态
    static class ClosingState extends LiftState {

        @Override
        //电梯门关闭，这是关闭状态要实现的动作
        public void close() {
            System.out.println("电梯门关闭...");
        }

        //电梯门关了再打开，逗你玩呢，那这个允许呀
        @Override
        public void open() {
            super.context.setLiftState(Context.OPENNING_STATE);
            super.context.open();
        }

        //电梯门关了就跑，这是再正常不过了
        @Override
        public void run() {
            super.context.setLiftState(Context.RUNNING_STATE);
            super.context.run();
        }

        //电梯门关着，我就不按楼层
        @Override
        public void stop() {
            super.context.setLiftState(Context.STOPPING_STATE);
            super.context.stop();
        }
    }

    //运行状态
    public static class RunningState extends LiftState {

        //运行的时候开电梯门？你疯了！电梯不会给你开的
        @Override
        public void open() {
            //do nothing
        }

        //电梯门关闭？这是肯定了
        @Override
        public void close() {//虽然可以关门，但这个动作不归我执行
            //do nothing
        }

        //这是在运行状态下要实现的方法
        @Override
        public void run() {
            System.out.println("电梯正在运行...");
        }

        //这个事绝对是合理的，光运行不停止还有谁敢做这个电梯？！估计只有上帝了
        @Override
        public void stop() {
            super.context.setLiftState(Context.STOPPING_STATE);
            super.context.stop();
        }
    }

    //停止状态
    public static class StoppingState extends LiftState {

        //停止状态，开门，那是要的！
        @Override
        public void open() {
            //状态修改
            super.context.setLiftState(Context.OPENNING_STATE);
            //动作委托为CloseState来执行，也就是委托给了ClosingState子类执行这个动作
            super.context.getLiftState().open();
        }

        @Override
        public void close() {//虽然可以关门，但这个动作不归我执行
            //状态修改
            super.context.setLiftState(Context.CLOSING_STATE);
            //动作委托为CloseState来执行，也就是委托给了ClosingState子类执行这个动作
            super.context.getLiftState().close();
        }

        //停止状态再跑起来，正常的很
        @Override
        public void run() {
            //状态修改
            super.context.setLiftState(Context.RUNNING_STATE);
            //动作委托为CloseState来执行，也就是委托给了ClosingState子类执行这个动作
            super.context.getLiftState().run();
        }

        //停止状态是怎么发生的呢？当然是停止方法执行了
        @Override
        public void stop() {
            System.out.println("电梯停止了...");
        }
    }


    // 在这个示例中，我们创建了一个模拟电梯系统，其中有开门状态和关门状态两个具体状态类，以及电梯类作为上下文类。
    // 通过切换状态，电梯在不同状态下有不同的行为表现。这就是状态模式的基本思想。
    public static class Client {
        public static void main(String[] args) {
            //创建环境角色对象
            Context context = new Context();
            //设置当前电梯装填
            context.setLiftState(new ClosingState());
            context.open();
            context.run();
            context.close();
            context.stop();
        }
    }
}
