package com.jackmeng.cloudspin.lib.blurhash;

import com.jackmeng.cloudspin.helpers.math;

public final class base_83 {
  private base_83() {
  }

  public static final char[] TABLE = {
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9',
      'A',
      'B',
      'C',
      'D',
      'E',
      'F',
      'G',
      'H',
      'I',
      'J',
      'K',
      'L',
      'M',
      'N',
      'O',
      'P',
      'Q',
      'R',
      'S',
      'T',
      'U',
      'V',
      'W',
      'X',
      'Y',
      'Z',
      'a',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'v',
      'w',
      'x',
      'y',
      'z',
      '#',
      '$',
      '%',
      '*',
      '+',
      ',',
      '-',
      '.',
      ':',
      ';',
      '=',
      '?',
      '@',
      '[',
      ']',
      '^',
      '_',
      '{',
      '|',
      '}',
      '~',
  };

  public static String encode(int length, long val, char[] buff, int offset) {
    int _i = 1;
    for (int i = 1; i <= length; i++) {
      int curr = (int) val / _i % 83;
      buff[offset + length - i] = TABLE[curr];
      _i *= 83;
    }
    return new String(buff);
  }

  public static long encode(double[] val) {
    return (long) ((((long) BlurHashChild._as_linear(val[0])) << 16) + (((long) BlurHashChild._as_linear(val[1])) << 8)
        + BlurHashChild._as_linear(val[2]));
  }

  public static long encode(double[] val, double m) {
    return Math
        .round((Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[0] / m, 0.5) * 9 + 9.5))))) * 19 * 19
            + (Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[1] / m, 0.5) * 9 + 9.5))))) * 19
            + (Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[2] / m, 0.5) * 9 + 9.5))))));
  }
}
