import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static int numCities;
    private static int numMonths;
    private static int[][] relocCosts;
    private static int[][] opsCosts;

    private static Node[][] minCosts;
    private static String[] cityNames;
    private static int[] cityPath;

    public static void main(String[] args) {
        //TODO: import city data
        // initializes numCities, numMonths, cityNames, and opsCosts from input file
        importCityData("/home/quintero/Algorithms/dp_project/src/opsCost.txt", opsCosts, numCities, numMonths, cityNames);

        //initialize the things
        relocCosts = new int[numCities][numCities];
        minCosts = new Node[numMonths][numCities];
        cityPath = new int[numMonths];

        //TODO: import relocation data
//        importRelocCosts("someString........", relocCosts);

        //TODO: calculate costs table
//        populateMinCosts();

        //TODO: generate "city path"
//        generateTraceBack(minCosts, cityPath);

        //TODO: print the cities
//        printPath(cityPath, cityNames);
    }

    private static void populateMinCosts() {
        //TODO: calculate table data
        //TODO: double check because Garrett can't read
        int minCost;
        int opsCost;
        int relocCost;
        int prevCost;
        int sumCost;
        int prevCity = 0;
        for (int i = 0; i < numMonths; ++i) {
            // loop over the months
            for (int j = 0; j < numCities; ++j) {
                // loop over cities to find the min cost to oeprate in that city in that month
                minCost = Integer.MAX_VALUE;
                for(int k = 0; k < numCities; k++){
                    opsCost = opsCosts[j][i];
                    relocCost = relocCosts[j][k];
                    prevCost = minCosts[k][i-1].minCost;
                    sumCost = opsCost + relocCost + prevCost;
                    if(sumCost < minCost){
                        minCost = sumCost;
                        prevCity = k;
                    }
                }
                minCosts[j][i].minCost = minCost;
                minCosts[j][i].parentCity = prevCity;
            }
        }
    }

    private static void generateTraceBack(Node[][] minCosts, int[] cityPath) {
        //TODO: generate path
    }

    private static void importCityData(String txtFile, int[][] opsCosts, int numCities, int numMonths, String[] cityNames) {
        try {
            Scanner scanner = new Scanner(new File(txtFile));

            scanner.useDelimiter("\\s+|\\n");

            numCities = Integer.parseInt(scanner.next());
            numMonths = Integer.parseInt(scanner.next());

            opsCosts = new int[numMonths][numCities];
            cityNames = new String[numCities];

            for (int i = 0; i < numCities; i++) {
                cityNames[i] = scanner.next();

                for (int j = 0; j < numMonths; j++) {
                    
                }
            }
        } catch (FileNotFoundException fe) {
            System.err.println(fe.getMessage());
            System.err.println(fe.getCause());
        }

        //TODO: import numCities


        //TODO: import numMonths
        //TODO: import city names

        //TODO: import operational costs

    }

    private static void importRelocCosts(String txtFile, int[][] relocCosts) {
        //TODO: import relocation costs
    }

    private static void printPath(int[] cityPath, String[] cityNames) {
        //TODO: print the cities
    }

    private class Node {
        public Node(int minCost, int cityIndex) {
            this.minCost = minCost;
            this.parentCity = cityIndex;
        }

        public int minCost;
        public int parentCity;
    }

}
