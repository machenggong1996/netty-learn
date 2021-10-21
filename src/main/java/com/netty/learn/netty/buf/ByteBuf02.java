package com.netty.learn.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author machenggong
 * @since 2021/10/21
 */
public class ByteBuf02 {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello 北京", StandardCharsets.UTF_8);

        // 使用相关的方法
        if(buf.hasArray()){
            byte[] content = buf.array();
            System.out.println(new String(content,StandardCharsets.UTF_8));
            // 真实类型 UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 12, cap: 24)
            System.out.println(buf);
            System.out.println(buf.arrayOffset());
            System.out.println(buf.getCharSequence(0,9,StandardCharsets.UTF_8));
        }
    }

}
