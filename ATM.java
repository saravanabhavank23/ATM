package ATM;
import java.util.*;
import java.sql.*;
public class ATM2 {
static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean st=true;
		while(st) {
		System.out.println("WELCOME TO SMART ATM");
		System.out.println("1.Existing Customer");
		System.out.println("2.New Customer,Create an Account");
		System.out.println("3.Exit");
		System.out.println("Enter the option");
		int opt=sc.nextInt();
		switch(opt){
		case 1 :
			usermenu();
			break;
		case 2:
			createAcc();
			break;
		case 3:
			System.out.println("Thank You For Using Our Services");
			st=false;break;
		default :
			System.out.println("Please Enter an valid Option!");
		}
		}
	}
	static void usermenu() {
		boolean state=true;
		while(state) {
		System.out.println("1.Check Balance");
		System.out.println("2.deposit");
		System.out.println("3.Withdraw");
		System.out.println("4.Back");
		System.out.println("Enter the Choice");
		int choice=sc.nextInt();
		switch (choice) {
		case 1:
			checkbalance();
			break;
		case 2:
			deposit();
			break;
		case 3:
			withdraw();
			break;
		case 4:
			state=false;
			break;
		default:
			System.out.println("Enter a valid  choice");
		}
		}
	}
	static void checkbalance() {
		try(Connection con=getconnection()){
			System.out.println("Enter the Account Number");
			String acn=sc.next();
			System.out.println("Enter the password");
			String pas=sc.next();
			PreparedStatement ps=con.prepareStatement("select * from details where Account_no=? and password=?");
			ps.setString(1, acn);
			ps.setString(2, pas);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("Current Balance : "+rs.getDouble(3));
			}
			else {
				System.out.println("Invalid username or password");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	static void deposit() {
		try(Connection con=getconnection()){
		System.out.println("Enter the Account Number");
		String acn=sc.next();
		System.out.println("Enter the password");
		String pas=sc.next();
		System.out.println("Enter Amount of Deposition");
		Double ub=sc.nextDouble();
		PreparedStatement ps=con.prepareStatement("select * from details where Account_no=? and password=?");
		ps.setString(1, acn);
		ps.setString(2, pas);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
		Double nb=ub+rs.getDouble("balance");
		PreparedStatement pst=con.prepareStatement("update details set balance=? where Account_no=? and password=?");
		pst.setDouble(1, nb);
		pst.setString(2, acn);
		pst.setString(3, pas);
		int rst=pst.executeUpdate(); 
		System.out.println("Deposited successfully ");
		System.out.println("Current Balance : "+nb);
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	static void withdraw() {
		try(Connection con=getconnection()){
			System.out.println("Enter the Account Number");
			String acn=sc.next();
			System.out.println("Enter the password");
			String pas=sc.next();
			System.out.println("Enter Amount of Withdrawal");
			Double wb=sc.nextDouble();
			PreparedStatement ps=con.prepareStatement("select * from details where Account_no=? and password=?");
			ps.setString(1, acn);
			ps.setString(2, pas);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
			Double cb=rs.getDouble("balance");
			Double nb=cb-wb;
			if(wb<=cb) {
			PreparedStatement pst=con.prepareStatement("update details set balance=? where Account_no=? and password=?");
			pst.setDouble(1, nb);
			pst.setString(2, acn);
			pst.setString(3, pas);
			int rst=pst.executeUpdate();
			System.out.println("Withdrawal Success");
			System.out.println("Current Balance : "+nb);
			}
			else {
				System.out.println("Insufficient Balance");
			}
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	static Connection getconnection() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Root");
			return con;
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	static void createAcc() {
		try(Connection con=getconnection()) {
			System.out.println("Enter the New Account Number");
			String accno=sc.next();
			System.out.println("Enter the Account Holder Name");
			String ah=sc.next();
			boolean stc=true;
			while(stc) {
			System.out.println("Enter the Initial  Amount(Minimum than 1000)");
			int ad=sc.nextInt();
			boolean stcc=true;
			if(ad>999) {
			while(stcc) {
			System.out.println("Enter the New Password(Password should have 6 numbers)");
			String np=sc.next();
			if(np.length()==6) {
			stcc=false;
			PreparedStatement ps=con.prepareStatement("insert into  details values(?,?,?,?)");
			ps.setString(1, accno);
			ps.setString(2, ah);
			ps.setInt(3,ad);
			ps.setString(4, np);
			int rs=ps.executeUpdate();
			System.out.println("Account created Successfully");
			System.out.println("Thank you");
			stc=false;
			}
			else {
				System.out.println("The Password Must Have 6 Characters");
			}
			}
			}
			else {
				System.out.println("Initial Amount Is Insufficient!");
			}
			}
		}
		catch (Exception e) {
			System.out.println("Account Number Already  Exist , Please Enter An Another Account Number");
			
		}
	}

}
