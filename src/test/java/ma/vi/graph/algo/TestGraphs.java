package ma.vi.graph.algo;

import ma.vi.base.collections.Maps;
import ma.vi.base.tuple.T2;
import ma.vi.graph.DirectedGraph;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;

import java.util.Map;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class TestGraphs {
  public static DirectedGraph<Integer, Integer> basicGraph1 = new DirectedGraph<>(
      new VertexMap<Integer, Integer>()
          .add(1, 1,  2, 3, 4, 5)
          .add(5, 2,  6, 7)
          .add(6, 1,  8)
          .add(6, 4,  9)
          .add(8, 1,  9)
          .add(8, 20, 10)
          .add(9, 2,  10));

  public static UndirectedGraph<String, Integer> basicGraph2 = new UndirectedGraph<>(
      new VertexMap<String, Integer>()
          .add("v0", 4,   "v1")
          .add("v0", 8,   "v8")
          .add("v1", 8,   "v3")
          .add("v1", 11,  "v8")
          .add("v2", 2,   "v3")
          .add("v2", 7,   "v8")
          .add("v2", 6,   "v7")
          .add("v3", 7,   "v4")
          .add("v3", 4,   "v6")
          .add("v4", 9,   "v5")
          .add("v4", 14,  "v6")
          .add("v6", 2,   "v7")
          .add("v7", 1,   "v8")
  );

  public static UndirectedGraph<String, Integer> romaniaCities = new UndirectedGraph<>(
      new VertexMap<String, Integer>()
          .add("Oradea",         71,  "Zerind")
          .add("Oradea",         151, "Sibiu")
          .add("Zerind",         75,  "Arad")
          .add("Arad",           140, "Sibiu")
          .add("Arad",           118, "Timisoara")
          .add("Timisoara",      111, "Lugoj")
          .add("Lugoj",          70,  "Mehadia")
          .add("Mehadia",        75,  "Drobeta")
          .add("Drobeta",        120, "Craiova")
          .add("Craiova",        146, "Rimnicu Vilcea")
          .add("Craiova",        138, "Pitesti")
          .add("Rimnicu Vilcea", 97,  "Pitesti")
          .add("Rimnicu Vilcea", 80,  "Sibiu")
          .add("Sibiu",          99,  "Fagaras")
          .add("Fagaras",        211, "Bucharest")
          .add("Pitesti",        101, "Bucharest")
          .add("Bucharest",      90,  "Giurgiu")
          .add("Bucharest",      85,  "Urziceni")
          .add("Urziceni",       98,  "Hirsova")
          .add("Urziceni",       142, "Vaslui")
          .add("Vaslui",         92,  "Iasi")
          .add("Iasi",           87,  "Neamt")
          .add("Hirsova",        86,  "Eforie"));

  public static Map<String, Integer> distanceToBucharest = Maps.of(
      T2.of("Arad",           366),
      T2.of("Bucharest",      0),
      T2.of("Craiova",        160),
      T2.of("Drobeta",        242),
      T2.of("Eforie",         161),
      T2.of("Fagaras",        176),
      T2.of("Giurgiu",        77),
      T2.of("Hirsova",        151),
      T2.of("Iasi",           226),
      T2.of("Lugoj",          244),
      T2.of("Mehadia",        241),
      T2.of("Neamt",          234),
      T2.of("Oradea",         380),
      T2.of("Pitesti",        100),
      T2.of("Rimnicu Vilcea", 193),
      T2.of("Sibiu",          253),
      T2.of("Timisoara",      329),
      T2.of("Urziceni",       80),
      T2.of("Vaslui",         199),
      T2.of("Zerind",         374));
}
