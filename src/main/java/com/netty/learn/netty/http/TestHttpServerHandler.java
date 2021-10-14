package com.netty.learn.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * HttpObject 客户端和服务端相互通信的数据封装
 *
 * @author machenggong
 * @since 2021/10/14
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断msg是不是httpRequest请求
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("favicon.ico 不做响应");
                return;
            }
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址=" + ctx.channel().remoteAddress());

            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello 我是server", CharsetUtil.UTF_16);
            // 构造一个http的响应
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain:charset=utf-8");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(defaultFullHttpResponse);

        }

    }
}
