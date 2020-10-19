package ma.vi.graph.algo.explore;

import ma.vi.graph.DirectedEdge;
import ma.vi.graph.algo.TestGraphs;
import ma.vi.graph.path.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ExploreTest {
  @BeforeAll
  static void print() {
    System.out.println(TestGraphs.basicGraph1.toGraphViz());
  }

  @Test
  void breadthFirst() {
    List<Integer> acc = new ArrayList<>();
    TestGraphs.basicGraph1.apply(
        new BreadthFirstExplore<Integer, Integer, DirectedEdge<Integer, Integer>, Void>(1)
          .exploreOp(
              (g, p) -> {
                System.out.println(p);
                acc.add(p.lastVertex().orElse(0));
                return Optional.empty();
              }
          )
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
    TestGraphs.basicGraph1.apply(
        new DepthFirstExplore<Integer, Integer, DirectedEdge<Integer, Integer>, Void>(1)
            .exploreOp(
                (g, p) -> {
                  System.out.println(p);
                  acc.add(p);
                  return Optional.empty();
                }
            )
    );
    System.out.println(acc);
    System.out.println();
    Set<Path<Integer, Integer, DirectedEdge<Integer, Integer>>> exp = new HashSet<>(asList(
        TestGraphs.basicGraph1.path(1L, 0),
        TestGraphs.basicGraph1.path(1L, 1, 5),
        TestGraphs.basicGraph1.path(3L, 1, 5, 7),
        TestGraphs.basicGraph1.path(3L, 1, 5, 6),
        TestGraphs.basicGraph1.path(7L, 1, 5, 6, 9),
        TestGraphs.basicGraph1.path(9L, 1, 5, 6, 9, 10),
        TestGraphs.basicGraph1.path(4L, 1, 5, 6, 8),
        TestGraphs.basicGraph1.path(1L, 1, 4),
        TestGraphs.basicGraph1.path(1L, 1, 3),
        TestGraphs.basicGraph1.path(1L, 1, 2)));

    assertEquals(acc, exp);
  }

  @Test
  void minCostFirst() {
    List<Integer> acc = new ArrayList<>();
    TestGraphs.basicGraph1.apply(
        new MinCostExplore<Integer, Integer, DirectedEdge<Integer, Integer>, Void>(1)
            .exploreOp(
                (g, p) -> {
                  System.out.println(p);
                  acc.add(p.lastVertex().orElse(0));
                  return Optional.empty();
                }
            )
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
