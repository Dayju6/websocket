package cn.eeye.gnss.service.websocket.handler;

import cn.eeye.gnss.service.websocket.config.AppConfig;
import cn.eeye.gnss.service.websocket.model.IClientsManager;
import cn.eeye.gnss.service.websocket.model.IWebSocketClient;
import cn.eeye.gnss.service.websocket.model.WebSocketClientInfo;
import cn.eeye.gnss.service.websocket.model.WebSocketClientsManager;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.Attribute;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description：websocket 处理句柄
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);
    private WebSocketServerHandshaker handshaker;
    private AppConfig appConfig;
    private IClientsManager clientsManager;

    public WebSocketServerHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        clientsManager = WebSocketClientsManager.getInstance();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientAddr = insocket.getAddress().getHostAddress()+ ":" +  insocket.getPort();
        //绑定连接信息
        Attribute<Session> attr = ctx.channel().attr(ChannelAttrs.SESSION);
        attr.set(new Session(clientAddr));
        LOGGER.info("client({}) connected", clientAddr);

        //TODO 加入客户端管理
        IWebSocketClient clientInfo = new WebSocketClientInfo(ctx.channel());
        clientsManager.addClientInfo(clientAddr, clientInfo);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = ctx.channel().attr(ChannelAttrs.SESSION).get();
        if (session != null) {
            LOGGER.info("client({}) disconnect, keep connect times:{}(s)",
                        session.getAddr(), (System.currentTimeMillis()-session.getConnectTime())/1000);

            //TODO 资源清理
            clientsManager.removeClientInfo(session.getAddr());
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Session session = ctx.channel().attr(ChannelAttrs.SESSION).get();
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg, session);
        }

        //update 客户端最后请求时间
        if (session != null) {
            session.setLatestRequest(System.currentTimeMillis());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果HTTP解码失败，返回HHTP异常
        if (!req.decoderResult().isSuccess() ||
                (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        //获取url参数,鉴权
        HttpMethod method=req.getMethod();
        String uri=req.getUri();
        // TODO: 2018/6/6 通过parameters鉴权,鉴权失败直接断开连接
        boolean isValidConnect = true;
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        //doAuthentication();
        if (!isValidConnect) {
            ctx.close();
        }
        //分发路由，确定客户端连接类型
       /* if(method==HttpMethod.GET&&"/web".equals(uri)){
        }else if(method==HttpMethod.GET&&"/mob".equals(uri)){
        }*/

        // 构造握手响应返回
        String host = req.headers().get(HttpHeaderNames.HOST);
        Joiner joiner = Joiner.on("").skipNulls();
        String webSocketUrl = joiner.join("ws://", host,"/websocket")
                                    .toString();

        //WebSocketServerHandshakerFactory第一个参数不为空任意字符串
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "1", null, false);
        handshaker = wsFactory.newHandshaker(req);

        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame, Session session) {

        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write( new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            ctx.channel().write(
                    new TextWebSocketFrame(String.format(
                            "%s frame types not supported", frame.getClass().getName())));
            return;
        }

        //文本消息处理
        String request = ((TextWebSocketFrame) frame).text();
        if (session != null) {
            LOGGER.debug("receive msg from client({}), msg:{}", session.getAddr(), request);
            //todo test
            Iterable<String> stringIt = Splitter.on(',')
                                        .trimResults()
                                        .omitEmptyStrings()
                                        .split(request);
            List<String> vehs = new LinkedList<>();
            Iterator<String> sit = stringIt.iterator();
            while (sit.hasNext()) {
                vehs.add( sit.next() );
            }
            clientsManager.getClientInfo(session.getAddr()).subscribeVehsTrack(vehs);
        }

        // 返回应答消息
        ctx.channel().write(
                new TextWebSocketFrame(request
                        + " , 欢迎使用Netty WebSocket服务，现在时刻："
                        + new java.util.Date().toString()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
