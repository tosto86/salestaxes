package com.calc.tax;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ReceiptSalesTaxXmlImp implements Receipt {
	
	private List<String> exceptgoods = Arrays.asList("book", "chocolate", "pills");
	
	private List<Good> list = new ArrayList<Good>();
	
	private BigDecimal Total = new BigDecimal(0);
	
	private BigDecimal Tax = new BigDecimal(0);
	
	private double precision = 0.05;
	
	public static final String IMPORTEDTOKEN = " imported";
	
	public static final String CR = System.getProperty("line.separator");

	@Override
	public void generate(String input) {
		    
			try {
				Tax salestax = (p,t,i) -> {
					Optional<String> optional = exceptgoods.stream()
							.filter(x -> t.toUpperCase().contains(x.toUpperCase()))
						    .findFirst();
					int perc = ((optional.isPresent() ? 0 : 1)*10)+(5*(i? 1 : 0));
					BigDecimal d = ((p.multiply(new BigDecimal(perc))).divide(new BigDecimal(100)));
					return new BigDecimal(Math.ceil(d.doubleValue() * (1/precision)) / (1/precision)).setScale(2, RoundingMode.HALF_EVEN);
					
				};
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(input));
				Document doc = builder.parse(is);
				NodeList nList = doc.getElementsByTagName("Good");
				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String Type = eElement.getElementsByTagName("Type").item(0).getChildNodes().item(0).getNodeValue();
						String Quantity = eElement.getElementsByTagName("Quantity").item(0).getChildNodes().item(0).getNodeValue();
						String Imported = eElement.getElementsByTagName("Imported").item(0)!=null ? eElement.getElementsByTagName("Imported").item(0).getChildNodes().item(0).getNodeValue() : null;
						String Price = eElement.getElementsByTagName("Price").item(0).getChildNodes().item(0).getNodeValue();
						Good g = new Good();
						g.setQuantity(Integer.parseInt(Quantity));
						g.setType(Type);
						g.setImported(Imported!=null && Imported.equalsIgnoreCase("true"));
						g.setPrice(new BigDecimal(Price));
						BigDecimal tax = salestax.Calc(g.getPrice(), g.getType(), g.isImported());
						g.setTax(tax);
						Tax = Tax.add(tax);
						Total = Total.add(g.getPrice().add(tax));
						list.add(g);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
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
