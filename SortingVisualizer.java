import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SortingVisualizer extends JPanel {
    private int[] array;
    private static final int ARRAY_SIZE = 50;
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int BAR_WIDTH = PANEL_WIDTH / ARRAY_SIZE;
    private static final int DELAY = 50;
    private JTable arrayTable;
    private JFrame arrayFrame;
    private String complexityInfo = "";

    public SortingVisualizer() {
        array = new int[ARRAY_SIZE];
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.RED);
        initializeArray();
        initializeArrayWindow();
    }

    private void initializeArray() {
        int choice = JOptionPane.showOptionDialog(null, "Choose array initialization method:", "Array Initialization",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[] { "Random Numbers", "User Input" }, "Random Numbers");

        if (choice == 0) {
            initializeArrayWithRandomNumbers();
        } else {
            initializeArrayWithUserInput();
        }
    }

    private void initializeArrayWithRandomNumbers() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = i + 1;
        }
        shuffleArray();
    }

    private void initializeArrayWithUserInput() {
        String input = JOptionPane.showInputDialog("Enter " + ARRAY_SIZE + " integers separated by commas:");
        if (input != null && !input.trim().isEmpty()) {
            String[] tokens = input.split(",");
            if (tokens.length == ARRAY_SIZE) {
                try {
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        array[i] = Integer.parseInt(tokens[i].trim());
                    }
                } catch (NumberFormatException e) {
                    handleInvalidInput();
                }
            } else {
                handleInvalidInput();
            }
        } else {
            handleInvalidInput();
        }
    }

    private void handleInvalidInput() {
        JOptionPane.showMessageDialog(null, "Invalid input. Using random numbers instead.");
        initializeArrayWithRandomNumbers();
    }

    private void shuffleArray() {
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = list.get(i);
        }
    }

    private void initializeArrayWindow() {
        arrayFrame = new JFrame("Array Values");
        arrayTable = new JTable(ARRAY_SIZE, 1);
        arrayFrame.add(new JScrollPane(arrayTable));
        arrayFrame.setSize(200, PANEL_HEIGHT);
        arrayFrame.setVisible(true);
        updateArrayWindow();
    }

    public void updateArrayWindow() {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < array.length; i++) {
                arrayTable.setValueAt(array[i], i, 0);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBars(g);
        drawComplexityInfo(g);
    }

    private void drawBars(Graphics g) {
        int max = Arrays.stream(array).max().orElse(1);
        int heightFactor = PANEL_HEIGHT - 10; // Margin

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int barHeight = (int) ((value / (double) max) * heightFactor);

            g.setColor(Color.BLACK);
            g.fillRect(i * BAR_WIDTH, PANEL_HEIGHT - barHeight, BAR_WIDTH, barHeight);
        }
    }

    private void drawComplexityInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        String[] lines = complexityInfo.split("\n");
        int y = 20; // Starting Y position for text
        for (String line : lines) {
            g.drawString(line, 10, y);
            y += 20; // Move to next line
        }
    }

    public void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void visualizeSort(String algorithm) {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            switch (algorithm) {
                case "Bubble Sort":
                    SortingAlgorithms.bubbleSort(array, this);
                    complexityInfo = "Bubble Sort:\nTime Complexity: O(n^2)\nSpace Complexity: O(1)";
                    break;
                case "Selection Sort":
                    SortingAlgorithms.selectionSort(array, this);
                    complexityInfo = "Selection Sort:\nTime Complexity: O(n^2)\nSpace Complexity: O(1)";
                    break;
                case "Insertion Sort":
                    SortingAlgorithms.insertionSort(array, this);
                    complexityInfo = "Insertion Sort:\nTime Complexity: O(n^2)\nSpace Complexity: O(1)";
                    break;
                case "Merge Sort":
                    SortingAlgorithms.mergeSort(array, 0, array.length - 1, this);
                    complexityInfo = "Merge Sort:\nTime Complexity: O(n log n)\nSpace Complexity: O(n)";
                    break;
                case "Quick Sort":
                    SortingAlgorithms.quickSort(array, 0, array.length - 1, this);
                    complexityInfo = "Quick Sort:\nTime Complexity: O(n log n)\nSpace Complexity: O(log n)";
                    break;
                case "Heap Sort":
                    SortingAlgorithms.heapSort(array, this);
                    complexityInfo = "Heap Sort:\nTime Complexity: O(n log n)\nSpace Complexity: O(1)";
                    break;
            }
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            complexityInfo += "\nTime Taken: " + timeTaken + " ms";
            repaint();
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sorting Algorithm Visualizer");
            SortingVisualizer visualizer = new SortingVisualizer();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(visualizer);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            String[] options = { "Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort",
                    "Heap Sort" };
            String algorithm = (String) JOptionPane.showInputDialog(frame, "Choose Sorting Algorithm:",
                    "Sorting Algorithm",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (algorithm != null) {
                visualizer.visualizeSort(algorithm);
            }
        });
    }
}
