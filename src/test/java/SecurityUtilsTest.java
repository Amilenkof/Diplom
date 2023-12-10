import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.security.UserDetailsImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityContextHolder.class})
public class SecurityUtilsTest {

    @Test
    public void testGetCurrentUser() {
        String username = "testUser";
        User expectedUser = new User();

        // Создаем моки для необходимых объектов
        Authentication authenticationMock = mock(Authentication.class);
        UserDetailsImpl userDetailsMock = mock(UserDetailsImpl.class);
        SecurityContext securityContextMock = mock(SecurityContext.class);

        // Задаем ожидаемое поведение моков
        when(authenticationMock.getName()).thenReturn(username);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUser()).thenReturn(expectedUser);

        // Мокируем статические методы
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContextMock);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Вызываем метод для тестирования
        User actualUser = SecurityUtils.INSTANCE.getCurrentUser(username);

        // Проверяем результаты
        Assert.assertEquals(expectedUser, actualUser);
    }
}