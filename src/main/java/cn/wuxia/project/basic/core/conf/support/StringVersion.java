package cn.wuxia.project.basic.core.conf.support;

import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.common.util.StringVersionUtil;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

public class StringVersion {
    public boolean eq(String v1, String v2) {
        return StringVersionUtil.compareVersion(v1, v2) == 0;
    }

    public boolean ne(String v1, String v2) {
        return StringVersionUtil.compareVersion(v1, v2) != 0;
    }

    public boolean gt(String v1, String v2) {
        return StringVersionUtil.compareVersion(v1, v2) > 0;
    }

    public boolean lt(String v1, String v2) {
        return StringVersionUtil.compareVersion(v1, v2) < 0;
    }


    public boolean gte(String v1, String v2) {
        return gt(v1, v2) || eq(v1, v2);
    }

    public boolean lte(String v1, String v2) {
        return lt(v1, v2) || eq(v1, v2);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new StringVersion().lt("1.2.0", "1.12.1"));
        System.out.println(new StringVersion().gt("1.2.0.1", "1.2.1.9"));
        System.out.println(new StringVersion().lte("9.9.9", "10.0.0"));
        System.out.println(new StringVersion().lte("9.9.9", "9.9.9"));
        System.out.println(new StringVersion().eq("9.9.9_20180911.3", "9.9.9_20180911.2"));

        String express8 = "(os=='ios' && version <= '10.0.0') || a==2";
        String express9 = "(os=='ios' && lte(version,\"10.0.0\")) || a==2";
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String, Object> context = new DefaultContext<String, Object>();
        ((DefaultContext<String, Object>) context).put("os", "ios");
        ((DefaultContext<String, Object>) context).put("a", "1");
        runner.addFunctionOfServiceMethod("eq", new StringVersion(), "eq",
                new Class[]{String.class, String.class}, null);
        runner.addFunctionOfServiceMethod("gt", new StringVersion(), "gt",
                new Class[]{String.class, String.class}, null);
        runner.addFunctionOfServiceMethod("lt", new StringVersion(), "lt",
                new Class[]{String.class, String.class}, null);
        runner.addFunctionOfServiceMethod("gte", new StringVersion(), "gte",
                new Class[]{String.class, String.class}, null);
        runner.addFunctionOfServiceMethod("lte", new StringVersion(), "lte",
                new Class[]{String.class, String.class}, null);
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    String version = i + "." + j + "." + k;
                    ((DefaultContext<String, Object>) context).put("version", version);
                    if (StringUtil.contains(express8, "version")) {

                    }
                    Object trueOrFalse = runner.execute(express9, context, null, true, false);
                    System.out.println(version + " <= 10.0.0 ? ===  " + trueOrFalse);
                }
            }
        }
    }


}
