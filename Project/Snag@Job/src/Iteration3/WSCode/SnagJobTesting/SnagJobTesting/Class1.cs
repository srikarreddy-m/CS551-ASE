
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NUnit.Framework;
using System.Net;
using System.IO;

namespace Lab7TestWS
{
    [TestFixture]
    public class Service
    {
        [Test]
        public void InsertJobDetails()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/insertJobDetails/'java','dev','developer','hyd','hyd',500019,40,'01-06-2015','Technical','Software','srikar'");
            req.Method ="GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream,Encoding.UTF8);
                    Assert.AreEqual("{\"msg\":\"Inserted data\"}",reader.ReadToEnd().ToString());
                }
            }
            
        }
        [Test]
        public void RetrieveJobDetailsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/retrieveJobDetails/srikar,,C");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("[{\"Category\":\"Technical\",\"CreatedBy\":\"srikar\",\"CreationDate\":\"/Date(1426111020000-0500)/\",\"Id\":204,\"JobAddress\":\"posadd\",\"JobCity\":\"1daksfl\",\"JobDate\":\"/Date(1439096400000-0500)/\",\"JobPay\":778,\"JobZipCode\":7878,\"LastUpdateDate\":\"/Date(1426111020000-0500)/\",\"LongDescription\":\"postdate\",\"ShortDescription\":\"shrt1\",\"SkillSet\":\"sikkll1\",\"StatusCode\":\"C\",\"SubCategory\":\"Software\"}]", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void InsertloginDetailsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/insertUserDetails/'hi11127',%20'hi2327','testttt',%20'testttt',1234,%20'testttt','testttt',%20'testttt',64111");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("{\"msg\":\"Inserted data\"}", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void RetrieveloginDetailsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/retrievelogin/dani");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("{\"address\":\"ks\",\"city\":\"mo\",\"email\":\"dani\",\"firstName\":\"dani\",\"lastName\":\"dani\",\"password\":\"dani\",\"phno\":789798797,\"state\":\"ks\",\"zipCode\":8980}", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void retrieveIndividualJobDetailTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/retrieveIndividualJobDetail/204,srikar");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("{\"Category\":\"Technical\",\"CreatedBy\":\"srikar\",\"CreationDate\":\"/Date(1426111020000-0500)/\",\"Id\":204,\"JobAddress\":\"posadd\",\"JobCity\":\"1daksfl\",\"JobDate\":\"/Date(1439096400000-0500)/\",\"JobPay\":778,\"JobZipCode\":7878,\"LastUpdateDate\":\"/Date(1428864293057-0500)/\",\"LongDescription\":\"postdate\",\"ShortDescription\":\"shrt1\",\"SkillSet\":\"sikkll1\",\"StatusCode\":\"C\",\"SubCategory\":\"Software\"}", reader.ReadToEnd().ToString());
                }
            }

        }
         [Test]
        public void jobSearchDetailsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/jobSearchDetails/Technical,Software,1daksfl,7878,778");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("[{\"Category\":\"Technical\",\"CreatedBy\":\"srikar\",\"CreationDate\":\"/Date(1426111020000-0500)/\",\"Id\":204,\"JobAddress\":\"posadd\",\"JobCity\":\"1daksfl\",\"JobDate\":\"/Date(1439096400000-0500)/\",\"JobPay\":778,\"JobZipCode\":7878,\"LastUpdateDate\":\"/Date(1428864293057-0500)/\",\"LongDescription\":\"postdate\",\"ShortDescription\":\"shrt1\",\"SkillSet\":\"sikkll1\",\"StatusCode\":\"C\",\"SubCategory\":\"Software\"}]", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
         public void updatePostJobDetailsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/updatePostJobDetails/210,java4,dev1,developer1,hyd1,hyd,500019,40,P");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("{\"msg\":\"updated data\"}", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void jobApplicationTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/jobApplication/UPDATE,101,srikar,A");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("{\"msg\":\"updated data\"}", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void individualJobApplicationsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/individualJobApplications/SINGLE,101,srikar");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("[{\"ApplicationID\":1,\"CreationDate\":\"/Date(1428801517363-0500)\/\",\"Email\":\"srikar\",\"FirstName\":\"sri\",\"JobID\":101,\"LastName\":\"dani\",\"MobileNo\":6688,\"SkillSet\":\"oracle\"}]", reader.ReadToEnd().ToString());
                }
            }

        }
        [Test]
        public void retrieveQuestionsTest()
        {
            WebRequest req = WebRequest.Create(@"http://localhost:60838/Service1.svc/retrieveQuestions/Technical,Software");
            req.Method = "GET";
            HttpWebResponse resp = req.GetResponse() as HttpWebResponse;
            if (resp.StatusCode == HttpStatusCode.OK)
            {
                using (Stream respStream = resp.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(respStream, Encoding.UTF8);
                    Assert.AreEqual("[{\"Category\":\"Technical\",\"CorrectChoice\":3,\"Id\":1,\"Option1\":\"Analysis\",\"Option2\":\"Design\",\"Option3\":\"Problem Identification\",\"Option4\":\"Implementation\",\"Question\":\"Which is the first step in the software development life cycle ?\",\"SubCategory\":\"Software\"},{\"Category\":\"Technical\",\"CorrectChoice\":4,\"Id\":2,\"Option1\":\"Programmers\",\"Option2\":\"Project managers\",\"Option3\":\"Technical writers\",\"Option4\":\" Database administrators\",\"Question\":\"Who designs and implement database structures?\",\"SubCategory\":\"Software\"},{\"Category\":\"Technical\",\"CorrectChoice\":2,\"Id\":3,\"Option1\":\" creating program code.\",\"Option2\":\" finding and correcting errors in the program code.\",\"Option3\":\"identifying the task to be computerized.\",\"Option4\":\"creating the algorithm.\",\"Question\":\"Debugging is:\",\"SubCategory\":\"Software\"}]", reader.ReadToEnd().ToString());
                }
            }

        }
    }
}

