package umk.zychu.inzynierka.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.Test;



public interface TestRepository extends BaseRepository<Test, Long> {
		
	public final static String WYPAS_FIND = "SELECT t FROM Test t WHERE t.txt = :value";
	
	@Query(WYPAS_FIND)
    public Test findBySmth(@Param("value") String text);

}
