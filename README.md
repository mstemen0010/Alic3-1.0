# Alic3-1.0
Polymorphic Browser


# Al1c3-V3
Third variation on the Java FX Alice polymorphic browser

What is a polymorphic browswer one may ask? Simple answer, a browswer that is able to change its underlying engine (or version
of HttpClient) on the fly. This is done through a combination an enumations that contains values that are ultimately class names
and reflection. this results in typed/type safe driven reflection at runtime (very polymorphic indeed) and may represent a new 
type of entity in OOD. Alice was made to help explore a security threat cause by various versions of the Apaches HttpClient
and to be able to explore and widen the exploit it can cause on the fly. The user simply can change the version of HTTPClient 
(each version had various different and subtle nuanances to them) by selecting from a drop down. In theory this design could be 
used in a variety of ways outside of this application especically when concidering the fact that Interfaces can have enumerations 
on them as well (a fact little know in the Java world it seems) So an Java program could implement different interfaces on the fly 
through reflection as well and those interfaces could expose the running program to other classes (typed by way of the enumeration)
which could be spun up and loaded at runtime as well. This program used "proxy" classes to serve as an adaptor to other classes. 

