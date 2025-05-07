package cat.itacademy.s04.t02.n03.services;

import cat.itacademy.s04.t02.n03.model.Fruit;
import cat.itacademy.s04.t02.n03.repository.FruitsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class FruitsServices {
    @Autowired
    private FruitsRepository fruitsRepository;

    public void saveFruit(String name, double kg){
        Fruit n = new Fruit();
        n.setId(String.valueOf(fruitsRepository.count()+1));
        n.setName(name);
        n.setKg(kg);
        fruitsRepository.save(n);
    }

    public Iterable<Fruit> getAllFruits() throws SQLException {
       return fruitsRepository.findAll();
    }

    public boolean deleteFruitById(String id){
        if (fruitsRepository.existsById(id)) {
            fruitsRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Fruit getFruitById(String id){
        Optional<Fruit> optFruit = fruitsRepository.findById(id);
        return optFruit.orElse(null);
    }

    public boolean updateFruit(String id, String name, Double kg){
        Optional<Fruit> optFruit = fruitsRepository.findById(id);
        if(optFruit.isPresent()){
            if(name!=null) {
                optFruit.get().setName(name);
            }
            if(kg!=null && !kg.isNaN()) {
                optFruit.get().setKg(kg);
            }
            ObjectMapper mapper = new ObjectMapper();
            fruitsRepository.save(optFruit.get());
            return true;
        }else{
            return false;
        }
    }

}
