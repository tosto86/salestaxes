package com.calc.tax.entity;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public static final String IMPORTEDTOKEN = " imported";

    public static final String CR = System.getProperty("line.separator");

    public List<Good> getList() {
        return list;
    }

    public void setList(List<Good> list) {
        this.list = list;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setTotal(BigDecimal total) {
        Total = total;
    }

    public BigDecimal getTax() {
        return Tax;
    }

    public void setTax(BigDecimal tax) {
        Tax = tax;
    }

    private List<Good> list;

    private BigDecimal Total;

    private BigDecimal Tax;

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

}
