package com.netty.learn.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author machenggong
 * @since 2021/10/12
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        // 客户端使用BootStrap
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 设置相关参数
            // 设置线程组
            bootstrap.group(group)
                    // 设置客户端通道的实现内容 反射
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 通道加入自己的处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端ok");

            // 启动客户端去连接服务器端
            // 关于 ChannelFuture 涉及netty异步模型
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }


    }

}
