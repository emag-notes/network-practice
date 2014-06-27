package org.emamotor.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>usage</p>
 * <li>
 *   <ul>mvn clean compile exec:java -Dexec.mainClass=org.emamotor.nio.channel.ChannelTest5 -Dexec.args="-st src dst"</ul>
 *   <ul>mvn clean compile exec:java -Dexec.mainClass=org.emamotor.nio.channel.ChannelTest5 -Dexec.args="-ch src dst"</ul>
 * </li>
 * @author tanabe
 */
public class ChannelTest5 {

  public ChannelTest5(boolean flag, String srcFilename, String destFilename) {
    if (flag) {
      copyFileByChannel(srcFilename, destFilename);
    } else {
      copyFileByStream(srcFilename, destFilename);
    }
  }

  public void copyFileByStream(String srcFilename, String destFilename) {
    try (InputStream in = new FileInputStream(srcFilename);
         OutputStream out = new FileOutputStream(destFilename)) {

      long start = System.currentTimeMillis();

      byte[] buffer = new byte[10000000];
      int n;
      while ((n = in.read(buffer)) != -1) {
        out.write(buffer, 0, n);
      }

      out.flush();

      long end = System.currentTimeMillis();

      System.out.println("Total time = " + (end - start));

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void copyFileByChannel(String srcFilename, String destFilename) {
    try (FileChannel in = (new FileInputStream(srcFilename)).getChannel();
         FileChannel out = (new FileOutputStream(destFilename)).getChannel()) {

      long start = System.currentTimeMillis();

      ByteBuffer buffer = ByteBuffer.allocateDirect(10000000);
//      ByteBuffer buffer = ByteBuffer.allocate(10000000);

      while (true) {
        buffer.clear();
        if (in.read(buffer) < 0) {
          break;
        }
        buffer.flip();
        out.write(buffer);
      }

      long end = System.currentTimeMillis();

      System.out.println("Total time = " + (end - start));

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    if (args[0].equalsIgnoreCase("-ch")) {
      System.out.println("Copy by Channel");
      new ChannelTest5(true, args[1], args[2]);
    } else if (args[0].equalsIgnoreCase("-st")) {
      System.out.println("Copy by Stream");
      new ChannelTest5(false, args[1], args[2]);
    }
  }

}
