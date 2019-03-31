package senai.fatesg.spark.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Telefone {
	
	protected Long id;
	protected String foneComercial; 
	protected String foneResidensial; 
	protected String foneCelular; 
	
	
}

