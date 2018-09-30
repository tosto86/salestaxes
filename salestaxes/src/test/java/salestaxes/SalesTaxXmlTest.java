package salestaxes;

import java.io.File;

import org.junit.Test;

import com.calc.tax.Receipt;
import com.calc.tax.ReceiptSalesTaxXmlImp;

public class SalesTaxXmlTest {
	
	@Test
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
		
		Receipt r = new ReceiptSalesTaxXmlImp();
		r.generate(input1);
		System.out.println(r.toString());
	}
	
	@Test
	public void testInputFile() {
		
		ClassLoader classLoader = new SalesTaxStringTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.xml").getFile());
        Receipt r = new ReceiptSalesTaxXmlImp();
		r.generate(file);
		System.out.println(r.toString());
		
	}

}
