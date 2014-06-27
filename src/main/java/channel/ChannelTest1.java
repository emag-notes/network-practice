package channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tanabe
 */
public class ChannelTest1 {

  private static final String FILE_NAME = "ChannelTest1.txt";
  private static final String TEXT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789あいうえお";

  public ChannelTest1(String filename) {
    write(filename);
    read(filename);
  }

  public void write(String filename) {
    try {
      FileOutputStream stream = new FileOutputStream(filename);
      FileChannel channel = stream.getChannel();

      byte[] bytes = TEXT.getBytes();

      ByteBuffer buffer = ByteBuffer.wrap(bytes);

      channel.write(buffer);

      channel.close();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void read(String filename) {
    try {
      FileInputStream stream = new FileInputStream(filename);
      FileChannel channel = stream.getChannel();

      ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
      channel.read(buffer);

      buffer.clear();
      byte[] bytes = new byte[buffer.capacity()];
      buffer.get(bytes);
      System.out.println("Buffer: " + new String(bytes));

      channel.close();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new ChannelTest1(FILE_NAME);
  }

}
