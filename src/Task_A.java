class Bee implements Runnable {
    private int row = 0;
    private String name;
    Bee(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (!Task_A.manager.isPoohFound()){
            synchronized (Task_A.manager){
                row = Task_A.manager.givesRow();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(name + " " + row);
            for (int i = 0; i < Task_A.FOREST_SIZE; i++){
                if (Task_A.forest[row][i] == 1 && !Task_A.manager.isPoohFound()){
                    Task_A.manager.setPoohFound(true);
                    System.out.println("Winni Pooh found " + name + " in row " + row + " and column " + i);
                    break;
                }
            }
        }

    }
}
class Manager{
    private int row = 0;

    private boolean poohFound = false;

    public boolean isPoohFound() {
        return poohFound;
    }

    public void setPoohFound(boolean poohFound) {
        this.poohFound = poohFound;
    }

    public int givesRow(){
        int currentRow = row;
        row++;
        return currentRow;
    }

}


public class Task_A {
    public static final int FOREST_SIZE = 429;
    public static final int NUMBER_OF_BEES = 3;
    public static int [][] forest = new int [FOREST_SIZE][FOREST_SIZE];

    public static final Manager manager = new Manager();

    public static void main(String[] args) {
        //position Winnie Pooh
        int x, y = 0;
        x = (int)(Math.random()*FOREST_SIZE);
        y = (int)(Math.random()*FOREST_SIZE);
        forest[x][y] = 1;
        System.out.println("Winni Pooh in " + x +" "+y);

        Bee[] bees = new Bee[NUMBER_OF_BEES];
        for (int i = 0; i < NUMBER_OF_BEES; i++){
            bees[i] = new Bee("Bee " + i);
            Thread thread = new Thread(bees[i]);
            thread.start();
        }
    }
}
