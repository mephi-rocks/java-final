import java.util.UUID;

/**
 * Класс для сокращения длинных URL до коротких ссылок.
 */
public class LinkShortener {

    /**
     * Метод для генерации сокращенной ссылки.
     * @param longUrl длинный URL, который необходимо сократить
     * @param userId идентификатор пользователя, для которого создается ссылка
     * @return сокращенная ссылка
     */
    public String shortenURL(String longUrl, String userId) {
        // Создает уникальный идентификатор для URL, создавая уникальную сокращенную ссылку
        return "clck.ru/" + UUID.randomUUID().toString().substring(0, 8);
    }
}