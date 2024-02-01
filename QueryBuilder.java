package querybuilder;

import java.util.*;
public class QueryBuilder {
     private List<String> TableNames;
     private List<String> columnNamesForSelect;
     private List<String> columnNamesForGroup;
     private List<String> columnNamesForOrder;
     private List<String> joins;
     private List<String> conditionsForJoins;
     boolean isAlreadyBuilt;
     String whereCondition;
     
     QueryBuilder(){
         this.columnNamesForSelect = new ArrayList<>(); 
         this.columnNamesForOrder = new ArrayList<>();
         this.columnNamesForGroup = new ArrayList<>();
         this.TableNames = new ArrayList<>();
         this.joins = new ArrayList<>();
         this.conditionsForJoins = new ArrayList<>();
         this.isAlreadyBuilt = false;
     }
     
     public String build(){
         if(isAlreadyBuilt){
             throw new Error("Query is already built with current Query Builder instance");
         }
         StringBuilder expression = new StringBuilder("select ");
         addSelectedColumnNames(expression);
         addTableNamesWithJoins(expression);
         addWhereCondition(expression);
         addGroupBy(expression);
         addOrderBy(expression);
         if(expression.charAt(expression.length()-1) == ' '){
           expression.deleteCharAt(expression.length()-1);
         }
         expression.append(";");
         return expression.toString();
     }
     
     private void addOrderBy(StringBuilder expression){
        if(columnNamesForOrder.isEmpty()) return;
        expression.append("order by ");
        for(String colName: columnNamesForOrder){
           expression.append(colName)
                     .append(",");
        }
        expression.deleteCharAt(expression.length()-1);
        expression.append(" ");
     }
     
     private boolean isListSubsetOfAnother(List<String> l1, List<String> l2){
         HashSet<String> elements = new HashSet<>();
         for(String it : l2) {
            elements.add(it.toLowerCase());
         }
         for(String it: l1){
             if(!elements.contains(it.toLowerCase())) return false;
         }
         return true;
         
     } 
     
     private void addGroupBy(StringBuilder expression){
        if(columnNamesForGroup.isEmpty()) return;
        boolean isListSame = isListSubsetOfAnother(columnNamesForSelect, columnNamesForGroup);
        if(!isListSame){
           throw new Error("selecting columns which are not used for group by");
        }
        expression.append("group by ");
        for(String col: columnNamesForGroup){
            expression.append(col).
                       append(",");
        }
        expression.deleteCharAt(expression.length()-1);
        expression.append(" ");
     }
     
     private void addWhereCondition(StringBuilder expression){
        if(whereCondition == null){
            return;
        }
        expression.append("where ")
                  .append(whereCondition)
                  .append(" ");
     }
     
     private void addTableNamesWithJoins(StringBuilder expression){
         if(TableNames.size() == 0) throw new Error("No Tables Selected");
         if(TableNames.size() != joins.size() + 1){
             throw new Error("Wrong Query Formate");
         }
         if(joins.size() != conditionsForJoins.size()){
             throw new Error("Wrong Query Formate");
         }
         expression.append("from ")
                   .append(TableNames.get(0))
                   .append(" ");
         for(int i=1;i<TableNames.size();i++){
             expression.append(joins.get(i-1))
                        .append(" ")
                        .append(TableNames.get(i))
                        .append(" on ")
                        .append(conditionsForJoins.get(i-1))
                        .append(" ");
         }
     }
     
     private void addSelectedColumnNames(StringBuilder expression){
        if(columnNamesForSelect.isEmpty()){
            throw new Error("No columns for");
        }
        for(String columnName: columnNamesForSelect){
            expression.append(columnName);
            expression.append(",");
        }
        expression.deleteCharAt(expression.length()-1);
        expression.append(" ");
     }
     
     public QueryBuilder select(String fieldName){
         this.columnNamesForSelect.add(fieldName);
         return this;
     }
 
     public QueryBuilder select(String fieldName1,String fieldName2){
         this.columnNamesForSelect.add(fieldName1);
         this.columnNamesForSelect.add(fieldName2);
         return this;
     }
 
     public QueryBuilder select(List<String> columNames){
        this.columnNamesForSelect = columNames; 
        return this;
     }
     
     public QueryBuilder from(String tableName){
         this.TableNames.add(tableName);
         return this;
     }
     
     public QueryBuilder from(String tableName1,String tableName2){
         this.TableNames.add(tableName1);
         this.TableNames.add(tableName2);
         return this;
     }
     
     public QueryBuilder from(List<String> tableNames){
        this.TableNames = tableNames;
        return this;
     }
     
     public QueryBuilder orderBy(String fieldName){
         this.columnNamesForOrder.add(fieldName);
         return this;
     }
 
     public QueryBuilder orderBy(String fieldName1,String fieldName2){
         this.columnNamesForOrder.add(fieldName1);
         this.columnNamesForOrder.add(fieldName2);
         return this;
     }
 
     public QueryBuilder orderBy(List<String> columNames){
         this.columnNamesForOrder = columNames;
         return this;
     }
     public QueryBuilder groupBy(String colName){
         this.columnNamesForGroup.add(colName);
         return this;
     }
     
     public QueryBuilder groupBy(String colName1,String colName2){
         this.columnNamesForGroup.add(colName1);
         this.columnNamesForGroup.add(colName2);
         return this;
     }
     
     public QueryBuilder groupBy(List<String> columNames){
         this.columnNamesForGroup = columNames;
         return this;
     }
     
     public QueryBuilder join(String joinType){
         this.joins.add(joinType);
         return this;
     }
     
     public QueryBuilder join(String joinType1,String joinType2){
         this.joins.add(joinType1);
         this.joins.add(joinType2);
         return this;
     }
     
     public QueryBuilder join(List<String> joinTypes){
         this.joins = joinTypes;
         return this;
     }
    public QueryBuilder on(String joinCondition){
         this.conditionsForJoins.add(joinCondition);
         return this;
     }
     
    public QueryBuilder on(String joinCondition1, String joinCondition2){
        this.conditionsForJoins.add(joinCondition1);
        this.conditionsForJoins.add(joinCondition2);
        return this;
    } 
    
    public QueryBuilder on(List<String> joinConditions){
         this.conditionsForJoins = joinConditions;
         return this;
     }
     
     public QueryBuilder where(String condition){
         if(condition.length() <= 3){
             throw new Error("Invalid where expression");
         }
         this.whereCondition = condition;
         return this;
     }
 
 }    