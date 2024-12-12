package ns.maintainusers.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.Data;

/**
 * https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/
 */

@Data
@Entity
@Table(name = "NSUSER")
public class User {
	// Lombok generates getters, setters, toString, equals and hashcode
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;

	public User() {}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	// @Override
	// public String toString() {
	// 	return "Student [id=" + id + ", name=" + name + "]";
	// }

}
