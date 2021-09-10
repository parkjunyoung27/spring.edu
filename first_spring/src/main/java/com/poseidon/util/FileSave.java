package com.poseidon.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

//파일 저장하는 클래스
//그럼 이거 객체화 되어야 하니까 추가해줄것은?
//component 붙여야 class의 소문자로 저장됨
@Component
public class FileSave {
	//String에서 제공하는 fileCopyUtil을 이용해서 파일 저장하기
	public String save(String realPath, MultipartFile files) throws IOException {
		File file = new File(realPath);
		
		//파일이 없으면 경로만들기
		if(!file.exists()) {//file의 경로가 실제로 존재하는지 물어보는것? jsp때 경로 없어졌을 때 대비
			file.mkdir(); // 디렉토리 하나 
			file.mkdirs(); // 자기 경로 갈때까지 다 만들어주는것 // 부모 폴더까지 생성하기
		}
		
		//UUID(절대 겹칩수 없는) 유니크 파일이름을 절대 겹치지 않게 만들어줌 
		String fileName = UUID.randomUUID().toString();
		fileName = fileName + "_" + files.getOriginalFilename();
				//파일 저장 예시 : f4f2-fefe-fwae-afee_a.png
		System.out.println("만들어진 fileName : " + fileName);
		
		//파일 올리기
		//부모 추상 경로 이름과 자식 경로 이름 문자열에서 새 File 인스턴스를 만듭니다.
		//부모가 null이면 지정된 자식 경로 이름 문자열에서 단일 인수 File 생성자를 호출하는 것처럼 새 File 인스턴스가 생성됩니다.
		//그렇지 않으면 상위 추상 경로 이름을 사용하여 디렉토리를 나타내고 하위 경로 이름 문자열을 사용하여 디렉토리 또는 파일을 나타냅니다. 
		
		file = new File(file, fileName);
		//주어진 바이트 배열의 내용을 주어진 출력 파일에 복사합니다.
		FileCopyUtils.copy(files.getBytes(), file);
		
		return fileName;
	}
	
	//2가지 방법 다 가능
	
	//multipart에서 제공하는 업로드로 저장하기
	public String save2(String realPath, MultipartFile files) throws IllegalStateException, IOException {
		File file = new File(realPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String fileName = UUID.randomUUID().toString();
		fileName = fileName + "_" + files.getOriginalFilename();
		file = new File(file, fileName);
		files.transferTo(file);
		
		return fileName;
	}
	
	
}
















