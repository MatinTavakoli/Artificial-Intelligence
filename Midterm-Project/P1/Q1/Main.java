import java.util.Scanner;
public class Main{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int cube_sides_colors[][] = new int[6][4];

        int delay = 1000;//delay used to watch the algorithm step by step

        wait(delay);

        System.out.println("Welcome to the 2*2*2 Rubik's Cube Solver!");

        wait(delay);

        System.out.println("Please enter your 2*2*2 Rubik's Cube's Colors!");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                cube_sides_colors[i][j] = scanner.nextInt();
            }
        }
        Cube cube = new Cube(cube_sides_colors);
        wait(delay);
        System.out.println("Your cube is: ");
        wait(delay);
        cube.printCube();

        wait(delay);

        String mode = "";
        for (int i = 0; i < 1; i++) {
            System.out.println("Select 1 for IDS Search, 2 for Bidirectional Search, or 3 for UCS Search");
            mode = scanner.next();
            if(mode.equals("1") == false && mode.equals("2") == false && mode.equals("3") == false){
                System.out.println("Please try again!");
                i--;
            }
        }
        int mode_int = Integer.valueOf(mode);


        switch(mode_int){
            case 1:
                System.out.println("Plese enter initial depth: ");
                int depth = scanner.nextInt();
                IDS_Search ids_search = new IDS_Search(cube, depth);
                String result = ids_search.simulate();
                System.out.println("result is: " + result);
                break;
            case 2:
                Bidirectional_Search bidirectional_search = new Bidirectional_Search(cube);
                bidirectional_search.simulate();
                System.out.println("start explored set is: ");
                for (int j = 0; j < bidirectional_search.get_start_explored_set().size(); j++) {
                    System.out.println(bidirectional_search.get_start_explored_set().get(j));
                }
                System.out.println("goal explored set is: ");
                for (int j = 0; j < bidirectional_search.get_goal_explored_set().size(); j++) {
                    System.out.println(bidirectional_search.get_goal_explored_set().get(j));
                }
                int created_nodes = bidirectional_search.get_start_frontier().size() + bidirectional_search.get_goal_frontier().size() + bidirectional_search.get_start_explored_set().size() + bidirectional_search.get_goal_explored_set().size();
                System.out.println("created nodes(frontiers + explored set) size is: " + created_nodes);
                System.out.println("expanded nodes(explored set) size is: " + (bidirectional_search.get_start_explored_set().size() +bidirectional_search.get_goal_explored_set().size()));
                System.out.println("depth is: " + bidirectional_search.get_depth());
                break;
            case 3:
                UCS_Search ucs_search = new UCS_Search(cube);
                result = ucs_search.simulate();
                System.out.println("explored set is: ");
                for (int j = 0; j < ucs_search.get_explored_set().size(); j++) {
                    System.out.println(ucs_search.get_explored_set().get(j));
                }
                created_nodes = ucs_search.get_frontier().size() + ucs_search.get_explored_set().size();
                System.out.println("created nodes(frontier + explored set) size is: " + created_nodes);
                System.out.println("expanded nodes(explored set) size is: " + ucs_search.get_explored_set().size());
                System.out.println("depth is: " + ucs_search.get_depth());
                System.out.println("result is: " + result);
                break;
        }
    }

    //initiates some delay so the user can see what's happening!
    public static void wait(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}