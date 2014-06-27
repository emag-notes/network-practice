package org.emamotor.nio.channel;

import org.emamotor.nio.buffer.ByteBufferUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author tanabe
 */
public class FileChannelTest4 {

  private static final String FILE_NAME = "sample.txt";

  public FileChannelTest4(String filename) {
    access(filename);
  }

  private void access(String filename) {

    try (FileChannel channel = new RandomAccessFile(filename, "rw").getChannel()) {

      // Acquire Lock
      System.out.println("Acquire Lock.");
      FileLock lock = channel.lock();
      System.out.println("Lock: " + lock);

      ByteBuffer buffer = ByteBuffer.allocate(10);
      channel.read(buffer);

      System.out.println("Read 10 bytes");
      ByteBufferUtility.printByteBuffer(buffer);

      javax.swing.JOptionPane.showMessageDialog(null, "Release Lock?");
      lock.release();

      javax.swing.JOptionPane.showMessageDialog(null, "Close File?");

      System.exit(0);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new FileChannelTest4(FILE_NAME);
  }

}
