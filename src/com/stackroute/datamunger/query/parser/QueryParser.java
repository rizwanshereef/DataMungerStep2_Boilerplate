package com.stackroute.datamunger.query.parser;

/*There are total 4 DataMungerTest file:
 *
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 *
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 *
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 *
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 *
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 *
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 *
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class QueryParser
{
	private QueryParameter queryParameter = new QueryParameter();
	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString)
	{
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBaseQuery(getBaseQuery(queryString));
		queryParameter.setOrderByFields(getOrderByFields(queryString));
		queryParameter.setGroupByFields(getGroupByFields(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));
		return queryParameter;
	}
	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString)
	{
		String strFrom = queryString.split("from")[1].trim();
		String strFileName = strFrom.split(" ")[0].trim();
		return strFileName;
	}
	/*
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	public String getBaseQuery(String queryString)
	{
		String strBaseQuery = "";
		if(queryString.contains("where"))
		{
			strBaseQuery = queryString.toLowerCase().split("where")[0].trim();
		}
		else if(queryString.contains("group by")||queryString.contains("order by"))
		{
			strBaseQuery = queryString.toLowerCase().split("group by|order by")[0].trim();
		}
		else
		{
			strBaseQuery = queryString;
		}
		return strBaseQuery;
	}
	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public ArrayList<String> getOrderByFields(String queryString)
	{
		String str = queryString.toLowerCase();
		String[] strOrderByFields = null;
		ArrayList<String> list = new ArrayList<String>();
		if(str.contains("where")&&(str.contains("order by")))
		{
			String strWhere = str.split("where")[1].trim();
			String strOrderByString = strWhere.split("order by")[1].trim();
			if(strOrderByString.contains(","))
			{
				strOrderByFields = strOrderByString.split(",");
				for(int i = 0;i<str.length();i++)
				{
					list.add(strOrderByFields[i]);
				}
			}
			else
			{
				list.add(strOrderByString);
			}
			return list;
		}
		else if(str.contains("order by"))
		{
			String strNotWhere = str.split("order by")[1].trim();
			if(strNotWhere.contains(",")) {
				strOrderByFields = strNotWhere.split(",");
				for(int i = 0;i<strOrderByFields.length;i++)
				{
					list.add(strOrderByFields[i]);
				}
			}
			else
			{
				list.add(strNotWhere);
			}
			return list;
		}
		else
		{
			return null;
		}
	}
	public static void main(String[] args)
	{
		QueryParser qp = new QueryParser();
		System.out.println(qp.getOrderByFields("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner order by city"));
	}
	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public ArrayList<String> getGroupByFields(String queryString)
	{
		String str = queryString.toLowerCase();
		String[] strGroupByFields = null;
		ArrayList<String> list = new ArrayList<String>();
		if(str.contains("where")&&(str.contains("group by"))&&str.contains("order by"))
		{
			String whereString = str.split("where")[1].trim();
			String groupByString = whereString.split("group by")[1].trim();
			String strbeforeOrderBy= groupByString.split("order by")[0].trim();
			if(strbeforeOrderBy.contains(","))
			{
				strGroupByFields = strbeforeOrderBy.split(",");
				for(int i = 0;i<strGroupByFields.length;i++)
				{
					list.add(strGroupByFields[i]);
				}
			}
			else
			{
				list.add(strbeforeOrderBy);
			}
			return list;
		}
		else if(str.contains("group by"))
		{
			String notWhereString = str.split("group by")[1].trim();
			if(notWhereString.contains(","))
			{
				strGroupByFields = notWhereString.split(",");
				for(int i = 0;i<strGroupByFields.length;i++)
				{
					list.add(strGroupByFields[i]);
				}
			}
			else
			{
				list.add(notWhereString);
			}
			return list;
		}
		else
		{
			return null;
		}
	}
	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public ArrayList<String> getFields(String queryString)
	{
		String strSelect = queryString.toLowerCase().split("select")[1].trim();
		String strFrom = strSelect.split("from")[0].trim();
		String[] selectFields =null;
		ArrayList<String> list = new ArrayList<String>();
		if(strFrom.contains(","))
		{
			selectFields = strFrom.split(",");
			for(int i=0;i<selectFields.length;i++)
			{
				list.add(selectFields[i]);
			}
			return list;
		}
		else
		{
			list.add(strFrom);
			return list;
		}
	}
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 *
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 *
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 *
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 *
	 */
	public ArrayList<Restriction> getRestrictions(String queryString)
	{
		String query = queryString;
		String[] conditions = null;
		ArrayList<Restriction> restrictionList = null;
		if(query.contains("where"))
		{
			String conditionQuery = query.split("where|group by|order by")[1].trim();
			conditions = conditionQuery.split(" and | or ");
			restrictionList = new ArrayList<Restriction>();
			for(int i = 0; i < conditions.length; i++)
			{
				if(conditions[i].contains("'"))
				{
					String var = conditions[i].split(" ")[0];
					String restriction[] = conditions[i].split("'");
					Restriction r = new Restriction(var.trim(),restriction[1].trim(),restriction[0].trim().split(" ")[1]);
					restrictionList.add(r);
				}
				else
				{
					String restriction[] = conditions[i].split(" ");
					Restriction r = new Restriction(restriction[0].trim(),restriction[2].trim(),restriction[1].trim());
					restrictionList.add(r);
				}
			}
		}
		return restrictionList;
	}
	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 *
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public ArrayList<String> getLogicalOperators(String queryString)
	{
		String s = queryString.toLowerCase();
		String[] strAndOr = null;
		ArrayList<String> list = new ArrayList<String>();
		if(s.contains("where"))
		{
			String strwhere = s.split("where")[1].trim();
			strAndOr = strwhere.split(" ");
			for(int i = 0;i < strAndOr.length;i++)
			{
				if(strAndOr[i].equals("and")||strAndOr[i].equals("or"))
				{
					list.add(strAndOr[i]);
				}
			}
			return list;
		}
		else
		{
			return null;
		}
	}
	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 *
	 * Please note that more than one aggregate function can be present in a query.
	 *
	 *
	 */
	public ArrayList<AggregateFunction> getAggregateFunctions(String queryString)
	{
		String strFrom = queryString.toLowerCase().split("from")[0].trim();
		String strSelect = strFrom.split("select")[1].trim();
		String[] strFieldsAndAggrfunc = strSelect.split(",");
		ArrayList<String> myAggrFuncList = new  ArrayList<String>();
		ArrayList<AggregateFunction> list = new  ArrayList<AggregateFunction>();
		for(int i = 0;i < strFieldsAndAggrfunc.length;i++)
		{
			if(strFieldsAndAggrfunc[i].contains("("))
			{
				myAggrFuncList.add(strFieldsAndAggrfunc[i].trim());
			}
		}
		int listSize = myAggrFuncList.size();
		if(listSize == 0)
		{
			return null;
		}
		else
		{
			for(int i=0;i<listSize;i++)
			{
				String[] aggrFuncArray = myAggrFuncList.get(i).split("\\(|\\)");
				AggregateFunction af = new AggregateFunction(aggrFuncArray[1], aggrFuncArray[0]);
				list.add(af);
			}
			return list;
		}
	}
}
