
public class Test_2 {
	static byte g;
	static short h;
	static int a  ;
	static long bL;
	static double c;
	static float d;
	static char f;
	static boolean j;
	public static void main(String[] args) {
		
		System.out.println("数据类型的默认值分别为：");
		System.out.println(a );
		System.out.println(bL);
		System.out.println(c);
		System.out.println(d);
		System.out.println(f);
		System.out.println(j);
		System.out.println(g);
		System.out.println(h);
		
		System.out.println(" ");
		
		byte g = java.lang.Byte.MAX_VALUE;
		short h = java.lang.Short.MAX_VALUE;
	    int a = java.lang.Integer.MAX_VALUE;
	    long bL = java.lang.Long.MAX_VALUE;
	    double c = java.lang.Double.MAX_VALUE;
	    float d = java.lang.Float.MAX_VALUE;
	    char f = java.lang.Character.MAX_VALUE;
	    
		System.out.println("int的最大值为："+a );
		System.out.println("long类型的最大值为："+bL);
		System.out.println("double类型的最大值为："+c);
		System.out.println("float类型的最大值为："+d);
		System.out.println("char类型的最大值为："+f);
		System.out.println("byte类型的最大值为："+g);
		System.out.println("short类型的最大值为："+h);
	    
	}
	

}
