package jdkproxy;

import java.lang.reflect.Proxy;
import java.util.Map;

public class ProxyUsage {

    public static void main(String[] args) {
        Map map = (Map) Proxy.newProxyInstance(ProxyUsage.class.getClassLoader(),
                new Class[]{Map.class},
                new DynamicInvocationHandler());

        map.put("Hello", "world");
    }
}
