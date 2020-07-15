import java.util.ArrayList;

public class Testing{
    public static void main(String[] args){
        String[] vertices = {"a", "b", "c"};
        String[][] edges = {{"a", "b"}, {"a", "c"}};
        ArrayList<String> vertices_list = new ArrayList<>();
        ArrayList<String[]> edges_list = new ArrayList<>();
        for (int i = 0; i < vertices.length; i++) {
            vertices_list.add(vertices[i]);
        }
        for (int i = 0; i < edges.length; i++) {
            edges_list.add(edges[i]);
        }
        Graph graph = new Graph(vertices_list, edges_list);
        graph.show();

        Graph graph2 = graph.clone();
        graph2.show();

        ArrayList<String[]> new_edges = new ArrayList<>();
        String[] new_edge = {"b", "c"};
        new_edges.add(new_edge);
        graph2.set_edges(new_edges);
        graph2.show();
        graph.show();
    }
}