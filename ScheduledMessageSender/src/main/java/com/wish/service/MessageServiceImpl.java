package com.wish.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.wish.dto.Message;
import com.wish.dto.MessageDTO;

/**
 * The Class MessageServiceImpl.
 */
@Service
public class MessageServiceImpl implements MessageService {

	/** The driver. */
	private WebDriver driver;

	/** The local date time. */
	LocalDateTime localDateTime = LocalDateTime.now();

	/** The month predicate. */
	Predicate<Integer> monthPredicate = month -> month == localDateTime.getMonthValue();

	/** The day predicate. */
	Predicate<Integer> dayPredicate = day -> day == localDateTime.getDayOfMonth();

	/** The year predicate. */
	Predicate<Integer> yearPredicate = year -> year == localDateTime.getYear();

	/** The hour predicate. */
	Predicate<Integer> hourPredicate = hour -> hour == localDateTime.getHour();

	/** The minute predicate. */
	Predicate<Integer> minutePredicate = minute -> minute == localDateTime.getMinute();

	/**
	 * Send message.
	 *
	 * @param messageDTO the message DTO
	 */
	@Override
	public void sendMessage(MessageDTO messageDTO) {

		HashMap<String, String> filteredNames = new HashMap<>();
		for (Message message : messageDTO.getMessages()) {
			getFilteredNames(message, filteredNames);
		}
		System.out.println(filteredNames.keySet());
		setup(filteredNames);

	}

	/**
	 * Gets the filtered names.
	 *
	 * @param message       the message
	 * @param filteredNames the filtered names
	 * @return the filtered names
	 */
	private void getFilteredNames(Message message, HashMap<String, String> filteredNames) {

		if (monthPredicate.test(message.getMonth()) && dayPredicate.test(message.getDay())
				&& yearPredicate.test(message.getYear()) && hourPredicate.test(message.getHour())
				&& minutePredicate.test(message.getMinutes()))
			filteredNames.put(message.getName(), message.getMessage());
	}

	/**
	 * Setup.
	 *
	 * @param filteredNames the filtered names
	 */
	public void setup(HashMap<String, String> filteredNames) {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("user-data-<LOCATION TO YOUR CHROME USER DATA>");
		driver = new ChromeDriver();
		driver.get("https://web.whatsapp.com/");
		System.out.println(driver.getTitle());

		try {
			Thread.sleep(5000);
			for (Entry<String, String> entry : filteredNames.entrySet()) {
				WebElement chatElement = driver
						.findElement(By.xpath("//div[contains(@class,'_3FRCZ copyable-text selectable-text')]"));
				String name = entry.getKey();
				System.out.println("Name:" + name);
				chatElement.sendKeys(name);
				Thread.sleep(5000);
				WebElement findElement = driver.findElement(By.xpath("//span[@title=\'" + name + "\']"));
				findElement.click();
				while (true) {
					Thread.sleep(5000);
					WebElement parentElement = driver.findElement(By.xpath(
							"//div[contains(@class,'_2FVVk _2UL8j')]//div[contains(@class,'_3FRCZ copyable-text selectable-text')]"));
					System.out.println("Message:" + entry.getValue());
					parentElement.sendKeys(entry.getValue());
					WebElement findElement3 = driver.findElement(By.xpath("//button[@class ='_1U1xa']"));
					findElement3.click();
					break;
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

	}

}
