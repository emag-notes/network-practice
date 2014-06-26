package org.emamotor.nio.practice;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * http://www.javainthebox.net/laboratory/JDK1.4/NewIO/Buffer/Buffer.html
 * @author tanabe
 */
public class ByteBufferUtility {
  // Buffer に乱数をセット
  public static void initByteBuffer(ByteBuffer buffer) {
    // buffer の capacity と同サイズの byte 配列を生成
    int capacity = buffer.capacity();
    byte[] bytes = new byte[capacity];

    // 乱数を生成
    Random random = new Random(0);
    random.nextBytes(bytes);

    // 生成した乱数をセット
    buffer.position(0);
    buffer.put(bytes);

    // position を 0 に戻す
    buffer.position(0);
  }

  // ByteBuffer の表示
  public static void printByteBuffer(ByteBuffer buffer) {
    // Buffer の複製を作成
    ByteBuffer dupBuffer = buffer.duplicate();

    // position をリセット
    dupBuffer.position(0);

    // limit を capacity と同じにする
    dupBuffer.limit(dupBuffer.capacity());

    // position, limit, capacity の表示

    // 1 要素づつ取り出して表示
    for (int i = 0; i <= dupBuffer.capacity(); i++) {
      if (i == buffer.position()) {
        System.out.print("P");
        if (i == buffer.limit()) {
          System.out.print("L");
          if (i == buffer.capacity()) {
            System.out.print("C");
          } else {
            System.out.print(" ");
          }
        } else {
          System.out.print(" ");
        }

        System.out.print("  ");
      } else if (i == buffer.limit()) {
        System.out.print("L");
        if (i == buffer.capacity()) {
          System.out.print("C");
        } else {
          System.out.print(" ");
        }
        System.out.print("  ");
      } else if (i == buffer.capacity()) {
        System.out.print("C");
      } else {
        System.out.print("    ");
      }
    }
    System.out.println();
    System.out.print(" [");
    for (int i = 0; i < dupBuffer.capacity() - 1; i++) {
      int x = (int) dupBuffer.get();
      if (x >= 0 && x < 0x10) {
        System.out.print("0");
      }
      System.out.print(Integer.toHexString(x & 0xff) + ", ");
    }

    int x = (int) dupBuffer.get();
    if (x >= 0 && x < 0x10) {
      System.out.println("0");
    }
    System.out.println(Integer.toHexString(x & 0xff) + "]");
  }

}
