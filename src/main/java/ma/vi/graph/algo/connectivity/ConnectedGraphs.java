package ma.vi.graph.algo.connectivity;

import ma.vi.base.unionfind.UnionFind;
import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Construct a minimum spanning tree (MST) using Kruskal's algorithm.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class ConnectedGraphs<V, W> implements Algorithm<V, W, List<Graph<V, W>>> {
  @Override
  public List<Graph<V, W>> execute(Graph<V, W> graph) {
    UnionFind<V> vertexSet = new UnionFind<>();
    List<Edge<V, W>> edges = new ArrayList<>(graph.edges());
    Set<Edge<V, W>> subgraphEdges = new HashSet<>();
    for (Edge<V, W> edge: edges) {
      if (!vertexSet.find(edge.endPoint1).equals(vertexSet.find(edge.endPoint2))) {
        vertexSet.union(edge.endPoint1, edge.endPoint2);
        subgraphEdges.add(edge);
      }
    }
    return graph.newGraph(subgraphEdges);
  }
}
