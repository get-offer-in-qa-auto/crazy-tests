import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculateAverageTest {
    @Test
    public void test() {
        // Использование цепочки вызовов с Given-When-Then
        new TestScenario()
                .given()  // Подготовка данных
                .generateData(5)  // Генерация 5 случайных строк данных
                .withData("user1", "2025-05-11", 800.0)  // Добавление строки для alice
                .withData("user2", "2025-05-12", 1000.0)  // Добавление строки для bob
                .whenExecuteScript()
                .then()  // Проверка результатов
                .contains("user1", "ALL", 800.0)  // Проверка результата для alice
                .contains("user2", "ALL", 1000.0);  // Проверка результата для bob
    }

    @Test
    public void testInvalidDate() {
        // Проверка, что ошибка возникает при добавлении данных с неверным форматом даты
        new TestScenario()
                .given()  // Подготовка данных
                .generateData(0)  // Генерация 5 случайных строк данных
                .withData("user1", "invalid-date", 800.0)  // Неверный формат даты
                .whenExecuteScript()  // Выполнение скрипта
                .then()  // Проверка результатов
                .containsError("❌ Обнаружена неверная дата");  // Проверка на ошибку в формате даты
    }

    @Test
    public void testScriptFailure() {
        String file = "data.csv";
        // Проверка, что скрипт не может быть выполнен, если файл не существует или есть проблемы с файлом
        new TestScenario()
                .given()  // Подготовка данных
                .generateData(5)  // Генерация 5 случайных строк данных
                .withData("user1", "2025-05-11", 800.0)  // Добавление строки для user1
                .withData("user2", "2025-05-12", 1000.0)  // Добавление строки для user2
                .whenExecuteScript(file)  // Выполнение скрипта
                .then()  // Проверка результатов
                .containsError("Файл не найден: " + file);  // Проверка на ошибку из-за отсутствия файла
    }
}
