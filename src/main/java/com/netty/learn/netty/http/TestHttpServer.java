package com.netty.learn.netty.http;

import com.netty.learn.netty.simple.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author machenggong
 * @since 2021/10/14
 */
public class TestHttpServer {

    public static void main(String[] args) throws InterruptedException {
        // 线程数默认cpu核数乘2
        // 处理连接请求 accept
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理业务 read/write
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    // 通道处理类
                    .channel(NioServerSocketChannel.class)
                    // 给workerGroup设置对应的处理器
                    .childHandler(new TestServerInitializer());

            System.out.println("服务器 ready");
            // 绑定一个端口 并且同步，生成ChannelFuture 对象
            ChannelFuture sync = bootstrap.bind(7000).sync();
            // 对关闭通道进行监听
            ChannelFuture close = sync.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
