import java.util.Scanner;
import java.util.UUID;

/**
 * Класс запуска программы для управления ссылочными операциями.
 */
public class Main {
    public static void main(String[] args) {
        // Инициализация конфигурации
        Config config = new Config("config.properties");
        // Создание сервиса для управления ссылками
        LinkService linkService = new LinkService(config);
        // Создаем сканер для чтения ввода пользователя из консоли
        Scanner scanner = new Scanner(System.in);
        boolean exit = false; // Флаг для выхода из цикла

        while (!exit) {
            // Печать доступных команд для пользователя
            System.out.println("\nВведите команду:");
            System.out.println("create: Создать ссылку");
            System.out.println("access: Перейти по ссылке");
            System.out.println("cleanup: Удалить просроченные ссылки");
            System.out.println("exit: Выход");

            // Получаем команду от пользователя
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "create":
                    System.out.println("Введите полную URL-ссылку:");
                    String longUrl = scanner.nextLine();
                    // Генерация UUID для пользователя
                    String userId = UUID.randomUUID().toString();
                    // Создание ссылки
                    String shortLink = linkService.createLink(longUrl, userId);
                    System.out.println("Создана сокращенная ссылка: " + shortLink);
                    System.out.println("ID пользователя: " + userId);
                    break;

                case "access":
                    System.out.println("Введите сокращенную ссылку:");
                    // Получение URL для доступа
                    String linkToAccess = scanner.nextLine();
                    linkService.accessLink(linkToAccess);
                    break;

                case "cleanup":
                    // Очистка просроченных ссылок
                    linkService.removeExpiredLinks();
                    System.out.println("Просроченные ссылки удалены.");
                    break;

                case "exit":
                    // Завершение работы программы
                    exit = true;
                    break;

                default:
                    // Обработка неверных команд
                    System.out.println("Неверная команда, попробуйте еще раз.");
            }
        }

        // Закрытие сканера после завершения работы программы
        scanner.close();
    }
}