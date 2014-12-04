package result;

public class result {

	public static void main(String[] args) {
		int a=1, b=1 , c=0;
		System.out.print("ì³²¨ÄÇÆõÊıÁĞ:");
		System.out.print(a+" ");
		for(int i=1;i<=1000;i++ ){
			c=a+b;
			b=a;
			a=c;	
			System.out.print(" "+c+" ");
		}
	}

}
