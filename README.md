# com.fc_prc.projectmapping
a plug-in for eclipse 3.5+
#Tartget:
      Create a project mapping xml for your project that will be published on Tomcat Server.
#Causes:
      Eclipse Configuration:
           Eclipse uses custom location (does not modify Tomcat installation).
      Lead to:
           A 404 error happens when visiting localhost:8080.You can't use the manager-gui offered by Tomcat to manage projects.           
           You can still publish web projects on Tomcat Server and then access the web pages.
           That is because eclipse use a different configuration to startup Tomcat Server.This configuration doesn't have a manager-gui.
           Manager-gui can be used only when startup Tomcat from its installation.It also means the accessing to localhost:8080.
           Contrast startup server from Tomcat installation with startup from eclipse's console.
      What I want:
           Use a different location to publish projects rather than Tomcat inalltation.
           Simultaneously startup server from Tomcat installation so the manager-gui can be used.
#Solve:
      Ordinary way: Create a mpping.xml in "D:\apache-tomcat-7.0.53\conf\Catalina\localhost\"(an example way) for the project.
                    Its content are as follows:
      
                              <?xml version="1.0" encoding="UTF-8"?>
                              <Context path="/web_project_name" docBase="publish_directory" debug="0" privileged="true"></Context>
           
                    One example:
      
                              <?xml version="1.0" encoding="UTF-8"?>
                              <Context path="/WebDemo01" docBase="D:/testserverlocation/webapps/shopDemo2" debug="0" privileged="true"></Context>
      
      My way:Use a plug-in to do all the work.
#Introduction:
      1、Confuration in Window-->Preference-->TomcatMapping
         One example:
                   Tomcat Inatallation Directory:D:\apache-tomcat-7.0.53
                   Server Location:D:\testserverlocation
                   Deploy Path:webapps
      2、Select a web project and use right-mouse menu Create Tomcat Mapping
      3、Startup Tomcat server form its installaion directory.Recommend using Tomcat Manager Plugin.
         It can startup Tomcat from its installation directory without opening the directory.

