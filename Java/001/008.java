import java.util.Scanner;

public class Days {
	private static Scanner in22;

	public static void main(String[] args) {
		int a , b ;
		System.out.println("��������Ҫ��ѯ��������");
		in22 = new Scanner(System.in);
		a = in22.nextInt();
		b = in22.nextInt();
		if(13>b && b>0){
		if(b == 2  ){
			if(a % 4 == 0 && a % 100 != 0 || a % 400 == 0 ){
				System.out.println("�����е�����Ϊ��" + 29);
			}
			
			else { System.out.println("�����е�����Ϊ��" + 28);
				
			}
			
		}
		else if(b % 2 == 0){System.out.println("���µ�����Ϊ��"+31);
			
		}
		else {System.out.println("���µ�����Ϊ��"+30); }
		}
		else{System.out.println("�������");
			
		}
	}
	}


