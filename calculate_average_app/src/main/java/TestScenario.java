import steps.DataGenerator;
import steps.ScriptRunner;

public class TestScenario {

    private DataGenerator dataGenerator;
    private ScriptRunner scriptRunner;

    public TestScenario() {
        this.dataGenerator = new DataGenerator();
        this.scriptRunner = new ScriptRunner();
    }

    // Метод given() для инициализации данных
    public TestScenario given() {
        // Инициализация данных, но не выполнение действий
        System.out.println("Initializing data...");
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод when() для выполнения действий (например, запуск скрипта)
    public TestScenario whenExecuteScript(String inputFile) {
        // Выполнение действий
        System.out.println("Executing actions...");
        scriptRunner.calculateAverageExitSuccessfully(inputFile);  // Запуск скрипта
        return this;  // Возвращаем объект для продолжения цепочки
    }

    public TestScenario whenExecuteScript() {
        // Выполнение действий
        System.out.println("Executing actions...");
        scriptRunner.calculateAverageExitSuccessfully();  // Запуск скрипта
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод then() для проверки результатов
    public TestScenario then() {
        // Проверка результатов
        System.out.println("Verifying results...");
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для генерации данных
    public TestScenario generateData(int numRows) {
        dataGenerator.generateData(numRows);
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для добавления данных
    public TestScenario withData(String user, String date, double amount) {
        dataGenerator.withData(user, date, amount);
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для проверки результата
    public TestScenario contains(String user, String month, double expectedAverage) {
        scriptRunner.contains(user, month, expectedAverage);
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для проверки ошибки
    public TestScenario containsError(String expectedError) {
        scriptRunner.containsError(expectedError);  // Проверяем, что ошибка присутствует в выводе
        return this;  // Возвращаем объект для продолжения цепочки
    }
}
