import java.util.Scanner;


public class Test_02_004 {

	public static void main(String[] args) {
		int a ;
	 System.out.println("请输入一个数字：");
	 Scanner in = new Scanner(System.in);
		a = in.nextInt();
		if(a>1)
			for(int c=1 ; c<=a;c++ ){
		{
			for(int i=1 ; i <= c ; i++){
				
			System.out.print(" "+ i);
		
			}
			for(int i=c-1;i>=1;i--){
				System.out.print(" "+ i);	
			}
		}System.out.println(" ");
   }
}
}