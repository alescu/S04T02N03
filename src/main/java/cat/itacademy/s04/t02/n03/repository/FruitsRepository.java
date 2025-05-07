package cat.itacademy.s04.t02.n03.repository;

import cat.itacademy.s04.t02.n03.model.Fruit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FruitsRepository extends MongoRepository<Fruit, String> {
    @Query("{name:'?0'}")
    Fruit findItemByName(String name);
}
