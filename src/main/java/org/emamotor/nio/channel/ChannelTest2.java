package org.emamotor.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tanabe
 */
public class ChannelTest2 {

  private static final String FILE_NAME = "ChannelTest2.txt";
  private static final String[] TEXTS = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                            "abcdefghijklmnopqrstuvwxyz",
                            "0123456789",
                            "あいうえお"};

  public ChannelTest2(String filename) {
    write(filename);
    read(filename);
  }

  public void write(String filename) {
    try (FileOutputStream stream = new FileOutputStream(filename);
         FileChannel channel = stream.getChannel()) {

      for (String text : TEXTS) {
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());
        channel.write(buffer);
      }

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void read(String filename) {
    try (FileInputStream stream = new FileInputStream(filename);
         FileChannel channel = stream.getChannel()) {

      int sizeOfReadingBytes = 0;

      while (sizeOfReadingBytes < channel.size()) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.clear();

        sizeOfReadingBytes += channel.read(buffer);

        buffer.clear();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        System.out.println("Buffer: " + new String(bytes));
      }

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new ChannelTest2(FILE_NAME);
  }

}
