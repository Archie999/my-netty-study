package serer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Archie on 2018/11/6.
 * ECHO 服务端
 */
public class EchoServer {

    private final int port;

    public  EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8081).start();

    }


    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);
                    }
                });

        ChannelFuture f = b.bind().sync();
        System.out.println(EchoServer.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
        f.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }


}
