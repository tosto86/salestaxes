package salestaxes;

import java.io.File;

import org.junit.Test;

import com.calc.tax.Receipt;
import com.calc.tax.ReceiptSalesTaxImpl;

public class SalesTaxStringTest {
	
	@Test
	public void testInputString() {
		String input2 = "1 imported box of chocolates at 10.00\r\n" + 
				"1 imported bottle of perfume at 47.50"; 
		
		String input1 = "1 book at 12.49\r\n" + 
				"1 music CD at 14.99\r\n" + 
				"1 chocolate bar at 0.85";
					
		String input3 = "1 imported bottle of perfume at 27.99\r\n" + 
				"1 bottle of perfume at 18.99\r\n" + 
				"1 packet of headache pills at 9.75\r\n" + 
				"1 box of imported chocolates at 11.25";
		
		Receipt r = new ReceiptSalesTaxImpl();
		r.generate(input1);
		System.out.println(r.toString());
		r = new ReceiptSalesTaxImpl();
		r.generate(input2);
		System.out.println(r.toString());
		r = new ReceiptSalesTaxImpl();
		r.generate(input3);
		System.out.println(r.toString());
		
	}
	
	@Test
	public void testInputFile() {
		
		ClassLoader classLoader = new SalesTaxStringTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());
        Receipt r = new ReceiptSalesTaxImpl();
		r.generate(file);
		System.out.println(r.toString());
		
	}

}
