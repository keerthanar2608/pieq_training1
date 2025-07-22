# pieq_training1


#Training_day1 (22/07/2025)
Object Oreinted Programming :

1.Classes
2.Objects
3.Encapsulation
4.Polymorphism
5.Inheritance
6.Abstraction

*******************************************************************************************************************************************************************


1.Classes:
    Classes are the user defined data type and it is a template or a blueprint of objects.
    It provides variables and methods that are common to all objects in a particular file or program
For example : Let us take student as an example.
The student class can contains name , rollno , age . This is the template or blueprint of the student class

_____________________________________________________________________________________________________________________________________________________________

2.Object:

   Object are the instances of classes. The object is used to give actual value for the variables that is declared in class 
For the same example , The arguments passed to student class such as student("keerthu" , 12 , 20) 
=> st1 = new  student("keerthu" , 12 , 20) 

Here , the new keyword is the keyword which is used to create a memory and allocate those values .
once the object is created , the memory will not be allocated . the memory will be created only when new keyword is declared.

_________________________________________________________________________________________________________________________________________________________________

3.Encapsulation :

   The Encapsulation is wrapping or joining the data members and functions together.
   It is the one which is used for secured purpose . it can be done by declaring a class as private.
   Once a class is declared as private , then it cannot be accessed by any other classes . 
   But in case to access that class , encapsulation provides getter and setter methods to securely use.

 _______________________________________________________________________________________________________________________________________________________________
  
4.Polymorphism :
  =>The polymorphism means , it can be in many form (multiple forms).
  =>It enables the same method name to behave differently depending on the context.
  => The polymorphism is of two types:
          1.CompileTime polymorphism.
                      =>Method overloading
          2.Runtime polymorphism.
                      =>Method overriding

_________________________________________________________________________________________________________________________________________________________________                      

5.Inheritance:
    The inheritance means , one class can inherit the property of another class which is nothing but the child class can access the property of parent class 
    It mainly produces coe reusability.
    => Types of Inheritance:
           =>Single inheritance
           =>Multilevel inheritance
           =>Multiple inheritance
           =>Hybrid inheritance
           =>Heirarichal inheritance

____________________________________________________________________________________________________________________________________________________________________

6.Abstraction:
  The Abstraction means hiding the unnecessary or unrelevant details and providing only relevant details.
  It reduces complexity of the program since it hide the irrelevant info
  It can be acheived using abstarct classes and interface
  Abstarct class :
    > Partial implementation is done 
    > it may contains abstarct methods or concrete methods
  Inheritance :
    > 100% percentage abstarction is done
    > uses implements keyword
    > cannot be instantiated
    _________________________________________________________________________________________________________________________________________________________________
