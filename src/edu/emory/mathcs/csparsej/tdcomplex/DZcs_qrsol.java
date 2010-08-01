/* ***** BEGIN LICENSE BLOCK *****
 *
 * CSparse: a Concise Sparse matrix package.
 * Copyright (c) 2006, Timothy A. Davis.
 * http://www.cise.ufl.edu/research/sparse/CSparse
 *
 * -------------------------------------------------------------------------
 *
 * CSparseJ is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * CSparseJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this Module; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * ***** END LICENSE BLOCK ***** */

package edu.emory.mathcs.csparsej.tdcomplex;

import edu.emory.mathcs.csparsej.tdcomplex.DZcs_common.DZcsa;
import edu.emory.mathcs.csparsej.tdcomplex.DZcs_common.DZcs;
import edu.emory.mathcs.csparsej.tdcomplex.DZcs_common.DZcsn;
import edu.emory.mathcs.csparsej.tdcomplex.DZcs_common.DZcss;

/**
 * Solve a least-squares or underdetermined problem.
 *
 * @author Piotr Wendykier (piotr.wendykier@gmail.com)
 * @author Richard Lincoln (r.w.lincoln@gmail.com)
 *
 */
public class DZcs_qrsol {

    /**
     * Solve a least-squares problem (min ||Ax-b||_2, where A is m-by-n with m
     * >= n) or underdetermined system (Ax=b, where m < n)
     *
     * @param order
     *            ordering method to use (0 to 3)
     * @param A
     *            column-compressed matrix
     * @param b
     *            size max(m,n), b (size m) on input, x(size n) on output
     * @return true if successful, false on error
     */
    public static boolean cs_qrsol(int order, DZcs A, DZcsa b) {
        DZcsa x;
        DZcss S;
        DZcsn N;
        DZcs AT = null;
        int k, m, n;
        boolean ok;
        if (!DZcs_util.CS_CSC(A) || b == null)
            return (false); /* check inputs */
        n = A.n;
        m = A.m;
        if (m >= n) {
            S = DZcs_sqr.cs_sqr(order, A, true); /* ordering and symbolic analysis */
            N = DZcs_qr.cs_qr(A, S); /* numeric QR factorization */
            x = new DZcsa(S != null ? S.m2 : 1); /* get workspace */
            ok = (S != null && N != null);
            if (ok) {
                DZcs_ipvec.cs_ipvec(S.pinv, b, x, m); /* x(0:m-1) = b(p(0:m-1) */
                for (k = 0; k < n; k++) /* apply Householder refl. to x */
                {
                    DZcs_happly.cs_happly(N.L, k, N.B[k], x);
                }
                DZcs_usolve.cs_usolve(N.U, x); /* x = R\x */
                DZcs_ipvec.cs_ipvec(S.q, x, b, n); /* b(q(0:n-1)) = x(0:n-1) */
            }
        } else {
            AT = DZcs_transpose.cs_transpose(A, true); /* Ax=b is underdetermined */
            S = DZcs_sqr.cs_sqr(order, AT, true); /* ordering and symbolic analysis */
            N = DZcs_qr.cs_qr(AT, S); /* numeric QR factorization of A' */
            x = new DZcsa(S != null ? S.m2 : 1); /* get workspace */
            ok = (AT != null && S != null && N != null);
            if (ok) {
                DZcs_pvec.cs_pvec(S.q, b, x, m); /* x(q(0:m-1)) = b(0:m-1) */
                DZcs_utsolve.cs_utsolve(N.U, x); /* x = R'\x */
                for (k = m - 1; k >= 0; k--) /* apply Householder refl. to x */
                {
                    DZcs_happly.cs_happly(N.L, k, N.B[k], x);
                }
                DZcs_pvec.cs_pvec(S.pinv, x, b, n); /* b(0:n-1) = x(p(0:n-1)) */
            }
        }
        return (ok);
    }
}