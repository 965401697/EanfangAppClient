package net.eanfang.client.util;


import com.eanfang.util.FileUtils;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;


/**
 * @author wen
 *         Created at 2017/3/2
 * @desc
 */
public class LogUtils {

    public static final String TAG = "LogUtils";

    //是否显示日志，正式版屏蔽日志
    private static boolean isDebug = BuildConfig.LOG_DEBUG;

    // log2file config
    private static boolean V_log2file = true;
    private static boolean D_log2file = true;
    private static boolean I_log2file = true;
    private static boolean W_log2file = true;
    private static boolean E_log2file = true;

    public static void v(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.v(tag, msg);
        if (V_log2file)
            log2file("V/" + tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable t) {
        if (!isDebug) return;

        android.util.Log.v(tag, msg, t);
        if (V_log2file)
            log2file("V/" + tag, msg, t);
    }

    public static void vfmt(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.v(tag, msg);
        if (V_log2file)
            log2file("V/" + tag, msg, null);
    }

    public static void d(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.d(tag, msg);
        if (D_log2file)
            log2file("D/" + tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (!isDebug) return;

        android.util.Log.d(tag, msg, t);
        if (D_log2file)
            log2file("D/" + tag, msg, t);
    }

    public static void dfmt(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.d(tag, msg);
        if (D_log2file)
            log2file("D/" + tag, msg, null);
    }

    public static void i(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.i(tag, msg);
        if (I_log2file)
            log2file("I/" + tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable t) {
        if (!isDebug) return;

        android.util.Log.i(tag, msg, t);
        if (I_log2file)
            log2file("I/" + tag, msg, t);
    }

    public static void ifmt(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.i(tag, msg);
        if (I_log2file)
            log2file("I/" + tag, msg, null);
    }

    public static void w(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.w(tag, msg);
        if (W_log2file)
            log2file("W/" + tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable t) {
        if (!isDebug) return;

        android.util.Log.w(tag, msg, t);
        if (W_log2file)
            log2file("W/" + tag, msg, t);
    }

    public static void wfmt(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.w(tag, msg);
        if (W_log2file)
            log2file("W/" + tag, msg, null);
    }

    public static void e(String tag, String msg) {
        if (!isDebug) return;

        android.util.Log.e(tag, msg);
        if (E_log2file)
            log2file("E/" + tag, msg, null);
    }

    public static void e(String msg) {
        if (!isDebug) return;

        android.util.Log.e("TAG", msg);
        if (E_log2file)
            log2file("E/" + "TAG", msg, null);
    }

    public static void e(String tag, String msg, Throwable t) {
        if (!isDebug) return;

        android.util.Log.e(tag, msg, t);
        if (E_log2file)
            log2file("E/" + tag, msg, t);
    }

    public static void efmt(String tag, String fmt, Object... args) {
        if (!isDebug) return;

        String msg = String.format(Locale.US, fmt, args);
        android.util.Log.e(tag, msg);
        if (E_log2file)
            log2file("E/" + tag, msg, null);
    }

    // 获取 Throwable 的堆栈信息
    public static String getThrowableMsg(Throwable t) {
        if (t == null)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(t.toString());
        sb.append("\n");

        StackTraceElement[] stack = t.getStackTrace();
        for (StackTraceElement a : stack) {
            sb.append("\t\t\t");
            sb.append(a.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    // 打印字符串的16进制形式
    public static void printHexString(String tag, String str) {
        LogUtils.e(tag, "=== [0x] " + StringUtils.toHexString(str) + " ===");
    }

    /**
     * 打印 Throwable 的堆栈信息, 这个不能替换为 LogUtils.printStackTrace(),避免递归调用。
     */
    public static void printStackTrace(Throwable t) {
        if (!isDebug) {
            return;
        }

        t.printStackTrace();
        if (E_log2file) {
            log2file("Exception/", getThrowableMsg(t), null);
        }
    }

    // 把log写入文件
    private static synchronized void log2file(String tag, String msg, Throwable t) {
        //String filename = "/sdcard/" + Deeper.getContext().getPackageName() + "/log_20150101.txt";
        String dir = "/sdcard/carinternet/log";
        String filename = "log_" + GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow()) + ".txt";
        String logFilePath = dir + "/" + filename;
        File file = new File(dir);
        if (!file.exists()) {
            if (FileUtils.makeDir(dir) == null) {
                return;
            }
        }

//        //todo 用于删除用户本地的日志文件，之前遗留的问题导致用户本地日志过大
//        //todo 删除时间是否过长
//        try {
//            File logFile = new File(logFilePath);
//            if(logFile.exists()){
//                android.util.Log.d(TAG, "Delete start!");
//                boolean delete = logFile.delete();
//                android.util.Log.d(TAG, "Delete result:" + delete);
//            }else{
//                android.util.Log.d(TAG, "Delete File not exist!");
//            }
//        } catch (Exception e) {
//            android.util.Log.e(TAG, "Delete log file exception!", e);
//        }


        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(logFilePath, "rw");
            raf.seek(raf.length());
            raf.write(GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow()).getBytes());
            raf.write("  ".getBytes());
            raf.write(tag.getBytes());
            raf.write(": ".getBytes());
            raf.write(msg.getBytes());
            raf.write("\n".getBytes());
            if (t != null) {
                raf.write(getThrowableMsg(t).getBytes());
                raf.write("\n".getBytes());
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
