package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Eithan Samuel Quesada Milián
 * Universidad Fidelitas
 *
 */
public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.className("title");
    private final By addBackpackBtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By menuBtn = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public String obtenerTituloSeccion() {
        return driver.findElement(pageTitle).getText();
    }

    public void agregarMochilaAlCarrito() {
        driver.findElement(addBackpackBtn).click();
    }

    public String obtenerCantidadCarrito() {
        return driver.findElement(cartBadge).getText();
    }

    public void irAlCarrito() {
        driver.findElement(cartLink).click();
    }

    public void cerrarSesion() {
        driver.findElement(menuBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}