package com.netty.learn.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author machenggong
 * @since 2021/10/21
 */
public class ByteBuf01 {

    public static void main(String[] args) {

        // 不需要反转 flip
        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(buf.readByte());
        }
    }

}
