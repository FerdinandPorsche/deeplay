package deeplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SolutionWithSTAR {

    public static void getResult() {

        // объявляем игровое поле 4х4
        int[][] field = new int[4][4];
        // используем try с ресурсами (для автозакрытия файла)
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Георгий\\IdeaProjects\\deeplay\\src\\deeplay\\config.ini"))) {

            // читаем строку из файла
            String way = br.readLine();

            // в вложенном цикле заполняем поле стоимостью шагов
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    // читаем первый символ строки пути
                    String symbol = way.substring(0, 1);

                    // пишем стоимость шага в ячейку
                    field[i][j] = Integer.parseInt(symbol);

                    // обрезаем строку пути на 1 символ сначала
                    way = way.substring(1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // по условию стоимость первого шага = 0
        field[0][0] = 0;

        // берем значения длин полей игрового поля для создания матрицы сумм стоимости путей
        int M = field.length;
        int N = field[0].length;

        // `map[i][j]` будет содержать минимальную стоимость пути до (i, j) из ячейки (0, 0)
        int[][] map = new int[M][N];

        // заполним матрицу map сверху вниз
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = field[i][j];

                // заполняем первую строку map[0][j] стоимостью шагов по всем предыдущим ячейкам по горизонтали
                // (№0 + №1 = №1; №1(№0 + №1) + №2 = №2; №2(№0 + №1 + №2) + №3 = №3)
                if (i == 0 && j > 0) {
                    map[0][j] += map[0][j - 1];
                }

                // заполняем первую колонку map[i][0] стоимостью шагов по всем предыдущим ячейкам по вертикали
                // (№0 + №1 = №1; №1(№0 + №1) + №2 = №2; №2(№0 + №1 + №2) + №3 = №3)
                else if (j == 0 && i > 0) {
                    map[i][0] += map[i - 1][0];
                }

                // заполняем остальную матрицу минимальным значением из двух путей (до каждой ячейки можно дойти или сверху или слева)
                else if (i > 0 && j > 0) {
                    map[i][j] += Integer.min(map[i - 1][j], map[i][j - 1]);
                }
            }
        }

        // в последней ячейке матрицы сумм стоимости шагов map будет лежать минимальная стоимость пути из ячейки (0, 0)
        System.out.println(map[M - 1][N - 1]);
    }

    public static void main(String[] args) {

        // для проверки
        SolutionWithSTAR.getResult();
    }
}