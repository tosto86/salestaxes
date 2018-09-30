package salestaxes;

import java.io.File;

import com.calc.tax.entity.Receipt;
import org.junit.Test;

import com.calc.tax.Interface.ReceiptGenerator;
import com.calc.tax.impl.ReceiptGeneratorXmlImp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SalesTaxXmlTest {

	ReceiptGenerator r;
	

	public void testInputString() {
		String input1 = "<Goods>\r\n" + 
				"<Good>\r\n" + 
				"<Type>box of chocolates</Type>\r\n" + 
				"<Quantity>1</Quantity>\r\n" + 
				"<Imported>true</Imported>\r\n" + 
				"<Price>10.00</Price>\r\n" + 
				"</Good>\r\n" + 
				"<Good>\r\n" + 
				"<Type>bottle of perfume</Type>\r\n" + 
				"<Quantity>1</Quantity>\r\n" + 
				"<Imported>true</Imported>\r\n" + 
				"<Price>47.50</Price>\r\n" + 
				"</Good>\r\n" + 
				"</Goods>";

		Receipt receipt = r.generate(input1);
		System.out.println(receipt.toString());
	}

	public void testInputFile() {
		
		ClassLoader classLoader = new SalesTaxStringTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.xml").getFile());
        Receipt receipt = r.generate(file);
		System.out.println(receipt.toString());
		
	}

	@Test
	public void AppTest(){

		ApplicationContext context =
				new ClassPathXmlApplicationContext("spring.xml");

		SalesTaxXmlTest m = (SalesTaxXmlTest) context.getBean("mainxml");
		m.testInputString();
		m.testInputFile();
	}

	public ReceiptGenerator getR() {
		return r;
	}

	public void setR(ReceiptGenerator r) {
		this.r = r;
	}

}
