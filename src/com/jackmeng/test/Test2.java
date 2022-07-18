package com.jackmeng.test;

import javax.swing.*;
import java.util.*;

import java.awt.*;

public class Test2 {
  private JFrame f;
  private JPanel panel;
  private JLabel label;

  public Test2() {
    f = new JFrame("test");
    f.setPreferredSize(new Dimension(800, 800));

    panel = new JPanel();
    panel.setPreferredSize(f.getPreferredSize());

    label = new JLabel();

    f.getContentPane().add(panel);
  }
}
