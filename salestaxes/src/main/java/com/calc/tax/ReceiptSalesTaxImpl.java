package com.calc.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptSalesTaxImpl implements Receipt {
	
	private List<String> exceptgoods = Arrays.asList("book", "chocolate", "pills");

	public static final String IMPORTEDTOKEN = " imported";
	
	public static final String REGEX = "^(\\d+)\\s(\\w+(\\s\\w+)*)\\s(at)\\s(\\d+(\\.\\d+)?)$";
	
	public static final String CR = System.getProperty("line.separator");
	
	private List<Good> list = new ArrayList<Good>();
	
	private BigDecimal Total = new BigDecimal(0);
	
	private BigDecimal Tax = new BigDecimal(0);
	
	private double precision = 0.05;
	

	@Override
	public void generate(String input) {
		if (input==null) throw new IllegalStateException("input non presente"); 
		Tax salestax = (p,t,i) -> {
			Optional<String> optional = exceptgoods.stream()
					.filter(x -> t.toUpperCase().contains(x.toUpperCase()))
				    .findFirst();
			int perc = ((optional.isPresent() ? 0 : 1)*10)+(5*(i? 1 : 0));
			BigDecimal d = ((p.multiply(new BigDecimal(perc))).divide(new BigDecimal(100)));
			return new BigDecimal(Math.ceil(d.doubleValue() * (1/precision)) / (1/precision)).setScale(2, RoundingMode.HALF_EVEN);
			
		};
		String[] goodsline = input.split(CR);
		Pattern pattern = Pattern.compile(REGEX);
		list = new ArrayList<Good>();
		BigDecimal tax;
		for (String k : goodsline) {
			Matcher matcher = pattern.matcher(k.replace(IMPORTEDTOKEN, ""));
			if (!matcher.matches()) throw new IllegalStateException("input errato"); 
			Good g = new Good();
			g.setQuantity(Integer.parseInt(matcher.group(1)));
			g.setType(matcher.group(2));
			g.setImported(k.contains(IMPORTEDTOKEN));
			g.setPrice(new BigDecimal(matcher.group(5)));
			tax = salestax.Calc(g.getPrice(), g.getType(), g.isImported());
			g.setTax(tax);
			Tax = Tax.add(tax);
			Total = Total.add(g.getPrice().add(tax));
			list.add(g);
		}		
	}
	
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Good g : list) {
			b.append(g.getQuantity());
			b.append(g.isImported() ? IMPORTEDTOKEN : "");
			b.append(" ");
			b.append(g.getType());
			b.append(": ");
			b.append(g.getTax().add(g.getPrice()));
			b.append(CR);
		}
		b.append("Sales Taxes: "+Tax+CR);
		b.append("Total: "+Total);
		return b.toString();
	}
	
	public List<String> getExceptgoods() {
		return exceptgoods;
	}

	public void setExceptgoods(List<String> exceptgoods) {
		this.exceptgoods = exceptgoods;
	}

}
