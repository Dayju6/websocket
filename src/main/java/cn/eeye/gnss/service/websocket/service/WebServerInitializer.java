package cn.eeye.gnss.service.websocket.service;

import cn.eeye.gnss.service.websocket.config.AppConfig;
import cn.eeye.gnss.service.websocket.handler.WebSocketServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description：websocket 初始化
 * @author: amos-lsl
 * @date: 2018/6/5
 */

public class WebServerInitializer extends ChannelInitializer<Channel>{
	private final ChannelGroup group;
    private final AppConfig appConfig;

	public WebServerInitializer(ChannelGroup group, AppConfig appConfig) {
		this.group = group;
		this.appConfig = appConfig;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//处理日志
		//pipeline.addLast(new LoggingHandler(LogLevel.INFO));
		
		//处理心跳
//		pipeline.addLast(new IdleStateHandler(0, 0, 1800, TimeUnit.SECONDS));
//		pipeline.addLast(new HeartbeatHandler());
//
//		pipeline.addLast(new HttpServerCodec());
//		pipeline.addLast(new ChunkedWriteHandler());
//		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
//		pipeline.addLast(new HttpRequestHandler("/ws"));
//		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
//		pipeline.addLast(new TextWebSocketFrameHandler(group));
        //将请求和应答解码或编码为http消息
		pipeline.addLast("http-codec", new HttpServerCodec());
		//将http消息的多个部分组合成一条完整http消息
		pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
		//向客户端发送html5文件，主要用于支持浏览器和服务器进行websocket通信（处理大数据流）
		pipeline.addLast("http-chunked", new ChunkedWriteHandler());
		//业务处理
		pipeline.addLast("handler", new WebSocketServerHandler(appConfig));
	}
}
