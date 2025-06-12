package steps;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResultAnalyzer {

    private static final String RESULTS_FILE_PATH = "/Users/alex.pshe/IdeaProjects/crazy-tests/calculate_average_app/script/averages.csv";

    // Метод для проверки, что файл averages.csv существует и доступен
    public boolean checkResult() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESULTS_FILE_PATH))) {
            String line = reader.readLine();
            if (line == null) {
                System.out.println("No data found in averages.csv.");
                return false;  // Если файл пуст, возвращаем false
            }
            System.out.println("File contains data: " + line);
        } catch (IOException e) {
            System.err.println("Error reading the result file: " + e.getMessage());
            return false;  // Ошибка при чтении файла
        }
        return true;  // Успешная проверка
    }

    // Метод для проверки, что в файле содержатся правильные данные для указанного пользователя
    public boolean contains(String user, String month, double expectedAverage) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESULTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(user) && parts[1].equals(month)) {
                    // Заменяем все символы новой строки и возврата каретки на пустую строку
                    String actualAverageString = parts[2].replace("\\n", "").replace("\r", "").trim();  // Убираем \n и \r
                    System.out.println("Processing value: " + actualAverageString);  // Для отладки

                    double actualAverage = Double.parseDouble(actualAverageString); // Преобразование в double

                    // Используем assertEquals для сравнения значений
                    Assertions.assertEquals(expectedAverage, actualAverage, 0.01, "The average value is incorrect for " + user + " in " + month);

                    System.out.println("Verified: " + user + " in " + month + " with average " + actualAverage);
                    return true;  // Успешная проверка
                }
            }
            System.out.println("User not found in result: " + user + " in " + month);
            return false;  // Пользователь не найден
        } catch (IOException e) {
            System.err.println("Error reading the result file: " + e.getMessage());
            return false;  // Ошибка при чтении файла
        }
    }
}
