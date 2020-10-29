package ma.vi.graph.algo.explore;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;

/**
 * An algorithm to explore a graph in order of minimum cost of paths.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class MinCostExplore<V, W, R> implements Algorithm<V, W, R> {
  public MinCostExplore(V startVertex) {
    this.startVertex = startVertex;
  }

  @Override
  public R execute(Graph<V, W> graph) {
    return new Explore<V, W, R>(startVertex)
        .pathQueue(new PriorityPathQueue<>())
        .exploreOp(exploreOp)
        .expandOp(expandOp)
        .pathCostOp(pathCostOp)
        .execute(graph);
  }

  /**
   * The function taking a graph and a path invoked for every path explored for the
   * graph. The accumulator parameter is passed as the last parameter to this function
   * and can be used to accumulate certain information during the exploration. It could
   * be used, for instance, to construct a spanning subgraph of this graph.
   */
  public MinCostExplore<V, W, R> exploreOp(ExploreOp<V, W, R> exploreOp) {
    this.exploreOp = exploreOp;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public MinCostExplore<V, W, R> expandOp(ExpandOp<V, W> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public MinCostExplore<V, W, R> pathCostOp(PathCostOp<V, W> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  protected final V startVertex;
  protected ExpandOp<V, W> expandOp = ExpandOp::outgoingEdges;
  protected ExploreOp<V, W, R> exploreOp;
  protected PathCostOp<V, W> pathCostOp = PathCostOp::byWeight;
}
