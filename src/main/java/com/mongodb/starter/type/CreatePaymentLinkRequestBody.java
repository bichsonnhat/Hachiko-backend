package com.mongodb.starter.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
  private String productName;
  private String description;
  private String returnUrl;
  private int price;
  private String cancelUrl;
  
  // Explicit getter methods to ensure they are available during compilation
  public String getProductName() {
    return productName;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getReturnUrl() {
    return returnUrl;
  }
  
  public String getCancelUrl() {
    return cancelUrl;
  }
  
  public int getPrice() {
    return price;
  }
}