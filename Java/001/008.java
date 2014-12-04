import java.util.Scanner;

public class Days {
	private static Scanner in22;

	public static void main(String[] args) {
		int a , b ;
		System.out.println("请输入你要查询的月数：");
		in22 = new Scanner(System.in);
		a = in22.nextInt();
		b = in22.nextInt();
		if(13>b && b>0){
		if(b == 2  ){
			if(a % 4 == 0 && a % 100 != 0 || a % 400 == 0 ){
				System.out.println("该月有的天数为：" + 29);
			}
			
			else { System.out.println("该月有的天数为：" + 28);
				
			}
			
		}
		else if(b % 2 == 0){System.out.println("该月的天数为："+31);
			
		}
		else {System.out.println("该月的天数为："+30); }
		}
		else{System.out.println("输入错误！");
			
		}
	}
	}


