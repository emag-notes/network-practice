package org.emamotor.nio.practice;

import java.nio.ByteBuffer;

/**
 * @author tanabe
 */
public class BufferTest6 {
  public BufferTest6() {
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    // Compacting
    System.out.println("\nBuffer compact()");
    buffer.position(5);
    buffer.limit(12);
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();
    ByteBufferUtility.printByteBuffer(buffer.compact());

    // Duplicating
    System.out.println("\nBuffer duplicate()");
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    ByteBuffer dupBuffer = buffer.duplicate();
    ByteBufferUtility.printByteBuffer(dupBuffer);

    // コピーした Buffer を操作する
    System.out.println("\nコピーした Buffer を操作");
    dupBuffer.position(0);
    dupBuffer.put((byte) 0x10);

    System.out.println("Original Buffer");
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();

    System.out.println("Duplicated Buffer");
    ByteBufferUtility.printByteBuffer(dupBuffer);

    // Slicing
    System.out.println("\n\nBuffer slice()");
    buffer.limit(12);
    ByteBufferUtility.printByteBuffer(buffer);
    System.out.println();
    ByteBuffer sliecedBuffer = buffer.slice();
    ByteBufferUtility.printByteBuffer(sliecedBuffer);

    sliecedBuffer.put((byte) 0x99);
    ByteBufferUtility.printByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(sliecedBuffer);

    // Wrapping
    System.out.println("\nCreate ByteBuffer using wrap()");
    byte[] array = new byte[]{(byte) 0x20, (byte) 0x21, (byte) 0x22, (byte) 0x23, (byte) 0x24};
    System.out.println("array = " + bytesToString(array));
    System.out.println();
    ByteBufferUtility.printByteBuffer(ByteBuffer.wrap(array));
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
    new BufferTest6();
  }

}
