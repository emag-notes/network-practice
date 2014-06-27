package org.emamotor.nio.channel;

import org.emamotor.nio.buffer.ByteBufferUtility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tanabe
 */
public class FileChannelTest1 {

  private static final String FILE_NAME = "sample.txt";

  public FileChannelTest1(String filename) {
    positionCheck1(filename);
    positionCheck2(filename);
  }

  public void positionCheck1(String filename) {
    try (RandomAccessFile raFile = new RandomAccessFile(filename, "rw");
         FileChannel channel = raFile.getChannel()) {

      System.out.println("\n\nFile Opened as Read Write mode.");

      System.out.println("\nSize: " + channel.size());
      System.out.println("Position: " + channel.position());

      ByteBuffer buffer = ByteBuffer.allocate(10);
      channel.read(buffer);

      System.out.println("Read 10 bytes");
      ByteBufferUtility.printByteBuffer(buffer);

      System.out.println("Position: " + channel.position());

      System.out.print("\nPosition changes from " + channel.position());

      channel.position(5);

      System.out.println(" to " + channel.position());

      System.out.println("\nRead 10 bytes");
      buffer.clear();
      channel.read(buffer);

      ByteBufferUtility.printByteBuffer(buffer);
      System.out.println("Position: " + channel.position());

      System.out.println("\nWrite 4 bytes");
      buffer = ByteBuffer.wrap(new byte[]{0x41, 0x42, 0x43, 0x44});
      channel.write(buffer);

      System.out.println("Position: " + channel.position());

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void positionCheck2(String filename) {
    try (FileOutputStream stream = new FileOutputStream(filename, true);
         FileChannel channel = stream.getChannel()) {

      System.out.println("\n\nFile Opened as Append mode.");

      System.out.println("\nSize: " + channel.size());
      System.out.println("Position: " + channel.position());  // <- JDK1.4 の時は 0 だったが、JDK7ではこの時点で 200 になる

      ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x41, 0x42, 0x43, 0x44});
      channel.write(buffer);

      System.out.println("\nWrite 4bytes");

      System.out.println("\nSize: " + channel.size());
      System.out.println("Position: " + channel.position());

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new FileChannelTest1(FILE_NAME);
  }

}
