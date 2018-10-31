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
        // import city data
        // initializes numCities, numMonths, cityNames, and opsCosts from input file
        importCityData("src/opsCost.txt");

        //initialize the things
        relocCosts = new int[numCities][numCities];
        minCosts = new Node[numCities][numMonths];
        cityPath = new int[numMonths];

        // import relocation data
        importRelocCosts("src/relocCost.txt", numCities);

        // calculate costs table
        populateMinCosts();

        // generate "city path"
        generateTraceBack(minCosts, cityPath);

        // print the cities
        printPath(cityPath, cityNames);
    }

    private static void populateMinCosts() {

        int minCost;
        int opsCost;
        int relocCost;
        int prevCost;
        int sumCost;
        int prevCity = 0;

        for (int i = 0; i < numCities; i++) {
            Node node = new Node(opsCosts[i][0], -1);
            minCosts[i][0] = node;
        }

        // loop over the months
        for (int i = 1; i < numMonths; i++) {
            // loop over the cities 
            for (int j = 0; j < numCities; j++) {
                // loop over cities to find the min cost to operate in that city in that month
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
                Node node = new Node(minCost, prevCity);
                minCosts[j][i] = node;

            }
        }
    }

    private static void generateTraceBack(Node[][] minCosts, int[] cityPath) {
    	finalMinCost = Integer.MAX_VALUE;
    	int minCity = -1;
    	for(int i = 0; i < numCities; i++) {
    		if(minCosts[i][numMonths-1].minCost < finalMinCost) {
    			finalMinCost = minCosts[i][numMonths-1].minCost; 
    			minCity = i;
    		}
    	}
    	
    	cityPath[numMonths-1] = minCity; 
    	
    	int nextParentCity = minCosts[minCity][numMonths-1].parentCity;
    	for(int j = numMonths - 2; j > 0; j--) {
    		cityPath[j] = nextParentCity;
    		nextParentCity = minCosts[nextParentCity][j].parentCity;
    	}
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
            opsCosts = new int[numCities][numMonths];
            cityNames = new String[numCities];

            for (int i = 0; i < numCities; i++) {
                // get the name for city i
                cityNames[i] = scanner.next();

                for (int j = 0; j < numMonths; j++) {
                    // load the operation costs for city i in month j
                    opsCosts[i][j] = Integer.parseInt(scanner.next());
                }
            }
        } catch (FileNotFoundException fe) {
            System.err.println(fe.getMessage());
            System.err.println(fe.getCause());
        }
    }

    private static void importRelocCosts(String txtFile, int numCities) {
        //TODO: import relocation costs
        try {
            Path path = Paths.get(txtFile);
            Scanner scanner = new Scanner(new File(path.toUri()));

            scanner.useDelimiter("\\s+|\\n");
            int thisNumCities;

            thisNumCities = Integer.parseInt(scanner.next());
            if (thisNumCities != numCities) { // checks input validity
                System.err.println("numCities does not match!");
            }

            relocCosts = new int[numCities][numCities];

            for (int i = 0; i < numCities; i++) {

                for (int j = 0; j < numCities; j++) {
                    relocCosts[j][i] = Integer.parseInt(scanner.next());
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
    	
    	System.out.println(finalMinCost);
    	System.out.println(path);
    }

    private static class Node {
        public Node(int minCost, int cityIndex) {
            this.minCost = minCost;
            this.parentCity = cityIndex;
        }

        public int minCost;
        public int parentCity;
    }

}
