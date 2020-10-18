package ma.vi.graph.algo.search;

import ma.vi.base.collections.Maps;
import ma.vi.base.tuple.T2;
import ma.vi.graph.UndirectedEdge;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;
import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class SearchTest {
  static UndirectedGraph<String, Integer> graph = new UndirectedGraph<>(
      new VertexMap<String, Integer>()
        .add("Oradea",          71,   "Zerind")
        .add("Oradea",          151,  "Sibiu")
        .add("Zerind",          75,   "Arad")
        .add("Arad",            140,  "Sibiu")
        .add("Arad",            118,  "Timisoara")
        .add("Timisoara",       111,  "Lugoj")
        .add("Lugoj",           70,   "Mehadia")
        .add("Mehadia",         75,   "Drobeta")
        .add("Drobeta",         120,  "Craiova")
        .add("Craiova",         146,  "Rimnicu Vilcea")
        .add("Craiova",         138,  "Pitesti")
        .add("Rimnicu Vilcea",  97,   "Pitesti")
        .add("Rimnicu Vilcea",  80,   "Sibiu")
        .add("Sibiu",           99,   "Fagaras")
        .add("Fagaras",         211,  "Bucharest")
        .add("Pitesti",         101,  "Bucharest")
        .add("Bucharest",       90,   "Giurgiu")
        .add("Bucharest",       85,   "Urziceni")
        .add("Urziceni",        98,   "Hirsova")
        .add("Urziceni",        142,  "Vaslui")
        .add("Vaslui",          92,   "Iasi")
        .add("Iasi",            87,   "Neamt")
        .add("Hirsova",         86,   "Eforie")
  );

  static Map<String, Integer> distanceToBucharest = Maps.of(
      T2.of("Arad", 366),
      T2.of("Bucharest", 0),
      T2.of("Craiova", 160),
      T2.of("Drobeta", 242),
      T2.of("Eforie", 161),
      T2.of("Fagaras", 176),
      T2.of("Giurgiu", 77),
      T2.of("Hirsova", 151),
      T2.of("Iasi", 226),
      T2.of("Lugoj", 244),
      T2.of("Mehadia", 241),
      T2.of("Neamt", 234),
      T2.of("Oradea", 380),
      T2.of("Pitesti", 100),
      T2.of("Rimnicu Vilcea", 193),
      T2.of("Sibiu", 253),
      T2.of("Timisoara", 329),
      T2.of("Urziceni", 80),
      T2.of("Vaslui", 199),
      T2.of("Zerind", 374)
  );

  @BeforeAll
  static void print() {
    System.out.println(graph.toGraphViz());
  }

  @Test
  void breadthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new BreadthFirstSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));

    System.out.println(path);
    assertEquals(path, graph.path( 450L, "Arad", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void depthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new DepthFirstSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, graph.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void minCost() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new MinCostSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, graph.path( 418L, "Arad", "Sibiu", "Rimnicu Vilcea", "Pitesti", "Bucharest"));
  }

  @Test
  void aStar() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new AStarSearch<>(
          "Arad",
          "Bucharest",
          (v1, v2) -> distanceToBucharest.get(v1)
        ));
    System.out.println(path);
    assertEquals(path, graph.path( 418L, "Arad", "Sibiu", "Rimnicu Vilcea", "Pitesti", "Bucharest"));
  }

  @Test
  void iterativeDeepening() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new IterativeDeepeningSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, graph.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void iterativeDeepeningNotFound() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new IterativeDeepeningSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad").goalVertex("Unknown"));
    System.out.println(path);
    assertNull(path);
  }

  @Test
  void depthLimited() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new DepthLimitedSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad", 5).goalVertex("Bucharest"));
    System.out.println(path);
    assertEquals(path, graph.path( 607L, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void depthLimitedNotFound() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path =
        graph.apply(new DepthLimitedSearch<String, Integer, UndirectedEdge<String, Integer>>("Arad", 4).goalVertex("Bucharest"));
    System.out.println(path);
    assertNull(path);
  }
}