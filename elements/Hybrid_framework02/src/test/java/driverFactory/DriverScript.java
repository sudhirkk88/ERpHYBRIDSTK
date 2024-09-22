package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.ExcelFileUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import commonFunctions.FunctionLibrary;

public class DriverScript {

	private static final FunctionLibrary FunctionLiberary = null;
	WebDriver driver;
	String inputpath ="./FileInput/DataEngine02.xlsx";
	String outputpath ="./FileOutput/HybridResults2.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TCSheet ="MasterTestCases";
	public void startTest()throws Throwable
	{
		String Module_status="";
		String Module_new ="";
		//create object for excel file util class
    utilities.ExcelFileUtil xl = new utilities.ExcelFileUtil(inputpath);
		//iterate all test cases in TCSheet
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//store each sheet into one varibale
				String TCModule =xl.getCellData(TCSheet, i, 1);
				report = new ExtentReports("./target/ExtentReports/"+TCModule+FunctionLiberary.generateDate()+".html");
				logger = report.startTest(TCModule);
				logger.assignAuthor("sudhir");
				//iterate all rows in every sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read each cell from TCModule
					String Description =xl.getCellData(TCModule, j, 0);
					String ObjectType =xl.getCellData(TCModule, j, 1);
					String Ltype = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver =FunctionLibrary.startBrowser();
							
						}
						if(ObjectType.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openurl();
						
						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Ltype,Lvalue);
							logger.log(LogStatus.INFO, Description);
						
						}
						if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO, Description);
						
						}
						if(ObjectType.equalsIgnoreCase("closerBrowser"))
						{
							FunctionLibrary.closerBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Ltype, Lvalue, TestData);
						}
						
							if(ObjectType.equalsIgnoreCase("captuserStock"))
							{
								FunctionLibrary.captuserStock(Ltype, Lvalue);
								logger.log(LogStatus.INFO, Description);

						}
							
							if(ObjectType.equalsIgnoreCase("stockTable"))
							{
								FunctionLibrary.stockTable();
								logger.log(LogStatus.INFO, Description);
							}
							if(ObjectType.equalsIgnoreCase("supplierTable"))
							{
								FunctionLibrary.supplierTable();
								logger.log(LogStatus.INFO, Description);
							}
							if(ObjectType.equalsIgnoreCase("captureCus"))
							{
								FunctionLibrary.captureCus(Ltype,Lvalue);
								logger.log(LogStatus.INFO, Description);
								
							}
							if(ObjectType.equalsIgnoreCase("customerTable"))
							{
								FunctionLibrary.customerTable();
								logger.log(LogStatus.INFO, Description);
								

							
							}
							
								
								
								
						//write as pass into status cell in TCModule sheet
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_status ="True";
					}catch(Throwable t)
					{
						System.out.println(t.getMessage());
						//write as Fail into status cell in TCModule sheet
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL,Description);
						Module_new="False";
						//take screen shot
						File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen,new File("./target/screenshot/"+Description+FunctionLiberary.generateDate()+".png"));
					}
					if(Module_status.equalsIgnoreCase("True"))
					{
						xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
					}
					if(Module_new.equalsIgnoreCase("False"))
					{
						xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
					}
					report.endTest(logger);
					report.flush(); 
				}
			}
			else
			{
				//write as blocked for testcases flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
		}
	}

	}

