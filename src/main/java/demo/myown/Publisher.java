package demo.myown;

public interface Publisher<T> {

	public void subscribe(Subscriber<T> subscriber);
}
