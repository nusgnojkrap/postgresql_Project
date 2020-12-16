
package db;

import java.sql.*;
import java.util.Scanner;

public class DB {
	
	 public static void main(String[] args) throws Exception
	    {
	        try
	        {
	  //1. DB����
	            Scanner sc = new Scanner(System.in);
	            System.out.println("SQL Programming Test");
	            System.out.println();
	            System.out.println("Connecting PostgreSQL database :");
	            
	            // JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
	            Connection conn =null;
	            Statement stmt =null;
	            ResultSet rs = null;
	            PreparedStatement pstmt = null;
	            
	            ////////////////////////////////////////////////////////////////////////////////////////
	            String url = "jdbc:postgresql://localhost:5432";//"jdbc:postgresql://���� ip/db��"
	            String user = "DBassign";//"���̺� ���鶧 �� owner"
	            String password = "12345";//"postgre���"
	            ////////////////////////////////////////////////////////////////////////////////////////

	            try {
	            	
	                conn = DriverManager.getConnection(url, user, password);
	                stmt = conn.createStatement();
	                rs = stmt.executeQuery("SELECT VERSION()");

	                if (rs.next())
	                    System.out.println(rs.getString(1));
	           
	            } catch (SQLException sqlEX) {
	                System.out.println(sqlEX);
	            } finally {
	                try {
	                    rs.close();
	                    stmt.close();
	                    conn.close();
	                 
	                } catch (Exception  sqlEX) {
	                    System.out.println( sqlEX);
	                }
	            }
	            System.out.println();
	            
	            try {
	                conn = DriverManager.getConnection(url, user, password);
	                stmt = conn.createStatement();
	                rs = stmt.executeQuery("SELECT VERSION()");

	                if (rs.next())
	                    System.out.println(rs.getString(1));
	            } catch (SQLException sqlEX) {
	                System.out.println(sqlEX);
	            } 
	            System.out.println();
	        
	      
	            System.out.println("'ôô ���� �ڻ�'�� ����Ͻ� ���� �ִٸ� 2��, �ƴϸ� 1���� �Է����ּ���.");
	            int enrollCheck = sc.nextInt();
	      
	     //2. ȸ������

	     //2-1. ����� ȸ������
	            if(enrollCheck == 1) {
	            System.out.println("----------�� ȸ������ ��� ----------");
	            System.out.print("# ���̵�  : ");
	            String userId = sc.next();
	            System.out.print("# ��й�ȣ  : ");
	            String userPw = sc.next();
	            System.out.print("# �ڵ��� ��ȣ  (- ����)   : ");
	            String phNum= sc.next();
	            System.out.println("--------------------------------");
	           try {
	              conn = DriverManager.getConnection(url,user,password);
	              stmt = conn.createStatement();
	              String in_sql = "INSERT INTO Customer (userId,userPw,phNum) VALUES ('"+
	                           userId+"','"+userPw+"','"+phNum+"');";
	               stmt.executeUpdate(in_sql);
	           } catch ( Exception e ) {
	              System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	              e.printStackTrace();
	              System.exit(0);
	            }
	            
	            System.out.println("�� ȸ������ �Ϸ�!");
	           }else if(enrollCheck ==2) {

	     //3-1. ����� �α��� 
	            System.out.println("---------- �� �α���  ---------");
	            System.out.print("���̵�  : ");
	            String customName = sc.next();
	            System.out.print("��й�ȣ  : ");
	            String customPw = sc.next();
	            
	            int check = 0;
	            
	            try {

	              String check_sql = "SELECT * FROM Customer WHERE userid = '"+ customName +"'";
	              pstmt =  conn.prepareStatement(check_sql);
	          //    pstmt.setString(1, customName);
	          //    pstmt.setString(2, customPw);
	              
	              rs = pstmt.executeQuery();
	              
	              if(rs.next()) {//���̵� ��ġ�� ���
	                 String dbPw = rs.getString("userPw");
	                 if(dbPw.contentEquals(customPw)) {//��й�ȣ�� ��ġ�� ���
	                    check =1;
	              
	                    System.out.println("ȯ���մϴ� '"+ customName+"'��");
	                     System.out.println("----------------------------------");
	                     
	                     
	                     
	     //////////////////////////////////////////////////////////////d=����                
	                     
	                     //������ ã��
	                     
	                     try {
	                        
	                        System.out.print("# ���۳�¥(YYYYMMDD)  : ");
	                         String want_Start_date = sc.next();//���ϴ� ���۳�¥ �Է�
	                        System.out.print("# ����¥(YYYYMMDD)  : ");
	                         String want_End_date = sc.next();//���ϴ� ����¥ �Է�

	                         
	                         
	                         String check_sql1 = "SELECT stageName, address, phnum, seatNum, parking, startDate, endDate, stageNum "
	                               + "From stage "
	                               + "where '"+ want_Start_date +"' <=startDate and '"+ want_End_date +"' >= endDate";
	                       //���ϴ� ���۳�¥���� ū ���۳�¥, ���ϴ� ����¥���� ���� ����¥���� ������ ���� ����      
	                         
	                         conn = DriverManager.getConnection(url, user, password);
	                        pstmt = conn.prepareStatement(check_sql1);//Stage table ��������
	                        rs = pstmt.executeQuery();
	                        System.out.println();
	                        
	                         

	                          while(rs.next()) {
	                             System.out.printf("%s\t%s\t%s\t%s\t%s\t%d\t%d\t%d\t\n"
	                                     ,rs.getString("stageName")
	                                     ,rs.getString("address")
	                                     ,rs.getString("phNum")
	                                   ,rs.getString("seatNum")
	                                   ,rs.getString("parking")
	                                   ,rs.getInt("startDate")
	                                   ,rs.getInt("endDate")
	                                   ,rs.getInt("stageNum"));
	                       }
	                     }catch (Exception e) {
	                          e.printStackTrace();
	                      }
	                        
	                        
	                                    
	                     try {     
	                        System.out.print("# ���ϴ� ������ ��ȣ �Է� : ");
	                        int want_stageNum = sc.nextInt();//���ϴ� ������ ��ȣ �Է�
	                        
	                        String check_sql2 = "SELECT stageName, address, phNum, seatNum, parking, startDate, endDate, stageNum "
	                                 + "From Stage "
	                                 + "Where stageNum = '"+ want_stageNum + "'";//??�� Stage table�� ��ȣ ������ �Է�
	                           //������ ������ ���� ������ 
	                        
	                        
	                        //stage table���� ������ ������ ����
	                        
	                        
	                        
	                        
	                        
	                        
	                        pstmt = conn.prepareStatement(check_sql2);//Stage table ��������
	                        rs = pstmt.executeQuery();
	                        
	                       while(rs.next()) {
	                           if(want_stageNum == rs.getInt("stageNum")) {
	                               System.out.printf("%s\t%s\t%s\t%s\t%s\t%d\t%d\t%d\t\n"
	                                       ,rs.getString("stageName")
	                                       ,rs.getString("address")
	                                       ,rs.getString("phNum")
	                                     ,rs.getString("seatNum")
	                                     ,rs.getString("parking")
	                                     ,rs.getInt("startDate")
	                                     ,rs.getInt("endDate")
	                                     ,rs.getInt("stageNum"));
	                         
	                               
//	                               String address = rs.getString("address");
//	                               String startdate = rs.getString("startDate");
//	                               
	                                  
	                                   
	                                   //������ ������� ������ stamp table�� �ֱ�
	                               String check_sql3 = "INSERT INTO Stamp VALUES('"+customName+"','"+ rs.getString("address")+"','"+rs.getString("startDate")+"')";
	                               stmt.executeUpdate(check_sql3);               
	                               
	                                   
	                                   //Customer update
	                                   String check_sql4 = "UPDATE Customer SET stampNum = (SELECT count(*) FROM Stamp WHERE userID = '"+ customName +"')";
	                                   stmt.executeUpdate(check_sql4);               
	                                
	                                   
	                                   System.out.println("\n��ϿϷ�\n");
	                                   
	                            }
	                        }
	                     }catch (Exception e) {
	                         e.printStackTrace();
	                     }
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     ////////////////////////////////////////////////////���
	                     
	                     
	                     
	                 }
	                 else {//��й�ȣ�� Ʋ�� ���
	                    check = 0;
	                    System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�.");
	                 }
	              }else {//���̵� Ʋ�� ���
	                  System.out.println("��ġ�ϴ� ������ �����ϴ�.");
	              }
	           } catch ( Exception e ) {
	              System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	              e.printStackTrace();
	              System.exit(0);
	           }
	           }
	            
	            

	        System.out.println("Continue? (Enter 1 for continue)");
	        sc.nextLine();

	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	        } catch(Exception ex)
	        {
	            throw ex;
	        }
	    }
}
