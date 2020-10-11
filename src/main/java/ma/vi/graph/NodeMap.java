package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A NodeMap maps nodes to a set of adjacent nodes and is used
 * primarily to build a graph.
 *
 * @param <N> The node type of the graph to build.
 * @param <W> The type of weight on the edges.
 * @author vikash.madhow@gmail.com
 */
public class NodeMap<N, W> extends HashMap<N, Set<T2<N, W>>> {
  public void add(N node1, N node2, W weight) {
    Set<T2<N, W>> targets = get(node1);
    if (targets == null) {
      targets = new HashSet<T2<N, W>>();
      put(node1, targets);
    }
    targets.add(T2.of(node2, weight));
  }
}
