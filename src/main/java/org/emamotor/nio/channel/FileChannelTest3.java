package org.emamotor.nio.channel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * @author tanabe
 */
public class FileChannelTest3 {

  private static final String SRC = "sample.txt";
  private static final String DST = "sample1.txt";

  public FileChannelTest3(String srcFilename, String destFilename) {
    System.out.println("どちらの方法でコピーしますか");
    System.out.println("1: TransferTo 2: TransferFrom");
    System.out.print("> ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    try {
      String inputText = reader.readLine();

      if (inputText.equals("1")) {
        copyUsingTransferTo(srcFilename, destFilename);
      } else if (inputText.equals("2")) {
        copyUsingTransferFrom(srcFilename, destFilename);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void copyUsingTransferTo(String srcFilename, String destFilename) {
    System.out.println("TransferTo を使用してコピーを行います");

    try (FileChannel inputChannel = new FileInputStream(srcFilename).getChannel();
          FileChannel outputChannel = new FileOutputStream(destFilename).getChannel()) {

      inputChannel.transferTo(0, (int) inputChannel.size(), outputChannel);

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void copyUsingTransferFrom(String srcFilename, String destFilename) {
    System.out.println("TransferFrom を使用してコピーを行います");

    try (FileChannel inputChannel = new FileInputStream(srcFilename).getChannel();
          FileChannel outputChannel = new FileOutputStream(destFilename).getChannel()) {

      outputChannel.transferFrom(inputChannel, 0, (int) inputChannel.size());

    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new FileChannelTest3(SRC, DST);
  }

}
