package org.liyaojin.study.netty.api.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.liyaojin.study.netty.server.EchoServer;

public class EchoServerTest {
    @Test
    public void start() {
        final IServer server = new EchoServer(9999);
        Thread startThread = new Thread(new Runnable() {
            public void run() {
                assertNotEquals(server.start(), false);
            }
        });
        // 设置成守护线程，防止jvm不自动结束，jvm结束的前提是所有用户线程都已经退出
        startThread.setDaemon(true);
        startThread.start();
        try {
            startThread.join(3000);
        } catch (Throwable ex) {
            fail("测试失败");
        }
    }
}
