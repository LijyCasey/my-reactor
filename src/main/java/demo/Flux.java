package demo;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.Function;

/**
 * 基础类Flux，它是一个Publisher
 * @author Casey
 *
 * @param <T>
 */
public abstract class Flux<T> implements Publisher<T> {

	public abstract void subscribe(Subscriber<? super T> subscriber) ;
	
	public <V> Flux<V> map(Function<? super T,? extends V> mapper){
		return new FluxMap<>(this,mapper);
	}

	public static <T> Flux<T> just(T... data){
		return new FluxArray<T>(data);
	}
	
}
