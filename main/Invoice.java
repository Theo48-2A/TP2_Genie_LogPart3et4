import java.util.*;

public class Invoice {

  private Customer customer;
  //private String customer;  //Le nom du client
  private List<Performance> performances;   // La liste des performances pour ce client

  public Invoice(Customer customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }
  
  public Customer getCustomer(){
     return this.customer;
  }
  
  public List<Performance> getPerformances(){
     return this.performances;
  }
  
  
}
