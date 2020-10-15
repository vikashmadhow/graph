package ma.vi.graph.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.path.Path;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Exploration algorithm on graph, such as depth-first
 * breadth-first, et.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Explore {
  /**
   * <p>
   * Explores the vertices in the graph starting from start_vertex. For every path from
   * start_vertex to an arbitrary vertex in the graph, the explore_op function is called
   * with the graph and the path (from the start_vertex to the current vertex). This function
   * can return None to continue the exploration, the constant DONT_FOLLOW_THIS_PATH to
   * continue the exploration without expanding the current path (ignoring all following
   * vertices on path) or any other value, in which case the exploration is stopped and that
   * value is returned as the result of the exploration.
   * </p>
   *
   * <p>
   * The path_cost_op function, if provided, is used to compute the cost of every
   * path produced and is useful when searching the graph using more complicated cost
   * functions (such as the heuristics used in A* search). If not provided, the path cost
   * is taken to be the sum of the weight of the edges (if the weight are numeric) or simply
   * the length of the path, otherwise.
   * </p>
   *
   * <p>
   * This exploration function is used to implement the search and tree-growing functions.
   * </p>
   *
   * @param graph       The graph to explore.
   *
   * @param startVertex The vertex to start the exploration at.
   *
   * @param pathQueue   The path queue to use, controlling the exploration behaviour.
   *
   * @param exploreOp   The function taking a graph and a path invoked for every path explored for the
   *                    graph. The accumulator parameter is passed as the last parameter to this function
   *                    and can be used to accumulate certain information during the exploration. It could
   *                    be used, for instance, to construct a spanning subgraph of this graph.
   *
   * @param accumulator A value supplied to the exploreOp to accumulate data during the exploration.
   *                    Could be used by custom graph algorithms.
   *
   * @param expandOp    A function taking as parameters the graph and current exploring path and
   *                    returning the successor edges to explore. The default function returns
   *                    all outgoing edges of the current path.
   *
   * @param pathCostOp  The optional function to compute the cost of a new path, invoked
   *                    with the graph, path to be extended and edge with which the
   *                    path is being extended.
   *
   * @return The result of the exploration which is the result returned by the exploreOp function,
   *         or null if the exploration completed without the exploreOp function returning any
   *         result (other than null).
   */
  public static <V, W, E extends Edge<V, W>, A, R> R explore(Graph<V, W, E> graph,
                                                             V startVertex,
                                                             PathQueue<V, W, E> pathQueue,
                                                             ExploreOp<V, W, E, A, R> exploreOp,
                                                             A accumulator,
                                                             ExpandOp<V, W, E> expandOp,
                                                             PathCostOp<V, W, E> pathCostOp) {
    pathQueue.add(graph.path(startVertex));
    return explore(graph,
                   pathQueue,
                   exploreOp,
                   accumulator,
                   expandOp,
                   pathCostOp);
  }

  public static <V, W, E extends Edge<V, W>, R> R explore(Graph<V, W, E> graph,
                                                            V startVertex,
                                                            PathQueue<V, W, E> pathQueue,
                                                            ExploreOp<V, W, E, ?, R> exploreOp) {
    pathQueue.add(graph.path(startVertex));
    return explore(graph,
                   pathQueue,
                   exploreOp,
                   null,
                   ExpandOp::outgoingEdges,
                   PathCostOp::byWeight);
  }

  private static <V, W, E extends Edge<V, W>, A, R> R explore(Graph<V, W, E> graph,
                                                              PathQueue<V, W, E> pathQueue,
                                                              ExploreOp<V, W, E, A, R> exploreOp,
                                                              A accumulator,
                                                              ExpandOp<V, W, E> expandOp,
                                                              PathCostOp<V, W, E> pathCostOp) {
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
                                    pathCostOp == null ? null : pathCostOp.op(graph, path, edge)));
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

  /**
   * Explores the graph breadth-first.
   */
  public static <V, W, E extends Edge<V, W>, A, R> R breadthFirst(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  ExploreOp<V, W, E, A, R> exploreOp,
                                                                  A accumulator,
                                                                  ExpandOp<V, W, E> expandOp) {
    return explore(graph,
                   startVertex,
                   new FifoPathQueue<>(),
                   exploreOp,
                   accumulator,
                   expandOp,
                   PathCostOp::byWeight);
  }

  public static <V, W, E extends Edge<V, W>, A, R> R breadthFirst(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  ExploreOp<V, W, E, A, R> exploreOp,
                                                                  A accumulator) {
    return breadthFirst(graph,
                   startVertex,
                   exploreOp,
                   accumulator,
                   ExpandOp::outgoingEdges);
  }  
  
  public static <V, W, E extends Edge<V, W>, R> R breadthFirst(Graph<V, W, E> graph,
                                                               V startVertex,
                                                               ExploreOp<V, W, E, ?, R> exploreOp) {
    return breadthFirst(graph,
                        startVertex,
                        exploreOp,
                        null,
                        ExpandOp::outgoingEdges);
  }


  /**
   * Explores the graph depth-first.
   */
  public static <V, W, E extends Edge<V, W>, A, R> R depthFirst(Graph<V, W, E> graph,
                                                                V startVertex,
                                                                ExploreOp<V, W, E, A, R> exploreOp,
                                                                A accumulator,
                                                                ExpandOp<V, W, E> expandOp) {
    return explore(graph,
                   startVertex,
                   new LifoPathQueue<>(),
                   exploreOp,
                   accumulator,
                   expandOp,
                   PathCostOp::byWeight);
  }

  public static <V, W, E extends Edge<V, W>, A, R> R depthFirst(Graph<V, W, E> graph,
                                                                V startVertex,
                                                                ExploreOp<V, W, E, A, R> exploreOp,
                                                                A accumulator) {
    return depthFirst(graph,
                      startVertex,
                      exploreOp,
                      accumulator,
                      ExpandOp::outgoingEdges);
  }

  public static <V, W, E extends Edge<V, W>, R> R depthFirst(Graph<V, W, E> graph,
                                                             V startVertex,
                                                             ExploreOp<V, W, E, ?, R> exploreOp) {
    return depthFirst(graph,
                      startVertex,
                      exploreOp,
                     null,
                      ExpandOp::outgoingEdges);
  }

  /**
   * Explores the graph in order of minimum cost pth.
   */
  public static <V, W, E extends Edge<V, W>, A, R> R minCostFirst(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  ExploreOp<V, W, E, A, R> exploreOp,
                                                                  A accumulator,
                                                                  ExpandOp<V, W, E> expandOp) {
    return explore(graph,
                   startVertex,
                   new PriorityPathQueue<>(),
                   exploreOp,
                   accumulator,
                   expandOp,
                   PathCostOp::byWeight);
  }

  public static <V, W, E extends Edge<V, W>, A, R> R minCostFirst(Graph<V, W, E> graph,
                                                                  V startVertex,
                                                                  ExploreOp<V, W, E, A, R> exploreOp,
                                                                  A accumulator) {
    return minCostFirst(graph,
                        startVertex,
                        exploreOp,
                        accumulator,
                        ExpandOp::outgoingEdges);
  }

  public static <V, W, E extends Edge<V, W>, R> R minCostFirst(Graph<V, W, E> graph,
                                                               V startVertex,
                                                               ExploreOp<V, W, E, ?, R> exploreOp) {
    return minCostFirst(graph,
                        startVertex,
                        exploreOp,
                        null,
                        ExpandOp::outgoingEdges);
  }
}
