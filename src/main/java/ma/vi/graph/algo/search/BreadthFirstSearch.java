package ma.vi.graph.algo.search;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;
import ma.vi.graph.algo.explore.Explore;
import ma.vi.graph.path.Path;

/**
 * <p>
 * Searches a graph in breadth-first order.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 * @param <E> The edge type of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class BreadthFirstSearch<V, W, E extends Edge<V, W>> implements Algorithm<V, W, E, Path<V, W, E>> {
  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the search at.
   */
  public BreadthFirstSearch(V startVertex) {
    this.startVertex = startVertex;
  }

  /**
   * Sets the function to test whether a vertex is a goal or not.
   * This allows for setting a goal based on the properties of a
   * vertex instead of a specific predefined vertex. Use the
   * {@link #goalVertex} method to set a specific vertex as a goal
   * of the search.
   */
  public BreadthFirstSearch<V, W, E> goalOp(GoalOp<V, W, E> goalOp) {
    this.goalOp = goalOp;
    return this;
  }

  /**
   * Sets a vertex as a goal of the search. Use the {@link #goalOp}
   * method to instead search for vertices which possess certain
   * properties.
   */
  public BreadthFirstSearch<V, W, E> goalVertex(V goalVertex) {
    this.goalOp = new GoalOp.MatchVertex<>(goalVertex);
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public BreadthFirstSearch<V, W, E> expandOp(ExpandOp<V, W, E> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public BreadthFirstSearch<V, W, E> pathCostOp(PathCostOp<V, W, E> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public Path<V, W, E> execute(Graph<V, W, E> graph) {
    return new Explore<V, W, E, Path<V, W, E>>(startVertex)
                .pathQueue(new FifoPathQueue<>())
                .exploreOp(new SearchExploreOp<>(goalOp))
                .expandOp(expandOp)
                .pathCostOp(pathCostOp)
                .execute(graph);
  }

  protected final V startVertex;
  protected GoalOp<V, W, E> goalOp;
  protected ExpandOp<V, W, E> expandOp = ExpandOp::outgoingEdges;
  protected PathCostOp<V, W, E> pathCostOp = PathCostOp::byWeight;
}