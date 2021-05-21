package demo.myown;

public interface Subscription<T> {

	public void request(long n);
	
	public void cancel();
}
