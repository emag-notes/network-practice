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
public class ChannelTest3 {

  private static final String FILE_NAME = "ChannelTest3.txt";
  private String[] TEXTS = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                            "abcdefghijklmnopqrstuvwxyz",
                            "0123456789",
                            "あいうえお"};

  public ChannelTest3(String filename) {
    write(filename);
    read(filename);
  }

  public void write(String filename) {
    try (FileOutputStream stream = new FileOutputStream(filename);
         FileChannel channel = stream.getChannel()) {

      ByteBuffer[] buffers = new ByteBuffer[TEXTS.length];
      for (int i = 0; i < TEXTS.length; i++) {
        buffers[i] = ByteBuffer.wrap(TEXTS[i].getBytes());
      }

      channel.write(buffers);

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void read(String filename) {
    try (FileInputStream stream = new FileInputStream(filename);
         FileChannel channel = stream.getChannel()) {

      ByteBuffer[] buffers = new ByteBuffer[10];
      for (int i = 0; i < 10; i++) {
        buffers[i] = ByteBuffer.allocate(10);
      }

      channel.read(buffers);

      for (int i = 0; i < 10; i++) {
        buffers[i].clear();
        byte[] bytes = new byte[buffers[i].capacity()];
        buffers[i].get(bytes);
        System.out.println("buf[" + i + "]: " + new String(bytes));
      }

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new ChannelTest3(FILE_NAME);
  }

}
