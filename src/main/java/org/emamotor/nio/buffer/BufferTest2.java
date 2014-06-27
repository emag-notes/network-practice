package org.emamotor.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author tanabe
 */
public class BufferTest2 {
  public BufferTest2() {
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    // Clear
    System.out.println("\nBuffer Clear");
    buffer.position(7);
    buffer.limit(10);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println();

    buffer.clear();
    ByteBufferUtility.printByteBuffer(buffer);

    // Rewind
    System.out.println("\nBuffer Rewind");
    buffer.position(7);
    buffer.limit(10);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println();

    buffer.rewind();
    ByteBufferUtility.printByteBuffer(buffer);

    // Flip
    System.out.println("\nBuffer Flip");
    buffer.position(7);
    buffer.limit(10);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println();

    buffer.flip();
    ByteBufferUtility.printByteBuffer(buffer);

    // Mark and Reset
    System.out.println("\nBuffer Mark & Reset");
    buffer.position(2);
    buffer.mark();         // position ‚ÌˆÊ’u‚É mark

    buffer.position(7);
    buffer.limit(10);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println();

    buffer.reset();
    ByteBufferUtility.printByteBuffer(buffer);
  }

  public static void main(String[] args){
    new BufferTest2();
  }

}
