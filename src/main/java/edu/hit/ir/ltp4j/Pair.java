package edu.hit.ir.ltp4j;
public class Pair<F, S> {
  public final F first;
  public final S second;

  /**
   * Constructor for a Pair.
   * 
   * @param first
   *            the first object in the Pair
   * @param second
   *            the second object in the pair
   */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Compute a hash code using the hash codes of the underlying objects
   * 
   * @return a hashcode of the Pair
   */
  @Override
  public int hashCode() {
    return (first == null ? 0 : first.hashCode())
      ^ (second == null ? 0 : second.hashCode());
  }

  public boolean equals(Object o) {
    if (!(o instanceof Pair)) {
      return false;
    }
    Pair<?, ?> p = (Pair<?, ?>) o;
    return (p.first.equals(first)) && (p.second.equals(second));
  }

  /**
   * Convenience method for creating an appropriately typed pair.
   * 
   * @param a
   *            the first object in the Pair
   * @param b
   *            the second object in the pair
   * @return a Pair that is templatized with the types of a and b
   */
  public static <A, B> Pair<A, B> create(A a, B b) {
    return new Pair<A, B>(a, b);
  }
}
