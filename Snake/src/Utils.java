import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 3/14/2016.
 */
public class Utils {

    private static final String SAVE_FILE_PREFIX = "training_";

    private static List<Example> examples = new ArrayList<>();

    public static void addExample(int positions, int blockSize, int appleX, int appleY,
                                  int[] snakeX, int[] snakeY, int snakeLen,
                                  boolean up, boolean right, boolean down, boolean left,
                                  int action) {

        int[] input = new int[positions*positions*2 + 4];
        int[] output;

        for (int y=0; y<positions; y++) {
            for (int x=0; x<positions; x++) {

                int isSnakeIndex = y*positions*2 + x*2;
                int isAppleIndex = isSnakeIndex + 1;

                for (int z=0; z<snakeLen; z++) {
                    if (x == snakeX[z]/blockSize && y == snakeY[z]/blockSize) {
                        input[isSnakeIndex] = 1;
                    }
                }

                if (x == appleX/blockSize && y == appleY/blockSize) {
                    input[isAppleIndex] = 1;
                }

            }
        }

        // Snake direction
        input[input.length-4] = up ? 1 : 0;
        input[input.length-3] = right ? 1 : 0;
        input[input.length-2] = down ? 1 : 0;
        input[input.length-1] = left ? 1 : 0;

        if (action == KeyEvent.VK_UP) {
            if (up) {
                output = new int[]{0, 0, 0, 0, 1};
            } else {
                output = new int[]{1, 0, 0, 0, 0};
            }
        } else if (action == KeyEvent.VK_RIGHT) {
            if (right) {
                output = new int[]{0, 0, 0, 0, 1};
            } else {
                output = new int[]{0, 1, 0, 0, 0};
            }
        } else if (action == KeyEvent.VK_DOWN) {
            if (down) {
                output = new int[]{0, 0, 0, 0, 1};
            } else {
                output = new int[]{0, 0, 1, 0, 0};
            }
        } else if (action == KeyEvent.VK_LEFT) {
            if (left) {
                output = new int[]{0, 0, 0, 0, 1};
            } else {
                output = new int[]{0, 0, 0, 1, 0};
            }
        } else if (action == KeyEvent.VK_SPACE) {
            output = new int[] {0, 0, 0, 0, 1};
        } else {
            output = new int[] {0, 0, 0, 0, 0};
        }

        Example e = new Example(input, output);
        System.out.println(e);
        examples.add(e);

    }

    public static void exportExamples() {
        System.out.println("Exporting examples");

        try {
            PrintWriter writer = new PrintWriter(SAVE_FILE_PREFIX+System.currentTimeMillis()+".csv", "UTF-8");
            for (Example e : examples) {
                writer.println(e.toCSV());
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}