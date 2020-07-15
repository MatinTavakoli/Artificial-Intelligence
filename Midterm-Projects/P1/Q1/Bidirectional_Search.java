import java.util.ArrayList;

public class Bidirectional_Search{
    private Cube start_cube;
    private Cube goal_cube;
    private ArrayList<String> start_frontier;
    private ArrayList<String> goal_frontier;
    private ArrayList<String> start_explored_set;
    private ArrayList<String> goal_explored_set;
    private int depth;
    public Bidirectional_Search(Cube start_cube){
        this.start_cube = start_cube;
        int [][]goal_sides = {{1,1,1,1}, {2,2,2,2}, {3,3,3,3}, {4,4,4,4}, {5,5,5,5} ,{6,6,6,6}};
        goal_cube = new Cube(goal_sides);
        start_frontier = new ArrayList<String>();
        goal_frontier = new ArrayList<String>();
        start_explored_set = new ArrayList<String>();
        goal_explored_set = new ArrayList<String>();
    }

    public ArrayList<String> get_start_frontier(){
        return start_frontier;
    }

    public ArrayList<String> get_goal_frontier(){
        return goal_frontier;
    }

    public ArrayList<String> get_start_explored_set(){
        return start_explored_set;
    }

    public ArrayList<String> get_goal_explored_set(){
        return goal_explored_set;
    }

    public int get_depth(){
        return depth;
    }

    public String simulate(){
        System.out.println("Simulating Bidirectional Search...");
        start_frontier.add(start_cube.state_to_string_converter());
        goal_frontier.add(goal_cube.state_to_string_converter());
        while(!start_frontier.isEmpty() && !goal_frontier.isEmpty()){
            if(!start_frontier.isEmpty()){
                String new_cube_string = start_frontier.remove(0);//dequeue
                //Cube reconstruction
                int new_sides[][] = new int[6][4];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        new_sides[i][j] = Integer.valueOf(new_cube_string.charAt(4 * i + j)) - 48;
                    }
                }
                Cube new_cube = new Cube(new_sides);
                System.out.println(goal_frontier.contains(new_cube.state_to_string_converter()));//checking
                if(goal_frontier.contains(new_cube.state_to_string_converter())){//this state is already reached from the other side!
                    System.out.println("goal frontier has state: " + new_cube.state_to_string_converter());
                    depth = calculate_depth(start_explored_set) + calculate_depth(goal_explored_set);
                    return "success";
                }
                ArrayList<String> moves = new ArrayList<>();
                start_explored_set.add(new_cube.state_to_string_converter());//adding the expanded node
                for (int i = 0; i < 12; i++) {
                    String code = new_cube.generate_move();
                    if(!moves.contains(code)){//this move is new and has not been tested yet
                        moves.add(code);//adding move to the list
                        System.out.println("code is " + code);
                        Cube child_cube = (Cube)new_cube.clone();
                        child_cube.rotate(Integer.valueOf(code.charAt(0)) - 48, Integer.valueOf(code.charAt(1) - 48));//because of ASCII, we subtract it from 48
                        if(!start_explored_set.contains(child_cube.state_to_string_converter())){
                            //wait(1000);
                            System.out.println("new move!");
                            //wait(1000);
                            start_frontier.add(child_cube.state_to_string_converter());
                        }
                        else
                            continue;
                        
                    }
                    else
                        i--;//try another different move
                    
                }
            }
            if(!goal_frontier.isEmpty()){
                String new_cube_string = goal_frontier.remove(0);//dequeue
                //Cube reconstruction
                int new_sides[][] = new int[6][4];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        new_sides[i][j] = Integer.valueOf(new_cube_string.charAt(4 * i + j)) - 48;
                    }
                }
                Cube new_cube = new Cube(new_sides);
                System.out.println(start_frontier.contains(new_cube.state_to_string_converter()));//checking
                if(start_frontier.contains(new_cube.state_to_string_converter())){//this state is already reached from the other side!
                    System.out.println("start frontier has state: " + new_cube.state_to_string_converter());
                    depth = calculate_depth(start_explored_set) + calculate_depth(goal_explored_set);
                    return "success";
                }
                ArrayList<String> moves = new ArrayList<>();
                goal_explored_set.add(new_cube.state_to_string_converter());//adding the expanded node
                for (int i = 0; i < 12; i++) {
                    String code = new_cube.generate_move();
                    if(!moves.contains(code)){//this move is new and has not been tested yet
                        moves.add(code);//adding move to the list
                        System.out.println("code is " + code);
                        Cube child_cube = (Cube)new_cube.clone();
                        child_cube.rotate(Integer.valueOf(code.charAt(0)) - 48, Integer.valueOf(code.charAt(1) - 48));//because of ASCII, we subtract it from 48
                        if(!goal_explored_set.contains(child_cube.state_to_string_converter())){
                            //wait(1000);
                            System.out.println("new move!");
                            //wait(1000);
                            goal_frontier.add(child_cube.state_to_string_converter());
                        }
                        else
                            continue;
                        
                    }
                    else
                        i--;//try another different move
                    
                }
            }
        }
        return "failure";//in case our search fails!
    }

    private int calculate_depth(ArrayList<String> explored_set){//this method is accessed only from within the class!
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