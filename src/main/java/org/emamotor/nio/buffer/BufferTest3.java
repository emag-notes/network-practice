package org.emamotor.nio.buffer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author tanabe
 */
public class BufferTest3 {
  public BufferTest3() {
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get()");

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    byte b = buffer.get();
    System.out.println("Return value = 0x" + Integer.toHexString((int) b & 0xff));
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get()");

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    b = buffer.get();
    System.out.println("Return value = 0x" + Integer.toHexString((int) b & 0xff));
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get(byte[] dst)");

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    byte[] dst = new byte[5];
    buffer.get(dst);
    System.out.println("dst is " + bytesToString(dst));
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get(byte[] dst, int offset, int length)");

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    dst = new byte[5];
    buffer.get(dst, 1, 3);
    System.out.println("dst is " + bytesToString(dst) + ", offset = 1, length = 3");
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get(int index)");

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    b = buffer.get(2);
    System.out.println("index = 2, return value = 0x" + Integer.toHexString((int) b & 0xff));
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get() position = limit の場合");
    buffer.limit(10);
    buffer.position(buffer.limit());
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    try {
      b = buffer.get();
      System.out.println("Return value = 0x" + Integer.toHexString((int) b & 0xff));
    } catch (BufferUnderflowException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer get(byte[] dst) dst のサイズが残りのバイト数より大きかったら");
    buffer.limit(buffer.capacity());
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    dst = new byte[20];
    try {
      buffer.get(dst);
      System.out.println("Values are " + bytesToString(dst));
    } catch (BufferUnderflowException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);
  }

  private String bytesToString(byte[] bytes) {
    StringBuffer strBuffer = new StringBuffer("[");
    int i;
    for (i = 0; i < bytes.length - 1; i++) {
      strBuffer.append(Integer.toHexString((int) bytes[i] & 0xff));
      strBuffer.append(", ");
    }
    strBuffer.append(Integer.toHexString((int) bytes[i] & 0xff));
    strBuffer.append("]");

    return strBuffer.toString();
  }

  public static void main(String[] args) {
    new BufferTest3();
  }

}
