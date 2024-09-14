//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapParser {
//    public static MapDetails parseMap(String filename) throws IOException {
//        List<String> lines = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                lines.add(line);
//            }
//        }
//        char[][] grid = new char[lines.size()][lines.get(0).length()];
//        for (int i = 0; i < lines.size(); i++) {
//            grid[i] = lines.get(i).toCharArray();
//        }
//        return new MapDetails(grid);
//    }
//}
