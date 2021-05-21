package demo.myown;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Mono<T> implements Publisher<T> {

	private T data;
	
	public static <T> Mono<T> just(T data) {
		Mono<T> t = new Mono<>();
		t.data=data;
		return t;
	}
	
	public Mono<T> filter(Predicate<T> predicate){
		return new MonoFilter<>(this, predicate);
	}
	
	@Override
	public void subscribe(Subscriber<T> subscriber) {
		subscriber.onSubscribe(new MonoSubscription<T>(data, subscriber));
	}

	
	static class MonoSubscription<T> implements Subscription<T>{
		T actual;
		Subscriber<T> subscriber;
		boolean isCanceled;
		
		public MonoSubscription(T t,Subscriber<T> sub) {
			this.actual = t;
			this.subscriber = sub;
		}

		@Override
		public void request(long n) {
//			for(long i =0;i<n;i++) {
//				subscriber.onNext(actual);
//			}
			if(isCanceled) {
				return;
			}
			if(n>0 && actual!=null) {
				subscriber.onNext(actual);
				cancel();
				subscriber.onComplete();
			}
		}

		@Override
		public void cancel() {
			if(!isCanceled) {
				isCanceled = true;
			}
		}
		
	}

}
