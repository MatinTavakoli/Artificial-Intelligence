import java.util.Random;
import java.util.Arrays;

public class Cube implements Cloneable{//we need to be able to clone cubes for their children!
    private int sides_colors[][];

    public Cube(){

    }


    public Cube(int sides_colors[][]){
        this.sides_colors = sides_colors;
    }

    public void rotate(int side, int direction){
        String code = String.valueOf(side) + String.valueOf(direction);
        switch(code){
            case "10":
                this.rotate_front_clockwise();
                System.out.println("cube front side (1) rotated clockwise");
                break;
            case "11":
                this.rotate_front_counter_clockwise();
                System.out.println("cube front side (1) rotated counter clockwise");
                break;
            case "20":
                this.rotate_right_clockwise();
                System.out.println("cube right side (2) rotated clockwise");
                break;
            case "21":
                this.rotate_right_counter_clockwise();
                System.out.println("cube right side (2) rotated counter clockwise");
                break;
            case "30":
                this.rotate_down_clockwise();
                System.out.println("cube down side (3) rotated clockwise");
                break;
            case "31":
                this.rotate_down_counter_clockwise();
                System.out.println("cube down side (3) rotated counter clockwise");
                break;
            case "40":
                this.rotate_left_clockwise();
                System.out.println("cube left side (4) rotated clockwise");
                break;
            case "41":
                this.rotate_left_counter_clockwise();
                System.out.println("cube left side (4) rotated counter clockwise");
                break;
            case "50":
                this.rotate_back_clockwise();
                System.out.println("cube back side (5) rotated clockwise");
                break;
            case "51":
                this.rotate_back_counter_clockwise();
                System.out.println("cube back side (5) rotated counter clockwise");
                break;
            case "60":
                this.rotate_up_clockwise();
                System.out.println("cube up side (6) rotated clockwise");
                break;
            case "61":
                this.rotate_up_counter_clockwise();
                System.out.println("cube up side (6) rotated counter clockwise");
                break;
            default:
                System.out.println("fucked up!");
                break;
        }
    }

    public String twin_code(String code){
        switch(code){
            case "10":
                return "51";
            case "11":
                return "50";
            case "20":
                return "41";
            case "21":
                return "40";
            case "30":
                return "61";
            case "31":
                return "60";
            case "40":
                return "21";
            case "41":
                return "20";
            case "50":
                return "11";
            case "51":
                return "10";
            case "60":
                return "31";
            case "61":
                return "30";
            default:
                return "";
        }
    }


