package demo;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class FluxArray<T> extends Flux<T> {
	private T[] array;

	public FluxArray(T[] data) {
		this.array = data;
	}

	public void subscribe(Subscriber<? super T> subscriber) {
		subscriber.onSubscribe(new ArraySubscription<>(subscriber, array));
	}

	static class ArraySubscription<T> implements Subscription {
		final Subscriber<? super T> actual;
		final T[] array;
		int index;
		boolean canceled;

		public ArraySubscription(Subscriber<? super T> actual, T[] array) {
			this.actual = actual;
			this.array = array;
		}

		@Override
		public void request(long n) {
			if (canceled) {
				return;
			}
			long length = array.length;
			for (int i = 0; i < n && index < length; i++) {
				actual.onNext(array[index++]);
			}
			if(index == length) {
				actual.onComplete();
			}
		}

		@Override
		public void cancel() {
			this.canceled = true;
		}

	}
}
