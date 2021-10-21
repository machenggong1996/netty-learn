package com.netty.learn.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author machenggong
 * @since 2021/10/14
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器 Channel中包含pipeline
        // 得到管道 pipeline是一个双向链表 类型是handler
        ChannelPipeline pipeline = ch.pipeline();
        // netty提供的处理http的编解码器 属于handler类型 链表一部分
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
