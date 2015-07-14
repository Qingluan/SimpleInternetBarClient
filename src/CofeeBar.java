import Tools.L;






public class CofeeBar {

			

	public static void main(String[] args) {
		CoffeeBarDB fe = new CoffeeBarDB();
		try {
			fe.CreateClient(100);
			fe.SighIn(23, 3);
			fe.SighIn("513321199309180014",44,4);
			L.l(fe.remainTime(44));
			L.l(fe.queryClient(24));
			fe.SighOut("513321199309180014");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}