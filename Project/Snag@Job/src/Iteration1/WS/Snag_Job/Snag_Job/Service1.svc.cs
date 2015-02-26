using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data.SqlClient;
using System.Data;

namespace Snag_Job
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    public class Service1 : IService1
    {
          [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "data/{value}")]
        public string GetData(String value)
        {
            Console.WriteLine("Method is executng");
            return string.Format("You entered: {0}", value);

        }


        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "insertUserDetails/{userDetails}")]
          public String insertUserDetails(String userDetails)
          {
              String str = "";
            string cs = System.Configuration.ConfigurationManager.ConnectionStrings["connectdb"].ConnectionString;
            System.Data.SqlClient.SqlConnection con = new System.Data.SqlClient.SqlConnection(cs);
            System.Data.SqlClient.SqlCommand cmd = new System.Data.SqlClient.SqlCommand();
            //SqlDataReader rdr = null;
          
       try{
           con.Open();
          
           cmd.CommandType = System.Data.CommandType.Text;
           cmd.CommandText = "INSERT UserDetails (email,password,firstName,lastName,phno,address,city,state,zipCode) VALUES (" + userDetails + ")";
           /*cmd.CommandText = "INSERT UserDetails (email,password,firstName,lastName,phno,address,city,state,zipCode) VALUES ('" + userDetails.getEmail() + "'," +
                                                                                                                              "'" + userDetails.getPassword() + "'," +
                                                                                                                              "'" + userDetails.getFirstName() + "'," +
                                                                                                                              "'" + userDetails.getLastName() + "'," +
                                                                                                                              +userDetails.getPhno() + "," +
                                                                                                                              "'" + userDetails.getAddress() + "'," +
                                                                                                                              "'" + userDetails.getCity() + "'," +
                                                                                                                              "'" + userDetails.getState() + "'," +
                                                                                                                              userDetails.getZipCode() + ")"; */
           cmd.Connection = con;

           
           cmd.ExecuteNonQuery();

           con.Close();
           str = str +"Inserted data";
       
       }
            catch(Exception e){

                str = str + "Exception in insert"+e;
            }

     
            
            return str;
          
        }
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json, UriTemplate = "retrieveLogin/{email}")]
        public String retrieveUserDetils(String email)
        {
            String str = "";
            String  email2;
            String pwd2;
            
            string cs = System.Configuration.ConfigurationManager.ConnectionStrings["connectdb"].ConnectionString;
            System.Data.SqlClient.SqlConnection con = new System.Data.SqlClient.SqlConnection(cs);
            System.Data.SqlClient.SqlCommand cmd = new System.Data.SqlClient.SqlCommand();
            UserDetailsActivity userDetailsActivity = new UserDetailsActivity();
            try
            {
                con.Open();
                str = str + "After Read Open";
                cmd.CommandType = System.Data.CommandType.Text;
                cmd.CommandText = "SELECT * from UserDetails where email ='" +email+ "'";
                cmd.Connection = con;
                SqlDataReader reader = cmd.ExecuteReader();
                str = str + "After Read Execute";
                while (reader.Read())
                {
                    str = str + "Inside Read Open loop";
                    /*userDetailsActivity.setEmail(reader.GetString(0));
                    userDetailsActivity.setPassword(reader.GetString(1));
                    userDetailsActivity.setFirstName(reader.GetString(2));
                    userDetailsActivity.setLastName(reader.GetString(3));
                    userDetailsActivity.setPhno(reader.GetInt32(4));
                    userDetailsActivity.setAddress(reader.GetString(5));
                    userDetailsActivity.setCity(reader.GetString(6));
                    userDetailsActivity.setState(reader.GetString(7));
                    userDetailsActivity.setZipCode(reader.GetInt32(8));
                    str = str + userDetailsActivity;*/
                    
                    email2 = reader.GetString(0);
                    pwd2 =reader.GetString(1);
                    str = str + email2 + pwd2;

                }


                reader.Close();


            }
            catch (Exception e)
            {
                str = str + "Exception in Read" + e;
            }


            return str;
          
        }
        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }
       }
}
