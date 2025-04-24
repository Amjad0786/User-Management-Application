package in.amjadIt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
	private String name;
	private Long phno;
	private String email;
	
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;

}
