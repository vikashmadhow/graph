package ma.vi.graph.algo.search;

import ma.vi.base.tuple.T1;
import ma.vi.graph.Graph;
import ma.vi.graph.Path;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.algo.GoalOp;
import ma.vi.graph.algo.PathCostOp;
import ma.vi.graph.algo.PriorityPathQueue;
import ma.vi.graph.algo.explore.Explore;

import static java.util.Collections.emptySet;

/**
 * <p>
 * A depth-first search that limits the depth that search can go to and
 * increasing the depth at each iteration until the goal is found or the
 * longest path in the graph has been reached.
 * </p>
 *
 * <p>
 * Iterative deepening search will find a goal even in an infinite graph
 * whereas a simple DFS would go into a infinite loop in such a graph.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class IterativeDeepeningSearch<V, W> implements Algorithm<V, W, Path<V, W>> {
  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the search at.
   */
  public IterativeDeepeningSearch(V startVertex) {
    this.startVertex = startVertex;
  }

  /**
   * Sets the function to test whether a vertex is a goal or not.
   * This allows for setting a goal based on the properties of a
   * vertex instead of a specific predefined vertex. Use the
   * {@link #goalVertex} method to set a specific vertex as a goal
   * of the search.
   */
  public IterativeDeepeningSearch<V, W> goalOp(GoalOp<V, W> goalOp) {
    this.goalOp = goalOp;
    return this;
  }

  /**
   * Sets a vertex as a goal of the search. Use the {@link #goalOp}
   * method to instead search for vertices which possess certain
   * properties.
   */
  public IterativeDeepeningSearch<V, W> goalVertex(V goalVertex) {
    this.goalOp = new GoalOp.MatchVertex<>(goalVertex);
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public IterativeDeepeningSearch<V, W> pathCostOp(PathCostOp<V, W> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public Path<V, W> execute(Graph<V, W> graph) {
    T1<Integer> depth = T1.of(1);
    Path<V, W> path = null;
    T1<Integer> maxDepthInPreviousIteration = T1.of(Integer.MAX_VALUE);
    T1<Integer> maxDepthInCurrentIteration = T1.of(-1);
    while (path == null
        && !maxDepthInCurrentIteration.a.equals(maxDepthInPreviousIteration.a)) {
      maxDepthInPreviousIteration.a = maxDepthInCurrentIteration.a;
      path = new Explore<V, W, Path<V, W>>(startVertex)
                  .pathQueue(new PriorityPathQueue<>())
                  .exploreOp(new SearchExploreOp<>(goalOp))
                  .expandOp((g, p) -> {
                    int pathLength = p.length();
                    if (pathLength < depth.a) {
                      if (pathLength > maxDepthInCurrentIteration.a) {
                        maxDepthInCurrentIteration.a = pathLength;
                      }
                      V pathEnd = p.lastVertex().orElse(null);
                      return pathEnd == null ? emptySet() : graph.outgoing(pathEnd);
                    } else {
                      return emptySet();
                    }
                  })
                  .pathCostOp(pathCostOp)
                  .execute(graph);
       depth.a += 1;
    }
    return path;
  }

  protected final V startVertex;
  protected GoalOp<V, W> goalOp;
  protected PathCostOp<V, W> pathCostOp;
}