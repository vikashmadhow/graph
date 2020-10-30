package ma.vi.graph.algo.connectivity;

import ma.vi.base.unionfind.UnionFind;
import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Returns a list of connected subgraphs from a graph. This algorithm uses
 * the union-find structure to identify the set of edges which belong to the
 * same connected component; it then constructs an returns a list of graphs
 * with the edges for each connected component
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
    UnionFind<V, Edge<V, W>> vertexSet = new UnionFind<>();
    for (Edge<V, W> edge: graph.edges()) {
      V component = vertexSet.union(edge.endPoint1, edge.endPoint2);
      vertexSet.add(component, edge);
    }
    List<Graph<V, W>> graphs = new ArrayList<>();
    for (V component: vertexSet.components()) {
      graphs.add(graph.newGraph(vertexSet.elements(component)));
    }
    return graphs;
  }
}
