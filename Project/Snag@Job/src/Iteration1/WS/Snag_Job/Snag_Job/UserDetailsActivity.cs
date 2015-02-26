using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Snag_Job
{
    public class UserDetailsActivity
    {
        String email;
        String password;
        String firstName;
        String lastName;
        long phno;
        String address;
        String city;
        String state;
        long zipCode;
        public String getEmail()
        {
            return email;
        }
        public void setEmail(String email)
        {
            this.email = email;
        }
        public String getPassword()
        {
            return password;
        }
        public void setPassword(String password)
        {
            this.password = password;
        }
        public String getFirstName()
        {
            return firstName;
        }
        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }
        public String getLastName()
        {
            return lastName;
        }
        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }
        public long getPhno()
        {
            return phno;
        }
        public void setPhno(long phno)
        {
            this.phno = phno;
        }
        public String getAddress()
        {
            return address;
        }
        public void setAddress(String address)
        {
            this.address = address;
        }
        public String getCity()
        {
            return city;
        }
        public void setCity(String city)
        {
            this.city = city;
        }
        public String getState()
        {
            return state;
        }
        public void setState(String state)
        {
            this.state = state;
        }
        public long getZipCode()
        {
            return zipCode;
        }
        public void setZipCode(long zipCode)
        {
            this.zipCode = zipCode;
        }
    
	
    }
}