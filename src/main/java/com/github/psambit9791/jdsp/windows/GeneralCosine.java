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
 * <h2>General Cosine Window</h2>
 * Generic weighted sum of cosine terms window.
 *  
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class GeneralCosine extends _Window {
    private double[] window;
    private final double[] weights;
    private final boolean sym;
    private final int len;

    /**
     * This constructor initialises the General Cosine class.
     * @throws java.lang.IllegalArgumentException if window length is less than 1
     * @param len Length of the window
     * @param weights Sequence of weighting coefficients
     * @param sym Whether the window is symmetric
     */
    public GeneralCosine(int len, double[] weights, boolean sym) throws IllegalArgumentException {
        super(len);
        this.len = len;
        this.weights = weights;
        this.sym = sym;
        generateWindow();
    }

    /**
     * This constructor initialises the Rectangular class. Symmetricity is set to True.
     * @throws java.lang.IllegalArgumentException if window length is less than 1.
     * @param len Length of the window
     * @param weights Sequence of weighting coefficients
     */
    public GeneralCosine(int len, double[] weights) throws IllegalArgumentException {
        this(len, weights, true);
    }

    private void generateWindow() {
        int tempLen = super.extend(this.len, this.sym);
        double[] tempArr = UtilMethods.linspace(-Math.PI, Math.PI, tempLen, true);
        this.window = new double[tempLen];
        Arrays.fill(this.window, 0);
        for (int i=0; i<this.weights.length; i++) {
            for (int j=0; j<tempArr.length; j++) {
                this.window[j] = this.window[j] + this.weights[i]*Math.cos(i*tempArr[j]);
            }
        }
        this.window = super.truncate(this.window);
    }

    /**
     * Generates and returns the General Cosine Window
     * @return double[] the generated window
     */
    public double[] getWindow() {
        return this.window;
    }
}
