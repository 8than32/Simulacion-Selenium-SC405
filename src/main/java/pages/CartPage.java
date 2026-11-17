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
public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By itemName = By.className("inventory_item_name");
    private final By removeBackpackBtn = By.id("remove-sauce-labs-backpack");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public String obtenerNombreProductoEnCarrito() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemName)).getText();
    }

    public void eliminarMochilaDelCarrito() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBackpackBtn)).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(itemName, 0));
    }

    public boolean carritoEstaVacio() {
        return driver.findElements(itemName).isEmpty();
    }
}