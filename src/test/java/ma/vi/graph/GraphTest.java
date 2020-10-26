package ma.vi.graph;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static ma.vi.graph.algo.TestGraphs.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class GraphTest {
  @Test
  void directedGraphProperties() {
    assertEquals(Set.of(Edge.of(1, 20, 1),
                        Edge.of(5, 2,  1)), basicGraph1.incoming(1));
    assertEquals(Set.of(Edge.of(1, 20, 1),
                        Edge.of(1, 1,  2),
                        Edge.of(1, 1,  3),
                        Edge.of(1, 1,  4),
                        Edge.of(1, 1,  5)), basicGraph1.outgoing(1));

    assertEquals(Set.of(Edge.of(1, 1,  2)), basicGraph1.incoming(2));
    assertEquals(Collections.emptySet(), basicGraph1.outgoing(2));

    assertEquals(2, basicGraph1.inDegree(1));
    assertEquals(5, basicGraph1.outDegree(1));
    assertEquals(basicGraph1.inDegree(1) + basicGraph1.outDegree(1), basicGraph1.degree(1));

    assertEquals(1, basicGraph1.inDegree(2));
    assertEquals(0, basicGraph1.outDegree(2));
    assertEquals(basicGraph1.inDegree(2) + basicGraph1.outDegree(2), basicGraph1.degree(2));
  }
  
  @Test
  void undirectedGraphProperties() {
    assertEquals(basicGraph2.outgoing("v0"), basicGraph2.incoming("v0"));
    assertEquals(Set.of(Edge.of("v0", 100, "v0"),
                        Edge.of("v0", 4,  "v1"),
                        Edge.of("v0", 8,  "v8")),
                 basicGraph2.incoming("v0"));
    assertEquals(Set.of(Edge.of("v0", 100, "v0"),
                        Edge.of("v0", 4,  "v1"),
                        Edge.of("v0", 8,  "v8")),
                 basicGraph2.outgoing("v0"));

    assertEquals(Set.of(Edge.of(1, 1,  2)), basicGraph1.incoming(2));
    assertEquals(Collections.emptySet(), basicGraph1.outgoing(2));

    assertEquals(basicGraph2.outDegree("v0"), basicGraph2.inDegree("v0"));
    assertEquals(basicGraph2.outDegree("v1"), basicGraph2.inDegree("v1"));
    assertEquals(basicGraph2.outDegree("v2"), basicGraph2.inDegree("v2"));
    assertEquals(basicGraph2.outDegree("v3"), basicGraph2.inDegree("v3"));
    assertEquals(basicGraph2.outDegree("v4"), basicGraph2.inDegree("v4"));
    assertEquals(basicGraph2.outDegree("v5"), basicGraph2.inDegree("v5"));
    assertEquals(basicGraph2.outDegree("v6"), basicGraph2.inDegree("v6"));
    assertEquals(basicGraph2.outDegree("v7"), basicGraph2.inDegree("v7"));
    assertEquals(basicGraph2.outDegree("v8"), basicGraph2.inDegree("v8"));
    assertEquals(basicGraph2.outDegree("v9"), basicGraph2.inDegree("v9"));

    assertEquals(4, basicGraph2.inDegree("v0"));
    assertEquals(4, basicGraph2.outDegree("v0"));
    assertEquals(4, basicGraph2.degree("v0"));

    assertEquals(4, basicGraph2.inDegree("v3"));
    assertEquals(4, basicGraph2.outDegree("v3"));
    assertEquals(4, basicGraph2.degree("v3"));
  }
}