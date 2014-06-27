package org.emamotor.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * @author tanabe
 */
public class BufferTest5 {
  public BufferTest5(){
    ByteBuffer buffer = ByteBuffer.allocate(15);

    ByteBufferUtility.initByteBuffer(buffer);
    ByteBufferUtility.printByteBuffer(buffer);

    // int の読み込み
    System.out.println("\nBuffer getInt()");
    int x = buffer.getInt();
    System.out.println("return value = " + x + " : 0x" + Integer.toHexString(x));
    ByteBufferUtility.printByteBuffer(buffer);

    // int の書き出し
    x = 10;
    System.out.println("\nBuffer putInt(int value) value = " + x + " : 0x" + Integer.toHexString(x));
    buffer.putInt(x);
    ByteBufferUtility.printByteBuffer(buffer);

    // IntBuffer への変換
    System.out.println("\nIntBuffer へ変換");

    IntBuffer intBuffer = buffer.asIntBuffer();

    System.out.println(intBuffer);
    System.out.println("Last index value = 0x" + Integer.toHexString(intBuffer.get(intBuffer.limit() - 1)));

    // Endian の変換
    System.out.print("\nEndian の変換: " + buffer.order());

    buffer.order(ByteOrder.LITTLE_ENDIAN);
    System.out.println(" -> " + buffer.order());
    buffer.position(0);
    ByteBufferUtility.printByteBuffer(buffer);

    System.out.println("\nBuffer getInt()");
    x = buffer.getInt();
    System.out.println("return value = " + x + " : 0x" + Integer.toHexString(x));
    ByteBufferUtility.printByteBuffer(buffer);
  }

  private String bytesToString(byte[] bytes){
    StringBuffer strBuffer = new StringBuffer("[");
    int i;
    for(i = 0 ; i < bytes.length - 1 ; i++){
      strBuffer.append(Integer.toHexString((int)bytes[i] & 0xff));
      strBuffer.append(", ");
    }
    strBuffer.append(Integer.toHexString((int)bytes[i] & 0xff));
    strBuffer.append("]");

    return strBuffer.toString();
  }

  public static void main(String[] args){
    new BufferTest5();
  }
}
