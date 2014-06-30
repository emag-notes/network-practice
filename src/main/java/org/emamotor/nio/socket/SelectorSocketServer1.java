package org.emamotor.nio.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

/**
 * @author tanabe
 */
public class SelectorSocketServer1 {

  private Selector selector;
  private static final int PORT = 9000;

  private ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

  private static Charset charset = Charset.forName("UTF-16");
  private static CharsetDecoder decoder = charset.newDecoder();

  public SelectorSocketServer1() {
    try {
      selector = SelectorProvider.provider().openSelector();
//      selector = Selector.open(); // どちらでもOK

      ServerSocketChannel serverSocketChannel
        = SelectorProvider.provider().openServerSocketChannel();
//      serverSocketChannel = ServerSocketChannel.open();

      // Non-Blocking モードにする
      // Selector と一緒に使うときには、Blocking モードではダメ
      serverSocketChannel.configureBlocking(false);

      InetSocketAddress address
        = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
      serverSocketChannel.socket().bind(address);

      // Selector への登録
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

      System.out.println(getClass().getSimpleName() + " start.");
    } catch (IOException ex) {
      System.exit(1);
    }
  }

  public void start() {
    try {
      while (selector.select() > 0) {
        // セレクトされた SelectionKey オブジェクトをまとめて取得する
        Iterator keyIterator = selector.selectedKeys().iterator();

        while (keyIterator.hasNext()) {
          SelectionKey key = (SelectionKey) keyIterator.next();
          keyIterator.remove();

          // セレクトされた SelectionKey の状態に応じて処理を決める
          if (key.isAcceptable()) {
            // accept の場合

            ServerSocketChannel serverSocketChannel
              = (ServerSocketChannel) key.channel();
            accept(serverSocketChannel);

          } else if (key.isReadable()) {
            // データが送られてきたとき

            SocketChannel socketChannel = (SocketChannel) key.channel();
            sendBack(socketChannel);
          }
        }
      }
    } catch (IOException ex) {
      System.out.println(ex.getClass());
      ex.printStackTrace();
    }
  }

  private void accept(ServerSocketChannel serverSocketChannel) throws IOException {
    SocketChannel socketChannel = serverSocketChannel.accept();

    // Non-Blocking モードに変更
    socketChannel.configureBlocking(false);

    // Selector への登録
    socketChannel.register(selector, SelectionKey.OP_READ);
    System.out.println(socketChannel.socket().getRemoteSocketAddress() + " connect.");
  }

  private void sendBack(SocketChannel socketChannel) throws IOException {

    buffer.clear();

    // データの読み込み
    if (socketChannel.read(buffer) < 0) {
      socketChannel.close();
      return;
    }

    // 読み込んだデータをそのまま送り返す
    buffer.flip();
    System.out.println(socketChannel.socket().getRemoteSocketAddress()
      + " : " + decoder.decode(buffer.duplicate()));

    socketChannel.write(buffer);
  }

  public static void main(String[] args) {
    new SelectorSocketServer1().start();
  }

}
