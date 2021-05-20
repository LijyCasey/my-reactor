package demo.myown;

public interface Subscriber<T> {

	public void onSubscribe(Subscription sn);
	
	public void onNext(T t);
	
	public void onComplete();
	
	public void onError(Throwable e);
}
