/*
*  Copyright: (C) 2022 name of Jack Meng
* Halcyon MP4J is music-playing software.
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
* You should have received a copy of the GNU General Public License
* along with this program; If not, see <http://www.gnu.org/licenses/>.
*/

package com.jackmeng.cloudspin.lib.blurhash;

import java.util.Arrays;

import com.jackmeng.cloudspin.helpers.math;

/**
 * A low level implementation of the BlurHash
 * algorithm from here: https://blurha.sh/
 *
 * Original ported from an early version of this
 * program.
 *
 * @author Jack Meng
 * @since 1.5
 */
public final class BlurHashChild {
    static double[] __ll = new double[256];
    static {
        for (int i = 0; i < __ll.length; i++) {
            double _m = i / 255.0d;
            __ll[i] = _m <= 0.04045 ? (_m / 12.92) : (Math.pow((_m + 0.055) / 1.055, 2.4));
        }
    }

    public static double _is_linear(int val) {
        return val < 0 ? __ll[0] : (val >= 256 ? __ll[255] : __ll[val]);
    }

    public static double _as_linear(double val) {
        int _l = Arrays.binarySearch(__ll, val);
        if (_l < 0) {
            _l = ~_l;
        }
        return _l < 0 ? 0 : (_l >= 256 ? 255 : _l);
    }

    private BlurHashChild() {
    }

    public static String enc(int[] pixels, int _width, int _height, int _x, int _y) {
        double[][] _en_masse = new double[_x * _y][3];
        for (int i = 0; i < _y; i++) {
            for (int j = 0; j < _x; j++) {
                double normale = (i == 0 && j == 0) ? 1 : 2, _r = 0.0d, _g = 0.0d, _b = 0.0d;
                for (int x = 0; x < _width; x++) {
                    for (int y = 0; y < _height; y++) {
                        double _m = normale * Math.cos((Math.PI * j * x) / _width)
                                * Math.cos((Math.PI * i * y) / _height);
                        int _pix = pixels[y * _width + x];
                        _r += _m * _is_linear((_pix >> 16) & 0xFF);
                        _g += _m * _is_linear((_pix >> 8) & 0xFF);
                        _b += _m * _is_linear(_pix & 0xFF);
                    }
                }
                double _n = 1.0 / (_width * _height);
                int _i = i * _x + j;
                _en_masse[_i][0] = _r * _n;
                _en_masse[_i][1] = _g * _n;
                _en_masse[_i][2] = _b * _n;
            }
        }
        int _l = _en_masse.length;
        char[] hash = new char[4 + 2 * _l];
        long flag_size = ((long) _x) + _y * 9 - 10;
        base_83.encode(1, flag_size, hash, 0);
        double max;
        if (_l > 1) {
            double aM = math._max(_en_masse);
            double qM = Math.floor(Math.max(0, Math.min(82, Math.floor(aM * 166 - 0.5))));
            max = (qM + 1) / 166;
            base_83.encode(1, Math.round(qM), hash, 1);
        } else {
            max = 1;
            base_83.encode(1, 0, hash, 1);
        }

        double[] d = _en_masse[0];
        base_83.encode(4, base_83.encode(d), hash, 2);
        for (int i = 1; i < _l; i++) {
            base_83.encode(2, base_83.encode(_en_masse[i], max), hash, 4 + 2 * i);
        }
        return new String(hash);
    }
}
