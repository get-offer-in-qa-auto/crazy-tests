package  steps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class DataGenerator {

    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), "script", "data", "transactions.csv").toString();
    private static final String DIR_PATH = Paths.get(System.getProperty("user.dir"), "script", "data").toString();

    // Метод для генерации данных
    public DataGenerator generateData(int numRows) {
        // Создание директории, если она не существует
        File dir = new File(DIR_PATH);
        if (!dir.exists()) {
            dir.mkdirs();  // Создание директорий
        }

        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append("user,date,category,amount\n");  // Заголовок CSV

        // Массивы для случайных пользователей, категорий и даты
        String[] users = {"alice", "bob", "charlie", "dave"};
        String[] categories = {"food", "transport", "entertainment", "health", "education"};
        Random random = new Random();

        // Генерация данных для заданного количества строк
        for (int i = 0; i < numRows; i++) {
            String user = users[random.nextInt(users.length)];
            String date = generateRandomDate();
            String category = categories[random.nextInt(categories.length)];
            double amount = 100 + (2000 - 100) * random.nextDouble();  // Сумма между 100 и 2000

            dataBuilder.append(String.format("%s,%s,%s,%.2f\n", user, date, category, amount));
        }

        // Записываем данные в файл
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(dataBuilder.toString());
            System.out.println("Generated " + numRows + " random data rows and saved to: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error writing data to file: " + e.getMessage());
        }

        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для добавления конкретных данных
    public DataGenerator withData(String user, String date, double amount) {
        String data = String.format("%s,%s,food,%.2f\n", user, date, amount);

        // Дописываем данные в файл
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(data);
            System.out.println("Added data: " + data);
        } catch (IOException e) {
            System.err.println("Error appending data to file: " + e.getMessage());
        }

        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для генерации случайной даты в 2025 году
    private String generateRandomDate() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;  // Ограничиваем до 28 дней в месяце для упрощения
        return String.format("2025-%02d-%02d", month, day);
    }
}
