package ma.vi.graph.algo.search;

import ma.vi.graph.UndirectedEdge;
import ma.vi.graph.algo.TestGraphs;
import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class SearchTest {
  @BeforeAll
  static void print() {
    System.out.println(TestGraphs.romaniaCities.toGraphViz());
  }

  @Test
  void breadthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new BreadthFirstSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));

    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 450L, "Arad", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void depthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new DepthFirstSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void minCost() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new MinCostSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 418L, "Arad", "Sibiu", "Rimnicu Vilcea", "Pitesti", "Bucharest"));
  }

  @Test
  void aStar() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new AStarSearch<>(
          "Arad",
          "Bucharest",
          (v1, v2) -> TestGraphs.distanceToBucharest.get(v1)
        ));
    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 418L, "Arad", "Sibiu", "Rimnicu Vilcea", "Pitesti", "Bucharest"));
  }

  @Test
  void iterativeDeepening() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new IterativeDeepeningSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void iterativeDeepeningNotFound() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new IterativeDeepeningSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Unknown"));
    System.out.println(path);
    assertNull(path);
  }

  @Test
  void depthLimited() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new DepthLimitedSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad", 5).goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, TestGraphs.romaniaCities.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void depthLimitedNotFound() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        TestGraphs.romaniaCities.apply(new DepthLimitedSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad", 4).goalVertex("Bucharest"));
    System.out.println(path);
    assertNull(path);
  }
}