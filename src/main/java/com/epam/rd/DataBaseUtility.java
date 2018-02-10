package com.epam.rd;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;


public class DataBaseUtility
{
		private static BasicDataSource dataSource;

		public static BasicDataSource getDataSource()
		{

				if (dataSource == null)
				{
						BasicDataSource ds = new BasicDataSource();
						ds.setUrl("jdbc:mysql://localhost:3307/epm?useUnicode=true&characterEncoding=utf-8");
						ds.setUsername("root");
						ds.setPassword("wanted0813");
						ds.setMinIdle(5);
						ds.setMaxIdle(10);
						ds.setMaxOpenPreparedStatements(100);

						dataSource = ds;
				}
				return dataSource;
		}
}
