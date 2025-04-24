package in.amjadIt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPwd {
	
	private String email;
	private String oldPwd;
	private String newPwd;
	private String cnfPwd;

}
