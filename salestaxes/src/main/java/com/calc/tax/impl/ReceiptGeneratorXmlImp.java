package com.calc.tax.impl;

import com.calc.tax.entity.Good;
import com.calc.tax.Interface.ReceiptGenerator;
import com.calc.tax.entity.Receipt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReceiptGeneratorXmlImp implements ReceiptGenerator {
	
	private List<String> exceptgoods = Arrays.asList("book", "chocolate", "pills");
	
	private double precision = 0.05;

	@Override
	public Receipt generate(String input) {

        List<Good> list = new ArrayList<Good>();
        BigDecimal Tax = new BigDecimal(0);
        BigDecimal Total = new BigDecimal(0);
        try {
            com.calc.tax.Interface.Tax salestax = (p, t, i) -> {
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
        Receipt r = new Receipt();
        r.setList(list);
        r.setTotal(Total);
        r.setTax(Tax);
        return r;

	}

	public List<String> getExceptgoods() {
		return exceptgoods;
	}

	public void setExceptgoods(List<String> exceptgoods) {
		this.exceptgoods = exceptgoods;
	}

}
