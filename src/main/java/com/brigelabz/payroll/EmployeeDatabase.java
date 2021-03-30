package com.brigelabz.payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase {

    private Connection getConnection() throws IllegalAccessException {
        String JDBCURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String UserName = "root";
        String Password = "root123";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Dreiver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalAccessException(String.format("Driver not found in classpath%s", e));

        }
        try {
            System.out.println("Connecting to database" + JDBCURL);
            connection = DriverManager.getConnection(JDBCURL, UserName, Password);
            System.out.println("Connection succesfully" + connection);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return connection;

    }

    public List<PayrollServiceData> readData() {
        String Sql_Query = "select * from payroll_service_table";
        List<PayrollServiceData> payrollServiceData = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select * from payroll_service_table");
            try (ResultSet resultSet = preparedStatement.executeQuery(Sql_Query)) {

                while (resultSet.next()) {

                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    Date start = resultSet.getDate(3);
                    String gender = resultSet.getString(4);
                    int salary = resultSet.getInt(5);

                    System.out.println();
                    System.out.println("id=" + id);
                    System.out.println("Name=" + name);
                    System.out.println("gender=" + gender);
                    System.out.println("salary=" + salary);
                    System.out.println("start=" + start);

                    PayrollServiceData payrollServiceData1 = new PayrollServiceData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDate(5));
                    payrollServiceData.add(payrollServiceData1);
                }
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return payrollServiceData;
    }

    public long update_Record_into_database_returnCount_Using_PreparedStatement(double salary,int id){
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Update payroll_service_table set salary=? where id=? ; ");
            preparedStatement.setDouble(1,salary);
            preparedStatement.setInt(2,id);
            long resultSet=preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public long insert_New_Record_into_database_returnCount_Using_PreparedStatement(int id, String name,String gender,double salary,String date){
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("insert into payroll_service_table(id,name,gender,salary,start ) values(?,?,?,?,?); ");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,gender);
            preparedStatement.setDouble(4,salary);
            preparedStatement.setString(5, String.valueOf(Date.valueOf(date)));
            int resultSet=preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<PayrollServiceData> Payroll_Data_From_Salary(String Date) {
        String Sql_Query = "select * from payroll_service_table";
        List<PayrollServiceData> payrollServiceData = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select * from payroll_service_table where name>=?");
            preparedStatement.setDate(1, java.sql.Date.valueOf(Date));
            ResultSet resultSet = preparedStatement.executeQuery(Sql_Query);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender=resultSet.getString(3);
                 double salary = resultSet.getInt(4);
                Date start = resultSet.getDate(5);

                System.out.println();
                System.out.println("id=" + id);
                System.out.println("name=" + name);
                System.out.println("gender="+gender);
                System.out.println("salary=" + salary);
                System.out.println("start=" +start);

                PayrollServiceData payrollServiceData1 = new PayrollServiceData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getDate(5));
                payrollServiceData.add(payrollServiceData1);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return payrollServiceData;
    }

    public List<String> dataManipulation(){
        List<String> list=new ArrayList();
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select gender,sum(salary), avg(salary),min(salary),max(salary),count(salary) from payroll_service_table group by gender ");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int index=1;
                System.out.println("gender: "+resultSet.getString(1));
                System.out.println("salary: "+resultSet.getString(2));
                for (int i=0;i<10;i++){
                    if(index<5) {
                        list.add(i, resultSet.getString(index));
                        index++;
                    }
                }
                System.out.println(list);
            }
        }catch (SQLException | IllegalAccessException throwables){
            throwables.printStackTrace();
        }
        return list;
    }

}
