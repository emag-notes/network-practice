package org.emamotor.nio.practice;

import java.nio.ByteBuffer;

/**
 * @author tanabe
 */
public class BufferTest7 {

  public BufferTest7() {
    ByteBuffer normalBuffer = ByteBuffer.allocate(100000000);
    ByteBuffer directBuffer = ByteBuffer.allocateDirect(100000000);

    ByteBufferUtility.initByteBuffer(normalBuffer);
    System.out.println("Normal Buffer: " + normalBuffer);

    ByteBufferUtility.initByteBuffer(directBuffer);
    System.out.println("Direct Buffer: " + directBuffer);

    System.out.println("\n普通の Buffer オブジェクト");
    measure(normalBuffer);

    System.out.println("\nDirect Buffer オブジェクト");
    measure(directBuffer);
  }

  private void measure(ByteBuffer buffer) {

    long start = System.currentTimeMillis();
    while (buffer.hasRemaining()) {
      buffer.get();
    }
    long end = System.currentTimeMillis();

    System.out.println("Total time = " + (end - start));
  }

  public static void main(String[] args) {
    new BufferTest7();
  }

}
