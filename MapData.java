import java.awt.Point;
import java.util.ArrayList;

public class MapData {

    public static final int TILE_SIZE = 64;

    public static final int[][] MAP = {// 0 = Grass, 1 = Path ,2 = Start, 3 = Stop
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0},
        {2,1,1,1,1,1,1,0,0,1,0,0,0,0,1,1,1,1,0,0},
        {0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0},
        {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,3},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    public static ArrayList<Point> pathPoints = new ArrayList<>();

    public static void buildPath() {
        // pathPoints.clear();

        // for (int row = 0; row < MAP.length; row++) {
        //     for (int col = 0; col < MAP[0].length; col++) {

        //         if (MAP[row][col] == 1) {
        //             int x = col * TILE_SIZE + TILE_SIZE / 2;
        //             int y = row * TILE_SIZE + TILE_SIZE / 2;
        //             pathPoints.add(new Point(x, y));
        //         }
        //     }
        // }

            pathPoints.clear();

    int rows = MAP.length;
    int cols = MAP[0].length;

    boolean[][] visited = new boolean[rows][cols];

    // 1. หา start
    int sr = -1, sc = -1;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (MAP[r][c] == 2) {
                sr = r;
                sc = c;
                break;
            }
        }
    }

    int r = sr, c = sc;

    while (true) {
        visited[r][c] = true;

        int x = c * TILE_SIZE + (TILE_SIZE / 2);
        int y = r * TILE_SIZE + (TILE_SIZE / 2);
        pathPoints.add(new Point(x, y));

        if (MAP[r][c] == 3) break; // ถึง end

        // เดินต่อ 4 ทิศ
        int[][] dirs = {
            {0,1}, {1,0}, {0,-1}, {-1,0}
        };

        boolean moved = false;

        for (int[] d : dirs) {
            int nr = r + d[0];
            int nc = c + d[1];

            if (nr >= 0 && nr < rows && //ถ้าไม่ใช่ขอบ
                nc >= 0 && nc < cols && //ถ้าไม่ใช่ขอบ
                !visited[nr][nc] && //ถ้าไม่เคยไป
                (MAP[nr][nc] == 1 || MAP[nr][nc] == 3)) { //ถ้าเป็น 1 หรือ 3

                r = nr;
                c = nc; //อัปเดต r,c
                moved = true; //ตรวจว่าเข้า if ไหม
                break;
            }
        }

        if (!moved) break; // กัน error

        }
    }
}
