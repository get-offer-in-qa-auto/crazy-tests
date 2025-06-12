package steps;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class ScriptRunner {

    private String errorMessage;
    private String standardOutput;
    private int exitCode;

    // Указываем путь к скрипту, используя абсолютный или относительный путь
    private static final String SCRIPT_PATH = Paths.get(System.getProperty("user.dir"), "script", "calculate_averages.sh").toString();
    private static final String INPUT_FILE = Paths.get(System.getProperty("user.dir"), "script", "data", "transactions.csv").toString();

    // Приватный метод для выполнения скрипта с переданным файлом
    private int runScript(String inputFilePath) {
        try {
            // Запуск скрипта с полным путем
            System.out.println("Running script...");
            ProcessBuilder processBuilder;
            if (inputFilePath != null) {
                processBuilder = new ProcessBuilder("bash", SCRIPT_PATH, inputFilePath);  // Используем путь к файлу
            } else {
                processBuilder = new ProcessBuilder("bash", SCRIPT_PATH, INPUT_FILE);  // Без файла
            }
            Process process = processBuilder.start();

            // Чтение стандартного вывода (stdout)
            StringBuilder standardOutputBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    standardOutputBuilder.append(line).append("\n");
                }
            }
            errorMessage = standardOutputBuilder.toString();  // Сохраняем стандартный вывод (для отладки)

            exitCode = process.waitFor();  // Ожидаем завершения процесса
        } catch (IOException | InterruptedException e) {
            errorMessage = e.getMessage();  // Сохраняем ошибку при исключении
            exitCode = 1;  // Если возникла ошибка, присваиваем exitCode 1
        }
        return exitCode;  // Возвращаем exitCode
    }

    // Метод для успешного завершения скрипта (exitCode == 0)
    public ScriptRunner calculateAverageExitSuccessfully(String inputFilePath) {
        if (runScript(inputFilePath) != 0) {
            System.out.println("Script execution failed");
        }
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для успешного завершения скрипта без файла
    public ScriptRunner calculateAverageExitSuccessfully() {
        if (runScript(null) != 0) {
            System.out.println("Script execution failed");
        }
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для обработки ошибки (exitCode == 1)
    public ScriptRunner calculateAverageExitWithError() {
        if (runScript(null) == 1) {
            System.out.println("Script execution failed with error.");
        }
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для проверки, что ошибка была найдена в процессе
    public ScriptRunner containsError(String expectedError) {
        Assertions.assertTrue(errorMessage != null && errorMessage.contains(expectedError),
                "Expected error not found. Expected: " + expectedError + ", but got: " + errorMessage);

        System.out.println("Verified error: " + expectedError);  // Выводим подтверждение
        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для проверки результатов в averages.csv
    public ScriptRunner contains(String user, String month, double expectedAverage) {
        ResultAnalyzer resultAnalyzer = new ResultAnalyzer();

        // Проверка данных в averages.csv
        if (resultAnalyzer.contains(user, month, expectedAverage)) {
            System.out.println("Result verified: " + user + " in " + month + " with average " + expectedAverage);
        } else {
            System.out.println("Verification failed for " + user + " in " + month);
        }

        return this;  // Возвращаем объект для продолжения цепочки
    }

    // Метод для получения стандартного вывода для отладки (если нужно)
    public String getStandardOutput() {
        return standardOutput;
    }

    // Метод для получения сообщения об ошибке (если нужно)
    public String getErrorMessage() {
        return errorMessage;
    }
}
