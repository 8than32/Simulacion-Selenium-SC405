package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eithan Samuel Quesada Milián
 * Universidad Fidelitas
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceDemoSimulationTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeEach
    void iniciarNavegador() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }
    
    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("CP-01: Inicio de sesión exitoso con credenciales válidas")
    void testInicioSesionExitoso() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");

        String resultadoEsperado = "Products";
        String resultadoObtenido = inventoryPage.obtenerTituloSeccion();

        assertEquals(resultadoEsperado, resultadoObtenido,
                "El título de la página principal debe ser 'Products' tras iniciar sesión.");
    }

    @Test
    @Order(2)
    @DisplayName("CP-02: Bloqueo de acceso con credenciales inválidas")
    void testInicioSesionCredencialesInvalidas() {
        loginPage.iniciarSesion("usuario_invalido", "clave_erronea");

        String resultadoEsperado = "Epic sadface: Username and password do not match any user in this service";
        String resultadoObtenido = loginPage.obtenerMensajeError();

        assertEquals(resultadoEsperado, resultadoObtenido,
                "Debe mostrarse alerta de credenciales inválidas.");
    }

    @Test
    @Order(3)
    @DisplayName("CP-03: Validación de usuario bloqueado (locked_out_user)")
    void testUsuarioBloqueado() {
        loginPage.iniciarSesion("locked_out_user", "secret_sauce");

        String resultadoEsperado = "Epic sadface: Sorry, this user has been locked out.";
        String resultadoObtenido = loginPage.obtenerMensajeError();

        assertEquals(resultadoEsperado, resultadoObtenido,
                "El sistema debe impedir el acceso a usuarios bloqueados.");
    }

    @Test
    @Order(4)
    @DisplayName("CP-04: Adición de producto al carrito desde el catálogo")
    void testAgregarProductoAlCarrito() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");
        inventoryPage.agregarMochilaAlCarrito();

        String resultadoEsperado = "1";
        String resultadoObtenido = inventoryPage.obtenerCantidadCarrito();

        assertEquals(resultadoEsperado, resultadoObtenido,
                "El contador visual del carrito debe reflejar 1 artículo agregado.");
    }

    @Test
    @Order(5)
    @DisplayName("CP-05: Verificación y eliminación de artículo dentro del carrito")
    void testEliminarProductoDelCarrito() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");
        inventoryPage.agregarMochilaAlCarrito();
        inventoryPage.irAlCarrito();

        assertEquals("Sauce Labs Backpack", cartPage.obtenerNombreProductoEnCarrito());

        cartPage.eliminarMochilaDelCarrito();
        assertTrue(cartPage.carritoEstaVacio(),
                "El carrito debe quedar vacío tras eliminar el único artículo.");
    }

    @Test
    @Order(6)
    @DisplayName("CP-06: Cierre de sesión seguro desde el menú lateral")
    void testCierreSesionSeguro() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");
        inventoryPage.cerrarSesion();

        String urlEsperada = "https://www.saucedemo.com/";
        String urlObtenida = driver.getCurrentUrl();

        assertEquals(urlEsperada, urlObtenida,
                "Al cerrar sesión debe redirigir a la pantalla principal de login.");
    }
}