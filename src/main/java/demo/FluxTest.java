package demo;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class FluxTest {

	public static void main(String[] args) {
		Flux.just(1,2,3,4,5).subscribe(new Subscriber<Integer>() {

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("on subscribe.");
				subscription.request(3);
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("on next:" + item);
			}

			@Override
			public void onError(Throwable throwable) {
				
			}

			@Override
			public void onComplete() {
				System.out.println("on complete");
			}
		});
	}
}
