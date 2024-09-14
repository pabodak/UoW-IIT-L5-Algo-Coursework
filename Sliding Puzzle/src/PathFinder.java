//import java.util.*;
//
//public class PathFinder {
//    static List<Point> findShortestPath(MapDetails map) {
//        int[][] distance = new int[map.height][map.width];
//        int[][] parentX = new int[map.height][map.width];
//        int[][] parentY = new int[map.height][map.width];
//        boolean[][] visited = new boolean[map.height][map.width];
//
//        for (int i = 0; i < map.height; i++) {
//            Arrays.fill(distance[i], Integer.MAX_VALUE);
//        }
//
//        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(p -> distance[p.y][p.x]));
//        pq.offer(map.start);
//        distance[map.start.y][map.start.x] = 0;
//
//        while (!pq.isEmpty()) {
//            Point current = pq.poll();
//            int cx = current.x;
//            int cy = current.y;
//
//            if (visited[cy][cx]) continue;
//            visited[cy][cx] = true;
//
//            int[] dx = {0, 0, 1, -1};
//            int[] dy = {1, -1, 0, 0};
//
//            for (int i = 0; i < 4; i++) {
//                int nx = cx + dx[i];
//                int ny = cy + dy[i];
//
//                if (nx >= 0 && nx < map.width && ny >= 0 && ny < map.height && map.grid[ny][nx] != '0') {
//                    int newDistance = distance[cy][cx] + 1;
//                    if (newDistance < distance[ny][nx]) {
//                        distance[ny][nx] = newDistance;
//                        parentX[ny][nx] = cx;
//                        parentY[ny][nx] = cy;
//                        pq.offer(new Point(nx, ny));
//                    }
//                }
//            }
//        }
//
//        List<Point> path = new ArrayList<>();
//        int x = map.finish.x;
//        int y = map.finish.y;
//
//        while (x != map.start.x || y != map.start.y) {
//            path.add(new Point(x, y));
//            int tempX = x;
//            x = parentX[y][tempX];
//            y = parentY[y][tempX];
//        }
//        path.add(map.start);
//        Collections.reverse(path);
//        return path;
//    }
//}
