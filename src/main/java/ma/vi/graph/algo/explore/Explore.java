package ma.vi.graph.algo.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;
import ma.vi.graph.path.Path;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * A generic algorithm to explore the vertices in the graph starting from a start vertex.
 * For every path from the start vertex to an arbitrary vertex in the graph, an explore
 * function (of type {@link ExploreOp} is called with the graph and the path (from the start
 * vertex to that vertex). The exploreOp returns null to signify that the path should be
 * ignored (and exploration should continue from the next); an empty {@link Optional} means
 * that the exploration should expand this path; finally a value in the Optional means
 * that exploration is completed and the value should be returned as the result of the
 * exploration.
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
 * This exploration algorithm is used by other algorithms such as search and tree-growing algos.
 * </p>

 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 *   graph.apply(
 *     new Explore(startVertex).pathQueue(new LifoPathQueue())
 *                             .exploreOp((graph, path, acc) -> log(path)))
 * </pre>
 *
 * @param <V> The vertex type of the graph to explore.
 * @param <W> The weight type on the edges of the graph to explore.
 * @param <E> The edge type of the graph to explore.
 * @param <A> The type of accumulator passed to the exploreOp for accumulating partial results.
 * @param <R> The result of the exploration which is the result returned by the exploreOp function.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Explore<V, W, E extends Edge<V, W>, A, R> implements Algorithm<V, W, E, R> {

  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the exploration at.
   */
  public Explore(V startVertex) {
    this.startVertex = startVertex;
  }

  /**
   * Sets the path queue to use which controls the exploration behaviour.
   * For instance, {@link FifoPathQueue} will explore breadth-first,
   * {@link LifoPathQueue} will result in depth-first while {@link PriorityPathQueue}
   * will explore path in order of costs.
   */
  public Explore<V, W, E, A, R> pathQueue(PathQueue<V, W, E> pathQueue) {
    this.pathQueue = pathQueue;
    return this;
  }

  /**
   * The function taking a graph and a path invoked for every path explored for the
   * graph. The accumulator parameter is passed as the last parameter to this function
   * and can be used to accumulate certain information during the exploration. It could
   * be used, for instance, to construct a spanning subgraph of this graph.
   */
  public Explore<V, W, E, A, R> exploreOp(ExploreOp<V, W, E, A, R> exploreOp) {
    this.exploreOp = exploreOp;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public Explore<V, W, E, A, R> expandOp(ExpandOp<V, W, E> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public Explore<V, W, E, A, R> pathCostOp(PathCostOp<V, W, E> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  /**
   * A value supplied to the exploreOp to accumulate data during the exploration.
   * Could be used by custom graph algorithms.
   */
  public Explore<V, W, E, A, R> accumulator(A accumulator) {
    this.accumulator = accumulator;
    return this;
  }

  @Override
  public R execute(Graph<V, W, E> graph) {
     if (pathQueue == null) {
      pathQueue = new FifoPathQueue<>();
    }
    if (startVertex != null) {
      pathQueue.add(graph.path(startVertex));
    } else {
      throw new IllegalArgumentException("Start vertex to explore from not provided");
    }
    Set<V> explored = new HashSet<>();
    while (pathQueue.hasNext()) {
      Path<V, W, E> path = pathQueue.next();
      Optional<V> f = path.end();
      if (f.isPresent()) {
        V frontier = f.get();
        if (!explored.contains(frontier)) {
          explored.add(frontier);
          Optional<R> result = exploreOp.op(graph, path, accumulator);
          if (result != null) {
            /*
             * null returned from the exploreOp signifies that this path should be ignored
             * (and exploration should continue from the next); an empty Optional means that
             * the exploration should expand this path; finally a value in the Optional means
             * that exploration is completed and the value should be returned as its result.
             */
            if (result.isEmpty()) {
              Set<E> successors = expandOp.op(graph, path);
              if (!successors.isEmpty()) {
                for (E edge: successors) {
                  if (!explored.contains(edge.endPoint2())) {
                    pathQueue.add(
                        path.extend(edge.endPoint2(), edge.weight(),
                                    pathCostOp == null ? null : pathCostOp.op(graph, path, edge, null)));
                  }
                }
              }
            } else {
              return result.get();
            }
          }
        }
      }
    }
    return null;
  }

  protected final V startVertex;
  protected PathQueue<V, W, E> pathQueue;
  protected ExpandOp<V, W, E> expandOp = ExpandOp::outgoingEdges;
  protected ExploreOp<V, W, E, A, R> exploreOp;
  protected A accumulator;
  protected PathCostOp<V, W, E> pathCostOp = PathCostOp::byWeight;
}
