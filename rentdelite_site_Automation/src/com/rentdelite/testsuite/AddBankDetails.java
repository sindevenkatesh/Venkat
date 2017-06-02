package com.rentdelite.testsuite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.rentdelite.config.Constants;
import com.rentdelite.page.CxDetails_Page;
import com.rentdelite.page.CxHome_Page;

public class AddBankDetails extends Constants {
	static Logger Log = Logger.getLogger(AddBankDetails.class.getName());

	@Test
	public void BankDetails() throws IOException, InterruptedException, ClassNotFoundException, SQLException {

		PropertyConfigurator.configure(path0);
		Log.info("Add BankDetails Test Initiated");
		ProfilesIni pr = new ProfilesIni();
		FirefoxProfile fp = pr.getProfile("venkyProfile");
		driver = new FirefoxDriver(fp);
		driver.get("http://45.114.246.123");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(40, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		CxDetails_Page BANK = PageFactory.initElements(driver, CxDetails_Page.class);
		CxHome_Page CxHomePage = PageFactory.initElements(driver, CxHome_Page.class);
		Cx_ResultSet dbresults = PageFactory.initElements(driver, Cx_ResultSet.class);
		// Customer Login
		Row r = eo.getRowData(path1, 1, 1);
		String Email = r.getCell(0).getStringCellValue();
		BANK.HomeCxLogin(wait, r);
		Thread.sleep(5000);
		// Customer home page PopUp
		CxHomePage.BillingPop(r);
		CxHomePage.EmailPop(r);
		// Manage payment Options Link
		BANK.BankInfoDetails(fwait, r);
		dbresults.CxBank(Email);
		dbresults.getActiveBanks(Email);
		BANK.CxLogout();
		Log.info("============================================");
		Log.info("Add BankDetails Test is Successfully done");
		Log.info("============================================");
	}
}
