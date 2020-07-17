package cn.eeye.gnss.service.websocket;

import java.net.InetSocketAddress;

import cn.eeye.gnss.service.websocket.config.AppConfig;
import cn.eeye.gnss.service.websocket.config.KkConfig;
import cn.eeye.gnss.service.websocket.service.WebServer;
import cn.eeye.gnss.service.websocket.tracksource.*;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebsocketApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

	@Autowired
	private WebServer webServer;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private KkConfig kkConfig;

	@Override
	public void run(String... args) throws Exception {
		ITrackCache trackCache = new TrackCacheImpl();
		IMsgProcessor msgProcessor = new MsgProcessor(trackCache);

		//轨迹消费服务
		new KafkaConsumerIsch(kkConfig, msgProcessor).startup();

		//websocket服务
		InetSocketAddress address = new InetSocketAddress("0.0.0.0", appConfig.getListenPort());
		ChannelFuture future = webServer.start(address, appConfig, trackCache);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				webServer.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
