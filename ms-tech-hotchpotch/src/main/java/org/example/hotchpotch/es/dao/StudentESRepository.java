package org.example.hotchpotch.es.dao;

import org.example.es.pojo.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentESRepository extends ElasticsearchRepository<Student, Integer> {

}
