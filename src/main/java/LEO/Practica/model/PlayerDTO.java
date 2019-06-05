package LEO.Practica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class PlayerDTO {

    @NotNull
    private String name;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TeamDTO team;
}