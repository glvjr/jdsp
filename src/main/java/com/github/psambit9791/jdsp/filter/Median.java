/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.filter;

import com.github.psambit9791.jdsp.misc.UtilMethods;
import org.apache.commons.math3.stat.StatUtils;

import java.util.Arrays;

/**
 * <h2>Median Filter</h2>
 * The Median class implements median filter which can be applied on the input signal using a window based on the kernel size.
 * Use: Unlike an averaging filter, the median filter can preserve sharp edges while completely suppressing isolated out-of-range noise.
 * Reference <a href="http://fourier.eng.hmc.edu/e161/lectures/smooth_sharpen/node2.html">article</a> for more information on 1-D Median Filter.
 *  
 *
 * @author  Sambit Paul
 * @version 2.0
 */

public class Median implements _KernelFilter {

    private int windowSize;

    /**
     * This constructor initialises the prerequisites required to use Median filter.
     * @throws java.lang.IllegalArgumentException if wsize (3) is greater than or equal to signal length
     */
    public Median() throws IllegalArgumentException{
        this.windowSize = 3;
    }

    /**
     * This constructor initialises the prerequisites required to use Median filter.
     * @throws java.lang.IllegalArgumentException if wsize is greater than or equal to signal length
     * @param wsize Window or kernel size
     */
    public Median(int wsize) throws IllegalArgumentException {
        this.windowSize = wsize;
    }

    /**
     * This method implements a median filter with given parameters, applies it on the signal and returns it.
     * @param signal Signal to be filtered
     * @return double[] Filtered signal
     */
    public double[] filter(double[] signal) {
        if (this.windowSize >= signal.length) {
            throw new IllegalArgumentException("Window size cannot be greater than or equal to signal length");
        }
        int paddingSize = (this.windowSize - 1)/2;
        double[] cons = new double[paddingSize];
        double[] newSignal = new double[signal.length];
        Arrays.fill(cons, 0);
        double[] paddedSignal = {};
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, cons);
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, signal);
        paddedSignal = UtilMethods.concatenateArray(paddedSignal, cons);
        for (int i = 0; i<signal.length; i++) {
            newSignal[i] = StatUtils.percentile(paddedSignal, i, this.windowSize, 50);
        }
        return newSignal;
    }

}
