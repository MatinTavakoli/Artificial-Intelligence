import java.util.ArrayList;
import java.lang.Math;

public class UCS_Search{
    private Cube cube;
    private ArrayList<String> frontier;
    private ArrayList<String> explored_set;
    private int depth;
    public UCS_Search(Cube cube){
        this.cube = cube;
        frontier = new ArrayList<>();
        frontier.add(cube.state_to_string_converter());
        explored_set = new ArrayList<>();
        depth = 0;
    }

    public ArrayList<String> get_frontier(){
        return frontier;
    }

    public ArrayList<String> get_explored_set(){
        return explored_set;
    }

    public int get_depth(){
        return depth;
    }

    public String simulate(){
        System.out.println("Simulating UCS Search...");
        while(!frontier.isEmpty()){
            //Cube reconstruction
            String cube_string = frontier.remove(0);//dequeue
            int sides[][] = new int[6][4];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    sides[i][j] = Integer.valueOf(cube_string.charAt(4 * i + j)) - 48;
                }
            }
            cube = new Cube(sides);
            if(cube.goal_test()){
                System.out.println("cube solved!");
                depth = calculate_depth();
                return cube.state_to_string_converter();
            }
            else{
                explored_set.add(cube.state_to_string_converter());
                ArrayList<String> moves = new ArrayList<>();
                for (int i = 0; i < 6; i++) {//branching factor is 6
                    String code = cube.generate_move();
                    if(code.charAt(1) == '1'){
                        code = cube.twin_code(code);//we only use clockwise rotations
                    }
                    if(!moves.contains(code)){//this move is new and has not been tested yet
                        moves.add(code);//adding move to the list
                        System.out.println("code is " + code);
                        Cube child_cube = cube.clone();
                        child_cube.rotate(Integer.valueOf(code.charAt(0)) - 48, Integer.valueOf(code.charAt(1) - 48));//because of ASCII, we subtract it from 48
                        if(!explored_set.contains(child_cube.state_to_string_converter()) && !frontier.contains(child_cube.state_to_string_converter())){
                            //wait(1000);
                            System.out.println("new move!");
                            //wait(1000);
                            frontier.add(child_cube.state_to_string_converter());
                        }
                        else
                            continue;
                        
                    }
                    else
                        i--;//try another different move
                    
                }
            }
        }
        return "failure";
    }

    private int calculate_depth(){//this method is accessed only from within the class!
        int d = 0;
        int e = explored_set.size();
        int sum = 1;
        while(e > sum){
            d++;
            sum += Math.pow(6, d);
        }
        return d;
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