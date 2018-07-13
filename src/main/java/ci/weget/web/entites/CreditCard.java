package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;

@Embeddable
public class CreditCard {
	 @Column(name = "credit_card_number", length = 30) 
	 private String creditCardNumber; 
	 @Column(name = "credit_card_type")  
	 private String creditCardType; 
	 @Column(name = "credit_card_expiry_date", length = 5)  
	 private String creditCardExpDate;
	 
	   @PrePersist  
	   @PreUpdate  
	   private void validateData() { 
		   if (creditCardNumber==null || "".equals(creditCardNumber))
			   throw new ValidationException("Invalid number");  
		   if (creditCardType == null || "".equals(creditCardType))  
			   throw new ValidationException("Invalid type");   
		   if (creditCardExpDate==null|| "".equals(creditCardExpDate)) 
			   throw new ValidationException("Invalid expiry date"); 
		   }

}
