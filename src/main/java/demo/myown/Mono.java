package demo.myown;

public class Mono<T> implements Publisher<T> {

	private T data;
	
	public static <T> Mono<T> just(T data) {
		Mono<T> t = new Mono<>();
		t.data=data;
		return t;
	}
	
	@Override
	public void subscribe(Subscriber<T> subscriber) {
		
	}


}
