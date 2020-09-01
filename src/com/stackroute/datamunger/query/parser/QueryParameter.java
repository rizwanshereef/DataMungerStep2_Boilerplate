package com.stackroute.datamunger.query.parser;
import java.util.List;
/*
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	private String fileName, baseQuery, queryString, querytype;
	private List<Restriction> restrictions;
	private List<String> logicalOperators, fields, groupByFields, orderByFields;
	private List<AggregateFunction> aggregateFunctions;
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}
	public String getBaseQuery() {
		return baseQuery;
	}
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}
	public List<String> getLogicalOperators() {
		return logicalOperators;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}
	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getQuerytype() {
		return querytype;
	}
	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}
}