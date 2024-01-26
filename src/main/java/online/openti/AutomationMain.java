package online.openti;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AutomationMain {
    public static void main(String[] args) {
        // System.setProperty("webdriver.chrome.driver", "D:\\browserdrivers\\chrome\\121\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        var testLogin = loginTest(driver);

        if (testLogin) {
            searchTest(driver, "cafeteira");
        }

        driver.quit();
    }

    private static boolean loginTest(WebDriver driver) {
        try {
            driver.get("https://www.amazon.com.br/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com.br%2Fref%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=brflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0");
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email")));
            inputEmail.sendKeys("email");

            WebElement buttonContinue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("continue")));
            buttonContinue.click();

            String url = driver.getCurrentUrl();
            int index = url.indexOf("/signin/");
            String resultado = "";

            if (index != -1) {
                // Obter a parte da string antes da barra de "signin/"
                resultado = url.substring(0, index + "/signin/".length() - 1);
            } else {
                System.out.println("A barra de 'signin/' não foi encontrada na string.");
            }

            if (resultado.equals("https://www.amazon.com.br/ap/signin")) {
                WebElement inputPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
                inputPassword.sendKeys("password");

                WebElement buttonLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInSubmit")));
                buttonLogin.click();
            }

            System.out.println("Teste de login passou com sucesso!");

            return true;
        } catch (Exception e) {
            System.err.println("Teste falhou: " + e.getMessage());
            return false;
        }
    }

    private static void searchTest(WebDriver driver, String termo) {
        try {
            driver.get("https://www.amazon.com.br");
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement inputSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
            inputSearch.sendKeys(termo);

            WebElement buttonSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search-submit-button")));
            buttonSearch.click();

            System.out.println("Teste de pesquisa passou com sucesso!");
        } catch (Exception e) {
            System.err.println("Teste de pesquisa falhou: " + e.getMessage());
        }
    }
}