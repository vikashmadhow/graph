package ma.vi.graph.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.Path;

import java.util.Optional;

/**
 * Search algorithms on graph.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Search {
  /**
   * <p>
   * Search the graph, starting from the start vertex, for a path to a node
   * for which goalOp returns true. The pathQueue and pathCostOp arguments
   * have the same semantics as for those arguments in the
   * {@link Explore#explore(Graph, Object, PathQueue, ExploreOp, Object, ExpandOp, PathCostOp)}
   * function.
   * </p>
   *
   * @param graph       The graph to search.
   * @param startVertex The vertex to start the search from.
   * @param pathQueue   The {@link PathQueue} to use, modifying the search behaviour as
   *                    described for the explore() function.
   * @param goalOp      This function is supplied with the graph and the current path
   *                    and, if it returns true, the search ends returning the current path.
   *                    Use the {@link GoalOp.MatchVertex} to create a goalOp which matches a
   *                    specific vertex in the graph.
   * @param pathCostOp  A function for calculating path-cost as used in the
   *                    explore() function.
   * @return The path from the start vertex to a goal node (as defined by the goalOp) or
   *         null if no such path is found.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E> search(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  PathQueue<V, W, E> pathQueue,
                                                                  GoalOp<V, W, E> goalOp,
                                                                  ExpandOp<V, W, E> expandOp,
                                                                  PathCostOp<V, W, E> pathCostOp) {
    class SearchExploreOp implements ExploreOp<V, W, E, Void, Path<V, W, E>> {
      @Override
      public Optional<Path<V, W, E>> op(Graph<V, W, E> graph,
                                        Path<V, W, E> path,
                                        Void accumulator) {
        return goalOp.op(graph, path) ? Optional.of(path) : Optional.empty();
      }
    }
    return Explore.explore(graph,
                           startVertex,
                           pathQueue,
                           new SearchExploreOp(),
                           null,
                           expandOp,
                           pathCostOp);
  }

  public static <V, W, E extends Edge<V, W>> Path<V, W, E> search(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  PathQueue<V, W, E> pathQueue,
                                                                  GoalOp<V, W, E> goalOp) {
    return search(graph,
                  startVertex,
                  pathQueue,
                  goalOp,
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Breadth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  breadthFirst(Graph<V, W, E> graph,
                                                                         V startVertex,
                                                                         GoalOp<V, W, E> goalOp,
                                                                         ExpandOp<V, W, E> expandOp,
                                                                         PathCostOp<V, W, E> pathCostOp) {
    return search(graph,
                  startVertex,
                  new FifoPathQueue<>(),
                  goalOp,
                  expandOp,
                  pathCostOp);
  }

  /**
   * Breadth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  breadthFirst(Graph<V, W, E> graph,
                                                                         V startVertex,
                                                                         GoalOp<V, W, E> goalOp) {
    return search(graph,
                  startVertex,
                  new FifoPathQueue<>(),
                  goalOp,
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Breadth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  breadthFirst(Graph<V, W, E> graph,
                                                                         V startVertex,
                                                                         V goalVertex) {
    return search(graph,
                  startVertex,
                  new FifoPathQueue<>(),
                  new GoalOp.MatchVertex<>(goalVertex),
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Depth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  depthFirst(Graph<V, W, E> graph,
                                                                         V startVertex,
                                                                         GoalOp<V, W, E> goalOp,
                                                                         ExpandOp<V, W, E> expandOp,
                                                                         PathCostOp<V, W, E> pathCostOp) {
    return search(graph,
                  startVertex,
                  new LifoPathQueue<>(),
                  goalOp,
                  expandOp,
                  pathCostOp);
  }

  /**
   * Depth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  depthFirst(Graph<V, W, E> graph,
                                                                       V startVertex,
                                                                       GoalOp<V, W, E> goalOp) {
    return search(graph,
                  startVertex,
                  new LifoPathQueue<>(),
                  goalOp,
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Depth-first search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  depthFirst(Graph<V, W, E> graph,
                                                                       V startVertex,
                                                                       V goalVertex) {
    return search(graph,
                  startVertex,
                  new LifoPathQueue<>(),
                  new GoalOp.MatchVertex<>(goalVertex),
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Min-cost search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  minCost(Graph<V, W, E> graph,
                                                                    V startVertex,
                                                                    GoalOp<V, W, E> goalOp,
                                                                    ExpandOp<V, W, E> expandOp,
                                                                    PathCostOp<V, W, E> pathCostOp) {
    return search(graph,
                  startVertex,
                  new PriorityPathQueue<>(),
                  goalOp,
                  expandOp,
                  pathCostOp);
  }

  /**
   * Min-cost search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  minCost(Graph<V, W, E> graph,
                                                                    V startVertex,
                                                                    GoalOp<V, W, E> goalOp) {
    return search(graph,
                  startVertex,
                  new PriorityPathQueue<>(),
                  goalOp,
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }

  /**
   * Min-cost search.
   */
  public static <V, W, E extends Edge<V, W>> Path<V, W, E>  minCost(Graph<V, W, E> graph,
                                                                    V startVertex,
                                                                    V goalVertex) {
    return search(graph,
                  startVertex,
                  new PriorityPathQueue<>(),
                  new GoalOp.MatchVertex<>(goalVertex),
                  ExpandOp::outgoingEdges,
                  PathCostOp::byWeight);
  }
}