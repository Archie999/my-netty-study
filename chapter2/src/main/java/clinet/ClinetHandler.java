package clinet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by Archie on 2018/11/6.
 * 客户端
 */
public class ClinetHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 从服务器收到一条消息被调用
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("CLINET RECEIVED"+byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 再到服务器的连接已经建立之后被调用,可以理解为连接创建时触发，
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("netty rocks",CharsetUtil.UTF_8));
    }

    /**
     * 出现异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

       cause.printStackTrace();
        ctx.close();
    }
}
