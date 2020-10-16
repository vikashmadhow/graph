package ma.vi.graph.algo.explore;

import ma.vi.graph.UndirectedEdge;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;
import ma.vi.graph.algo.search.Search;
import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @BeforeAll
  static void print() {
    System.out.println(graph.toGraphViz());
  }

  @Test
  void breadthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path = Search.breadthFirst(graph, "Arad", "Bucharest");
    System.out.println(path);
    assertEquals(path, graph.path( 450, "Arad", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void depthFirst() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path = Search.depthFirst(graph, "Arad", "Bucharest");
    System.out.println(path);
    assertEquals(path, graph.path( 607, "Arad", "Zerind", "Oradea", "Sibiu", "Fagaras", "Bucharest"));
  }

  @Test
  void minCost() {
    Path<String, Integer, UndirectedEdge<String, Integer>> path = Search.minCost(graph, "Arad", "Bucharest");
    System.out.println(path);
    assertEquals(path, graph.path( 418, "Arad", "Sibiu", "Rimnicu Vilcea", "Pitesti", "Bucharest"));
  }
}