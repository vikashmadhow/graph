package ma.vi.graph.algo.search;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.algo.GoalOp;
import ma.vi.graph.algo.LifoPathQueue;
import ma.vi.graph.algo.PathCostOp;
import ma.vi.graph.algo.explore.Explore;
import ma.vi.graph.path.Path;

import static java.util.Collections.emptySet;

/**
 * <p>
 * A depth-first search limited to a certain depth.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 * @param <E> The edge type of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class DepthLimitedSearch<V, W, E extends Edge<V, W>> implements Algorithm<V, W, E, Path<V, W, E>> {
  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the search at.
   */
  public DepthLimitedSearch(V startVertex, int maxDepth) {
    this.startVertex = startVertex;
    this.maxDepth = maxDepth;
  }

  /**
   * Sets the function to test whether a vertex is a goal or not.
   * This allows for setting a goal based on the properties of a
   * vertex instead of a specific predefined vertex. Use the
   * {@link #goalVertex} method to set a specific vertex as a goal
   * of the search.
   */
  public DepthLimitedSearch<V, W, E> goalOp(GoalOp<V, W, E> goalOp) {
    this.goalOp = goalOp;
    return this;
  }

  /**
   * Sets a vertex as a goal of the search. Use the {@link #goalOp}
   * method to instead search for vertices which possess certain
   * properties.
   */
  public DepthLimitedSearch<V, W, E> goalVertex(V goalVertex) {
    this.goalOp = new GoalOp.MatchVertex<>(goalVertex);
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public DepthLimitedSearch<V, W, E> pathCostOp(PathCostOp<V, W, E> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public Path<V, W, E> execute(Graph<V, W, E> graph) {
    return new Explore<V, W, E, Path<V, W, E>>(startVertex)
                  .pathQueue(new LifoPathQueue<>())
                  .exploreOp(new SearchExploreOp<>(goalOp))
                  .expandOp((g, p) -> {
                    int pathLength = p.length();
                    if (pathLength < maxDepth) {
                      V pathEnd = p.lastVertex().orElse(null);
                      return pathEnd == null ? emptySet() : graph.outgoing(pathEnd);
                    } else {
                      return emptySet();
                    }
                  })
                  .pathCostOp(pathCostOp)
                  .execute(graph);
  }

  protected final int maxDepth;
  protected final V startVertex;
  protected GoalOp<V, W, E> goalOp;
  protected PathCostOp<V, W, E> pathCostOp;
}