package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Eithan Samuel Quesada Milián
 * Universidad Fidelitas
 *
 */
public class LoginPage {
    private final WebDriver driver;

    // Localizadores de elementos web
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorBanner = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void iniciarSesion(String usuario, String clave) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(usuario);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(clave);
        driver.findElement(loginButton).click();
    }

    public String obtenerMensajeError() {
        return driver.findElement(errorBanner).getText();
    }
}