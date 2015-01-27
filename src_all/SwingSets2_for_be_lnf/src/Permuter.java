// TODO: Auto-generated Javadoc
/*
 * @(#)Permuter.java	1.9 05/11/17
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)Permuter.java	1.9 05/11/17
 */




/**
 * An object that implements a cheesy pseudorandom permutation of the integers
 * from zero to some user-specified value. (The permutation is a linear
 * function.) 
 *
 * @version 1.9 11/17/05
 * @author Josh Bloch
 */
class Permuter {
    /**
     * The size of the permutation.
     */
    private int modulus;

    /**
     * Nonnegative integer less than n that is relatively prime to m.
     */
    private int multiplier;

    /**
     * Pseudorandom nonnegative integer less than n.
     */
    private int addend = 22;

    /**
     * Instantiates a new permuter.
     *
     * @param n the n
     */
    public Permuter(int n) {
        if (n<0) {
            throw new IllegalArgumentException();
	}
        modulus = n;
        if (n==1) {
            return;
	}

        // Initialize the multiplier and offset
        multiplier = (int) Math.sqrt(n);
        while (gcd(multiplier, n) != 1) {
            if (++multiplier == n) {
                multiplier = 1;
	    }
	}
    }

    /**
     * Returns the integer to which this permuter maps the specified integer.
     * The specified integer must be between 0 and n-1, and the returned
     * integer will be as well.
     *
     * @param i the i
     * @return the int
     */
    public int map(int i) {
        return (multiplier * i + addend) % modulus;
    }

    /**
     * Calculate GCD of a and b, which are assumed to be non-negative.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    private static int gcd(int a, int b) {
        while(b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    /**
     * Simple test.  Takes modulus on command line and prints out permutation.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        int modulus = Integer.parseInt(args[0]);
        Permuter p = new Permuter(modulus);
        for (int i=0; i<modulus; i++) {
            System.out.print(p.map(i)+" ");
	}
        System.out.println();
    }
}

