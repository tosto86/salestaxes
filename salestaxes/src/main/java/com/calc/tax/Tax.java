package com.calc.tax;

import java.math.BigDecimal;

public interface Tax {

	public BigDecimal Calc(BigDecimal price,String type,boolean isImported);

}
