package org.emamotor.nio.buffer;

import java.nio.ByteBuffer;

/**
 * http://www.javainthebox.net/laboratory/JDK1.4/NewIO/Buffer/Buffer.html
 * @author tanabe
 */
public class BufferTest1 {

  public BufferTest1() {
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    // position の移動
    System.out.println("\nposition を 12 へ移動");
    buffer.position(12);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nposition を 5 へ移動");
    buffer.position(5);
    ByteBufferUtility.printByteBuffer(buffer);

    // limit の移動
    System.out.println("\nlimit を 10 へ移動");
    buffer.limit(10);
    ByteBufferUtility.printByteBuffer(buffer);

    // position を limit より大きくすると
    System.out.println("\nposition を 12 へ移動 (position > limit)");
    try {
      buffer.position(12);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);

    // limit を position より小さくすると
    System.out.println("\nlimit を 1 へ移動 (limit < position)");
    try {
      buffer.limit(1); // 例外は発生しない。position は limit と同じ値にされる
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);

    // position を capactity より大きくすると
    System.out.println("\nposition を 20 へ移動 (position > capacity)");
    try {
      buffer.position(20);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);

    // limit を capactity より大きくすると
    System.out.println("\nlimit を 20 へ移動 (limit > capactity)");
    try {
      buffer.limit(20);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);
  }

  public static void main(String[] args) {
    new BufferTest1();
  }

}
