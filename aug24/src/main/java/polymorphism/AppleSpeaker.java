package polymorphism;

import org.springframework.stereotype.Component;

@Component("apple")
public class AppleSpeaker implements Speaker {

	@Override
	public void volumeUp() {
		System.out.println("apple Speaker 볼륨 높이기");
	}

	@Override
	public void volumeDown() {
		System.out.println("apple Speaker 볼륨 내리기");
	}

}
