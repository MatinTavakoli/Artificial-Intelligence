import java.util.Arrays;

public class Testing{
    public static void main(String[] args){
        int[][] colors = {{1,1,1,1}, {2,2,2,2}, {3,3,3,3}, {4,4,4,4}, {5,5,5,5}, {6,6,6,6}};
        Cube cube = new Cube(colors);

        cube.rotate(1,0);
        cube.rotate(3,1);
        cube.rotate(4,1);
        cube.rotate(3,1);
        cube.rotate(2,1);
        cube.rotate(4,0);
        cube.printCube();
    }
}