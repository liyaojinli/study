package org.liyaojin.study.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoClient {
    private final int remotePort;
    private final String remoteIp;
    private Channel channel;

    public EchoClient(String remoteIp, int remotePort) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            channel = bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("connected");
//                                    ctx.writeAndFlush(Unpooled.copiedBuffer("hi server", CharsetUtil.UTF_8));
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf buf = (ByteBuf) msg;
                                    System.out.println("服务器返回信息为:" + buf.toString(CharsetUtil.UTF_8));
                                }
                            });
                        }
                    }).connect(remoteIp, remotePort).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void echo(String message) {
        try {
            ByteBuf buf = Unpooled.copiedBuffer(message.getBytes());
            channel.writeAndFlush(buf);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        final EchoClient client = new EchoClient("127.0.0.1", 9999);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                client.connect();
            }
        });
        thread.start();
//        thread.join();
        Thread.sleep(10000);


        client.echo("hi server");

    }
}
