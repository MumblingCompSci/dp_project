import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static int numCities;
    private static int numMonths;
    private static int[][] relocCosts;
    private static int[][] opsCosts;

    private static Node[][] minCosts;
    private static String[] cityNames;
    private static int[] cityPath;
    private static int finalMinCost;

    public static void main(String[] args) {
        //TODO: import city data
        // initializes numCities, numMonths, cityNames, and opsCosts from input file
        importCityData("src/opsCost.txt");

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
    	finalMinCost = Integer.MAX_VALUE;
    	int nextParentCity = -1;
    	for(int i = 0; i < numCities; i++) {
    		if(minCosts[i][numMonths-1].minCost < finalMinCost) {
    			finalMinCost = minCosts[i][numMonths-1].minCost; 
    			nextParentCity = i;
    		}
    	}
    	
    	for(int j = numMonths - 1; j > 0; j--) {
    		cityPath[j] = nextParentCity;
    		nextParentCity = minCosts[nextParentCity][j-1].parentCity;
    	}
    	
    	cityPath[0] = nextParentCity;
    }

    private static void importCityData(String txtFile) {
        try {
            Path path = Paths.get(txtFile);
            Scanner scanner = new Scanner(new File(path.toUri()));
            scanner.useDelimiter("\\s+|\\n");

            // read in the values
            numCities = Integer.parseInt(scanner.next());
            numMonths = Integer.parseInt(scanner.next());

            // set up the storage data structures
            opsCosts = new int[numMonths][numCities];
            cityNames = new String[numCities];

            for (int i = 0; i < numCities; i++) {
                // get the name for city i
                cityNames[i] = scanner.next();

                for (int j = 0; j < numMonths; j++) {
                    // load the operation costs for city i in month j
                    opsCosts[j][i] = Integer.parseInt(scanner.next());
                }
            }
            System.out.println("City Data Imported...");
        } catch (FileNotFoundException fe) {
            System.err.println(fe.getMessage());
            System.err.println(fe.getCause());
        }
    }

    private static void importRelocCosts(String txtFile, int numCities, int[][] relocCosts) {
        //TODO: import relocation costs
        try {
            Scanner scanner = new Scanner(new File(txtFile));

            scanner.useDelimiter("\\s+|\\n");
            int thisNumCities;

            thisNumCities = Integer.parseInt(scanner.next());
            if (thisNumCities != numCities) { // checks input validity
                System.err.println("numCities does not match!");
            }

            relocCosts = new int[numCities][numCities];

            for (int i = 0; i < numCities; i++) {

                for (int j = i; j <= numCities; j++) {
                    relocCosts[i][j] = Integer.parseInt(scanner.next());
                }
            }
        } catch (FileNotFoundException fe) {
            System.err.println(fe.getMessage());
            System.err.println(fe.getCause());
        }
    }

    private static void printPath(int[] cityPath, String[] cityNames) {
    	String path = "";
    	for(int city : cityPath) {
    		path = path + cityNames[city] + " ";
    	}
    	
    	System.out.println(path);
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
