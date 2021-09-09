package polymorphism;

import org.springframework.stereotype.Component;

@Component("sony")
public class SonySpeaker implements Speaker{
	public SonySpeaker() {
		System.out.println("Sony Speaker 생성");
	}
	
	@Override
	public void volumeUp() {
		System.out.println("Sony Speaker 볼륨 올리기");
	}

	@Override
	public void volumeDown() {
		System.out.println("Sony Speaker 볼륨 내리기");		
	}
	
}
