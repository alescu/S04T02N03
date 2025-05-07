package cat.itacademy.s04.t02.n03.controllers;

import cat.itacademy.s04.t02.n03.model.Fruit;
import cat.itacademy.s04.t02.n03.repository.FruitsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/fruits")
public class FruitsController {

    @Autowired
    private FruitsRepository fruitsRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addingFruit(@RequestParam String name, @RequestParam double kg){
        Fruit n = new Fruit();
        n.setId(String.valueOf(fruitsRepository.count()+1));
        n.setName(name);
        n.setKg(kg);
        fruitsRepository.save(n);
        return ResponseEntity.status(HttpStatus.OK).body("Fruit added successfully");
    }


    @GetMapping("/getAll")
    public ResponseEntity<String> getAll() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFruit = mapper.writeValueAsString(fruitsRepository.findAll());
            return ResponseEntity.ok(jsonFruit);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error showing data", e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id){
        if (fruitsRepository.existsById(id)) {
            fruitsRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Fruit with ID " + id + " deleted successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fruit with ID " + id + " not found.");
        }
    }

    @GetMapping("/getOne")
    public ResponseEntity<String> getOne(@RequestParam String id){
        try{
            Optional<Fruit> optFruit = fruitsRepository.findById(id);
            if(optFruit.isPresent()){
                ObjectMapper mapper = new ObjectMapper();
                String jsonFruit = mapper.writeValueAsString(optFruit.get());
                return ResponseEntity.ok(jsonFruit);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Fruit id not found");
            }

        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error showing data", e);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateFruit(@RequestParam String id, String name, Double kg){
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Fruit updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Fruit id not found");
        }
    }

}
