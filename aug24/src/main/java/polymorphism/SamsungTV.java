package polymorphism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("tv")
public class SamsungTV implements TV {
	
	//스피커 자동으로 연결
	@Autowired
	@Qualifier("apple")
	private Speaker speaker;	
	private int price; 

	public Speaker getSpeaker() {
		return speaker;
	}
	
	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public SamsungTV() {
		//파라미터가 없는 생성자. // 파라미터가 없는 생성자 //젤 중요
	}
	
	public SamsungTV(Speaker speaker, int price) {
		this.speaker = speaker;
		this.price = price;
	}
	

	@Override
	public void powerOn() {
		System.out.println("SamsungTV 전원 켠다.");
		System.out.println("가격 = " + price);
	}

	@Override
	public void powerOff() {
		System.out.println("SamsungTV 전원 끈다.");
	}

	@Override
	public void volumeUp() {
		System.out.println("SamsungTV 소리 올린다.");
		//sonySpeaker = new SonySpeaker();
		speaker.volumeUp();
	}

	@Override
	public void volumeDown() {
		System.out.println("SamsungTV 소리 내린다.");
		//sonySpeaker = new SonySpeaker();
		speaker.volumeDown();
	}

}
