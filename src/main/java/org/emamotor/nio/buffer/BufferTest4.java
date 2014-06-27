package org.emamotor.nio.buffer;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * @author tanabe
 */
public class BufferTest4 {

  public BufferTest4() {
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(0x10)");
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    byte b = (byte) 0x10;
    buffer.put(b);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(0x11)");
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    b = (byte) 0x11;
    buffer.put(b);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(byte[] src)");
    byte[] src = new byte[]{(byte) 0x20, (byte) 0x21, (byte) 0x22, (byte) 0x23, (byte) 0x24};
    System.out.println("src = " + bytesToString(src));
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    buffer.put(src);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(byte[] src, int offset, int length)");

    src = new byte[]{(byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34};
    System.out.println("src = " + bytesToString(src) + ", offset = 2, length = 2");
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    buffer.put(src, 2, 2);
    ByteBufferUtility.printByteBuffer(buffer);


    System.out.println("\nBuffer put(ByteBuffer src)");
    ByteBuffer srcBuffer = ByteBuffer.allocate(6);
    srcBuffer.put((byte) 0x40).put((byte) 0x41).put((byte) 0x42);
    srcBuffer.put((byte) 0x43).put((byte) 0x44).put((byte) 0x45);
    srcBuffer.position(1);
    srcBuffer.limit(4);

    System.out.println("src:");
    ByteBufferUtility.printByteBuffer(srcBuffer);
    System.out.println();

    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    buffer.put(srcBuffer);
    System.out.println("src:");
    ByteBufferUtility.printByteBuffer(srcBuffer);
    System.out.println();
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(int index, byte b)");
    b = (byte) 0x50;
    System.out.println("index = 2, b = 0x" + Integer.toHexString((int) b & 0xff));
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    buffer.put(2, b);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(0x50) position == limit の場合");

    buffer.limit(10);
    buffer.position(buffer.limit());
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    b = (byte) 0x60;
    try {
      buffer.put(b);
    } catch (BufferOverflowException ex) {
      ex.printStackTrace();
    }
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer put(byte[] src) src のサイズが残りのバイト数より大きかったら");
    buffer.position(5);
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    src = new byte[20];
    try {
      buffer.put(src);
      ByteBufferUtility.printByteBuffer(buffer);
    } catch (BufferOverflowException ex) {
      ex.printStackTrace();
    }
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
    new BufferTest4();
  }

}
