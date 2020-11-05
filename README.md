# Graph data structure and algorithms
A generic graph data structure which can be parameterized with different vertex and edge weight
types and a set of graph algorithms to work on such graphs. Both directed and undirected graphs 
are supported.

## Install
To use through Maven, include the following dependency in your pom.xml file:
 
    <dependency>
      <groupId>com.vikmad</groupId>
      <artifactId>graph</artifactId>
      <version>0.2.2</version>
    </dependency>
    
or in Gradle, add the following line to your dependencies list in your build.gradle file:

    compile "com.vikmad:graph:0.2.2"
    
## Example usage
### Creating graphs

    DirectedGraph<Integer, Integer> g1 = new DirectedGraph<>(
          new VertexMap<Integer, Integer>()
              .add(1, 20, 1)
              .add(1, 1,  2, 3, 4, 5)
              .add(5, 2,  1, 6, 7)
              .add(6, 1,  8)
              .add(6, 4,  9)
              .add(8, 1,  9)
              .add(8, 20, 10)
              .add(9, 2,  10)
              .build());

    UndirectedGraph<String, Integer> g2 = new UndirectedGraph<>(
          new VertexMap<String, Integer>()
              .add("v0", 100, "v0")
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
              .build());

    UndirectedGraph<String, Integer> cities = new UndirectedGraph<>(
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
              .add("Hirsova",        86,  "Eforie")
              .build());
              
### Search

Breadth-first search:

    Path<String, Integer> path =
        cities.apply(new BreadthFirstSearch<String, Integer>("Arad").goalVertex("Bucharest"));


Minimum-cost search:

    Path<String, Integer> path =
        cities.apply(new MinCostSearch<String, Integer>("Arad").goalVertex("Bucharest"));

Several other algorithms such as A* has been implemented; check the javadocs for examples.

### Minimum spanning tree

Creating an MST with Prim's algorithm:

    Graph<String, Integer> tree = graph.apply(new PrimMst<>());
    
Creating an MST with Kruskal's algorithm:

    Graph<String, Integer> tree = graph.apply(new KruskalMst<>());
    
### Connected components and subgraphs

Getting the connected components as a set of vertices:

    List<Set<V>> components = graph.apply(new ConnectedComponents<>());

Getting the connected components as subgraphs:

    List<Graph<String, Integer>> components = graph.apply(new ConnectedGraphs<>());

