package ma.vi.graph.explore;

import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ma.vi.graph.*;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ExploreTest {
  static DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
      new VertexMap<Integer, Integer>()
          .add(1, 1,  2, 3, 4, 5)
          .add(5, 2,  6, 7)
          .add(6, 1,  8)
          .add(6, 4,  9)
          .add(8, 1,  9)
          .add(8, 20, 10)
          .add(9, 2,  10));

  @BeforeAll
  static void print() {
    System.out.println(graph.toGraphViz());
  }

  @Test
  void breadthFirst() {
    List<Integer> acc = new ArrayList<>();
    Explore.breadthFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p.end().orElse(0));
        return Optional.empty();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    List<Integer> exp = asList(1, 4, 2, 5, 3, 6, 7, 8, 9, 10);
    assertEquals(new HashSet<>(acc.subList(0, 1)),
                 new HashSet<>(exp.subList(0, 1)));
    assertEquals(new HashSet<>(acc.subList(1, 5)),
                 new HashSet<>(exp.subList(1, 5)));
    assertEquals(new HashSet<>(acc.subList(5, 7)),
                 new HashSet<>(exp.subList(5, 7)));
    assertEquals(new HashSet<>(acc.subList(7, 9)),
                 new HashSet<>(exp.subList(7, 9)));
    assertEquals(new HashSet<>(acc.subList(9, 10)),
                 new HashSet<>(exp.subList(9, 10)));
  }

  @Test
  void depthFirst() {
    Set<Path<Integer, Integer, DirectedEdge<Integer, Integer>>> acc = new HashSet<>();
    Explore.depthFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p);
        return Optional.empty();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    Set<Path<Integer, Integer, DirectedEdge<Integer, Integer>>> exp = new HashSet<>(asList(
        graph.path(1, 0),
        graph.path( 1,1, 5),
        graph.path( 3,1, 5, 7),
        graph.path( 3,1, 5, 6),
        graph.path( 7,1, 5, 6, 9),
        graph.path( 9,1, 5, 6, 9, 10),
        graph.path( 4,1, 5, 6, 8),
        graph.path( 1,1, 4),
        graph.path( 1,1, 3),
        graph.path( 1,1, 2)));

    assertEquals(acc, exp);
  }

  @Test
  void minCostFirst() {
    List<Integer> acc = new ArrayList<>();
    Explore.minCostFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p.end().orElse(0));
        return Optional.empty();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    List<Integer> exp = asList(1, 4, 2, 5, 3, 6, 7, 8, 9, 10);
    assertEquals(new HashSet<>(acc.subList(0, 1)),
                 new HashSet<>(exp.subList(0, 1)));
    assertEquals(new HashSet<>(acc.subList(1, 5)),
                 new HashSet<>(exp.subList(1, 5)));
    assertEquals(new HashSet<>(acc.subList(5, 7)),
                 new HashSet<>(exp.subList(5, 7)));
    assertEquals(new HashSet<>(acc.subList(7, 8)),
                 new HashSet<>(exp.subList(7, 8)));
    assertEquals(new HashSet<>(acc.subList(8, 9)),
                 new HashSet<>(exp.subList(8, 9)));
    assertEquals(new HashSet<>(acc.subList(9, 10)),
                 new HashSet<>(exp.subList(9, 10)));
  }
}
