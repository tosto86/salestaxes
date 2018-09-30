package salestaxes;

import java.io.File;

import com.calc.tax.entity.Receipt;
import org.junit.Test;

import com.calc.tax.Interface.ReceiptGenerator;
import com.calc.tax.impl.ReceiptGeneratorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SalesTaxStringTest {


	ReceiptGenerator r;


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

		Receipt receipt = r.generate(input1);
		System.out.println(receipt.toString());
        receipt = r.generate(input2);
        System.out.println(receipt.toString());
        receipt= r.generate(input3);
        System.out.println(receipt.toString());
		
	}


	public void testInputFile() {

        ClassLoader classLoader = new SalesTaxStringTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

        Receipt receipt = r.generate(file);
        System.out.println(receipt.toString());

    }

    @Test
    public void AppTest(){

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        SalesTaxStringTest m = (SalesTaxStringTest) context.getBean("main");
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
