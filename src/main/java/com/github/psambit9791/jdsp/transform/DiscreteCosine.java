/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.transform;

/**
 * <h2>Discrete Cosine Transform</h2>
 * The Discrete Cosine class decomposes a finite sequence of data points in terms of a sum of cosine functions of different frequencies.
 * Of the 8 types of DCT, this class incorporates Types 1 to 4.
 * For details about DCT Types, refer to this <a href="https://en.wikipedia.org/wiki/Discrete_cosine_transform#Formal_definition">article</a>
 *  
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class DiscreteCosine implements _SineCosine {

    private double[] signal;
    private double[] output = null;
    private Normalization norm;

    /**
     * This constructor initialises the prerequisites required to use DiscreteCosine
     * @param signal The signal to be transformed
     * @param norm The normalization option (STANDARD or ORTHOGONAL).
     */
    public DiscreteCosine(double[] signal, Normalization norm) {
        this.signal = signal;
        this.norm = norm;

    }

    /**
     * This constructor initialises the prerequisites required to use DiscreteCosine, normalization is set to STANDARD
     * @param signal The signal to be transformed
     */
    public DiscreteCosine(double[] signal) {
        this.signal = signal;
        this.norm = Normalization.STANDARD;
    }

    private double get_scaling_factor(int k, int type) {
        int N = this.signal.length;
        double factor = 0;
        if (type == 1) {
            if (k == 0 || k == (N-1)) {
                factor = 0.5 * Math.sqrt(1.0/(N-1));
            }
            else {
                factor = 0.5 * Math.sqrt(2.0/(N-1));
            }
        }
        else if (type == 2) {
            if (k == 0) {
                factor = Math.sqrt(1.0/ (4*N));
            }
            else {
                factor = Math.sqrt(1.0/ (2*N));
            }
        }
        else if (type == 3) {
            factor = 1.0/Math.sqrt(N);
        }
        else if (type == 4) {
            factor = 1.0/ Math.sqrt(2*N);
        }
        return factor;
    }

    private double[] type1() {
        double[] out = new double[this.signal.length];
        double factor1 = 1.0;
        double factor2 = 1.0;
        if (this.norm == Normalization.ORTHOGONAL) {
            factor1 = Math.sqrt(2.0);
        }
        for (int k=0; k<out.length; k++) {
            if (this.norm == Normalization.ORTHOGONAL) {
                factor2 = this.get_scaling_factor(k, 1);
            }
            double temp0 = factor1 * this.signal[0];
            double temp1 = factor1 * Math.pow(-1, k) * this.signal[this.signal.length - 1];
            double sum = 0;
            for (int n = 1; n < this.signal.length - 1; n++) {
                sum += this.signal[n] * Math.cos((Math.PI * k * n) / (this.signal.length - 1));
            }
            out[k] = factor2 * (temp0 + temp1 + 2 * sum);
        }
        return out;
    }

    private double[] type2() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++){
            double sum = 0;
            for (int n=0; n<this.signal.length; n++) {
                sum += this.signal[n] * Math.cos((Math.PI * k * (2*n + 1))/(2 * this.signal.length));
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k, 2) * 2 * sum;
            }
            else {
                out[k] = 2 * sum;
            }
        }
        return out;
    }

    private double[] type3() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++) {

            double temp0 = this.signal[0];
            double sum = 0;
            for (int n = 1; n < this.signal.length; n++) {
                sum += this.signal[n] * Math.cos((Math.PI * (2*k + 1) * n) / (this.signal.length * 2));
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k, 3) * (temp0 + Math.sqrt(2) * sum);
            }
            else {
                out[k] = temp0 + 2 * sum;
            }
        }
        return out;
    }

    private double[] type4() {
        double[] out = new double[this.signal.length];
        for (int k=0; k<out.length; k++){
            double sum = 0;
            for (int n=0; n<this.signal.length; n++) {
                sum += this.signal[n] * Math.cos((Math.PI * (2*k + 1) * (2*n + 1))/(4 * this.signal.length));
            }
            if (this.norm == Normalization.ORTHOGONAL) {
                out[k] = this.get_scaling_factor(k, 4) * 2 * sum;
            }
            else {
                out[k] = 2 * sum;
            }
        }
        return out;

    }

    /**
     * This function performs the discrete cosine transform on the input signal
     * @param type Type of transform to apply
     * @throws java.lang.IllegalArgumentException If type is not between 1 and 4
     */
    public void transform(int type) throws IllegalArgumentException {
        if ((type <= 0) || (type > 4)) {
            throw new IllegalArgumentException("Type must be between 1 and 4");
        }
        switch (type) {
            case 1:
                this.output = this.type1();
                break;
            case 2:
                this.output = this.type2();
                break;
            case 3:
                this.output = this.type3();
                break;
            case 4:
                this.output = this.type4();
                break;
        }
    }

    /**
     * This function performs the discrete cosine transform (type 1) on the input signal.
     */
    public void transform() {
        this.output = this.type1();
    }

    /**
     * Returns the length of the input signal.
     *
     * @return int The updated length of the input signal.
     */
    public int getSignalLength() {
        return this.signal.length;
    }

    /**
     * Returns the output of the transformation.
     *
     * @throws java.lang.ExceptionInInitializerError if called before executing transform() method
     * @return double[] The transformed signal.
     */
    public double[] getOutput() throws ExceptionInInitializerError {
        if (this.output == null) {
            throw new ExceptionInInitializerError("Execute transform() function before returning result");
        }
        return this.output;
    }
}
