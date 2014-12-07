
public class Test_02_005 {

	public static void main(String[] args) {
		int a ;
		for(a=7;a<1000;a++){
			if( a % 2 == 1&&a % 3 == 1&&a % 4 == 1&&a % 5 == 1&&a % 6 == 1){
								System.out.print("这个数最小为："+ a);
								break;
							}
		}
	}
}
