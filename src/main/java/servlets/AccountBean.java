package servlets;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "AccountBean")
@ManagedBean
@SessionScoped
public class AccountBean implements Serializable{

}
