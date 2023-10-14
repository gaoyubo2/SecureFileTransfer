package gyb.securefiletransfer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("gyb.securefiletransfer.mapper")
public class SecureFileTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureFileTransferApplication.class, args);
	}
}
