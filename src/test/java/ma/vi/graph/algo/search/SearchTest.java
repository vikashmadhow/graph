package ma.vi.graph.algo.search;

import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static ma.vi.graph.algo.TestGraphs.distanceToBucharest;
import static ma.vi.graph.algo.TestGraphs.romaniaCities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class SearchTest {
  @BeforeAll
  static void print() {
    System.out.println(romaniaCities.toGraphViz());
  }

  @Test
  void breadthFirst() {
    Path<String, Integer> path =
        romaniaCities.apply(new BreadthFirstSearch<String, Integer>("Arad").goalVertex("Bucharest"));

    System.out.println(path);
    assertEquals(romaniaCities.path(450L,
                                    romaniaCities.edge("Arad", "Sibiu"),
                                    romaniaCities.edge("Sibiu", "Fagaras"),
                                    romaniaCities.edge("Fagaras", "Bucharest")), path);
  }

  @Test
  void depthFirst() {
    Path<String, Integer> path =
        romaniaCities.apply(new DepthFirstSearch<String, Integer>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals("Arad", path.firstVertex().get());
    assertEquals("Bucharest", path.lastVertex().get());
  }

  @Test
  void minCost() {
    Path<String, Integer> path =
        romaniaCities.apply(new MinCostSearch<String, Integer>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals( romaniaCities.path( 418L,
                                      romaniaCities.edge("Arad", "Sibiu"),
                                      romaniaCities.edge("Sibiu", "Rimnicu Vilcea"),
                                      romaniaCities.edge("Rimnicu Vilcea", "Pitesti"),
                                      romaniaCities.edge("Pitesti", "Bucharest")),
                  path);
  }

  @Test
  void aStar() {
    Path<String, Integer> path =
        romaniaCities.apply(new AStarSearch<>(
          "Arad",
          "Bucharest",
          (v1, v2) -> distanceToBucharest.get(v1)
        ));
    System.out.println(path);
    assertEquals(romaniaCities.path(418L, romaniaCities.edge("Arad", "Sibiu"),
                                    romaniaCities.edge("Sibiu", "Rimnicu Vilcea"),
                                    romaniaCities.edge("Rimnicu Vilcea", "Pitesti"),
                                    romaniaCities.edge("Pitesti", "Bucharest")), path);
  }

  @Test
  void iterativeDeepening() {
    Path<String, Integer> path =
        romaniaCities.apply(new IterativeDeepeningSearch<String, Integer>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(romaniaCities.path(3L,
                                    romaniaCities.edge("Arad", "Sibiu"),
                                    romaniaCities.edge("Sibiu", "Fagaras"),
                                    romaniaCities.edge("Fagaras", "Bucharest")), path);
  }

  @Test
  void iterativeDeepeningNotFound() {
    Path<String, Integer> path =
        romaniaCities.apply(new IterativeDeepeningSearch<String, Integer>("Arad").goalVertex("Unknown"));
    System.out.println(path);
    assertNull(path);
  }

  @Test
  void depthLimited() {
    Path<String, Integer> path =
        romaniaCities.apply(new DepthLimitedSearch<String, Integer>("Arad", 5).goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(romaniaCities.path(3L,
                                    romaniaCities.edge("Arad", "Sibiu"),
                                    romaniaCities.edge("Sibiu", "Fagaras"),
                                    romaniaCities.edge("Fagaras", "Bucharest")), path);
  }

  @Test
  void depthLimitedNotFound() {
    Path<String, Integer> path =
        romaniaCities.apply(new DepthLimitedSearch<String, Integer>("Arad", 2).goalVertex("Bucharest"));
    System.out.println(path);
    assertNull(path);
  }
}