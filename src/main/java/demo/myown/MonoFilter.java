package demo.myown;

import java.util.function.Predicate;

public class MonoFilter<T> extends Mono<T> {

	private Mono<T> source;

	private Predicate<T> predicate;

	public MonoFilter(Mono<T> actual, Predicate<T> predicate) {
		this.source = actual;
		this.predicate = predicate;
	}

	public void subscribe(Subscriber<T> subscriber) {
//		subscriber.onSubscribe(new FilterSubscription<>(subscriber,this));
		source.subscribe(new FilterSubscription<>(subscriber, predicate));
	}

	static class FilterSubscription<T> implements Subscription<T>, Subscriber<T> {
		private Subscriber<T> actual;

		private Predicate<T> predicate;

		private Subscription<T> subscriptionFromUpstream;

		public FilterSubscription(Subscriber<T> fromupStream, Predicate<T> predicate) {
			this.actual = fromupStream;
			this.predicate = predicate;
		}

		@Override
		public void onSubscribe(Subscription<T> sn) {
			this.subscriptionFromUpstream = sn;
			actual.onSubscribe(sn);
		}

		@Override
		public void onNext(T t) {
			if(predicate.test(t)) {
				actual.onNext(t);
			}
		}

		@Override
		public void onComplete() {
			actual.onComplete();

		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void request(long n) {
			this.subscriptionFromUpstream.request(n);
		}

		@Override
		public void cancel() {
			this.subscriptionFromUpstream.cancel();
		}

	}
}
