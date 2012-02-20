package edu.emory.mathcs.csparsej.tdcomplex;

public class DZcs_complex {

    public static final double[] cs_czero() {
        return new double[] {0.0, 0.0};
    }

    public static final double[] cs_cone() {
        return new double[] {1.0, 0.0};
    }

    public static final double cs_creal(double[] x) {
        return x[0];
    }

    public static final double cs_cimag(double[] x) {
        return x[1];
    }

    public static final double[] cs_cget(double[] x, int idx) {
        return new double[] {x[idx], x[idx + 1]};
    }

    public static final void cs_cset(double[] x, int idx, double[] val) {
        x[idx] = val[0];
        x[idx + 1] = val[1];
    }

    public static final boolean cs_cequal(double[] x, double[] y) {
        return cs_cequal(x, y, 1e-14);
    }

    public static final boolean cs_cequal(double[] x, double[] y, double tol) {
        if (cs_cabs(x[0] - y[0], x[1] - y[1]) <= Math.abs(tol)) {
            return true;
        } else {
            return false;
        }
    }

    public static final double cs_cabs(double[] x) {
        double absX = Math.abs(x[0]);
        double absY = Math.abs(x[1]);
        if (absX == 0.0 && absY == 0.0) {
            return 0.0;
        } else if (absX >= absY) {
            double d = x[1] / x[0];
            return absX * Math.sqrt(1.0 + d * d);
        } else {
            double d = x[0] / x[1];
            return absY * Math.sqrt(1.0 + d * d);
        }
    }

    public static final double cs_cabs(double re, double im) {
        double absX = Math.abs(re);
        double absY = Math.abs(im);
        if (absX == 0.0 && absY == 0.0) {
            return 0.0;
        } else if (absX >= absY) {
            double d = im / re;
            return absX * Math.sqrt(1.0 + d * d);
        } else {
            double d = re / im;
            return absY * Math.sqrt(1.0 + d * d);
        }
    }

    public static final double[] cs_conj(double[] x) {
        double[] z = new double[2];
        z[0] = x[0];
        z[1] = -x[1];
        return z;
    }

    public static final double[] cs_cdiv(double[] x, double re, double im) {
        double[] z = new double[2];
        double scalar;

        if (Math.abs(re) >= Math.abs(im)) {
            scalar = 1.0 / (re + im * (im / re));

            z[0] = scalar * (x[0] + x[1] * (im / re));
            z[1] = scalar * (x[1] - x[0] * (im / re));

        } else {
            scalar = 1.0 / (re * (re / im) + im);

            z[0] = scalar * (x[0] * (re / im) + x[1]);
            z[1] = scalar * (x[1] * (re / im) - x[0]);
        }

        return z;
    }

    public static final double[] cs_cdiv(double[] x, double[] y) {
        return cs_cdiv(x, y[0], y[1]);
    }

    public static final double[] cs_cplus(double[] x, double[] y) {
        double[] z = new double[2];
        z[0] = x[0] + y[0];
        z[1] = x[1] + y[1];
        return z;
    }

    public static final double[] cs_cminus(double[] x, double[] y) {
        double[] z = new double[2];
        z[0] = x[0] - y[0];
        z[1] = x[1] - y[1];
        return z;
    }

    public static final double[] cs_cmult(double[] x, double y) {
        double[] z = new double[2];
        z[0] = x[0] * y;
        z[1] = x[1] * y;
        return z;
    }

    public static final double[] cs_cmult(double[] x, double[] y) {
        double[] z = new double[2];
        z[0] = x[0] * y[0] - x[1] * y[1];
        z[1] = x[1] * y[0] + x[0] * y[1];
        return z;
    }

    public static final double[] cs_csqrt(double[] x) {
        double[] z = new double[2];
        double absx = cs_cabs(x);
        double tmp;
        if (absx > 0.0) {
            if (x[0] > 0.0) {
                tmp = Math.sqrt(0.5 * (absx + x[0]));
                z[0] = tmp;
                z[1] = 0.5 * (x[1] / tmp);
            } else {
                tmp = Math.sqrt(0.5 * (absx - x[0]));
                if (x[1] < 0.0) {
                    tmp = -tmp;
                }
                z[0] = 0.5 * (x[1] / tmp);
                z[1] = tmp;
            }
        } else {
            z[0] = 0.0;
            z[1] = 0.0;
        }
        return z;
    }

    public static final double[] cs_csquare(double[] x) {
        return cs_cmult(x, x);
    }

//    public static double[] cs_cminus(double[] x, double[] y) {
//        double[] z = new double[2];
//        z[0] = x[0] - y[0];
//        z[1] = x[1] - y[1];
//        return z;
//    }
//
//    public static double[] cs_cplus(double[] x, double[] y) {
//        double[] z = new double[2];
//        z[0] = x[0] + y[0];
//        z[1] = x[1] + y[1];
//        return z;
//    }
//
//    public static double[] cs_cdiv(double[] x, double[] y) {
//        double re = y[0];
//        double im = y[1];
//
//        double[] z = new double[2];
//        double scalar;
//
//        if (Math.abs(re) >= Math.abs(im)) {
//            scalar = (1.0 / (re + im * (im / re)));
//
//            z[0] = scalar * (x[0] + x[1] * (im / re));
//            z[1] = scalar * (x[1] - x[0] * (im / re));
//
//        } else {
//            scalar = (1.0 / (re * (re / im) + im));
//
//            z[0] = scalar * (x[0] * (re / im) + x[1]);
//            z[1] = scalar * (x[1] * (re / im) - x[0]);
//        }
//
//        return z;
//    }
//
//    public static double[] cs_cmult(double[] x, double[] y) {
//        double[] z = new double[2];
//        z[0] = x[0] * y[0] - x[1] * y[1];
//        z[1] = x[1] * y[0] + x[0] * y[1];
//        return z;
//    }
//
//    public static double[] cs_cmult(double[] x, double y) {
//        return cs_cmult(x, new double[] { y, 0 });
//    }
//
//    public static double[] sqrt(double[] x) {
//        double[] z = new double[2];
//        double absx = DComplex.abs(x);
//        double tmp;
//        if (absx > 0.0) {
//            if (x[0] > 0.0) {
//                tmp = Math.sqrt(0.5 * (absx + x[0]));
//                z[0] = tmp;
//                z[1] = 0.5f * (x[1] / tmp);
//            } else {
//                tmp = Math.sqrt(0.5 * (absx - x[0]));
//                if (x[1] < 0.0) {
//                    tmp = -tmp;
//                }
//                z[0] = 0.5f * (x[1] / tmp);
//                z[1] = tmp;
//            }
//        } else {
//            z[0] = 0.0f;
//            z[1] = 0.0f;
//        }
//        return z;
//    }

}