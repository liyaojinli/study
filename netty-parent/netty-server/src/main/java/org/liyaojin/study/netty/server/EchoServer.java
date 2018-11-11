package org.liyaojin.study.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import org.liyaojin.study.netty.api.exception.ServerStartException;
import org.liyaojin.study.netty.api.exception.ServerStopException;
import org.liyaojin.study.netty.api.server.AbstractServer;

public class EchoServer extends AbstractServer {

    private static class EchoChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("收到客户端信息："+((ByteBuf)msg).toString(CharsetUtil.UTF_8));
            ctx.write(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("server channelReadComplete..");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public EchoServer(int port) {
        super(port);
    }

    protected ChannelInitializer<? extends Channel> initChannel() throws ServerStartException {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new EchoChannelHandler());
            }
        };
    }

    protected void doStop() throws ServerStopException {
        //do nothing
    }

    public static void main(String[] args){
        EchoServer server = new EchoServer(9999);
        server.start();

    }
}
