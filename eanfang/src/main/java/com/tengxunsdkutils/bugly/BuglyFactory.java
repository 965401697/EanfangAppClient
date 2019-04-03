package com.tengxunsdkutils.bugly;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public class BuglyFactory {
    public static IBugly createBuglyManager(){
        IBugly mIBugly=new BuglyManager();
        return mIBugly;
    }
}
