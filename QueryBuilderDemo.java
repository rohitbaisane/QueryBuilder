package querybuilder;

import java.util.List;

public class QueryBuilderDemo {

    public static void main(String[] args) {
        QueryBuilder q1 = new QueryBuilder();
        
        //query1
        String query1 = q1.select("name", "age")
                         .from("users")
                         .where("age > 30")
                         .orderBy("name")
                         .build();
               
        System.out.println(query1);
      QueryBuilder q2 = new QueryBuilder();
        
       //query2
       String query2 = q2.select(List.of("employee.id","employee.name","employee_sal.salary"))
                        .from("employee", "employee_sal")
                        .join("left join")
                        .on("employee.id == employee_sal.id")
                        .build();
                        
        System.out.println(query2);
        
        
        //wrong query
        
//        QueryBuilder q3 = new QueryBuilder();
//        
//        String query3 = q3.select("name","age")
//                        .from("users")
//                        .groupBy("name")
//                        .build();
                                      
    }   
 }