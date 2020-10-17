package ma.vi.graph.algo.search;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
@FunctionalInterface
public interface GoalEstimate<V> {
  long op(V from, V to);
}
