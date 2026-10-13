package com.kitakokaken.CMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CmsApplication {

	public static void main(String[] args) {//実行は８，９行目の間にあるRunを押して
		SpringApplication.run(CmsApplication.class, args);//http:localhost:8080/"設定したやつ"
	}

}
