package LEO.Practica.controller;

import LEO.Practica.model.Player;
import LEO.Practica.model.PlayerDTO;
import LEO.Practica.model.Team;
import LEO.Practica.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerRepository pr;

    private ModelMapper mm = new ModelMapper();

    @PostMapping("")
    public void addPlayer(@RequestBody final @NotNull Player p) {
        pr.save(p);
    }

    @PostMapping("/{id}/update")
    public void updatePlayer(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Player p) {
        Player pl = pr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Player " + id + " not found."));
        pl.setName(p.getName());
        pl.setSurname(p.getSurname());
        pr.save(pl);
    }

    @PostMapping("/{id}/delete")
    public void deletePlayer(@PathVariable final @NotNull Integer id) {
        pr.deleteById(id);
    }

    @GetMapping("")
    public List<Player> getAll() {
        return pr.findAll();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable final @NotNull Integer id) {
        return pr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Player " + id + " not found."));
    }

    @GetMapping("/{id}/getDTO")
    public PlayerDTO getPlayerDTO(@PathVariable final @NotNull Integer id) {
        Player p = pr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Player " + id + " not found."));
        return mm.map(p, PlayerDTO.class);
    }

    @GetMapping("/playersByTeam")
    public Map<Team, Long> getPlayersByTeam() {
        return pr.findAll().stream().collect(Collectors.groupingBy(Player::getTeam, Collectors.counting()));
    }
}