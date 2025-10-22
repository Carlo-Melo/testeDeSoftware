package senai.carlomelo.veiculo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VeiculoCrudSeleniumTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Teste 1: Verificar se a página carrega corretamente")
    void testPaginaCarrega() {
        driver.get("http://localhost:" + port);

        Assertions.assertTrue(driver.getTitle().contains("Gerenciamento de Veículos"));
        Assertions.assertTrue(driver.getPageSource().contains("Cadastrar Veículo"));
        Assertions.assertTrue(driver.getPageSource().contains("Recarregar Lista"));
    }

    @Test
    @Order(2)
    @DisplayName("Teste 2: Criar um novo veículo")
    void testCriarVeiculo() throws InterruptedException {
        driver.get("http://localhost:" + port);

        WebElement btnCadastrar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Cadastrar Veículo')]")
            )
        );
        btnCadastrar.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("modalCadastro"))
        );

        driver.findElement(By.id("cadDescricao")).sendKeys("Civic EX");
        driver.findElement(By.id("cadFabricante")).sendKeys("Honda");
        driver.findElement(By.id("cadCor")).sendKeys("Preto");
        driver.findElement(By.id("cadPlaca")).sendKeys("ABC-1234");

        WebElement btnSalvar = driver.findElement(
            By.xpath("//div[@id='modalCadastro']//button[contains(text(), 'Salvar')]")
        );
        btnSalvar.click();

        Thread.sleep(1000);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Civic EX"));
        Assertions.assertTrue(pageSource.contains("Honda"));
        Assertions.assertTrue(pageSource.contains("Preto"));
        Assertions.assertTrue(pageSource.contains("ABC-1234"));
    }

    @Test
    @Order(3)
    @DisplayName("Teste 3: Editar um veículo existente")
    void testEditarVeiculo() throws InterruptedException {
        driver.get("http://localhost:" + port);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));

        WebElement btnEditar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn-editar")
            )
        );
        btnEditar.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("modalEditar"))
        );

        WebElement inputDescricao = driver.findElement(By.id("editDescricao"));
        inputDescricao.clear();
        inputDescricao.sendKeys("Civic EXL Atualizado");

        WebElement inputCor = driver.findElement(By.id("editCor"));
        inputCor.clear();
        inputCor.sendKeys("Azul");

        WebElement btnSalvarEdicao = driver.findElement(
            By.xpath("//div[@id='modalEditar']//button[contains(text(), 'Salvar')]")
        );
        btnSalvarEdicao.click();

        Thread.sleep(1000);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Civic EXL Atualizado"));
        Assertions.assertTrue(pageSource.contains("Azul"));
    }

    @Test
    @Order(4)
    @DisplayName("Teste 4: Validação de campos obrigatórios no cadastro")
    void testValidacaoCamposObrigatorios() throws InterruptedException {
        driver.get("http://localhost:" + port);

        WebElement btnCadastrar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Cadastrar Veículo')]")
            )
        );
        btnCadastrar.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalCadastro")));

        WebElement btnSalvar = driver.findElement(
            By.xpath("//div[@id='modalCadastro']//button[contains(text(), 'Salvar')]")
        );
        btnSalvar.click();

        Thread.sleep(500);
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        Assertions.assertTrue(alertText.contains("Preencha todos os campos"));
        driver.switchTo().alert().accept();
    }

    @Test
    @Order(5)
    @DisplayName("Teste 5: Cancelar cadastro de veículo")
    void testCancelarCadastro() throws InterruptedException {
        driver.get("http://localhost:" + port);

        WebElement btnCadastrar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Cadastrar Veículo')]")
            )
        );
        btnCadastrar.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("modalCadastro"))
        );

        driver.findElement(By.id("cadDescricao")).sendKeys("Teste Cancelar");
        driver.findElement(By.id("cadFabricante")).sendKeys("Teste");

        WebElement btnCancelar = driver.findElement(
            By.xpath("//div[@id='modalCadastro']//button[contains(text(), 'Cancelar')]")
        );
        btnCancelar.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modalCadastro")));

        String pageSource = driver.getPageSource();
        Assertions.assertFalse(pageSource.contains("Teste Cancelar"));
    }

    @Test
    @Order(6)
    @DisplayName("Teste 6: Deletar um veículo")
    void testDeletarVeiculo() throws InterruptedException {
        driver.get("http://localhost:" + port);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));

        WebElement primeiraLinha = driver.findElement(By.cssSelector("tbody tr"));

        WebElement btnDeletar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn-deletar")
            )
        );
        btnDeletar.click();

        Thread.sleep(500);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        Thread.sleep(1000);
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        Assertions.assertTrue(alertText.toLowerCase().contains("deletado"));
        driver.switchTo().alert().accept();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));

    }

    @Test
    @Order(7)
    @DisplayName("Teste 7: Recarregar lista de veículos")
    void testRecarregarLista() throws InterruptedException {
        driver.get("http://localhost:" + port);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));

        WebElement btnRecarregar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Recarregar Lista')]")
            )
        );
        btnRecarregar.click();

        Thread.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));

        Assertions.assertTrue(driver.getPageSource().contains("Gerenciamento de Veículos"));
    }
}