package ma.vi.graph.algo.connectivity;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.algo.explore.DepthFirstExplore;

import java.util.*;

/**
 * <p>
 * Find connected components in undirected graphs and strongly connected components
 * in directed graphs. For undirected graphs connected components is searched for by
 * running depth-search on the graph from non-explored vertices.
 * </p>
 *
 * <p>
 * This algorithm returns each connected components as a set of vertices. If you need
 * the connected components as a full subgraph, use the {@link ConnectedGraphs} algorithm.
 * </p>
 *
 * @param <V> The vertex type of the graph to explore.
 * @param <W> The weight type on the edges of the graph to explore.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class ConnectedComponents<V, W> implements Algorithm<V, W, List<Set<V>>> {
  @Override
  public List<Set<V>> execute(Graph<V, W> graph) {
    List<Set<V>> components = new ArrayList<>();
    if (graph.directed()) {
    } else {
      /*
       * For undirected graphs, explore all vertices using DFS
       */
      Set<V> explored = new HashSet<>();
      for (V vertex: graph.vertices()) {
        if (!explored.contains(vertex)) {
          Set<V> connected = new HashSet<>();
          connected.add(vertex);
          new DepthFirstExplore<V, W, Void>(vertex)
              .exploreOp((g, p) -> {
                Optional<V> v = p.lastVertex();
                if (v.isPresent()) {
                  explored.add(v.get());
                  connected.add(v.get());
                }
                return Optional.empty();
              })
              .execute(graph);
          components.add(connected);
        }
      }
    }
    return components;
  }
}
