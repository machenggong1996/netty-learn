package com.netty.learn.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * 1. 自定义handler
 *
 * @author machenggong
 * @since 2021/10/11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据 读取客户端发的消息
     *
     * @param ctx 含有管道 pipeline，通道，连接地址
     * @param msg 客户端发送的消息数据 对象形式
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx =" + ctx);
        // netty 提供
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
        // super.channelRead(ctx, msg);
    }

    /**
     * 数据读取完毕
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
        // super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常关闭处理器
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }
}