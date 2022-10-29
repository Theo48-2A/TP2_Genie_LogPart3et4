import java.util.*;

public class Invoice {

  private String customer;  //Le nom du client
  private List<Performance> performances;   // La liste des performances pour ce client

  public Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }
  
  public String getCustomer(){
     return this.customer;
  }
  
  public List<Performance> getPerformances(){
     return this.performances;
  }
  
  
}
