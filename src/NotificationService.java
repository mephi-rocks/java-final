/**
 * Сервис для отправки уведомлений пользователю.
 */
public class NotificationService {

    /**
     * Метод для уведомления пользователя об определенных событиях.
     * @param message сообщение уведомления
     */
    public void notify(String message) {
        System.out.println("Notification: " + message);
    }
}