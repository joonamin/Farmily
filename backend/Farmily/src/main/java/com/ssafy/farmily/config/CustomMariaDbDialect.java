package com.ssafy.farmily.config;

import org.hibernate.dialect.MariaDBDialect;

public class CustomMariaDbDialect extends MariaDBDialect {
	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
	}
}
