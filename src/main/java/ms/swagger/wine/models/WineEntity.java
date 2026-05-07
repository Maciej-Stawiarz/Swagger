package ms.swagger.wine.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Wine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WineEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	private Long age;
	private String name;
	private String taste;
}