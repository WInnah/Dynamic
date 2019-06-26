import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("[1]Longest Common Non-Contiguous Substring\n[2]Backpacking Problem\n[3]Coin Change Problem\nEnter choice: ");
        Scanner scan = new Scanner(System.in);
        Integer choice = scan.nextInt();
        if (choice == 1){//Longest Common Non-Contiguous Substring
            //vertical string is the first and horizontal the second
            //bottom-up approach
            System.out.println("Enter 1st String: ");
            String first = scan.next();
            System.out.println("Enter 2nd String: ");
            String second = scan.next();
            char S[] = first.toCharArray();
            char T[] = second.toCharArray();
            System.out.println("longest: " + longest(S, T));

        }else if (choice == 2){//Backpacking Problem
            System.out.println("Enter size of the set of object");
            int size = scan.nextInt();
            int SVal[] = new int[size];
            int SSize[] = new int[size];
            System.out.println("Enter objects' values");
            for (int i = 0; i < size; i++){
                SVal[i] = scan.nextInt();
            }

            System.out.println("Enter objects' sizes");
            for (int i = 0; i < size; i++){
                SSize[i] = scan.nextInt();
            }

            System.out.println("Enter maximum size: ");
            int m = scan.nextInt();
            List<int []> A = backpack(SVal, SSize, m, size);
            for (int i = 0; i < A.size(); i++) {
                System.out.println();
                for (int j = 0; j < A.get(i).length; j++){
                    System.out.print(A.get(i)[j] + " ");
                }
            }
        }else {//Coin Change Problem
            //top-down approach
            System.out.println("Enter size of coin denomination: ");
            int size = scan.nextInt();
            int S[] = new int[size];
            System.out.println("Enter set of coin denomination: ");
            for (int i = 0; i < size; i++){
                S[i] = scan.nextInt();
            }
            System.out.println("Enter amount of money: ");
            int n = scan.nextInt();
            System.out.println("Number of ways it can be represented: " + coin(n, S, 0));
        }
    }

    public static int coin(int n, int S[], int i){
        if (n < 0){
            return 0;
        }
        if (n == 0){
            return 1;
        }
        if (i == S.length && n > 0){
            return 0;
        }
        return coin(n - S[i], S, i) + coin(n, S, i+1);
    }

    public static String longest(char S[], char T[]){
        String str = "";
        //char A[] = new char[S.length];
        int LCNS[][] = new int[S.length+1][T.length+1];
        for (int i = 0; i <= T.length; i++){
            LCNS[0][i] = 0;
        }

        for (int i = 0; i <= S.length; i++){
            LCNS[i][0] = 0;
        }

        for (int i = 1; i <= S.length; i++){
            for (int j = 1; j <= T.length; j++){
                if (S[i-1] == T[j-1]){
                    LCNS[i][j] = LCNS[i-1][j-1] + 1;
                }else {
                    LCNS[i][j] = max(max(LCNS[i][j-1], LCNS[i-1][j-1]), LCNS[i-1][j]);
                }
            }
        }

        int curVal = LCNS[S.length][T.length];
        int curI = S.length;
        int curJ = T.length;
        while (curI > 0){
            int max = max(max(LCNS[curI][curJ-1], LCNS[curI-1][curJ-1]), LCNS[curI-1][curJ]);
            if (max == curVal){
                if (LCNS[curI][curJ-1] == max){
                    curJ = curJ -1;
                }else if (LCNS[curI-1][curJ-1] == max){
                    curI = curI - 1;
                    curJ = curJ - 1;
                }else if (LCNS[curI-1][curJ] == max){
                    curI = curI - 1;
                }else {
                }
            }else {
                curVal = LCNS[curI-1][curJ-1];
                curI = curI - 1;
                curJ = curJ - 1;
                str = Character.toString(S[curI]) + str;
            }
        }
        return str;
    }

    public static List<int []> backpack(int SVal[], int SSize[], int m, int size){
        int table[][] = new int[size][m+1];
        List<int []> A = new ArrayList<>();
        for (int i = 0; i < size; i++){
            table[i][0] = 0;
        }
        for (int j = 0; j <= m; j++){
            if (SSize[0] <= j){
                table[0][j] = SVal[0];
            }else {
                table[0][j] = 0;
            }
        }

        for (int i = 1; i < size; i++){
            for (int j = 1; j <= m; j++){
                if (SSize[i] <= j){
                    table[i][j] = max(table[i-1][j], (SVal[i] + table[i-1][j - SSize[i]]));
                }else {
                    table[i][j] = table[i-1][j];
                }
            }
        }

        int curVal = table[size-1][m];
        int curI = size-1;
        int curJ = m;
        while (curI > 0){
            if (curVal == table[curI -1][curJ]){
                curI = curI - 1;
            }else {
                //store the item value
                A.add(new int[] {SVal[curI], SSize[curI]});
                curJ = curJ - SSize[curI];
                curI = curI - 1;
                curVal = table[curI][curJ];
                if (curI == 0 && curVal != 0){
                    A.add(new int[] {SVal[curI], SSize[curI]});
                }
            }
        }
        return A;
    }

    public static int max(int x, int y){
        if(x > y) {
            return x;
        } else {
            return y;
        }
    }
}
