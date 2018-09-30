package com.calc.tax.Interface;

import com.calc.tax.entity.Receipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public interface ReceiptGenerator {

	public Receipt generate(String input);
	
	default public Receipt generate(File input) {
	    Receipt r = null;
		try {
            byte[] bytes = Files.readAllBytes(input.toPath());
            r = generate(new String(bytes,"UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
	};
	
	public String toString();
	
	
}
