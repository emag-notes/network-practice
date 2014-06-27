package org.emamotor.nio.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author tanabe
 */
public class SocketTestServer1 {

  private ServerSocketChannel serverSocketChannel;
  private static final int PORT = 9000;

  private static Charset charset = Charset.forName("UTF-16");
  private static CharsetDecoder decoder = charset.newDecoder();

  public SocketTestServer1() {
    try {
      serverSocketChannel = ServerSocketChannel.open();

      InetSocketAddress address
        = new InetSocketAddress(InetAddress.getLocalHost(), PORT);

      serverSocketChannel.socket().bind(address);
    } catch (IOException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }

  public void start() {
    while (true) {
      try {
        SocketChannel socketChannel = serverSocketChannel.accept();

        System.out.println(socketChannel.socket().getInetAddress() + " connect.");

        new MessageRepeater(socketChannel).start();
      } catch (IOException ex) {
        ex.printStackTrace();
        return;
      }
    }
  }

  class MessageRepeater implements Runnable {
    private ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
    private SocketChannel socketChannel;

    public MessageRepeater(SocketChannel socketChannel) {
      this.socketChannel = socketChannel;
    }

    public void start() {
      Thread thread = new Thread(this);
      thread.start();
    }

    public void run() {
      try {
        sendBack();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    private void sendBack() throws IOException {
      try {
        while (socketChannel.isConnected()) {
          buffer.clear();

          if (socketChannel.read(buffer) < 0) {
            break;
          }

          buffer.flip();
          System.out.println(socketChannel.socket().getInetAddress()
            + " : " + decoder.decode(buffer.duplicate()));

          socketChannel.write(buffer);
        }
      } finally {
        System.out.println(socketChannel.socket().getInetAddress() + " closed.");
        socketChannel.close();
      }
    }
  }

  public static void main(String[] args) {
    new SocketTestServer1().start();
  }

}
