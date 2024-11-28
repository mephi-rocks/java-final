import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для управления и обработки ссылок.
 */
public class LinkService {
    private final Map<String, Link> links = new HashMap<>(); // Хранение всех ссылок
    private final NotificationService notificationService = new NotificationService();
    private final LinkShortener linkShortener = new LinkShortener();
    private final Config config;

    /**
     * Конструктор, инициализирующий конфигурацию для сервиса.
     * @param config объект конфигурации для управления настройками системы
     */
    public LinkService(Config config) {
        this.config = config;
    }

    /**
     * Метод для создания новой сокращенной ссылки.
     * @param longUrl полный URL для сокращения
     * @param userId идентификатор пользователя, создающего ссылку
     * @return возвращает сокращенный URL
     */
    public String createLink(String longUrl, String userId) {
        int clickLimit = config.getIntProperty("link.click.limit", 5); // Берем лимит переходов из конфигурации
        long expirationDuration = config.getLongProperty("link.expiration.time", 86400000L); // Время жизни ссылки
        String shortLink = linkShortener.shortenURL(longUrl, userId);
        Link link = new Link(shortLink, longUrl, userId, clickLimit, expirationDuration);
        links.put(shortLink, link);
        return shortLink;
    }

    /**
     * Метод для доступа и открытия ссылки.
     * @param shortLink сокращенная ссылка для перехода
     */
    public void accessLink(String shortLink) {
        System.out.println("Attempting to access link: " + shortLink);

        if (!links.containsKey(shortLink)) {
            // Если ссылка не найдена, отправляем уведомление
            notificationService.notify("Ссылка не найдена.");
            return;
        }

        Link link = links.get(shortLink);
        if (System.currentTimeMillis() > link.getExpirationTime()) {
            // Проверка, не истек ли срок действия ссылки
            notificationService.notify("Срок действия ссылки истек.");
            return;
        }

        if (link.getCurrentClicks() >= link.getClickLimit()) {
            // Проверка, не исчерпан ли лимит переходов
            notificationService.notify("Лимит переходов для ссылки исчерпан.");
            return;
        }

        // Увеличиваем количество переходов
        link.incrementClicks();
        System.out.println("Opening link: " + link.getLongUrl());

        // Открытие ссылки в браузере
        try {
            Desktop.getDesktop().browse(new URI(link.getLongUrl()));
        } catch (IOException | URISyntaxException e) {
            notificationService.notify("Не удалось открыть ссылку: " + e.getMessage());
        }
    }

    /**
     * Метод для удаления всех просроченных ссылок.
     */
    public void removeExpiredLinks() {
        // Удаляем ссылки, срок действия которых истек
        links.entrySet().removeIf(entry -> System.currentTimeMillis() > entry.getValue().getExpirationTime());
    }
}