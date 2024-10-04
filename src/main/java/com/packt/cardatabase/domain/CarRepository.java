package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car, Long> {
	
//	@Query("select c from Car c where c.brand like %?1")
//	List<Car> findByBrandEndsWith(String brand);
	
	List<Car> findByColor(@Param("color") String color);
	
	List<Car> findByBrand(@Param("brand") String brand);

}