    //done
    private void rotate_front_clockwise(){
        int temp;
        //front side rotation
        temp = sides_colors[0][0];

        sides_colors[0][0] = sides_colors[0][2];
        sides_colors[0][2] = sides_colors[0][3];
        sides_colors[0][3] = sides_colors[0][1];
        sides_colors[0][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[1][0];
        int temp2 = sides_colors[1][1];

        sides_colors[1][0] = sides_colors[5][3];
        sides_colors[1][1] = sides_colors[5][2];

        sides_colors[5][3] = sides_colors[3][0];
        sides_colors[5][2] = sides_colors[3][1];

        sides_colors[3][0] = sides_colors[2][0];
        sides_colors[3][1] = sides_colors[2][1];

        sides_colors[2][0] = temp1;
        sides_colors[2][1] = temp2;
    }

    //done
    private void rotate_front_counter_clockwise(){
        int temp;
        //front side rotation
        temp = sides_colors[0][0];

        sides_colors[0][0] = sides_colors[0][1];
        sides_colors[0][1] = sides_colors[0][3];
        sides_colors[0][3] = sides_colors[0][2];
        sides_colors[0][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[1][0];
        int temp2 = sides_colors[1][1];

        sides_colors[1][0] = sides_colors[2][0];
        sides_colors[1][1] = sides_colors[2][1];

        sides_colors[2][0] = sides_colors[3][0];
        sides_colors[2][1] = sides_colors[3][1];

        sides_colors[3][0] = sides_colors[5][3];
        sides_colors[3][1] = sides_colors[5][2];

        sides_colors[5][3] = temp1;
        sides_colors[5][2] = temp2;
    }

    //done
    private void rotate_right_clockwise(){
        int temp;
        //left side rotation
        temp = sides_colors[1][0];

        sides_colors[1][0] = sides_colors[1][2];
        sides_colors[1][2] = sides_colors[1][3];
        sides_colors[1][3] = sides_colors[1][1];
        sides_colors[1][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][2];
        int temp2 = sides_colors[0][0];

        sides_colors[0][2] = sides_colors[2][2];
        sides_colors[0][0] = sides_colors[2][0];

        sides_colors[2][2] = sides_colors[4][2];
        sides_colors[2][0] = sides_colors[4][0];

        sides_colors[4][2] = sides_colors[5][2];
        sides_colors[4][0] = sides_colors[5][0];

        sides_colors[5][2] = temp1;
        sides_colors[5][0] = temp2;
    }

    //done
    private void rotate_right_counter_clockwise(){
        int temp;
        //left side rotation
        temp = sides_colors[1][0];

        sides_colors[1][0] = sides_colors[1][1];
        sides_colors[1][1] = sides_colors[1][3];
        sides_colors[1][3] = sides_colors[1][2];
        sides_colors[1][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][2];
        int temp2 = sides_colors[0][0];

        sides_colors[0][2] = sides_colors[5][2];
        sides_colors[0][0] = sides_colors[5][0];

        sides_colors[5][2] = sides_colors[4][2];
        sides_colors[5][0] = sides_colors[4][0];

        sides_colors[4][2] = sides_colors[2][2];
        sides_colors[4][0] = sides_colors[2][0];

        sides_colors[2][2] = temp1;
        sides_colors[2][0] = temp2;
    }

    //done
    private void rotate_down_clockwise(){
        int temp;
        //down side rotation
        temp = sides_colors[2][0];

        sides_colors[2][0] = sides_colors[2][2];
        sides_colors[2][2] = sides_colors[2][3];
        sides_colors[2][3] = sides_colors[2][1];
        sides_colors[2][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][2];
        int temp2 = sides_colors[0][3];

        sides_colors[0][2] = sides_colors[3][0];
        sides_colors[0][3] = sides_colors[3][2];

        sides_colors[3][0] = sides_colors[4][1];
        sides_colors[3][2] = sides_colors[4][0];

        sides_colors[4][1] = sides_colors[1][3];
        sides_colors[4][0] = sides_colors[1][1];

        sides_colors[1][3] = temp1;
        sides_colors[1][1] = temp2;
    }

    //done
    private void rotate_down_counter_clockwise(){
        int temp;
        //down side rotation
        temp = sides_colors[2][0];

        sides_colors[2][0] = sides_colors[2][1];
        sides_colors[2][1] = sides_colors[2][3];
        sides_colors[2][3] = sides_colors[2][2];
        sides_colors[2][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][2];
        int temp2 = sides_colors[0][3];

        sides_colors[0][2] = sides_colors[1][3];
        sides_colors[0][3] = sides_colors[1][1];

        sides_colors[1][3] = sides_colors[4][1];
        sides_colors[1][1] = sides_colors[4][0];

        sides_colors[4][1] = sides_colors[3][0];
        sides_colors[4][0] = sides_colors[3][2];

        sides_colors[3][0] = temp1;
        sides_colors[3][2] = temp2;
    }

    //done
    private void rotate_left_clockwise(){
        int temp;
        //right side rotation
        temp = sides_colors[3][0];

        sides_colors[3][0] = sides_colors[3][2];
        sides_colors[3][2] = sides_colors[3][3];
        sides_colors[3][3] = sides_colors[3][1];
        sides_colors[3][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][1];
        int temp2 = sides_colors[0][3];

        sides_colors[0][1] = sides_colors[5][1];
        sides_colors[0][3] = sides_colors[5][3];

        sides_colors[5][1] = sides_colors[4][1];
        sides_colors[5][3] = sides_colors[4][3];

        sides_colors[4][1] = sides_colors[2][1];
        sides_colors[4][3] = sides_colors[2][3];

        sides_colors[2][1] = temp1;
        sides_colors[2][3] = temp2;
    }

    //done
    private void rotate_left_counter_clockwise(){
        int temp;
        //right side rotation
        temp = sides_colors[3][0];

        sides_colors[3][0] = sides_colors[3][1];
        sides_colors[3][1] = sides_colors[3][3];
        sides_colors[3][3] = sides_colors[3][2];
        sides_colors[3][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][1];
        int temp2 = sides_colors[0][3];

        sides_colors[0][1] = sides_colors[2][1];
        sides_colors[0][3] = sides_colors[2][3];

        sides_colors[2][1] = sides_colors[4][1];
        sides_colors[2][3] = sides_colors[4][3];

        sides_colors[4][1] = sides_colors[5][1];
        sides_colors[4][3] = sides_colors[5][3];

        sides_colors[5][1] = temp1;
        sides_colors[5][3] = temp2;
    }

    //done
    private void rotate_back_clockwise(){
        int temp;
        //back side rotation
        temp = sides_colors[4][0];

        sides_colors[4][0] = sides_colors[4][2];
        sides_colors[4][2] = sides_colors[4][3];
        sides_colors[4][3] = sides_colors[4][1];
        sides_colors[4][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[5][0];
        int temp2 = sides_colors[5][1];

        sides_colors[5][0] = sides_colors[3][3];
        sides_colors[5][1] = sides_colors[3][2];

        sides_colors[3][3] = sides_colors[2][3];
        sides_colors[3][2] = sides_colors[2][2];

        sides_colors[2][3] = sides_colors[1][3];
        sides_colors[2][2] = sides_colors[1][2];

        sides_colors[1][3] = temp1;
        sides_colors[1][2] = temp2;
    }

    //done
    private void rotate_back_counter_clockwise(){
        int temp;
        //back side rotation
        temp = sides_colors[4][0];

        sides_colors[4][0] = sides_colors[4][1];
        sides_colors[4][1] = sides_colors[4][3];
        sides_colors[4][3] = sides_colors[4][2];
        sides_colors[4][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[5][0];
        int temp2 = sides_colors[5][1];

        sides_colors[5][0] = sides_colors[1][3];
        sides_colors[5][1] = sides_colors[1][2];

        sides_colors[1][3] = sides_colors[2][3];
        sides_colors[1][2] = sides_colors[2][2];

        sides_colors[2][3] = sides_colors[3][3];
        sides_colors[2][2] = sides_colors[3][2];

        sides_colors[3][3] = temp1;
        sides_colors[3][2] = temp2;
    }

    //done
    private void rotate_up_clockwise(){
        int temp;
        //up side rotation
        temp = sides_colors[5][0];

        sides_colors[5][0] = sides_colors[5][2];
        sides_colors[5][2] = sides_colors[5][3];
        sides_colors[5][3] = sides_colors[5][1];
        sides_colors[5][1] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][0];
        int temp2 = sides_colors[0][1];

        sides_colors[0][0] = sides_colors[1][2];
        sides_colors[0][1] = sides_colors[1][0];

        sides_colors[1][2] = sides_colors[4][3];
        sides_colors[1][0] = sides_colors[4][2];

        sides_colors[4][3] = sides_colors[3][1];
        sides_colors[4][2] = sides_colors[3][3];

        sides_colors[3][1] = temp1;
        sides_colors[3][3] = temp2;
    }

    //done
    private void rotate_up_counter_clockwise(){
        int temp;
        //up side rotation
        temp = sides_colors[5][0];

        sides_colors[5][0] = sides_colors[5][1];
        sides_colors[5][1] = sides_colors[5][3];
        sides_colors[5][3] = sides_colors[5][2];
        sides_colors[5][2] = temp;

        //other sides rotation
        int temp1 = sides_colors[0][0];
        int temp2 = sides_colors[0][1];

        sides_colors[0][0] = sides_colors[3][1];
        sides_colors[0][1] = sides_colors[3][3];

        sides_colors[3][1] = sides_colors[4][3];
        sides_colors[3][3] = sides_colors[4][2];

        sides_colors[4][3] = sides_colors[1][2];
        sides_colors[4][2] = sides_colors[1][0];

        sides_colors[1][2] = temp1;
        sides_colors[1][0] = temp2;
    }

    public String state_to_string_converter(){
        String state_string = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                state_string += String.valueOf(sides_colors[i][j]);
            }
        }
        return state_string;

    }

    public String generate_move(){
        int rand1 = (Math.abs(new Random().nextInt())) % 6 + 1;
        int rand2 = (Math.abs(new Random().nextInt())) % 2;
        return String.valueOf(rand1) + String.valueOf(rand2);
    }

    public String reverse_rotate(String code){
        return code.charAt(0) + String.valueOf(1 - Integer.valueOf(code.charAt(1)));//if rotate is [0-5]0, then reverse_rotate is [0-5]1. otherwise, reverse_rotate is [0-5]0.
    }

    public int heuristic(){
        int h = 0;
        int dif_color;
        for (int i = 0; i < 6; i++) {
            dif_color = 0;
            for (int color = 1; color <= 6; color++) {
                if(sides_colors[i][0] == color || sides_colors[i][1] == color || sides_colors[i][2] == color || sides_colors[i][3] == color){
                    dif_color++;
                }
            }
            if(dif_color == 1)
                continue;
            else if(dif_color == 2)
                h++;
            else if(dif_color == 3)
                h += 2;
            else if(dif_color == 4)
                h += 4;
        }
        return h;
    }

    public boolean goal_test(){
        for (int i = 0; i < 6; i++) {
            if(sides_colors[i][0] != sides_colors[i][1] || sides_colors[i][0] != sides_colors[i][2] || sides_colors[i][0] != sides_colors[i][3] || sides_colors[i][1] != sides_colors[i][2] || sides_colors[i][1] != sides_colors[i][3] || sides_colors[i][2] != sides_colors[i][3])
                return false;
        }
        return true;
    }

    public void printCube(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(sides_colors[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");
        }
    }

    public int[][] get_sides_colors(){
        return sides_colors;
    }

    public void set_sides_colors(int[][] sides_colors){
        this.sides_colors = sides_colors;
    }

    private int[][] deepCopyColorsMatrix() {
        if (sides_colors == null)
            return null;
        int[][] result = new int[sides_colors.length][];
        for (int r = 0; r < sides_colors.length; r++) {
            result[r] = sides_colors[r].clone();
        }
        return result;
    }

    @Override
    public Cube clone() {
        return new Cube(deepCopyColorsMatrix());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return Arrays.equals(sides_colors, cube.sides_colors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(sides_colors);
    }
}