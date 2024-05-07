package org.example.algorithm.HaiKangAlgo;

/**
 * 模板方法模式 - 机房巡检
 *
 */
public class ShortQA {
    /**
     * 巡检机房的抽象类
     *
     */
    public static abstract class InspectAbstractClass {

        // 巡检的模板方法
        public final void inspectProcess() {

            inspect();
            commitResult();

        }

        // 抽象类，两种巡检任务：扫描二维码 和 远程视频
        public abstract void inspect();

        // 提交巡检结果
        public abstract void commitResult();
    }

    /**
     * A巡检任务：二维码方式 - 服务器状态（正常/异常）
     *
     */
    public static class AInspection extends InspectAbstractClass {

        private int serverStatus = 1; // 服务器状态 （正常：1/异常：0）

        @Override
        public void inspect() {

            // 巡检流程
            // 判定服务器状态, 若服务器异常
            this.serverStatus = 0;
            // this.serverStatus = 1;
        }

        @Override
        public void commitResult() {

            if (this.serverStatus == 1) {
                System.out.println("A巡检任务提交结果：服务器状态正常！");
            } else {
                System.out.println("A巡检任务提交结果：服务器状态异常！");
            }
        }
    }

    /**
     * B巡检任务：远程视频方式 - 学生出勤率（百分比）
     *
     */
    public static class BInspection extends InspectAbstractClass {

        private double attendRate;   // 出勤率

        @Override
        public void inspect() {
            // 巡检：获取应到学生人数、获取实际到达的学生人数
//            int total = getTotal();
            int total = 10;
//            int attend = getAttend();
            int attend = 3;
            // 计算出勤率
            this.attendRate = (double) attend / total;
        }

        @Override
        public void commitResult() {

            // 提交结果
            System.out.printf("B巡检任务提交结果：%.2f%%", this.attendRate * 100);
        }
    }

    // Client
    public static void main(String[] args) {

        // 实现 A 巡检任务
        InspectAbstractClass aInspection = new AInspection();
        aInspection.inspectProcess();

        // 实现 B 巡检任务
        InspectAbstractClass bInspection = new BInspection();
        bInspection.inspectProcess();
    }

}
