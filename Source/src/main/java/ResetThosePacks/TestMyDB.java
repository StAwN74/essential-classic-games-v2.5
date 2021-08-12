package ResetThosePacks;

import java.util.Date;

//import java.math.BigDecimal;

//import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="players")
public class TestMyDB {
		
		@Id
		@GeneratedValue
		public int idP;
		public String NomP;
		private String PassP; // Put password as private
		public int BestScore; // NB: default 0 in table
		//public String NomG; // Set to NULL by Java here
		public int BestFlap;
		
		//getter
		public String getPassP() {
			return this.PassP;
		}
		
		//setter
		public void setPassP(String PassP) {
			this.PassP = PassP;
		}
		
//		public String toString() {
//			//return String.format("%d - %s", idC, NomC);
//			if (this.DoneT) {
//				String strf = this.idT +" - " +this.NomT +" - Effectué";
//				return strf;
//			}
//			else {
//				String strf = this.idT +" - " +this.NomT +" - Pas encore effectué";
//				return strf;
//			}
			//return String.format("%d - %s", idC, NomC);
		//}
		
		//public Date date_naissance;
	    // you can also use getters and setters
}
