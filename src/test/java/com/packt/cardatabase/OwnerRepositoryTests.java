package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@DataJpaTest
public class OwnerRepositoryTests {
	@Autowired
	private OwnerRepository repository; 
	
	@Test
	void saveOwner() {
		repository.save(new Owner("Lucy","Smith"));
		//assertThat() 동일여부체크메소드, isPresent() true, false반환합니다
		//저장된 데이터에서 찾는 이름과 동일하다면 true가 반환됩니다.
		//isTrue() 메소드는 앞서 반환 값이 true이면 true을 반환하고, 
		//null이거나 false이면 false를 반환합니다.
		assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();
	}
	
	@Test
	void deleteOwners() {
		repository.save(new Owner("Lisa","Morrison"));
		repository.deleteAll();
		assertThat(repository.count()).isEqualTo(0);
	}
	

}
