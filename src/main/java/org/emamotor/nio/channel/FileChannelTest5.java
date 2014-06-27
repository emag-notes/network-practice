package org.emamotor.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author tanabe
 */
public class FileChannelTest5 {

  private static final String[] ARGS = {"sample.txt", "0", "200", "shared"};

  public FileChannelTest5(String filename, int position, int size, boolean shareFlag) {
    access(filename, position, size, shareFlag);
  }

  private void access(String filename, int position, int size, boolean shareFlag) {

    try (FileChannel channel = new RandomAccessFile(filename, "rw").getChannel()) {

      // Acquire Exclusive Lock
      System.out.println("Acquire Lock.");
      FileLock lock = channel.lock(position, size, shareFlag);
      System.out.println("Lock: " + lock);

      ByteBuffer buffer = ByteBuffer.allocate(size);

      channel.position(position);
      channel.read(buffer);

      System.out.println("Read " + size + " bytes from position: " + position);

      javax.swing.JOptionPane.showMessageDialog(null, "Release Lock?");
      lock.release();

      System.exit(0);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new FileChannelTest5(ARGS[0],
      Integer.parseInt(ARGS[1]),
      Integer.parseInt(ARGS[2]),
      ARGS[3].equalsIgnoreCase("shared"));
  }

}
