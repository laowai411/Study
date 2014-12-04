package shuzi;

import java.util.Scanner;

public class shuzi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a , b , temp ;
		int m ;
		System.out.println("请输入两个数字：");
		@SuppressWarnings("resource")
		Scanner in = new Scanner (System.in);
		a = in.nextInt();
		b = in.nextInt();
		if(a<b){
			temp = a ;
			a = b ;
            b = temp ;			
		}
         m = shuzi.d(a, b);
         System.out.println("最大公约数为：" + m);
         System.out.println("最小公倍数为：" + (a * b) / m);
		
	}
	public static int d(int a1 , int b1 ){
		int f ;
		f = a1 % b1;
		while (f>0 ){
			a1 = b1;
			b1 = f;
			f = a1 % b1;
		}
		return (b1);
	}
	

}
