package org.liyaojin.study.netty.api.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.liyaojin.study.netty.api.exception.ServerStartException;
import org.liyaojin.study.netty.api.exception.ServerStopException;

public abstract class AbstractServer implements IServer {
    protected final int port;

    public AbstractServer(int port) {
        this.port = port;
    }

    public boolean start() {
        boolean result = true;
        EventLoopGroup group_boss = new NioEventLoopGroup(), group_worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group_boss, group_worker).channel(NioServerSocketChannel.class)
                    .localAddress(port);
            ChannelInitializer<?> channelInitializer = initChannel();
            if (null != channelInitializer) {
                bootstrap.childHandler(initChannel());
                ChannelFuture future = bootstrap.bind().sync();
                future.channel().closeFuture().sync();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            result = false;
        } finally {
            group_boss.shutdownGracefully();
            group_worker.shutdownGracefully();
            try {
                doStop();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    protected abstract ChannelInitializer<? extends Channel> initChannel() throws ServerStartException;

    protected abstract void doStop() throws ServerStopException;
}
