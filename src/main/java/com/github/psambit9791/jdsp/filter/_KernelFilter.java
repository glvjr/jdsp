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

/**
 * <h2>Kernel Filter Interface</h2>
 * The Kernel Filter interface is implemented by all kernel-based filter classes - Median, Savgol and Wiener.
 * The user of this interface has control over implementing the filter function for a specific class.
 * For this interface, SAVGOL Filter ONLY RETURNS OUTPUT FOR "nearest" MODE
 *  
 *
 * @author  Sambit Paul
 * @version 2.0
 */
public interface _KernelFilter {
    /**
     * This method implements a kernel filter with given parameters, applies it on the signal and returns it.
     * @param signal Signal to be filtered
     * @return double[] Filtered signal
     */
    double[] filter(double[] signal);
}
