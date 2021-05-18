package demo;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

public class FluxMap<T, R> extends Flux<R> {
	private final Flux<? extends T> source;
	private final Function<? super T, ? extends R> mapper;

	public FluxMap(Flux<? extends T> source, Function<? super T, ? extends R> mapper) {
		this.source = source;
		this.mapper = mapper;
	}


	@Override
	public void subscribe(Subscriber<? super R> actual) {
		source.subscribe(new MapSubscriber<T,R>(actual, mapper));
	}
	
	// 对于上游Flux.just来说是一个订阅者，而对于下游订阅者来说是一个publisher
	static final class MapSubscriber<T,R> implements Subscription,Subscriber<T>{
		private final Subscriber<? super R> actual;
		private final Function<? super T,? extends R> mapper;
		
		boolean done;
		
		Subscription subscriptionOfUpstream;
		
		public MapSubscriber(Subscriber<? super R> actual,Function<? super T,? extends R> mapper) {
			this.actual = actual;
			this.mapper = mapper;
		}

		@Override
		public void request(long n) {
			this.subscriptionOfUpstream.request(n);
		}

		@Override
		public void cancel() {
			this.subscriptionOfUpstream.cancel();
		}

		@Override
		public void onSubscribe(Subscription subscription) {
			this.subscriptionOfUpstream = subscription;
			actual.onSubscribe(this);
		}

		@Override
		public void onNext(T item) {
			if(done) {
				return;
			}
			actual.onNext(mapper.apply(item));
		}

		@Override
		public void onError(Throwable throwable) {
			if(done) {
				return;
			}
			done = true;
			actual.onError(throwable);
		}

		@Override
		public void onComplete() {
			if(done) {
				return;
			}
			done = true;
			actual.onComplete();
		}
		
	}

}
