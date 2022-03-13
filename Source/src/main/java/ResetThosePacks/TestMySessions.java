package ResetThosePacks;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Table(name="sessions_v2_3")
public class TestMySessions {
	@Id
	@GeneratedValue
	public int id;
	public int idG;
	public String NomG;
	public int idP;
	public Date DateS;
}
