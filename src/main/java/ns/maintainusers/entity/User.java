package ns.maintainusers.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NSUSER")
public class User {
	// Lombok generates getters, setters, toString, equals and hashcode
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private Long userId;
	@NotNull
	private String userName;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	@NotNull
	private char userStatus;
	private String department;

}
