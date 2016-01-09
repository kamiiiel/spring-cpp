So you want to create C++ applications that are manageable, extensible and configurable?

This project is about bringing Dependency Injection into the C++ world in **the simplest** possible and least obtrusive way.

C++ is missing some features that are present in Java, for example reflection. So it is not possible to get in C++ the same as you have in Java, but you can get very close with... code generation.

The key idea is to take a set of POCO's (plain old c++ objects) together with an xml configuration file and automatically generate everything else that is needed (a Bean Factory). C++ objects can be really plain, there is no need to implement special interfaces, no need to add additional classes, wrappers, etc.

Code generator is written in **java**, so it runs on every platform. It generates C++ code of course.

This framework is currently in active development. Would you like to join? Questions or suggestions? [PLEASE WRITE!](http://groups.google.com/group/spring-cpp/post)


WhyThisFramework

For convenience in windows there is .exe launcher provided as well.