package deeplay;

public class Solution {
    public static void main(String[] args) {

        // мне было лень писать юнит-тесты дял такой простой задачи
        // просто запустите класс на исполнение
        int[][] gameField;
        gameField = constructField("STWSWTPPTPTTPWPP", "Human");
        int path;
        path = getResult(gameField);
        System.out.println(path);
    }

    // так как есть вариант решения задачи со звёздочкой, то разбил на 2 подзадачи:
    // 1. построение игрового поля;
    // 2. нахождение минимальной стоимости пути

    public static int[][] constructField(String way, String type) {

        // инициализируем переменные
        int S, W, P, T;

        // присваиваем переменным значения исходя из типа существа
        // можно использовать и switch, на скорость работы не влияет. JIT компилятор сам выберет быстрый вариант
        // при создании байт-кода
        if (type.equals("Human")){
            S = 5;
            W = 2;
            T = 3;
            P = 1;
        } else if (type.equals("Swamper")){
            S = 2;
            W = 2;
            T = 5;
            P = 2;
        } else if (type.equals("Woodman")){
            S = 3;
            W = 3;
            T = 2;
            P = 2;
        } else {
            System.out.println("Incorrect type of creature");
            return null;
        }

        // заполняем матрицу field значениями переменных
        int[][] field = new int[4][4];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                // читаем первый символ строки пути
                char ch = way.charAt(0);
                if (ch == 'S') field[i][j] = S;
                if (ch == 'W') field[i][j] = W;
                if (ch == 'T') field[i][j] = T;
                if (ch == 'P') field[i][j] = P;

                // обрезаем строку пути на 1 символ сначала
                way = way.substring(1);
            }
        }
        return field;
    }

    public static int getResult(int[][] field) {

        // на всякий случай
        if (field == null || field.length == 0) {
            return 0;
        }

        // по условию стоимость первого шага = 0
        field[0][0] = 0;

        // берем значения длин полей игрового поля для создания матрицы сумм стоимости путей
        int M = field.length;
        int N = field[0].length;

        // `map[i][j]` будет содержать минимальную стоимость пути до (i, j) из ячейки (0, 0)
        int[][] map = new int[M][N];

        // заполним матрицу map сверху вниз
        for (int i = 0; i < M; i++)
        {
            for (int j = 0; j < N; j++)
            {
                map[i][j] = field[i][j];

                // заполняем первую строку map[0][j] стоимостью шагов по всем предыдущим ячейкам по горизонтали (№0 + №1 = №1; №1(№0 + №1) + №2 = №2; №2(№0 + №1 + №2) + №3 = №3)
                if (i == 0 && j > 0) {
                    map[0][j] += map[0][j - 1];
                }

                // заполняем первую колонку map[i][0] стоимостью шагов по всем предыдущим ячейкам по вертикали (№0 + №1 = №1; №1(№0 + №1) + №2 = №2; №2(№0 + №1 + №2) + №3 = №3)
                else if (j == 0 && i > 0) {
                    map[i][0] += map[i - 1][0];
                }

                // заполняем остальную матрицу минимальным значением из двух путей (до каждой ячейки можно дойти или сверху или слева)
                else if (i > 0 && j > 0) {
                    map[i][j] += Integer.min(map[i - 1][j], map[i][j - 1]);
                }
            }
        }

        // в последней ячейке матрицы сумм стоимости шагов будет лежать минимальная стоимость пути из ячейки (0, 0)
        return map[M - 1][N - 1];
    }
 }