package iStore_ltd;


public class test {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        int[] newNumbers = new int[numbers.length + 1];
        System.arraycopy(numbers, 0, newNumbers, 0, numbers.length);
        newNumbers[newNumbers.length - 1] = 4;
        System.out.println(newNumbers);
    }
}