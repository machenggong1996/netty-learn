package com.netty.learn.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author machenggong
 * @since 2021/10/11
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
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
                    // 设置线程队列得到的连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 给workerGroup设置对应的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            // 绑定一个端口 并且同步，生成ChannelFuture 对象
            ChannelFuture sync = bootstrap.bind(6668).sync();
            // 对关闭通道进行监听
            ChannelFuture close = sync.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
