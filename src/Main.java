public class Main {

    private static int numCities;
    private static int numMonths;
    private static int[][] relocCosts;
    private static int[][] opsCosts;

    private static Node[][] minCosts;
    private static String[] cityNames;

    public static void main(String[] args) {
        //TODO: import city data
        importCityData("someString.....", opsCosts, numCities, numMonths);

        //TODO: import relocation data
        importRelocCosts("someString........", relocCosts);

        //TODO: calculate costs table
        calculateCosts(relocCosts, opsCosts, minCosts);
        //TODO: generate "city path"

    }

    private static void calculateCosts(int[][] relocationCosts, int[][] operationalCosts, Node[][] minCosts) {
        //TODO: calculate table data
    }

    private static void importCityData(String txtFile, int[][] opsCosts, int numCities, int numMonths) {
        //TODO: import numCities
        //TODO: import numMonths

        //TODO: import operational costs

    }

    private static void importRelocCosts(String txtFile, int[][] relocCosts) {
        //TODO: import relocation costs
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
