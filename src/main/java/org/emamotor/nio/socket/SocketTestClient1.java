package org.emamotor.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * mvn compile exec:java -Dexec.mainClass=org.emamotor.nio.socket.SocketTestClient1 -Dexec.args="[server-address]"
 * @author tanabe
 */
public class SocketTestClient1 {

  private InetSocketAddress address;
  private SocketChannel socketChannel;
  private static final int PORT = 9000;

  private static Charset charset = Charset.forName("UTF-16");
  private static CharsetEncoder encoder = charset.newEncoder();
  private static CharsetDecoder decoder = charset.newDecoder();

  private static ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

  public SocketTestClient1(String host) throws IOException {
    InetSocketAddress address
      = new InetSocketAddress(InetAddress.getByName(host), PORT);

    socketChannel = SocketChannel.open();
    System.out.println("Channel " + socketChannel.isBlocking());
    socketChannel.connect(address);
  }

  public void communicate() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    try {
      while (socketChannel.isConnected()) {
        System.out.print(">");
        String inputText = reader.readLine();
        if (inputText.equalsIgnoreCase("q")) {
          break;
        }

        socketChannel.write(encoder.encode(CharBuffer.wrap(inputText)));

        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        System.out.println(socketChannel.socket().getInetAddress()
          + " : " + decoder.decode(buffer));
      }
    } finally {
      if (socketChannel != null) {
        System.out.println(socketChannel.socket().getInetAddress()
          + " cloesed.");
        socketChannel.close();
      }
    }
  }

  public static void main(String[] args) {
    try {
      new SocketTestClient1(args[0]).communicate();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}
