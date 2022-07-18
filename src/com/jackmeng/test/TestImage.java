package com.jackmeng.test;

import java.awt.image.*;

import javax.imageio.ImageIO;

import com.jackmeng.cloudspin.lib.blurhash.BlurHash;
import com.jackmeng.cloudspin.lib.blurhash.BlurHashChild;

import java.awt.*;

import java.io.*;
import java.util.*;

public class TestImage {
  public static void main(String ... args) throws Exception {
    BufferedImage img = ImageIO.read(new File("/home/jackm/Code/cloudspin/src/com/jackmeng/test/test.png"));
    File f = new File("/home/jackm/Code/cloudspin/src/com/jackmeng/test/test_blurred.png");
    if(f.exists()) {
      f.delete();
    }
    new File("/home/jackm/Code/cloudspin/src/com/jackmeng/test/test_blurred.png").createNewFile();
    long curr = System.currentTimeMillis();

    ImageIO.write(new BlurHash().blur(img, 4, 4, new Double(2.0d)), "png", f);
    System.out.println("Took me (ms): " + (System.currentTimeMillis() - curr));
  }
}
