package org.example.base.pattern;

public class AbstractFactory {

    // 抽象产品接口：操作系统
    interface OperatingSystem {
        void run();
    }

    // 具体产品：Windows 操作系统
    static class WindowsOS implements OperatingSystem {

        @Override
        public void run() {
            System.out.println("Running Windows OS");
        }
    }

    // 具体产品：Linux 操作系统
    static class LinuxOS implements OperatingSystem {

        @Override
        public void run() {
            System.out.println("Running Linux OS");
        }
    }

    // 抽象产品接口：应用程序
    interface Application {
        void open();
    }

    // 具体产品：word 应用程序
    static class WordApplication implements Application {

        @Override
        public void open() {
            System.out.println("Opening Word Application");
        }
    }

    // 具体产品：Excel 应用程序
    static class ExcelApplication implements Application {

        @Override
        public void open() {
            System.out.println("Opening Excel Application");
        }
    }

    // 抽象工厂接口
    interface SoftwareFactory {

        OperatingSystem createOperatingSystem();

        Application createApplication();
    }

    // 具体工厂：Windows + Excel 工厂
    static class WindowsExcelFactory implements SoftwareFactory {

        @Override
        public OperatingSystem createOperatingSystem() {
            return new WindowsOS();
        }

        @Override
        public Application createApplication() {
            return new ExcelApplication();
        }
    }

    // 具体工厂：Linux + Word 工厂
    static class LinuxWordFactory implements SoftwareFactory {

        @Override
        public OperatingSystem createOperatingSystem() {
            return new LinuxOS();
        }

        @Override
        public Application createApplication() {
            return new WordApplication();
        }
    }

    // 在这个示例中，抽象工厂模式通过SoftwareFactory接口和其实现类来创建不同类型的操作系统和应用程序。
    // 客户端代码可以根据需要选择不同的工厂实例来创建不同的产品组合。
    static class Client {
        public static void main(String[] args) {

            // windows excel 产品族生产工厂
            SoftwareFactory windowsExcelFactory = new WindowsExcelFactory();
            OperatingSystem windowsOS = windowsExcelFactory.createOperatingSystem();
            Application excelApplication = windowsExcelFactory.createApplication();

            windowsOS.run();
            excelApplication.open();

            // linux word 产品族生产工厂
            SoftwareFactory linuxWordFactory = new LinuxWordFactory();
            OperatingSystem linuxOS = linuxWordFactory.createOperatingSystem();
            Application wordApplication = linuxWordFactory.createApplication();

            linuxOS.run();
            wordApplication.open();
        }
    }
}
