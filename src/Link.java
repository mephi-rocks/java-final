/**
 * Класс представляющий короткую ссылку.
 */
public class Link {
    private final String shortLink;          // Сокращенная ссылка
    private final String longUrl;            // Длинный исходный URL
    private final String userId;             // UUID пользователя
    private final int clickLimit;            // Максимально допустимое количество переходов
    private final long expirationTime;       // Время истечения срока действия ссылки
    private int currentClicks;               // Текущее количество переходов

    /**
     * Конструктор для создания новой ссылки с ограничениями.
     * @param shortLink сокращенная версия URL
     * @param longUrl полный исходный URL
     * @param userId идентификатор пользователя
     * @param clickLimit максимальное количество переходов
     * @param expirationDuration продолжительность времени жизни ссылки в миллисекундах
     */
    public Link(String shortLink, String longUrl, String userId, int clickLimit, long expirationDuration) {
        this.shortLink = shortLink;
        this.longUrl = longUrl;
        this.userId = userId;
        this.clickLimit = clickLimit;
        this.expirationTime = System.currentTimeMillis() + expirationDuration;
        this.currentClicks = 0;
    }

    public String getShortLink() {
        return shortLink;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public int getCurrentClicks() {
        return currentClicks;
    }

    public int getClickLimit() {
        return clickLimit;
    }

    /**
     * Метод увеличивает количество переходов по ссылке на единицу.
     */
    public void incrementClicks() {
        currentClicks++;
    }
}