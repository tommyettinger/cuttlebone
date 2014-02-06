package squid.squidmath;
import java.util.Random;
/**
 * This is a XorShift* RNG written by Sebastiano Vigna.
 * @author Sebastiano Vigna
 *
 */
public class XorRNG extends Random {
	private static final long serialVersionUID = 2L;

	/** 2 raised to the 53, - 1. */
	private static final long DOUBLE_MASK = ( 1L << 53 ) - 1;
	/** 2 raised to the -53. */
	private static final double NORM_53 = 1. / ( 1L << 53 );
	/** 2 raised to the 24, -1. */
	private static final long FLOAT_MASK = ( 1L << 24 ) - 1;
	/** 2 raised to the -24. */
	private static final double NORM_24 = 1. / ( 1L << 24 );

	/** The internal state of the algorithm. */
	private long x;

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
		x ^= x >>> 11;
		x ^= x >>> 32;
		return 1181783497276652981L * ( x ^= ( x << 5 ) );
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


	/** Sets the seed of this generator. Passing this 0 will just set it to -1 instead.
	 * 
	 */
	@Override
	public void setSeed( final long seed ) {
		x = seed == 0 ? -1 : seed;
	}
}
