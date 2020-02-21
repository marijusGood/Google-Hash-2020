import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GoogleHash {

    public static void main(String[] args) {
        //stores books that have been already shipped
        HashMap<Integer, Boolean> hashMapTags = new HashMap<>();
        //variables
        int[] bookScore = null;
        ArrayList<ArrayList<Integer>> bookInLibrary = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int[][] librariesInfo = null;
        /*
        librariesInfo stores the following information:
        0. number of books in the library
        1. how long the sign-up precess takes
        2. how many books it can ship per day
        3. the ID of the library
         */

        //putting the file into the scanner
        File inputFile = new File("B1.txt");
        Scanner in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //read the file parameters
        int books = in.nextInt();
        int libraries = in.nextInt();
        int days = in.nextInt();
        in.nextLine();

        //read the book scores
        bookScore = new int[books];
        String lienStr = in.nextLine();
        Scanner line = new Scanner(lienStr);
        for(int i = 0; i < books; i++) {
            bookScore[i] = line.nextInt();
        }
        line.close();

        //read the rest of the file
        librariesInfo = new int[libraries][4];

        for(int i = 0; i < libraries; i++) {
            //reading the libraries
            lienStr = in.nextLine();
            Scanner line2 = new Scanner(lienStr);
            for(int j = 0; j < 3; j++) {
                librariesInfo[i][j] = line2.nextInt();
            }
            librariesInfo[i][3] = i;//storing the number of the library

            //reading the books in the libraries
            lienStr = in.nextLine();
            line2 = new Scanner(lienStr);
            bookInLibrary.add(new ArrayList());
            for(int j = 0; j < librariesInfo[i][0]; j++) {
                bookInLibrary.get(i).add(line2.nextInt());//storing the books of the libraries
            }
            line2.close();
        }

        in.close();

        // sorts the libraries by the signing process from lowest to highest
        for (int i = 0; i < libraries-1; i++) {
            for (int j = 0; j < libraries - i - 1; j++) {
                if (librariesInfo[j][1] > librariesInfo[j + 1][1]) {
                    int[] temp22 = librariesInfo[j];
                    librariesInfo[j] = librariesInfo[j + 1];
                    librariesInfo[j + 1] = temp22;
                }
            }
        }

        //add the starting number of the amount of libraries in the file
        ans.add(new ArrayList<>());
        ans.get(0).add(0);

        boolean signing = false;//has the file been signed
        int counter = 0;//helps to track with which library we are working
        int countLib = 0;//tracks when the library has been signed

        for(int i = 0; i < days; i++) {//simulating days
            if(!signing) {//if the signing process hasn't finished
                if(librariesInfo.length > counter) {//protection from librariesInfo indexOutOfBounds
                    ArrayList<Integer> bookTemp = new ArrayList<>();//stores the libraries books that haven't been shaped by the oder libraries

                    countLib = librariesInfo[counter][1];//gets the amount of days it will take to sign
                    int newBooks = 0;//number of new books that haven't been shaped in the library
                    for (int k = 0; k < librariesInfo[counter][0]; k++) {
                        if (!hashMapTags.containsKey(bookInLibrary.get(librariesInfo[counter][3]).get(k))) {
                            /*if the book dose not exist in the hasMap then its a new book. Add that book to the hasMap,
                            add that book to the bookTemp and add +1 to the newBooks. If the book dose exist the do nothing.
                            */
                            hashMapTags.put(bookInLibrary.get(librariesInfo[counter][3]).get(k), true);
                            newBooks++;
                            bookTemp.add(bookInLibrary.get(librariesInfo[counter][3]).get(k));
                        }
                    }
                    if(newBooks > 0){//if at least 1 new books is in the library
                        ans.get(0).set(0, counter+1);//new library has been added so change the number to new one
                        //adding the new libraries and the books that have been shaped
                        ans.add(new ArrayList<>());
                        ans.get(ans.size() - 1).add(librariesInfo[counter][3]);
                        ans.get(ans.size() - 1).add(newBooks);
                        ans.add(new ArrayList<>());
                        for (int a = 0; a < bookTemp.size(); a++) {
                            ans.get(ans.size() - 1).add(bookTemp.get(a));
                        }
                    }

                    counter++;
                    signing = true;//signing process is happening
                }
            }

            countLib--;
            if(countLib == 0) {//signing process has been complected
                signing = false;
            }
        }

        //printing the ans file out
        File outputfile = new File("ans.txt");
        PrintWriter out = null;
        try {
            out = new PrintWriter(outputfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ading spaces and new line brakes between numbers
        for(int i = 0; i < ans.size(); i++) {
            for(int j = 0; j < ans.get(i).size(); j++) {
                out.print(ans.get(i).get(j) + " ");
            }
            out.println();
        }
        out.close();
    }
}
