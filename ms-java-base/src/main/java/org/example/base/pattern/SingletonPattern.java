package org.example.base.pattern;

public class SingletonPattern {

    static class Singleton {
        // 私有静态成员变量，用于保存单例实例
        private static volatile Singleton instance;

        // 私有构造方法，防止外部实例化
        private Singleton() {
            // 初始化操作
        }

        // 公共静态方法，用于获取单例实例 - 双重检验锁
        public static Singleton getInstance() {

            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        // 如果实例为空，则创建一个新实例
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }

        // 其他成员方法
        public void showMessage() {
            System.out.println("Hello, I am a Singleton!");
        }
    }

    // 这个示例演示了如何创建一个简单的单例模式
    // 但请注意，这个实现并不是线程安全的。
    // 在多线程环境中，可能会出现多个线程同时访问getInstance()方法，导致创建多个实例的情况。
    // 为了实现线程安全的单例模式，可以使用双重检查锁定或其他同步机制。
    public static class SingletonExample {
        public static void main(String[] args) {
            // 获取单例实例
            Singleton singleton = Singleton.getInstance();

            // 调用成员方法
            singleton.showMessage();
        }
    }

}
