import java.util.Arrays;

public class SortingAlgorithms {

    // Bubble Sort
    public static void bubbleSort(int[] arr, SortingVisualizer visualizer) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    visualizer.repaint();
                    visualizer.updateArrayWindow();
                    visualizer.sleep();
                }
            }
        }
    }

    // Selection Sort
    public static void selectionSort(int[] arr, SortingVisualizer visualizer) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
        }
    }

    // Insertion Sort
    public static void insertionSort(int[] arr, SortingVisualizer visualizer) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                visualizer.repaint();
                visualizer.updateArrayWindow();
                visualizer.sleep();
            }
            arr[j + 1] = key;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
        }
    }

    // Merge Sort
    public static void mergeSort(int[] arr, int left, int right, SortingVisualizer visualizer) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, visualizer);
            mergeSort(arr, mid + 1, right, visualizer);
            merge(arr, left, mid, right, visualizer);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, SortingVisualizer visualizer) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
        }
    }

    // Quick Sort
    public static void quickSort(int[] arr, int low, int high, SortingVisualizer visualizer) {
        if (low < high) {
            int pi = partition(arr, low, high, visualizer);
            quickSort(arr, low, pi - 1, visualizer);
            quickSort(arr, pi + 1, high, visualizer);
        }
    }

    private static int partition(int[] arr, int low, int high, SortingVisualizer visualizer) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                visualizer.repaint();
                visualizer.updateArrayWindow();
                visualizer.sleep();
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        visualizer.repaint();
        visualizer.updateArrayWindow();
        visualizer.sleep();
        return i + 1;
    }

    // Heap Sort
    public static void heapSort(int[] arr, SortingVisualizer visualizer) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, visualizer);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
            heapify(arr, i, 0, visualizer);
        }
    }

    private static void heapify(int[] arr, int n, int i, SortingVisualizer visualizer) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            visualizer.repaint();
            visualizer.updateArrayWindow();
            visualizer.sleep();
            heapify(arr, n, largest, visualizer);
        }
    }
}
