/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.windows;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import java.util.Arrays;

/**
 * <h2>Tukey Window</h2>
 * Generates a Tukey window, also known as a tapered cosine window.
 *  
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Tukey extends _Window {
    private double[] window;
    private final double alpha;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the Tukey class.
     *
     * @param len Length of the window
     * @param alpha Shape parameter of the Tukey window, representing the fraction of the window inside the cosine tapered region.
     * @param sym Whether the window is symmetric
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     */
    public Tukey(int len, double alpha, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.alpha = alpha;
        this.sym = sym;
        if (alpha > 1 || alpha < 0) {
            throw new IllegalArgumentException("Alpha must be between 0 and 1");
        }
        generateWindow();
    }

    /**
     * This constructor initialises the Tukey class. Symmetricity is set to True.
     *
     * @param len Length of the window
     * @param alpha Shape parameter of the Tukey window, representing the fraction of the window inside the cosine tapered region.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     */
    public Tukey(int len, double alpha) throws IllegalArgumentException {
        this(len, alpha, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        this.window = new double[tempLen];

        double[] n = UtilMethods.arange(0.0, tempLen, 1.0);
        int width = (int)Math.floor(this.alpha*(tempLen-1)/2.0);

        double[] n1 = UtilMethods.splitByIndex(n, 0, width+1);
        double[] n2 = UtilMethods.splitByIndex(n, width+1, tempLen-width-1);
        double[] n3 = UtilMethods.splitByIndex(n, tempLen-width-1, tempLen);

        for (int i=0; i<n1.length; i++) {
            n1[i] = 0.5 * (1 + Math.cos(Math.PI * (-1 + 2.0*n1[i]/this.alpha/(tempLen-1))));
        }
        Arrays.fill(n2, 1.0);
        for (int i=0; i<n3.length; i++) {
            n3[i] = 0.5 * (1 + Math.cos(Math.PI * (-2.0/this.alpha + 1 + 2.0*n3[i]/this.alpha/(tempLen-1))));
        }

        this.window = UtilMethods.concatenateArray(n1, n2);
        this.window = UtilMethods.concatenateArray(this.window, n3);
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the Tukey Window
     *
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
