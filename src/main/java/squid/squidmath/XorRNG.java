/*
Written in 2015 by Sebastiano Vigna (vigna@acm.org)

To the extent possible under law, the author has dedicated all copyright
and related and neighboring rights to this software to the public domain
worldwide. This software is distributed without any warranty.

See <http://creativecommons.org/publicdomain/zero/1.0/>. */
package squid.squidmath;
import java.util.Random;
/**
 * This is a SplittableRandom-style generator.
 * Written in 2015 by Sebastiano Vigna (vigna@acm.org)
 * @author Sebastiano Vigna
 */

public class XorRNG extends Random {
	private static final long serialVersionUID = 3L;

    /** 2 raised to the 53, - 1. */
    private static final long DOUBLE_MASK = ( 1L << 53 ) - 1;
    /** 2 raised to the -53. */
    private static final double NORM_53 = 1. / ( 1L << 53 );
    /** 2 raised to the 24, -1. */
    private static final long FLOAT_MASK = ( 1L << 24 ) - 1;
    /** 2 raised to the -24. */
    private static final double NORM_24 = 1. / ( 1L << 24 );

    public long state; /* The state can be seeded with any value. */

    /** Creates a new generator seeded using Math.random. */
	public XorRNG() {
		this((long)Math.floor(Math.random() * Long.MAX_VALUE));
	}

	public XorRNG( final long seed ) {
		super( seed );
	}

	@Override
	protected int next( int bits ) {
		return (int)( nextLong() & ( 1L << bits ) - 1 );
	}

	@Override
	public long nextLong() {
        long z = ( state += 0x9E3779B97F4A7C15l );
        z = (z ^ (z >> 30)) * 0xBF58476D1CE4E5B9l;
        z = (z ^ (z >> 27)) * 0x94D049BB133111EBl;
        return z ^ (z >> 31);

	}

	@Override
	public int nextInt() {
		return (int)nextLong();
	}

    @Override
    public int nextInt( final int n ) {
        if ( n <= 0 ) throw new IllegalArgumentException();
        return (int)( ( nextLong() >>> 1 ) % n );
    }

    public int nextInt( final int lower, final int upper ) {
        if ( upper - lower <= 0 ) throw new IllegalArgumentException();
        return lower + (int)( ( nextLong() >>> 1 ) % (upper - lower) );
    }
	
	public long nextLong( final long n ) {
        if ( n <= 0 ) throw new IllegalArgumentException();
		for(;;) {
			final long bits = nextLong() >>> 1;
			final long value = bits % n;
			if ( bits - value + ( n - 1 ) >= 0 ) return value;
		}
	}
	
	@Override
	 public double nextDouble() {
		return ( nextLong() & DOUBLE_MASK ) * NORM_53;
	}
	
	@Override
	public float nextFloat() {
		return (float)( ( nextLong() & FLOAT_MASK ) * NORM_24 );
	}

	@Override
	public boolean nextBoolean() {
		return ( nextLong() & 1 ) != 0;
	}
	
	@Override
	public void nextBytes( final byte[] bytes ) {
		int i = bytes.length, n = 0;
		while( i != 0 ) {
			n = Math.min( i, 8 );
			for ( long bits = nextLong(); n-- != 0; bits >>= 8 ) bytes[ --i ] = (byte)bits;
		}
	}


    /** Sets the seed of this generator.
     *
     */
    @Override
    public void setSeed( final long seed ) {
        state = seed;
    }
    /** Sets the seed (also the current state) of this generator.
     *
     */
    public void setState( final long seed ) {
        state = seed;
    }
    /** Gets the current state of this generator.
     *
     */
    public long getState( ) {
        return state;
    }
}
