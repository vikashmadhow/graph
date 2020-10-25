package ma.vi.graph.algo.search;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;
import ma.vi.graph.algo.explore.*;
import ma.vi.graph.path.Path;

/**
 * <p>
 * The generic search algorithm searches a graph, starting from a start vertex, for a path
 * to a vertex for which the goalOp (of type {@link GoalOp}) predicate returns true. The
 * pathQueue and pathCostOp arguments have the same semantics as for those arguments in the
 * {@link Explore} algorithm. Notably using different {@link PathQueue} changes the behaviour
 * of the search algorithm to breadth-first, depth-first or minimum-cost.
 * </p>
 *
 * <p>
 * The path cost op function (of type {@link PathCostOp}), if provided, is used to compute
 * the cost of every path produced and is useful when searching the graph using more complicated
 * cost functions (such as the heuristics used in A* search). If not provided, the path cost
 * is taken to be the sum of the weight of the edges (if the weight are numeric) or simply
 * the length of the path, otherwise.
 * </p>
 *
 * <p>
 * The algorithm, if successful, returns a path from the start vertex to a goal vertex (as
 * defined by the goalOp or a fixed one set as the goalVertex).
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 * @param <E> The edge type of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Search<V, W> implements Algorithm<V, W, Path<V, W>> {
  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the search at.
   */
  public Search(V startVertex) {
    this.startVertex = startVertex;
  }

  /**
   * Sets the function to test whether a vertex is a goal or not.
   * This allows for setting a goal based on the properties of a
   * vertex instead of a specific predefined vertex. Use the
   * {@link #goalVertex} method to set a specific vertex as a goal
   * of the search.
   */
  public Search<V, W> goalOp(GoalOp<V, W> goalOp) {
    this.goalOp = goalOp;
    return this;
  }

  /**
   * Sets a vertex as a goal of the search. Use the {@link #goalOp}
   * method to instead search for vertices which possess certain
   * properties.
   */
  public Search<V, W> goalVertex(V goalVertex) {
    this.goalOp = new GoalOp.MatchVertex<>(goalVertex);
    return this;
  }

  /**
   * Sets the path queue to use which controls the search behaviour.
   * For instance, {@link FifoPathQueue} will search breadth-first,
   * {@link LifoPathQueue} will result in depth-first while {@link PriorityPathQueue}
   * will search path in order of costs.
   */
  public Search<V, W> pathQueue(PathQueue<V, W> pathQueue) {
    this.pathQueue = pathQueue;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public Search<V, W> expandOp(ExpandOp<V, W> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public Search<V, W> pathCostOp(PathCostOp<V, W> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public Path<V, W> execute(Graph<V, W> graph) {
    return new Explore<V, W, Path<V, W>>(startVertex)
                .pathQueue(pathQueue)
                .exploreOp(new SearchExploreOp<>(goalOp))
                .expandOp(expandOp)
                .pathCostOp(pathCostOp)
                .execute(graph);
  }

  protected final V startVertex;
  protected GoalOp<V, W> goalOp;
  protected PathQueue<V, W> pathQueue;
  protected ExpandOp<V, W> expandOp = ExpandOp::outgoingEdges;
  protected PathCostOp<V, W> pathCostOp = PathCostOp::byWeight;
}