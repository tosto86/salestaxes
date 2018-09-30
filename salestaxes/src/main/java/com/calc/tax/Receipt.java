package com.calc.tax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public interface Receipt {

	public void generate(String input);
	
	default public void generate(File input) {
		try {
            byte[] bytes = Files.readAllBytes(input.toPath());
            generate(new String(bytes,"UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	};
	
	public String toString();
	
	
}
