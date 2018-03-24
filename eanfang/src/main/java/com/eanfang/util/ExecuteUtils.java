package com.eanfang.util;

public class ExecuteUtils {

    /**
     * @param executeListener 需要执行的方法
     * @param successCode     成功code
     * @param resetCount      重试次数 0为 只执行一次
     * @param success         成功回掉
     * @param fail            失败回掉
     */
    public static void execute(ExecuteListener executeListener, int successCode, int resetCount, ExecutedListener success, ExecutedListener fail) {

        for (int i = 0; i <= resetCount; i++) {
            int executeCode = executeListener.execute();
            //99为特殊操作
            if (successCode == executeCode || executeCode == 99) {
                if (success != null) {
                    success.execute();
                }
                return;
            }
            if (i == resetCount) {
                if (fail != null) {
                    fail.execute();
                }
            }
        }
    }

    public static void execute(ExecuteListener executeListener, int successCode, int resetCount) {
        execute(executeListener, successCode, resetCount, null, null);

    }

    @FunctionalInterface
    public interface ExecuteListener {
        /**
         * @return 执行成功的标识
         */
        int execute();

    }

    @FunctionalInterface
    public interface ExecutedListener {
        void execute();
    }
}
