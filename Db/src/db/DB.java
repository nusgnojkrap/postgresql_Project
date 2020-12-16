
package db;

import java.sql.*;
import java.util.Scanner;

public class DB {
	
	 public static void main(String[] args) throws Exception
	    {
	        try
	        {
	  //1. DB연동
	            Scanner sc = new Scanner(System.in);
	            System.out.println("SQL Programming Test");
	            System.out.println();
	            System.out.println("Connecting PostgreSQL database :");
	            
	            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
	            Connection conn =null;
	            Statement stmt =null;
	            ResultSet rs = null;
	            PreparedStatement pstmt = null;
	            
	            ////////////////////////////////////////////////////////////////////////////////////////
	            String url = "jdbc:postgresql://localhost:5432";//"jdbc:postgresql://본인 ip/db명"
	            String user = "DBassign";//"테이블 만들때 준 owner"
	            String password = "12345";//"postgre비번"
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
	        
	      
	            System.out.println("'척척 공연 박사'에 등록하신 적이 있다면 2번, 아니면 1번을 입력해주세요.");
	            int enrollCheck = sc.nextInt();
	      
	     //2. 회원가입

	     //2-1. 사용자 회원가입
	            if(enrollCheck == 1) {
	            System.out.println("----------고객 회원가입 양식 ----------");
	            System.out.print("# 아이디  : ");
	            String userId = sc.next();
	            System.out.print("# 비밀번호  : ");
	            String userPw = sc.next();
	            System.out.print("# 핸드폰 번호  (- 빼고)   : ");
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
	            
	            System.out.println("고객 회원가입 완료!");
	           }else if(enrollCheck ==2) {

	     //3-1. 사용자 로그인 
	            System.out.println("---------- 고객 로그인  ---------");
	            System.out.print("아이디  : ");
	            String customName = sc.next();
	            System.out.print("비밀번호  : ");
	            String customPw = sc.next();
	            
	            int check = 0;
	            
	            try {

	              String check_sql = "SELECT * FROM Customer WHERE userid = '"+ customName +"'";
	              pstmt =  conn.prepareStatement(check_sql);
	          //    pstmt.setString(1, customName);
	          //    pstmt.setString(2, customPw);
	              
	              rs = pstmt.executeQuery();
	              
	              if(rs.next()) {//아이디가 일치한 경우
	                 String dbPw = rs.getString("userPw");
	                 if(dbPw.contentEquals(customPw)) {//비밀번호가 일치한 경우
	                    check =1;
	              
	                    System.out.println("환영합니다 '"+ customName+"'님");
	                     System.out.println("----------------------------------");
	                     
	                     
	                     
	     //////////////////////////////////////////////////////////////d=여기                
	                     
	                     //공연장 찾기
	                     
	                     try {
	                        
	                        System.out.print("# 시작날짜(YYYYMMDD)  : ");
	                         String want_Start_date = sc.next();//원하는 시작날짜 입력
	                        System.out.print("# 끝날짜(YYYYMMDD)  : ");
	                         String want_End_date = sc.next();//원하는 끝날짜 입력

	                         
	                         
	                         String check_sql1 = "SELECT stageName, address, phnum, seatNum, parking, startDate, endDate, stageNum "
	                               + "From stage "
	                               + "where '"+ want_Start_date +"' <=startDate and '"+ want_End_date +"' >= endDate";
	                       //원하는 시작날짜보다 큰 시작날짜, 원하는 끝날짜보다 작은 끝날짜들의 공연장 정보 나열      
	                         
	                         conn = DriverManager.getConnection(url, user, password);
	                        pstmt = conn.prepareStatement(check_sql1);//Stage table 변수설정
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
	                        System.out.print("# 원하는 공연장 번호 입력 : ");
	                        int want_stageNum = sc.nextInt();//원하는 공연장 번호 입력
	                        
	                        String check_sql2 = "SELECT stageName, address, phNum, seatNum, parking, startDate, endDate, stageNum "
	                                 + "From Stage "
	                                 + "Where stageNum = '"+ want_stageNum + "'";//??에 Stage table에 번호 변수명 입력
	                           //선택한 공연장 정보 보여줌 
	                        
	                        
	                        //stage table에서 선택한 공연장 삭제
	                        
	                        
	                        
	                        
	                        
	                        
	                        pstmt = conn.prepareStatement(check_sql2);//Stage table 변수설정
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
	                                  
	                                   
	                                   //선택한 공연장과 유저를 stamp table에 넣기
	                               String check_sql3 = "INSERT INTO Stamp VALUES('"+customName+"','"+ rs.getString("address")+"','"+rs.getString("startDate")+"')";
	                               stmt.executeUpdate(check_sql3);               
	                               
	                                   
	                                   //Customer update
	                                   String check_sql4 = "UPDATE Customer SET stampNum = (SELECT count(*) FROM Stamp WHERE userID = '"+ customName +"')";
	                                   stmt.executeUpdate(check_sql4);               
	                                
	                                   
	                                   System.out.println("\n등록완료\n");
	                                   
	                            }
	                        }
	                     }catch (Exception e) {
	                         e.printStackTrace();
	                     }
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     ////////////////////////////////////////////////////요기
	                     
	                     
	                     
	                 }
	                 else {//비밀번호가 틀린 경우
	                    check = 0;
	                    System.out.println("비밀번호가 틀렸습니다.");
	                 }
	              }else {//아이디가 틀린 경우
	                  System.out.println("일치하는 정보가 없습니다.");
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
