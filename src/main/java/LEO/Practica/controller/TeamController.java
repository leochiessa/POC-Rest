package LEO.Practica.controller;

import LEO.Practica.model.Player;
import LEO.Practica.model.Team;
import LEO.Practica.model.TeamDTO;
import LEO.Practica.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamRepository tr;

    private ModelMapper mm = new ModelMapper();

    @PostMapping("")
    public void addTeam(@RequestBody @NotNull Team t) {
        tr.save(t);
    }

    @PostMapping("/{id}/update")
    public void updateTeam(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Team t) {
        Team te = tr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Team " + id + " not found."));
        te.setName(t.getName());
        tr.save(te);
    }

    @PostMapping("/{id}/delete")
    public void deleteTeam(@PathVariable final @NotNull Integer id) {
        tr.deleteById(id);
    }

    @GetMapping("")
    public List<TeamDTO> getAll() {
        List<TeamDTO> teams = new ArrayList<>();
        for (Team t : tr.findAll()) {
            TeamDTO tdto = mm.map(t, TeamDTO.class);
            teams.add(tdto);
        }
        return teams;
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable final @NotNull Integer id) {
        return tr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Team " + id + " not found."));
    }

    @PostMapping("/{id}/addPlayer")
    public void addPlayer(@PathVariable final @NotNull Integer id, @RequestBody @NotNull Player p) {
        Team t = tr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Team " + id + " not found."));
        t.getPlayers().add(p);
        p.setTeam(t);
        tr.save(t);
    }
}