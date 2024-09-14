import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SlidingPuzzle {
    public static void main(String[] args) {
        String filename = "benchmark_series/puzzle_10.txt"; // Replace with the correct file path
        try {
            MapDetails map = MapParser.parseMap(filename);
            List<Point> path = PathFinder.findShortestPath(map);
            if (path.isEmpty()) {
                System.out.println("No solution found!");
            } else {
                System.out.println("Solution:");
                System.out.printf("1. Start at (%d,%d)%n", map.start.x + 1, map.start.y + 1);
                for (int i = 1; i < path.size(); i++) {
                    Point prev = path.get(i - 1);
                    Point curr = path.get(i);
                    String direction = getDirection(prev, curr);
                    System.out.printf("%d. Move %s to (%d,%d)%n", i + 1, direction, curr.x + 1, curr.y + 1);
                }
                System.out.println(path.size() + 1 + ". Done!");
            }
        } catch (IOException e) {
            System.out.println("Error: Input file location is incorrect.");
        }
    }

    private static String getDirection(Point prev, Point curr) {
        if (curr.x < prev.x) {
            return "left";
        } else if (curr.x > prev.x) {
            return "right";
        } else if (curr.y < prev.y) {
            return "up";
        } else {
            return "down";
        }
    }
}

class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class PathFinder {
    static List<Point> findShortestPath(MapDetails map) {
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        Set<Point> visited = new HashSet<>();
        queue.offer(map.start);
        visited.add(map.start);

        boolean isFinishReachable = false;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(map.finish)) {
                isFinishReachable = true;
                return reconstructPath(parentMap, current);
            }

            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};

            for (int i = 0; i < 4; i++) {
                int nx = current.x;
                int ny = current.y;
                Point nextPoint = moveInDirection(map, nx, ny, dx[i], dy[i]);
                if (nextPoint != null && !visited.contains(nextPoint)) {
                    queue.offer(nextPoint);
                    visited.add(nextPoint);
                    parentMap.put(nextPoint, current);
                }
            }
        }

        if (!isFinishReachable) {
            return reconstructPath(parentMap, queue.peek()); // Return the last reachable point
        }

        return Collections.emptyList(); // No path found
    }

    private static Point moveInDirection(MapDetails map, int x, int y, int dx, int dy) {
        while (isValidMove(map, x + dx, y + dy)) {
            x += dx;
            y += dy;
            if (map.grid[y][x] == '0') {
                break; // Stop sliding if a rock is encountered
            }
        }
        return new Point(x, y);
    }

    private static List<Point> reconstructPath(Map<Point, Point> parentMap, Point current) {
        List<Point> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static boolean isValidMove(MapDetails map, int x, int y) {
        return x >= 0 && x < map.width && y >= 0 && y < map.height && map.grid[y][x] != '0';
    }
}

class MapParser {
    static MapDetails parseMap(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return new MapDetails(grid);
    }
}

class MapDetails {
    int width, height;
    char[][] grid;
    Point start, finish;

    MapDetails(char[][] grid) {
        this.grid = grid;
        height = grid.length;
        width = grid[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 'S') {
                    start = new Point(j, i);
                } else if (grid[i][j] == 'F') {
                    finish = new Point(j, i);
                }
            }
        }
    }
}
