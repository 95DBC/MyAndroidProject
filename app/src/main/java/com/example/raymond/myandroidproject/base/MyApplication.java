package com.example.raymond.myandroidproject.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.example.raymond.myandroidproject.db.SharePrefenceHelper;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Raymond 陈徐锋 on 2018/2/23.
 * Email: raymond@hinteen.com
 * Description: 用于全局变量的使用
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
    private Session session;
    private boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        /**
         * 初始化Bugly 设置
         * 可直接在代码设置，或直接在Mainfest.xml 中进行配置
         * 详细可参考如下文档：https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20180119105842
         */
        Context context = getApplicationContext();
      // 获取当前包名
        String packageName = context.getPackageName();
      // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
      // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
      // 初始化Bugly
        CrashReport.initCrashReport(context, "4b34415a95", isDebug, strategy);
      // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
      // CrashReport.initCrashReport(context, strategy);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     * @return 获取MyApplication 的单例模式
     */
    public static MyApplication appSingleInstance(){
        return mApp;
    }


    /**
     * 获取当前对话实例
     *
     * @return
     */
    public Session getSession() {
        if (session == null){
            session = (Session) SharePrefenceHelper.get(this,"Session",session);
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
        SharePrefenceHelper.put(this,"Session",session);
    }
}
