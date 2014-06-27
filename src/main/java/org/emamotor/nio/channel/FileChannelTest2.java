package org.emamotor.nio.channel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tanabe
 */
public class FileChannelTest2 {

  private static final String SRC = "sample.txt";
  private static final String DST = "sample1.txt";

  public FileChannelTest2(String srcFilename, String destFilename) {
    System.out.println("どちらの方法でコピーしますか");
    System.out.println("1: コピー元をメモリにマップ 2: コピー先をメモリにマップ");
    System.out.print("> ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    try {
      String inputText = reader.readLine();

      if (inputText.equals("1")) {
        copyUsingMappedByteBuffer1(srcFilename, destFilename);
      } else if (inputText.equals("2")) {
        copyUsingMappedByteBuffer2(srcFilename, destFilename);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void copyUsingMappedByteBuffer1(String srcFilename,
                                          String destFilename) {
    System.out.println("コピー元ファイルをメモリにマップしてコピーを行います");

    try (FileInputStream in = new FileInputStream(srcFilename);
         FileChannel inputChannel = in.getChannel();

         FileOutputStream out = new FileOutputStream(destFilename);
         FileChannel outputChannel = out.getChannel()) {

      ByteBuffer buffer = inputChannel.map(
                            FileChannel.MapMode.READ_ONLY,
                            0,
                            (int) inputChannel.size());
      outputChannel.write(buffer);

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void copyUsingMappedByteBuffer2(String srcFilename,
                                          String destFilename) {
    System.out.println("コピー先ファイルをメモリにマップしてコピーを行います");

    try (FileInputStream in = new FileInputStream(srcFilename);
         FileChannel inputChannel = in.getChannel();

         RandomAccessFile out = new RandomAccessFile(destFilename, "rw");
         FileChannel outputChannel = out.getChannel()) {

      MappedByteBuffer buffer = outputChannel.map(
                                  FileChannel.MapMode.READ_WRITE,
                                  0,
                                  (int) inputChannel.size());
      inputChannel.read(buffer);
      buffer.force();

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new FileChannelTest2(SRC, DST);
  }

}
