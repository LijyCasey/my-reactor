package demo.myown;

import java.util.concurrent.TimeUnit;

public class MonoTest {

	public static void main(String[] args) {
		Mono.just("testData").subscribe(new Subscriber<String>() {
			
			@Override
			public void onSubscribe(Subscription<String> sn) {
				sn.request(10000);
			}
			
			@Override
			public void onNext(String t) {
				System.out.println("on next:"+t);
			}
			
			@Override
			public void onError(Throwable e) {
				System.out.println("onError" +e.getMessage());
			}
			
			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		});
//		try {
//			TimeUnit.SECONDS.sleep(1);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
}
