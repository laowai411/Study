package square;

import java.util.Scanner;

public class square_3 {

	private static Scanner in2;

	public static void main(String[] args) {
	int a;
	int b;
	int c;
	System.out.println("请输入三个正整数：");
	in2 = new Scanner(System.in);
	a=in2.nextInt();
	b=in2.nextInt();
	c=in2.nextInt();
	if(a<=0||b<=0||c<=0)
	{
		System.out.println("输入的必须是正整数！");
	}
	if((a+b)>c&&(a+c)>b&&(b+c)>a)
	{
		System.out.println("能够构成三角形！");
	}
	else {
		System.out.println("不能构成三角形！");
		}
	
	}
}